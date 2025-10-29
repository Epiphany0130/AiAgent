<template>
  <div class="home-container">
    <div class="cyber-grid"></div>
    <div class="glitch-overlay"></div>
    
    <!-- SEO优化：添加结构化数据 -->
    <div class="seo-hidden" itemscope itemtype="http://schema.org/WebSite">
      <meta itemprop="url" content="https://ai-love.example.com/"/>
      <meta itemprop="name" content="AI恋爱平台"/>
      <meta itemprop="description" content="AI恋爱平台提供智能交互体验，让您探索AI的浪漫可能，体验智能交互的甜蜜。"/>
    </div>
    
    <div class="hero-section">
      <div class="logo">
        <svg class="logo-svg" viewBox="0 0 200 200" xmlns="http://www.w3.org/2000/svg" aria-label="AI恋爱平台Logo">
          <defs>
            <linearGradient id="logoGradient" x1="0%" y1="0%" x2="100%" y2="100%">
              <stop offset="0%" stop-color="#ff758c" />
              <stop offset="50%" stop-color="#ff7eb3" />
              <stop offset="100%" stop-color="#ff8c9e" />
            </linearGradient>
            <filter id="glow">
              <feGaussianBlur stdDeviation="5" result="blur" />
              <feComposite in="SourceGraphic" in2="blur" operator="over" />
            </filter>
          </defs>
          <path d="M100 15C53.8 15 15 53.8 15 100s38.8 85 85 85 85-38.8 85-85S146.2 15 100 15zm0 150c-35.9 0-65-29.1-65-65s29.1-65 65-65 65 29.1 65 65-29.1 65-65 65z" fill="url(#logoGradient)" filter="url(#glow)"/>
          <path d="M100 65c-19.3 0-35 15.7-35 35s15.7 35 35 35 35-15.7 35-35-15.7-35-35-35zm0 50c-8.3 0-15-6.7-15-15s6.7-15 15-15 15 6.7 15 15-6.7 15-15 15z" fill="#ffffff" opacity="0.9"/>
          <path class="pulse-circle" d="M160 100c0 33.1-26.9 60-60 60V60c33.1 0 60 26.9 60 60z" fill="#ff758c" fill-opacity="0.7"/>
        </svg>
      </div>
      
      <div class="title-container">
        <h1 class="title glitch">AI恋爱平台</h1>
        <div class="title-bar"></div>
      </div>
      
      <p class="subtitle">
        <span class="typing-text">探索AI的浪漫可能，体验智能交互的甜蜜</span>
        <span class="blink">❤</span>
      </p>
    </div>
    
    <div class="app-cards">
      <div class="app-card" @click="navigateTo('love-app')">
        <div class="card-header">
          <div class="card-icon love-icon">
            <svg viewBox="0 0 24 24" xmlns="http://www.w3.org/2000/svg">
              <path d="M12 21.35l-1.45-1.32C5.4 15.36 2 12.28 2 8.5 2 5.42 4.42 3 7.5 3c1.74 0 3.41.81 4.5 2.09C13.09 3.81 14.76 3 16.5 3 19.58 3 22 5.42 22 8.5c0 3.78-3.4 6.86-8.55 11.54L12 21.35z" fill="#ff0066"/>
            </svg>
          </div>
          <div class="card-status">
            <span class="status-dot"></span>
            <span class="status-text">ONLINE</span>
          </div>
        </div>
        <h2>AI恋爱大师</h2>
        <p>与AI谈一场甜蜜的恋爱，体验智能情感交流</p>
        <div class="card-glow"></div>
        <div class="card-corner top-left"></div>
        <div class="card-corner top-right"></div>
        <div class="card-corner bottom-left"></div>
        <div class="card-corner bottom-right"></div>
      </div>
      
      <div class="app-card" @click="navigateTo('manus-app')">
        <div class="card-header">
          <div class="card-icon manus-icon">
            <svg viewBox="0 0 24 24" xmlns="http://www.w3.org/2000/svg">
              <path d="M12 2C6.48 2 2 6.48 2 12s4.48 10 10 10 10-4.48 10-10S17.52 2 12 2zm0 18c-4.41 0-8-3.59-8-8s3.59-8 8-8 8 3.59 8 8-3.59 8-8 8zm-1-13h2v6h-2zm0 8h2v2h-2z" fill="#00ffcc"/>
            </svg>
          </div>
          <div class="card-status">
            <span class="status-dot"></span>
            <span class="status-text">ONLINE</span>
          </div>
        </div>
        <h2>AI超级智能体</h2>
        <p>体验最先进的AI智能体，解决复杂问题</p>
        <div class="card-glow"></div>
        <div class="card-corner top-left"></div>
        <div class="card-corner top-right"></div>
        <div class="card-corner bottom-left"></div>
        <div class="card-corner bottom-right"></div>
      </div>
    </div>
    
    <div class="footer">
      <div class="footer-line"></div>
      <p>© 2023-2025 <span class="highlight">AI恋爱平台</span> | <span class="highlight">Richard</span> | 版权所有</p>
      <p class="footer-links">
        <a href="#" class="footer-link">隐私政策</a> | 
        <a href="#" class="footer-link">使用条款</a> | 
        <a href="#" class="footer-link">联系我们</a>
      </p>
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
@import url('https://fonts.googleapis.com/css2?family=Poppins:wght@300;400;500;600;700&family=Pacifico&display=swap');

/* SEO优化：添加结构化数据的样式支持 */
.seo-hidden {
  display: none;
}

.home-container {
  height: 100vh;
  background: linear-gradient(135deg, #fff6f8 0%, #f9f9f9 100%);
  color: #333;
  padding: 15px;
  display: flex;
  flex-direction: column;
  align-items: center;
  justify-content: space-between;
  font-family: 'Poppins', sans-serif;
  position: relative;
  overflow: hidden;
}

.cyber-grid {
  display: none;
}

.glitch-overlay {
  position: absolute;
  top: 0;
  left: 0;
  right: 0;
  bottom: 0;
  background: url("data:image/svg+xml,%3Csvg width='100' height='100' viewBox='0 0 100 100' xmlns='http://www.w3.org/2000/svg'%3E%3Cpath d='M12 21.35l-1.45-1.32C5.4 15.36 2 12.28 2 8.5 2 5.42 4.42 3 7.5 3c1.74 0 3.41.81 4.5 2.09C13.09 3.81 14.76 3 16.5 3 19.58 3 22 5.42 22 8.5c0 3.78-3.4 6.86-8.55 11.54L12 21.35z' fill='%23ff758c' fill-opacity='0.03' transform='scale(4)' /%3E%3C/svg%3E");
  opacity: 0.5;
  pointer-events: none;
  z-index: 2;
}

.hero-section {
  text-align: center;
  margin-top: 20px;
  animation: fadeIn 1s ease-in-out;
  position: relative;
  z-index: 5;
}

.logo {
  width: 100px;
  height: 100px;
  margin: 0 auto 20px;
  position: relative;
}

.logo::after {
  content: '';
  position: absolute;
  top: 50%;
  left: 50%;
  width: 160px;
  height: 160px;
  border-radius: 50%;
  background: radial-gradient(circle, rgba(255, 117, 140, 0.2) 0%, transparent 70%);
  transform: translate(-50%, -50%);
  z-index: -1;
}

.logo-svg {
  width: 100%;
  height: 100%;
  filter: drop-shadow(0 0 15px rgba(255, 117, 140, 0.7));
  animation: pulse 2s infinite ease-in-out;
}

.pulse-circle {
  animation: heartbeat 2s infinite ease-in-out;
  fill: #ff758c !important;
}

.title-container {
  position: relative;
  margin-bottom: 20px;
}

.title {
  font-family: 'Pacifico', cursive;
  font-size: 3.5rem;
  font-weight: 700;
  margin-bottom: 5px;
  background: linear-gradient(to right, #ff758c, #ff7eb3);
  -webkit-background-clip: text;
  -webkit-text-fill-color: transparent;
  text-shadow: 0 0 10px rgba(255, 117, 140, 0.5);
  position: relative;
  letter-spacing: 1px;
}

.title.glitch {
  position: relative;
}

.title-bar {
  height: 4px;
  width: 80%;
  background: linear-gradient(90deg, transparent, #ff758c, #ff7eb3, transparent);
  margin: 5px auto 15px;
  position: relative;
  overflow: hidden;
  border-radius: 2px;
}

.title-bar::after {
  content: '';
  position: absolute;
  top: 0;
  left: -100%;
  width: 50%;
  height: 100%;
  background: rgba(255, 255, 255, 0.7);
  animation: barScan 3s ease-in-out infinite;
}

.subtitle {
  font-size: 1.2rem;
  color: #666;
  max-width: 600px;
  margin: 0 auto;
  position: relative;
  padding: 15px;
  border-radius: 30px;
  background-color: rgba(255, 255, 255, 0.7);
  box-shadow: 0 4px 15px rgba(255, 117, 140, 0.2);
}

.typing-text {
  display: inline-block;
}

.blink {
  animation: blink 1s step-end infinite;
  font-weight: bold;
  color: #ff758c;
}

.cyber-scanner {
  display: none;
}

.app-cards {
  display: flex;
  justify-content: center;
  gap: 20px;
  margin-bottom: 20px;
  width: 100%;
  max-width: 900px;
  z-index: 5;
  position: relative;
}

.app-card {
  background-color: white;
  border-radius: 20px;
  padding: 20px;
  width: 280px;
  box-shadow: 0 10px 25px rgba(0, 0, 0, 0.05);
  transition: all 0.3s ease;
  cursor: pointer;
  position: relative;
  overflow: hidden;
}

.app-card::before {
  content: '';
  position: absolute;
  top: 0;
  left: 0;
  right: 0;
  height: 6px;
  background: linear-gradient(90deg, #ff758c, #ff7eb3);
  border-radius: 6px 6px 0 0;
}

.app-card:hover {
  transform: translateY(-10px);
  box-shadow: 0 15px 40px rgba(255, 117, 140, 0.25);
}

.app-card:hover .card-glow {
  opacity: 1;
}

.card-header {
  display: flex;
  justify-content: space-between;
  align-items: center;
  width: 100%;
  margin-bottom: 20px;
}

.card-icon {
  width: 70px;
  height: 70px;
  position: relative;
  z-index: 2;
  background: rgba(255, 117, 140, 0.1);
  border-radius: 50%;
  padding: 12px;
}

.card-icon svg {
  width: 100%;
  height: 100%;
  filter: drop-shadow(0 0 8px rgba(255, 117, 140, 0.7));
}

.card-status {
  display: flex;
  align-items: center;
  font-size: 0.8rem;
  color: #ff758c;
  background: rgba(255, 117, 140, 0.1);
  padding: 5px 10px;
  border-radius: 20px;
}

.status-dot {
  width: 8px;
  height: 8px;
  background-color: #ff758c;
  border-radius: 50%;
  margin-right: 5px;
  animation: blink 1.5s infinite;
}

.love-icon svg {
  animation: heartbeat 1.5s infinite ease-in-out;
}

.manus-icon svg {
  animation: float 3s infinite ease-in-out;
}

.app-card h2 {
  font-family: 'Pacifico', cursive;
  font-size: 1.8rem;
  margin-bottom: 15px;
  position: relative;
  z-index: 2;
  color: #333;
}

.app-card p {
  color: #666;
  margin-bottom: 20px;
  position: relative;
  z-index: 2;
  line-height: 1.6;
}

.card-glow {
  position: absolute;
  top: 0;
  left: 0;
  width: 100%;
  height: 100%;
  background: radial-gradient(circle at center, rgba(255, 117, 140, 0.1) 0%, transparent 70%);
  opacity: 0;
  transition: opacity 0.4s ease;
  pointer-events: none;
}

.card-corner {
  display: none;
}

.footer {
  margin-top: 10px;
  text-align: center;
  padding: 10px;
  color: #666;
  font-size: 0.9rem;
  width: 100%;
  position: relative;
  z-index: 5;
}

.footer-line {
  height: 1px;
  width: 80%;
  margin: 0 auto 15px;
  background: linear-gradient(90deg, transparent, rgba(255, 117, 140, 0.5), transparent);
}

.highlight {
  color: #ff758c;
  font-weight: bold;
}

.footer-links {
  margin-top: 10px;
  font-size: 0.9em;
}

.footer-link {
  color: #ff758c;
  text-decoration: none;
  transition: color 0.3s ease;
}

.footer-link:hover {
  color: #ff4d6d;
  text-decoration: underline;
}

@keyframes fadeIn {
  from { opacity: 0; transform: translateY(20px); }
  to { opacity: 1; transform: translateY(0); }
}

@keyframes pulse {
  0% { transform: scale(1); filter: brightness(1); }
  50% { transform: scale(1.05); filter: brightness(1.2); }
  100% { transform: scale(1); filter: brightness(1); }
}

@keyframes heartbeat {
  0% { transform: scale(1); }
  15% { transform: scale(1.2); }
  30% { transform: scale(1); }
  45% { transform: scale(1.1); }
  60% { transform: scale(1); }
  100% { transform: scale(1); }
}

@keyframes float {
  0% { transform: translateY(0px); }
  50% { transform: translateY(-10px); }
  100% { transform: translateY(0px); }
}

@keyframes barScan {
  0% { left: -100%; }
  100% { left: 100%; }
}

@keyframes blink {
  0%, 100% { opacity: 1; }
  50% { opacity: 0; }
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
    max-width: 320px;
  }
}
</style>