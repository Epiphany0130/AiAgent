<template>
  <div class="home-container">
    <div class="hero-section">
      <div class="logo">
        <svg class="logo-svg" viewBox="0 0 200 200" xmlns="http://www.w3.org/2000/svg">
          <path d="M100 15C53.8 15 15 53.8 15 100s38.8 85 85 85 85-38.8 85-85S146.2 15 100 15zm0 150c-35.9 0-65-29.1-65-65s29.1-65 65-65 65 29.1 65 65-29.1 65-65 65z" fill="#00a8ff"/>
          <path d="M100 65c-19.3 0-35 15.7-35 35s15.7 35 35 35 35-15.7 35-35-15.7-35-35-35zm0 50c-8.3 0-15-6.7-15-15s6.7-15 15-15 15 6.7 15 15-6.7 15-15 15z" fill="#192a56"/>
          <path d="M160 100c0 33.1-26.9 60-60 60V60c33.1 0 60 26.9 60 60z" fill="#ff9f43" fill-opacity="0.3"/>
        </svg>
      </div>
      <h1 class="title">AI智能体平台</h1>
      <p class="subtitle">探索AI的无限可能，体验智能交互的未来</p>
    </div>
    
    <div class="app-cards">
      <div class="app-card" @click="navigateTo('love-app')">
        <div class="card-icon love-icon">
          <svg viewBox="0 0 24 24" xmlns="http://www.w3.org/2000/svg">
            <path d="M12 21.35l-1.45-1.32C5.4 15.36 2 12.28 2 8.5 2 5.42 4.42 3 7.5 3c1.74 0 3.41.81 4.5 2.09C13.09 3.81 14.76 3 16.5 3 19.58 3 22 5.42 22 8.5c0 3.78-3.4 6.86-8.55 11.54L12 21.35z" fill="#e84118"/>
          </svg>
        </div>
        <h2>AI恋爱大师</h2>
        <p>与AI谈一场甜蜜的恋爱，体验智能情感交流</p>
        <div class="card-glow"></div>
      </div>
      
      <div class="app-card" @click="navigateTo('manus-app')">
        <div class="card-icon manus-icon">
          <svg viewBox="0 0 24 24" xmlns="http://www.w3.org/2000/svg">
            <path d="M12 2C6.48 2 2 6.48 2 12s4.48 10 10 10 10-4.48 10-10S17.52 2 12 2zm0 18c-4.41 0-8-3.59-8-8s3.59-8 8-8 8 3.59 8 8-3.59 8-8 8zm-1-13h2v6h-2zm0 8h2v2h-2z" fill="#00a8ff"/>
          </svg>
        </div>
        <h2>AI超级智能体</h2>
        <p>体验最先进的AI智能体，解决复杂问题</p>
        <div class="card-glow"></div>
      </div>
    </div>
    
    <div class="footer">
      <p>© 2023 AI智能体平台 | 极氪科技</p>
    </div>
  </div>
</template>

<script>
export default {
  name: 'Home',
  methods: {
    navigateTo(route) {
      this.$router.push(`/${route}`);
    }
  }
}
</script>

<style scoped>
.home-container {
  min-height: 100vh;
  background: linear-gradient(135deg, #192a56 0%, #273c75 100%);
  color: white;
  padding: 20px;
  display: flex;
  flex-direction: column;
  align-items: center;
  justify-content: space-between;
}

.hero-section {
  text-align: center;
  margin-top: 50px;
  animation: fadeIn 1s ease-in-out;
}

.logo {
  width: 120px;
  height: 120px;
  margin: 0 auto 20px;
}

.logo-svg {
  width: 100%;
  height: 100%;
  filter: drop-shadow(0 0 10px rgba(0, 168, 255, 0.7));
  animation: pulse 2s infinite ease-in-out;
}

.title {
  font-size: 3.5rem;
  font-weight: 800;
  margin-bottom: 10px;
  background: linear-gradient(to right, #00a8ff, #ff9f43);
  -webkit-background-clip: text;
  -webkit-text-fill-color: transparent;
  text-shadow: 0 2px 10px rgba(0, 0, 0, 0.2);
}

.subtitle {
  font-size: 1.2rem;
  color: #dcdde1;
  max-width: 600px;
  margin: 0 auto;
}

.app-cards {
  display: flex;
  justify-content: center;
  gap: 30px;
  margin: 60px 0;
  flex-wrap: wrap;
}

.app-card {
  width: 300px;
  height: 350px;
  background: rgba(255, 255, 255, 0.1);
  backdrop-filter: blur(10px);
  border-radius: 20px;
  padding: 30px;
  display: flex;
  flex-direction: column;
  align-items: center;
  justify-content: center;
  text-align: center;
  cursor: pointer;
  position: relative;
  overflow: hidden;
  transition: all 0.4s cubic-bezier(0.175, 0.885, 0.32, 1.275);
  box-shadow: 0 10px 30px rgba(0, 0, 0, 0.3);
}

.app-card:hover {
  transform: translateY(-10px) scale(1.02);
  box-shadow: 0 15px 40px rgba(0, 0, 0, 0.4);
}

.app-card:hover .card-glow {
  opacity: 1;
}

.card-icon {
  width: 80px;
  height: 80px;
  margin-bottom: 20px;
  position: relative;
  z-index: 2;
}

.card-icon svg {
  width: 100%;
  height: 100%;
  filter: drop-shadow(0 0 5px rgba(255, 255, 255, 0.5));
}

.love-icon svg {
  animation: heartbeat 1.5s infinite ease-in-out;
}

.manus-icon svg {
  animation: rotate 10s infinite linear;
}

.app-card h2 {
  font-size: 1.8rem;
  margin-bottom: 15px;
  position: relative;
  z-index: 2;
}

.app-card p {
  color: #dcdde1;
  margin-bottom: 20px;
  position: relative;
  z-index: 2;
}

.card-glow {
  position: absolute;
  top: 0;
  left: 0;
  width: 100%;
  height: 100%;
  background: radial-gradient(circle at center, rgba(0, 168, 255, 0.3) 0%, transparent 70%);
  opacity: 0;
  transition: opacity 0.4s ease;
  pointer-events: none;
}

.footer {
  margin-top: auto;
  padding: 20px 0;
  text-align: center;
  color: rgba(255, 255, 255, 0.6);
  font-size: 0.9rem;
}

@keyframes fadeIn {
  from { opacity: 0; transform: translateY(20px); }
  to { opacity: 1; transform: translateY(0); }
}

@keyframes pulse {
  0% { transform: scale(1); }
  50% { transform: scale(1.05); }
  100% { transform: scale(1); }
}

@keyframes heartbeat {
  0% { transform: scale(1); }
  15% { transform: scale(1.2); }
  30% { transform: scale(1); }
  45% { transform: scale(1.1); }
  60% { transform: scale(1); }
  100% { transform: scale(1); }
}

@keyframes rotate {
  from { transform: rotate(0deg); }
  to { transform: rotate(360deg); }
}

@media (max-width: 768px) {
  .title {
    font-size: 2.5rem;
  }
  
  .app-cards {
    flex-direction: column;
    align-items: center;
  }
  
  .app-card {
    width: 90%;
    max-width: 300px;
  }
}
</style>