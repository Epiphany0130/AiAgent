<template>
  <div class="chat-container">
    <!-- SEO优化：添加结构化数据 -->
    <div class="seo-hidden" itemscope itemtype="http://schema.org/SoftwareApplication">
      <meta itemprop="name" content="AI恋爱大师" />
      <meta itemprop="applicationCategory" content="社交" />
      <meta itemprop="operatingSystem" content="Web" />
      <meta itemprop="description" content="与AI谈一场甜蜜的恋爱，体验智能情感交流" />
      <div itemprop="aggregateRating" itemscope itemtype="http://schema.org/AggregateRating">
        <meta itemprop="ratingValue" content="4.8" />
        <meta itemprop="ratingCount" content="1024" />
      </div>
    </div>
    
    <div class="chat-header">
      <div class="back-button" @click="goBack">
        <svg viewBox="0 0 24 24" xmlns="http://www.w3.org/2000/svg" aria-label="返回按钮">
          <path d="M20 11H7.83l5.59-5.59L12 4l-8 8 8 8 1.41-1.41L7.83 13H20v-2z" fill="currentColor"/>
        </svg>
      </div>
      <h1>AI恋爱大师</h1>
      <div class="chat-id">会话ID: {{ chatId }}</div>
    </div>
    
    <div class="chat-messages" ref="messagesContainer">
      <div v-for="(message, index) in messages" :key="index" 
           :class="['message', message.isUser ? 'user-message' : 'ai-message']">
        <div class="message-avatar">
          <svg v-if="!message.isUser" viewBox="0 0 24 24" xmlns="http://www.w3.org/2000/svg">
            <path d="M12 2C6.48 2 2 6.48 2 12s4.48 10 10 10 10-4.48 10-10S17.52 2 12 2zm0 18c-4.41 0-8-3.59-8-8s3.59-8 8-8 8 3.59 8 8-3.59 8-8 8zm-1-13h2v6h-2zm0 8h2v2h-2z" fill="#e84118"/>
          </svg>
          <svg v-else viewBox="0 0 24 24" xmlns="http://www.w3.org/2000/svg">
            <path d="M12 12c2.21 0 4-1.79 4-4s-1.79-4-4-4-4 1.79-4 4 1.79 4 4 4zm0 2c-2.67 0-8 1.34-8 4v2h16v-2c0-2.66-5.33-4-8-4z" fill="#00a8ff"/>
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
import { createLoveAppChat } from '../utils/api';
import { marked } from 'marked';

export default {
  name: 'LoveApp',
  setup() {
    const router = useRouter();
    const messagesContainer = ref(null);
    const messages = ref([]);
    const userInput = ref('');
    const isLoading = ref(false);
    const chatId = ref('');
    let eventSource = null;

    // 生成随机会话ID
    const generateChatId = () => {
      return Math.random().toString(36).substring(2, 15) + Math.random().toString(36).substring(2, 15);
    };

    // 格式化消息文本（支持Markdown和处理换行）
    const formatMessage = (text) => {
      // 使用marked库解析Markdown
      return marked(text);
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
      
      eventSource = createLoveAppChat(messageText, chatId.value);
      
      let aiResponse = '';
      
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
          } else {
            // 更新最后一条AI消息
            aiResponse += event.data;
            messages.value[messages.value.length - 1].text = aiResponse;
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
      // 生成会话ID
      chatId.value = generateChatId();
      
      // 添加欢迎消息
      messages.value.push({
        text: "你好，我是AI恋爱大师。今天想聊些什么呢？",
        isUser: false,
        timestamp: Date.now()
      });
      
      scrollToBottom();
    });

    return {
      messages,
      userInput,
      isLoading,
      chatId,
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
/* 传统恋爱应用样式 */
@import url('https://fonts.googleapis.com/css2?family=Poppins:wght@300;400;500;600&family=Pacifico&display=swap');

.seo-hidden {
  display: none;
}

@keyframes heartbeat {
  0% { transform: scale(1); }
  15% { transform: scale(1.15); }
  30% { transform: scale(1); }
  45% { transform: scale(1.15); }
  60% { transform: scale(1); }
  100% { transform: scale(1); }
}

@keyframes floatingHearts {
  0% { transform: translateY(0) rotate(0deg); opacity: 0; }
  10% { opacity: 1; }
  90% { opacity: 0.5; }
  100% { transform: translateY(-100px) rotate(20deg); opacity: 0; }
}

@keyframes fadeIn {
  from { opacity: 0; transform: translateY(10px); }
  to { opacity: 1; transform: translateY(0); }
}

@keyframes bounce {
  0%, 80%, 100% { transform: scale(0); }
  40% { transform: scale(1); }
}

.chat-container {
  display: flex;
  flex-direction: column;
  height: 100vh;
  background-color: #fff;
  background-image: 
    radial-gradient(circle at 50% 50%, rgba(255, 192, 203, 0.1) 0%, transparent 70%);
  position: relative;
  font-family: 'Poppins', sans-serif;
  color: #333;
  overflow: hidden;
}

.chat-header {
  display: flex;
  align-items: center;
  padding: 15px 20px;
  background: linear-gradient(90deg, #ff758c 0%, #ff7eb3 100%);
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
  transition: all 0.3s;
  color: white;
}

.back-button:hover {
  transform: translateX(-3px);
}

.chat-header h1 {
  flex: 1;
  font-family: 'Pacifico', cursive;
  font-size: 1.5rem;
  margin: 0;
  position: relative;
  display: inline-block;
}

.chat-header h1::before {
  content: '❤';
  position: absolute;
  top: -2px;
  left: -25px;
  font-size: 1rem;
  animation: heartbeat 1.5s infinite;
}

.chat-header h1::after {
  content: 'Pro';
  position: absolute;
  top: -5px;
  right: -30px;
  font-size: 0.6rem;
  font-family: 'Poppins', sans-serif;
  background: rgba(255, 255, 255, 0.3);
  padding: 2px 5px;
  border-radius: 10px;
}

.chat-id {
  font-size: 0.8rem;
  opacity: 0.8;
  font-family: 'Poppins', sans-serif;
  background: rgba(255, 255, 255, 0.2);
  padding: 3px 8px;
  border-radius: 15px;
}

.chat-messages {
  flex: 1;
  overflow-y: auto;
  padding: 20px;
  display: flex;
  flex-direction: column;
  gap: 15px;
  position: relative;
  scrollbar-width: thin;
  scrollbar-color: #ff758c #f5f5f5;
  background-color: #f9f9f9;
  background-image: url("data:image/svg+xml,%3Csvg width='20' height='20' viewBox='0 0 20 20' xmlns='http://www.w3.org/2000/svg'%3E%3Cpath d='M10 15a5 5 0 1 1 0-10 5 5 0 0 1 0 10zm0-2a3 3 0 1 0 0-6 3 3 0 0 0 0 6z' fill='rgba(255,105,180,0.03)'/%3E%3C/svg%3E");
}

.chat-messages::-webkit-scrollbar {
  width: 6px;
}

.chat-messages::-webkit-scrollbar-track {
  background: #f5f5f5;
}

.chat-messages::-webkit-scrollbar-thumb {
  background-color: rgba(255, 117, 140, 0.5);
  border-radius: 10px;
}

.message {
  display: flex;
  max-width: 80%;
  animation: fadeIn 0.5s ease;
  position: relative;
  z-index: 5;
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
  background-color: #fff;
  display: flex;
  align-items: center;
  justify-content: center;
  margin: 0 10px;
  position: relative;
  overflow: hidden;
  border: 2px solid #ff758c;
  box-shadow: 0 2px 10px rgba(0, 0, 0, 0.1);
}

.user-message .message-avatar {
  border-color: #7986cb;
}

.ai-message .message-avatar {
  border-color: #ff758c;
}

.message-avatar svg {
  width: 24px;
  height: 24px;
}

.user-message .message-avatar svg {
  fill: #7986cb;
}

.ai-message .message-avatar svg {
  fill: #ff758c;
}

.message-content {
  background-color: #fff;
  padding: 12px 15px;
  border-radius: 18px;
  position: relative;
  border: none;
  box-shadow: 0 2px 10px rgba(0, 0, 0, 0.05);
  overflow: hidden;
}

.user-message .message-content {
  background-color: #e8eaf6;
  color: #333;
  border-top-right-radius: 4px;
}

.ai-message .message-content {
  background-color: #fff0f3;
  color: #333;
  border-top-left-radius: 4px;
}

.message-text {
  margin-bottom: 5px;
  line-height: 1.4;
  position: relative;
  overflow: hidden;
  font-family: 'Poppins', sans-serif;
}

/* Markdown样式 */
.message-text :deep(p) {
  margin: 0 0 10px 0;
}

.message-text :deep(h1), .message-text :deep(h2), .message-text :deep(h3),
.message-text :deep(h4), .message-text :deep(h5), .message-text :deep(h6) {
  margin-top: 10px;
  margin-bottom: 10px;
  font-weight: 600;
}

.message-text :deep(code) {
  background-color: rgba(0, 0, 0, 0.05);
  padding: 2px 4px;
  border-radius: 3px;
  font-family: monospace;
}

.message-text :deep(pre) {
  background-color: rgba(0, 0, 0, 0.05);
  padding: 10px;
  border-radius: 5px;
  overflow-x: auto;
}

.message-text :deep(blockquote) {
  border-left: 4px solid #ff758c;
  padding-left: 10px;
  margin-left: 0;
  color: #666;
}

.message-text :deep(a) {
  color: #ff758c;
  text-decoration: none;
}

.message-text :deep(a:hover) {
  text-decoration: underline;
}

.message-time {
  font-size: 0.7rem;
  opacity: 0.7;
  text-align: right;
  font-family: 'Poppins', sans-serif;
}

.ai-message .message-time::before {
  content: '❤';
  margin-right: 5px;
  color: #ff758c;
}

.user-message .message-time::before {
  content: '❤';
  margin-right: 5px;
  color: #7986cb;
}

.loading-indicator {
  display: flex;
  align-self: flex-start;
  margin: 10px 0 10px 50px;
  gap: 4px;
  background: rgba(255, 255, 255, 0.8);
  padding: 5px 10px;
  border-radius: 15px;
  box-shadow: 0 2px 5px rgba(0, 0, 0, 0.05);
}

.dot {
  width: 8px;
  height: 8px;
  background-color: #ff758c;
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
  border-top: 1px solid rgba(0, 0, 0, 0.05);
  box-shadow: 0 -2px 10px rgba(0, 0, 0, 0.05);
  position: relative;
  z-index: 10;
}

/* 添加浮动爱心背景 */
.chat-container::before {
  content: '';
  position: fixed;
  top: 0;
  left: 0;
  width: 100%;
  height: 100%;
  background-image: url("data:image/svg+xml,%3Csvg width='30' height='30' viewBox='0 0 30 30' xmlns='http://www.w3.org/2000/svg'%3E%3Cpath d='M15 25.5C15 25.5 4 19.5 4 11.5C4 8.5 6 6 9 6C11 6 13 7 15 9C17 7 19 6 21 6C24 6 26 8.5 26 11.5C26 19.5 15 25.5 15 25.5Z' fill='rgba(255,117,140,0.03)'/%3E%3C/svg%3E");
  pointer-events: none;
  z-index: 0;
}

.chat-input textarea {
  flex: 1;
  height: 40px;
  max-height: 120px;
  background-color: #f9f9f9;
  border: 1px solid rgba(0, 0, 0, 0.1);
  border-radius: 20px;
  color: #333;
  padding: 10px 15px;
  font-family: 'Poppins', sans-serif;
  resize: none;
  transition: all 0.3s;
}

.chat-input textarea:focus {
  outline: none;
  border-color: #ff758c;
  box-shadow: 0 0 10px rgba(255, 117, 140, 0.2);
}

.chat-input textarea::placeholder {
  color: rgba(0, 0, 0, 0.4);
}

.chat-input button {
  width: 40px;
  height: 40px;
  margin-left: 10px;
  background: linear-gradient(135deg, #ff758c 0%, #ff7eb3 100%);
  border: none;
  border-radius: 50%;
  color: white;
  display: flex;
  align-items: center;
  justify-content: center;
  cursor: pointer;
  transition: all 0.3s;
  box-shadow: 0 2px 10px rgba(255, 117, 140, 0.3);
}

.chat-input button:hover:not(:disabled) {
  transform: translateY(-2px);
  box-shadow: 0 5px 15px rgba(255, 117, 140, 0.4);
}

.chat-input button:disabled {
  opacity: 0.5;
  cursor: not-allowed;
}

.chat-input button svg {
  width: 20px;
  height: 20px;
}

/* 添加浮动爱心动画 */
@keyframes float {
  0% { transform: translateY(0) rotate(0deg); }
  50% { transform: translateY(-10px) rotate(5deg); }
  100% { transform: translateY(0) rotate(0deg); }
}

.chat-header h1::before {
  animation: heartbeat 1.5s infinite, float 3s ease-in-out infinite;
}

@media (max-width: 768px) {
  .chat-header h1 {
    font-size: 1.5rem;
  }
  
  .chat-id {
    font-size: 0.7rem;
  }
  
  .message {
    max-width: 90%;
  }
  
  .message-avatar {
    width: 35px;
    height: 35px;
  }
  
  .message-content {
    padding: 10px 12px;
  }
  
  .chat-input textarea {
    height: 36px;
  }
  
  .chat-input button {
    width: 36px;
    height: 36px;
  }
}
</style>