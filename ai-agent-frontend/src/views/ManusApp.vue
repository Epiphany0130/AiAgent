<template>
  <div class="chat-container">
    <div class="chat-header">
      <div class="back-button" @click="goBack">
        <svg viewBox="0 0 24 24" xmlns="http://www.w3.org/2000/svg">
          <path d="M20 11H7.83l5.59-5.59L12 4l-8 8 8 8 1.41-1.41L7.83 13H20v-2z" fill="currentColor"/>
        </svg>
      </div>
      <h1>AI超级智能体</h1>
    </div>
    
    <div class="chat-messages" ref="messagesContainer">
      <div v-for="(message, index) in messages" :key="index" 
           :class="['message', message.isUser ? 'user-message' : 'ai-message']">
        <div class="message-avatar">
          <svg v-if="!message.isUser" viewBox="0 0 24 24" xmlns="http://www.w3.org/2000/svg">
            <path d="M12 2C6.48 2 2 6.48 2 12s4.48 10 10 10 10-4.48 10-10S17.52 2 12 2zm0 18c-4.41 0-8-3.59-8-8s3.59-8 8-8 8 3.59 8 8-3.59 8-8 8zm-1-13h2v6h-2zm0 8h2v2h-2z" fill="#00a8ff"/>
          </svg>
          <svg v-else viewBox="0 0 24 24" xmlns="http://www.w3.org/2000/svg">
            <path d="M12 12c2.21 0 4-1.79 4-4s-1.79-4-4-4-4 1.79-4 4 1.79 4 4 4zm0 2c-2.67 0-8 1.34-8 4v2h16v-2c0-2.66-5.33-4-8-4z" fill="#192a56"/>
          </svg>
        </div>
        <div class="message-content">
          <div class="message-text" v-html="formatMessage(message.text)"></div>
          <div class="message-time">{{ formatTime(message.timestamp) }}</div>
        </div>
      </div>
      <div v-if="isLoading" class="loading-indicator">
        <div class="dot"></div>
        <div class="dot"></div>
        <div class="dot"></div>
      </div>
    </div>
    
    <div class="chat-input">
      <textarea 
        v-model="userInput" 
        placeholder="输入消息..." 
        @keydown.enter.prevent="sendMessage"
        :disabled="isLoading"
      ></textarea>
      <button @click="sendMessage" :disabled="isLoading || !userInput.trim()">
        <svg viewBox="0 0 24 24" xmlns="http://www.w3.org/2000/svg">
          <path d="M2.01 21L23 12 2.01 3 2 10l15 2-15 2z" fill="currentColor"/>
        </svg>
      </button>
    </div>
  </div>
</template>

<script>
import { ref, onMounted, nextTick, watch } from 'vue';
import { useRouter } from 'vue-router';
import { createManusChat } from '../utils/api';

export default {
  name: 'ManusApp',
  setup() {
    const router = useRouter();
    const messagesContainer = ref(null);
    const messages = ref([]);
    const userInput = ref('');
    const isLoading = ref(false);
    let eventSource = null;

    // 格式化消息文本（处理换行等）并按step分开
    const formatMessage = (text) => {
      return text.replace(/\n/g, '<br>');
    };
    
    // 将消息按step分割成多个消息
    const splitMessageBySteps = (text) => {
      // 查找所有的step标记（如"Step 1:"、"步骤1："等）
      const stepRegex = /(Step\s*\d+:|步骤\s*\d+：|第\s*\d+\s*步：?)/gi;
      const parts = text.split(stepRegex);
      
      // 如果没有找到step标记，直接返回原文本
      if (parts.length <= 1) {
        return [text];
      }
      
      const result = [];
      let currentStep = '';
      
      // 处理分割后的文本
      for (let i = 0; i < parts.length; i++) {
        if (i === 0 && parts[i].trim() !== '') {
          // 第一部分如果不为空，作为介绍部分
          result.push(parts[i].trim());
        } else if (stepRegex.test(parts[i])) {
          // 这是一个step标记，与下一个部分合并
          currentStep = parts[i];
          if (i + 1 < parts.length) {
            result.push(currentStep + parts[i + 1]);
            i++; // 跳过下一个部分，因为已经合并了
          }
        }
      }
      
      return result.filter(part => part.trim() !== '');
    };

    // 格式化时间
    const formatTime = (timestamp) => {
      const date = new Date(timestamp);
      return date.toLocaleTimeString([], { hour: '2-digit', minute: '2-digit' });
    };

    // 滚动到底部
    const scrollToBottom = async () => {
      await nextTick();
      if (messagesContainer.value) {
        messagesContainer.value.scrollTop = messagesContainer.value.scrollHeight;
      }
    };

    // 发送消息
    const sendMessage = () => {
      if (!userInput.value.trim() || isLoading.value) return;
      
      // 添加用户消息
      messages.value.push({
        text: userInput.value,
        isUser: true,
        timestamp: Date.now()
      });
      
      const messageText = userInput.value;
      userInput.value = '';
      scrollToBottom();
      
      // 设置加载状态
      isLoading.value = true;
      
      // 创建SSE连接
      if (eventSource) {
        eventSource.close();
      }
      
      eventSource = createManusChat(messageText);
      
      let aiResponse = '';
      let lastMessageIndex = -1;
      
      eventSource.onmessage = (event) => {
        if (event.data) {
          // 如果是第一条消息，创建新的AI消息
          if (aiResponse === '') {
            aiResponse = event.data;
            messages.value.push({
              text: aiResponse,
              isUser: false,
              timestamp: Date.now()
            });
            lastMessageIndex = messages.value.length - 1;
          } else {
            // 更新最后一条AI消息
            aiResponse += event.data;
            
            // 将完整响应按step分割
            const messageParts = splitMessageBySteps(aiResponse);
            
            // 删除之前添加的AI消息
            if (lastMessageIndex >= 0) {
              messages.value.splice(lastMessageIndex);
            }
            
            // 添加分割后的每个部分作为单独的消息
            messageParts.forEach((part, index) => {
              messages.value.push({
                text: part,
                isUser: false,
                timestamp: Date.now() + index
              });
            });
            
            lastMessageIndex = messages.value.length - messageParts.length;
          }
          scrollToBottom();
        }
      };
      
      eventSource.onerror = () => {
        isLoading.value = false;
        eventSource.close();
      };
      
      eventSource.onclose = () => {
        isLoading.value = false;
      };
    };

    // 返回主页
    const goBack = () => {
      if (eventSource) {
        eventSource.close();
      }
      router.push('/');
    };

    // 监听消息变化，自动滚动
    watch(messages, () => {
      scrollToBottom();
    });

    // 组件挂载时
    onMounted(() => {
      // 添加欢迎消息
      messages.value.push({
        text: "你好，我是AI超级智能体。我可以帮助你解决各种复杂问题，请告诉我你需要什么帮助？",
        isUser: false,
        timestamp: Date.now()
      });
      
      scrollToBottom();
    });

    return {
      messages,
      userInput,
      isLoading,
      messagesContainer,
      sendMessage,
      formatMessage,
      formatTime,
      goBack
    };
  }
}
</script>

<style scoped>
.chat-container {
  display: flex;
  flex-direction: column;
  height: 100vh;
  background-color: #f5f6fa;
}

.chat-header {
  display: flex;
  align-items: center;
  padding: 15px 20px;
  background: linear-gradient(135deg, #00a8ff 0%, #0097e6 100%);
  color: white;
  box-shadow: 0 2px 10px rgba(0, 0, 0, 0.1);
  position: relative;
  z-index: 10;
}

.back-button {
  width: 24px;
  height: 24px;
  margin-right: 15px;
  cursor: pointer;
  transition: transform 0.2s;
}

.back-button:hover {
  transform: translateX(-3px);
}

.chat-header h1 {
  flex: 1;
  font-size: 1.5rem;
  margin: 0;
}

.chat-messages {
  flex: 1;
  overflow-y: auto;
  padding: 20px;
  display: flex;
  flex-direction: column;
  gap: 5px;
}

.message {
  display: flex;
  max-width: 80%;
  animation: fadeIn 0.3s ease;
  margin-bottom: 10px;
}

.user-message {
  align-self: flex-end;
  flex-direction: row-reverse;
}

.ai-message {
  align-self: flex-start;
}

.message-avatar {
  width: 40px;
  height: 40px;
  border-radius: 50%;
  background-color: #f1f2f6;
  display: flex;
  align-items: center;
  justify-content: center;
  margin: 0 10px;
}

.message-avatar svg {
  width: 24px;
  height: 24px;
}

.message-content {
  background-color: white;
  padding: 12px 15px;
  border-radius: 18px;
  box-shadow: 0 2px 5px rgba(0, 0, 0, 0.05);
  position: relative;
}

.user-message .message-content {
  background-color: #192a56;
  color: white;
  border-top-right-radius: 4px;
}

.ai-message .message-content {
  background-color: white;
  border-top-left-radius: 4px;
}

.message-text {
  margin-bottom: 5px;
  line-height: 1.4;
}

.message-time {
  font-size: 0.7rem;
  opacity: 0.7;
  text-align: right;
}

.loading-indicator {
  display: flex;
  align-self: flex-start;
  margin: 10px 0 10px 50px;
  gap: 4px;
}

.dot {
  width: 8px;
  height: 8px;
  background-color: #00a8ff;
  border-radius: 50%;
  animation: bounce 1.4s infinite ease-in-out both;
}

.dot:nth-child(1) {
  animation-delay: -0.32s;
}

.dot:nth-child(2) {
  animation-delay: -0.16s;
}

.chat-input {
  display: flex;
  padding: 15px;
  background-color: white;
  border-top: 1px solid #eee;
  box-shadow: 0 -2px 10px rgba(0, 0, 0, 0.05);
}

.chat-input textarea {
  flex: 1;
  border: 1px solid #ddd;
  border-radius: 20px;
  padding: 12px 15px;
  resize: none;
  height: 50px;
  font-family: inherit;
  font-size: 1rem;
  outline: none;
  transition: border-color 0.3s;
}

.chat-input textarea:focus {
  border-color: #00a8ff;
}

.chat-input button {
  width: 50px;
  height: 50px;
  border-radius: 50%;
  background-color: #00a8ff;
  color: white;
  border: none;
  margin-left: 10px;
  cursor: pointer;
  display: flex;
  align-items: center;
  justify-content: center;
  transition: background-color 0.3s, transform 0.2s;
}

.chat-input button:hover:not(:disabled) {
  background-color: #0097e6;
  transform: scale(1.05);
}

.chat-input button:disabled {
  background-color: #ccc;
  cursor: not-allowed;
}

.chat-input button svg {
  width: 24px;
  height: 24px;
}

@keyframes fadeIn {
  from { opacity: 0; transform: translateY(10px); }
  to { opacity: 1; transform: translateY(0); }
}

@keyframes bounce {
  0%, 80%, 100% { transform: scale(0); }
  40% { transform: scale(1); }
}

@media (max-width: 768px) {
  .message {
    max-width: 90%;
  }
  
  .chat-header h1 {
    font-size: 1.2rem;
  }
}
</style>