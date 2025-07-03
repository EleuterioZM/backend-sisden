<template>
  <!-- Hero Section -->
  <section class="hero-section">
    <div class="hero-bg"></div>
    <div class="floating-elements">
      <div class="floating-element"></div>
      <div class="floating-element"></div>
      <div class="floating-element"></div>
    </div>
    <div class="hero-content">
      <div class="hero-badge">
        <i class="fas fa-shield-alt"></i>
        Plataforma Segura e Confiável
      </div>
      <h1 class="hero-title">Sua Voz, Nossa Ação</h1>
      <p class="hero-subtitle">
        Plataforma moderna e intuitiva para denúncias de serviços públicos em Moçambique. 
        Faça sua denúncia de forma rápida, segura e acompanhe o progresso em tempo real.
      </p>
      <div class="hero-cta-group">
        <router-link to="#services" class="btn btn-primary" @click.prevent="scrollToSection('services')">
          <i class="fas fa-bullhorn"></i>
          Fazer Denúncia
        </router-link>
        <router-link to="#services" class="btn btn-secondary" @click.prevent="scrollToSection('services')">
          <i class="fas fa-info-circle"></i>
          Saber Mais
        </router-link>
      </div>
    </div>
  </section>

  <!-- Services Section -->
  <section id="services" class="services-section">
    <div class="container">
      <div class="section-header fade-in">
        <h2 class="section-title">Serviços Disponíveis</h2>
        <p class="section-subtitle">
          Denuncie problemas em diferentes áreas de serviços públicos. 
          Sua participação é fundamental para melhorar nossa comunidade.
        </p>
      </div>
      <div class="services-grid">
        <div class="service-card fade-in">
          <div class="service-icon water">
            <i class="fas fa-tint"></i>
          </div>
          <h3 class="service-title">Água e Saneamento</h3>
          <p class="service-description">
            Reporte problemas relacionados ao abastecimento de água, 
            qualidade da água ou sistemas de saneamento básico.
          </p>
          <router-link to="/reports/new?type=agua" class="btn service-btn">
            <i class="fas fa-arrow-right"></i>
            Denunciar
          </router-link>
        </div>
        <div class="service-card fade-in">
          <div class="service-icon energy">
            <i class="fas fa-bolt"></i>
          </div>
          <h3 class="service-title">Energia Elétrica</h3>
          <p class="service-description">
            Denuncie falhas no fornecimento de energia, problemas na rede 
            elétrica ou irregularidades no serviço.
          </p>
          <router-link to="/reports/new?type=energia" class="btn service-btn">
            <i class="fas fa-arrow-right"></i>
            Denunciar
          </router-link>
        </div>
        <div class="service-card fade-in">
          <div class="service-icon security">
            <i class="fas fa-shield-alt"></i>
          </div>
          <h3 class="service-title">Segurança Pública</h3>
          <p class="service-description">
            Reporte questões de segurança, iluminação pública deficiente 
            ou outros problemas que afetem a segurança.
          </p>
          <router-link to="/reports/new?type=seguranca" class="btn service-btn">
            <i class="fas fa-arrow-right"></i>
            Denunciar
          </router-link>
        </div>
      </div>
    </div>
  </section>

  <!-- CTA Section -->
  <section class="cta-section fade-in">
    <div class="cta-content">
      <h2 class="cta-title">Juntos por um Moçambique Melhor</h2>
      <p class="cta-description">
        Sua denúncia é anônima, segura e faz a diferença. 
        Participe ativamente na melhoria dos serviços públicos.
      </p>
      <router-link to="/reports/new" class="btn btn-primary">
        <i class="fas fa-bullhorn"></i>
        Fazer Denúncia Agora
      </router-link>
     
    </div>
  </section>
</template>

<script setup>
import { onMounted } from 'vue';

function scrollToSection(sectionId) {
  const section = document.getElementById(sectionId);
  if (section) {
    section.scrollIntoView({ 
      behavior: 'smooth',
      block: 'start'
    });
  }
}

onMounted(() => {
  // Fade-in animation
  const observerOptions = {
    threshold: 0.1,
    rootMargin: '0px 0px -50px 0px'
  };
  const observer = new window.IntersectionObserver((entries) => {
    entries.forEach(entry => {
      if (entry.isIntersecting) {
        entry.target.classList.add('visible');
      }
    });
  }, observerOptions);
  document.querySelectorAll('.fade-in').forEach(el => {
    observer.observe(el);
  });

  // Animated counter for stats
  function animateCounter(element, target, duration = 2000) {
    let start = 0;
    const increment = target / (duration / 16);
    const timer = setInterval(() => {
      start += increment;
      element.textContent = Math.floor(start);
      if (start >= target) {
        element.textContent = target;
        clearInterval(timer);
      }
    }, 16);
  }
  const statsObserver = new window.IntersectionObserver((entries) => {
    entries.forEach(entry => {
      if (entry.isIntersecting) {
        const counters = entry.target.querySelectorAll('.stat-number');
        counters.forEach(counter => {
          const target = parseInt(counter.getAttribute('data-target'));
          animateCounter(counter, target);
        });
        statsObserver.unobserve(entry.target);
      }
    });
  }, { threshold: 0.3 });
  const statsSection = document.querySelector('.stats-section');
  if (statsSection) {
    statsObserver.observe(statsSection);
  }

  // Parallax effect for floating elements
  window.addEventListener('scroll', () => {
    const scrolled = window.pageYOffset;
    const floatingElements = document.querySelectorAll('.floating-element');
    floatingElements.forEach((element, index) => {
      const speed = 0.5 + (index * 0.1);
      element.style.transform = `translateY(${scrolled * speed}px) rotate(${scrolled * 0.1}deg)`;
    });
  });
});
</script>

<style scoped>
:root {
  --primary-blue: #2563eb;
  --light-blue: #3b82f6;
  --dark-blue: #1e40af;
  --accent: #06b6d4;
  --bg-dark: #0f172a;
  --bg-card: #1e293b;
  --text-light: #f8fafc;
  --text-muted: #cbd5e1;
  --gradient-1: linear-gradient(135deg, #667eea 0%, #764ba2 100%);
  --gradient-2: linear-gradient(135deg, #f093fb 0%, #f5576c 100%);
  --gradient-3: linear-gradient(135deg, #4facfe 0%, #00f2fe 100%);
}

* {
  margin: 0;
  padding: 0;
  box-sizing: border-box;
}

body {
  font-family: 'Inter', sans-serif;
  background: var(--bg-dark);
  color: var(--text-light);
  overflow-x: hidden;
}

/* Hero Section - Futuristic Design */
.hero-section {
  min-height: 100vh;
  position: relative;
  display: flex;
  align-items: center;
  justify-content: center;
  background: radial-gradient(ellipse at center, #1e40af 0%, #0f172a 70%);
  overflow: hidden;
}

.hero-bg {
  position: absolute;
  top: 0;
  left: 0;
  width: 100%;
  height: 100%;
  opacity: 0.1;
  background: url('data:image/svg+xml,<svg xmlns="http://www.w3.org/2000/svg" viewBox="0 0 1000 1000"><defs><pattern id="grid" width="50" height="50" patternUnits="userSpaceOnUse"><path d="M 50 0 L 0 0 0 50" fill="none" stroke="%23ffffff" stroke-width="1"/></pattern></defs><rect width="100%" height="100%" fill="url(%23grid)"/></svg>');
}

.floating-elements {
  position: absolute;
  width: 100%;
  height: 100%;
  pointer-events: none;
}

.floating-element {
  position: absolute;
  border-radius: 50%;
  background: linear-gradient(45deg, rgba(59, 130, 246, 0.1), rgba(6, 182, 212, 0.1));
  animation: float 6s ease-in-out infinite;
}

.floating-element:nth-child(1) {
  width: 100px;
  height: 100px;
  top: 20%;
  left: 10%;
  animation-delay: 0s;
}

.floating-element:nth-child(2) {
  width: 150px;
  height: 150px;
  top: 60%;
  right: 10%;
  animation-delay: 2s;
}

.floating-element:nth-child(3) {
  width: 80px;
  height: 80px;
  top: 40%;
  left: 80%;
  animation-delay: 4s;
}

@keyframes float {
  0%, 100% {
    transform: translateY(0px) rotate(0deg);
  }
  50% {
    transform: translateY(-20px) rotate(180deg);
  }
}

.hero-content {
  text-align: center;
  max-width: 800px;
  padding: 2rem;
  position: relative;
  z-index: 10;
}

.hero-badge {
  display: inline-block;
  padding: 0.5rem 1.5rem;
  background: rgba(59, 130, 246, 0.1);
  border: 1px solid rgba(59, 130, 246, 0.3);
  border-radius: 50px;
  font-size: 0.875rem;
  font-weight: 500;
  margin-bottom: 2rem;
  backdrop-filter: blur(10px);
  animation: pulse 2s infinite;
}

@keyframes pulse {
  0%, 100% {
    box-shadow: 0 0 0 0 rgba(59, 130, 246, 0.4);
  }
  50% {
    box-shadow: 0 0 0 10px rgba(59, 130, 246, 0);
  }
}

.hero-title {
  font-size: clamp(3rem, 8vw, 5rem);
  font-weight: 800;
  line-height: 1.1;
  margin-bottom: 1.5rem;
  background: linear-gradient(135deg, #ffffff, #cbd5e1);
  -webkit-background-clip: text;
  -webkit-text-fill-color: transparent;
  background-clip: text;
  text-shadow: 0 0 50px rgba(59, 130, 246, 0.3);
}

.hero-subtitle {
  font-size: 1.25rem;
  line-height: 1.7;
  color: var(--text-muted);
  margin-bottom: 3rem;
  max-width: 600px;
  margin-left: auto;
  margin-right: auto;
}

.hero-cta-group {
  display: flex;
  gap: 1rem;
  justify-content: center;
  flex-wrap: wrap;
}

.btn {
  display: inline-flex;
  align-items: center;
  justify-content: center;
  padding: 1rem 2rem;
  border-radius: 12px;
  font-weight: 600;
  text-decoration: none;
  transition: all 0.3s ease;
  border: none;
  cursor: pointer;
  font-size: 1rem;
  position: relative;
  overflow: hidden;
}

.btn-primary {
  background: linear-gradient(135deg, var(--primary-blue), var(--accent));
  color: white;
  box-shadow: 0 8px 30px rgba(37, 99, 235, 0.3);
}

.btn-primary:hover {
  transform: translateY(-2px);
  box-shadow: 0 15px 40px rgba(37, 99, 235, 0.5);
}

.btn-secondary {
  background: rgba(255, 255, 255, 0.1);
  color: white;
  border: 1px solid rgba(255, 255, 255, 0.2);
  backdrop-filter: blur(10px);
}

.btn-secondary:hover {
  background: rgba(255, 255, 255, 0.2);
  transform: translateY(-2px);
}

/* Glassmorphism Cards Section */
.services-section {
  padding: 6rem 2rem;
  position: relative;
}

.container {
  max-width: 1200px;
  margin: 0 auto;
}

.section-header {
  text-align: center;
  margin-bottom: 4rem;
}

.section-title {
  font-size: 2.5rem;
  font-weight: 700;
  margin-bottom: 1rem;
  color: white;
  background: none;
}

.section-subtitle {
  font-size: 1.125rem;
  color: white;
  max-width: 600px;
  margin: 0 auto;
}

.services-grid {
  display: grid;
  grid-template-columns: repeat(auto-fit, minmax(300px, 1fr));
  gap: 2rem;
  margin-bottom: 4rem;
}

.service-card {
  background: rgba(30, 41, 59, 0.8);
  border: 1px solid rgba(59, 130, 246, 0.3);
  border-radius: 20px;
  padding: 2.5rem;
  backdrop-filter: blur(20px);
  transition: all 0.3s ease;
  position: relative;
  overflow: hidden;
  transform: translateY(-10px);
  box-shadow: 0 20px 60px rgba(0, 0, 0, 0.4);
}

.service-card:hover {
  transform: translateY(-12px) scale(1.02);
  box-shadow: 0 24px 70px rgba(0, 0, 0, 0.5);
  border-color: rgba(59, 130, 246, 0.4);
}

.service-icon {
  width: 60px;
  height: 60px;
  border-radius: 15px;
  display: flex;
  align-items: center;
  justify-content: center;
  margin-bottom: 1.5rem;
  font-size: 1.5rem;
  background: white;
  color: var(--primary-blue);
}

.service-icon.water,
.service-icon.energy,
.service-icon.security {
  background: white;
}

.service-icon i {
  color: var(--primary-blue);
}

.service-card:hover .service-icon i {
  color: white;
}

.service-title {
  font-size: 1.25rem;
  font-weight: 600;
  margin-bottom: 0.75rem;
  color: white;
}

.service-description {
  color: white;
  line-height: 1.6;
  margin-bottom: 1.5rem;
}

.service-btn {
  width: 100%;
  background: rgba(59, 130, 246, 0.1);
  border: 1px solid rgba(59, 130, 246, 0.3);
  color: white;
  font-weight: 500;
}

.service-btn:hover {
  background: rgba(59, 130, 246, 0.2);
  color: var(--primary-blue);
}

/* CTA Section with Modern Design */
.cta-section {
  padding: 4rem 2rem;
  background: linear-gradient(135deg, rgba(30, 41, 59, 0.9), rgba(15, 23, 42, 0.9));
  border-radius: 24px;
  margin: 2rem;
  border: 1px solid rgba(255, 255, 255, 0.1);
  text-align: center;
  position: relative;
  overflow: hidden;
}

.cta-section::before {
  content: '';
  position: absolute;
  top: -50%;
  left: -50%;
  width: 200%;
  height: 200%;
  background: radial-gradient(circle, rgba(59, 130, 246, 0.1) 0%, transparent 50%);
  animation: rotate 20s linear infinite;
}

@keyframes rotate {
  from {
    transform: rotate(0deg);
  }
  to {
    transform: rotate(360deg);
  }
}

.cta-content {
  position: relative;
  z-index: 2;
}

.cta-title {
  font-size: 2rem;
  font-weight: 700;
  margin-bottom: 1rem;
  background: linear-gradient(135deg, #ffffff, #cbd5e1);
  -webkit-background-clip: text;
  -webkit-text-fill-color: transparent;
  background-clip: text;
}

.cta-description {
  font-size: 1.125rem;
  color: white;
  margin-bottom: 2rem;
  max-width: 500px;
  margin-left: auto;
  margin-right: auto;
}

.stats-section {
  display: grid;
  grid-template-columns: repeat(auto-fit, minmax(200px, 1fr));
  gap: 2rem;
  margin-top: 3rem;
}

.stat-item {
  text-align: center;
}

.stat-number {
  font-size: 2.5rem;
  font-weight: 800;
  color: var(--accent);
  display: block;
}

.stat-label {
  color: var(--text-muted);
  font-size: 0.875rem;
  text-transform: uppercase;
  letter-spacing: 0.5px;
}

/* Responsive Design */
@media (max-width: 768px) {
  .hero-content {
    padding: 1rem;
  }

  .hero-cta-group {
    flex-direction: column;
    align-items: center;
  }

  .btn {
    width: 100%;
    max-width: 300px;
  }

  .services-grid {
    grid-template-columns: 1fr;
  }

  .service-card {
    padding: 2rem;
  }

  .cta-section {
    margin: 1rem;
    padding: 3rem 1.5rem;
  }

  .stats-section {
    grid-template-columns: repeat(2, 1fr);
  }
}

/* Scroll animations */
.fade-in {
  opacity: 0;
  transform: translateY(30px);
  transition: all 0.6s ease;
}

.fade-in.visible {
  opacity: 1;
  transform: translateY(0);
}

.services-section .section-title,
.services-section .section-subtitle {
  color: var(--text-dark);
  background: none;
  -webkit-text-fill-color: var(--text-dark);
}
</style>