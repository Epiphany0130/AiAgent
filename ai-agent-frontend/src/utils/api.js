import axios from 'axios';

const API_URL = 'http://localhost:8123/api';

export const createLoveAppChat = (message, chatId) => {
  const eventSource = new EventSource(`${API_URL}/ai/love_app/chat/sse?message=${encodeURIComponent(message)}&chatId=${encodeURIComponent(chatId)}`);
  return eventSource;
};

export const createManusChat = (message) => {
  const eventSource = new EventSource(`${API_URL}/ai/manus/chat?message=${encodeURIComponent(message)}`);
  return eventSource;
};