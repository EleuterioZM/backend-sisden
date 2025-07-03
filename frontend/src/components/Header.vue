<template>
  <nav class="navbar">
    <div class="container">
      <div class="navbar-content">
        <!-- Logo Section -->
        <div class="logo-container">
          <div class="logo-icon">
            <i class="fas fa-bullhorn"></i>
          </div>
          <div class="brand-info">
            <h1 class="brand-text">DenúnciaMZ</h1>
            <p class="brand-subtitle">Sistema de Denúncias</p>
          </div>
        </div>

        <!-- Navigation Menu -->
        <div class="nav-menu" :class="{ 'nav-menu-open': isMenuOpen }">
          <router-link to="/" class="nav-link" @click="closeMenu">
            <i class="fas fa-home nav-icon"></i>
            <span>Início</span>
          </router-link>
          
          <router-link to="/reports/new" class="nav-link nav-link-primary" @click="closeMenu">
            <i class="fas fa-plus-circle nav-icon"></i>
            <span>Nova Denúncia</span>
          </router-link>
          <router-link to="/about" class="nav-link" @click="closeMenu">
            <i class="fas fa-info-circle nav-icon"></i>
            <span>Sobre</span>
          </router-link>
        </div>

        <!-- Mobile Menu Toggle -->
        <button class="mobile-menu-btn" @click="toggleMenu" :class="{ 'active': isMenuOpen }">
          <span class="hamburger-line"></span>
          <span class="hamburger-line"></span>
          <span class="hamburger-line"></span>
        </button>
      </div>
    </div>

    <!-- Mobile Menu Overlay -->
    <div class="mobile-overlay" :class="{ 'active': isMenuOpen }" @click="closeMenu"></div>
  </nav>
</template>

<script>
export default {
  name: 'Header',
  data() {
    return {
      isMenuOpen: false
    }
  },
  methods: {
    toggleMenu() {
      this.isMenuOpen = !this.isMenuOpen;
    },
    closeMenu() {
      this.isMenuOpen = false;
    }
  },
  mounted() {
    // Close menu on scroll
    window.addEventListener('scroll', this.closeMenu);
  },
  beforeUnmount() {
    window.removeEventListener('scroll', this.closeMenu);
  }
}
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
  --text-dark: #1e293b;
  --text-gray: #64748b;
  --bg-white: rgba(248, 250, 252, 0.95);
  --border-light: rgba(226, 232, 240, 0.8);
  --shadow-light: rgba(15, 23, 42, 0.08);
  --shadow-medium: rgba(15, 23, 42, 0.15);
}

.navbar {
  background: var(--bg-white);
  backdrop-filter: blur(20px);
  border-bottom: 1px solid var(--border-light);
  position: sticky;
  top: 0;
  z-index: 1000;
  padding: 0;
  box-shadow: 0 4px 30px var(--shadow-light);
  transition: all 0.3s ease;
}

.container {
  max-width: 1200px;
  margin: 0 auto;
  padding: 0 1rem;
}

.navbar-content {
  display: flex;
  align-items: center;
  justify-content: space-between;
  padding: 1rem 0;
  position: relative;
}

/* Logo Section */
.logo-container {
  display: flex;
  align-items: center;
  gap: 12px;
  z-index: 1001;
}

.logo-icon {
  width: 48px;
  height: 48px;
  background: linear-gradient(135deg, var(--primary-blue), var(--accent));
  border-radius: 12px;
  display: flex;
  align-items: center;
  justify-content: center;
  color: white;
  font-size: 20px;
  box-shadow: 0 8px 25px rgba(37, 99, 235, 0.25);
  transition: all 0.3s ease;
  position: relative;
  overflow: hidden;
}

.logo-icon::before {
  content: '';
  position: absolute;
  top: -50%;
  left: -50%;
  width: 200%;
  height: 200%;
  background: linear-gradient(45deg, transparent, rgba(255, 255, 255, 0.1), transparent);
  transform: rotate(45deg);
  transition: all 0.6s ease;
  opacity: 0;
}

.logo-icon:hover {
  transform: scale(1.05) rotate(5deg);
  box-shadow: 0 12px 35px rgba(37, 99, 235, 0.4);
}

.logo-icon:hover::before {
  opacity: 1;
  animation: shine 0.6s ease;
}

@keyframes shine {
  0% {
    transform: translateX(-100%) translateY(-100%) rotate(45deg);
  }
  100% {
    transform: translateX(100%) translateY(100%) rotate(45deg);
  }
}

.brand-info {
  display: flex;
  flex-direction: column;
}

.brand-text {
  color: var(--text-dark);
  font-weight: 800;
  font-size: 1.5rem;
  margin: 0;
  line-height: 1.2;
  background: linear-gradient(135deg, var(--primary-blue), var(--accent));
  -webkit-background-clip: text;
  -webkit-text-fill-color: transparent;
  background-clip: text;
}

.brand-subtitle {
  color: var(--text-gray);
  font-size: 0.8rem;
  margin: 0;
  font-weight: 500;
  opacity: 0.8;
}

.logo-container .brand-text {
  background: none;
  color: var(--text-light);
  -webkit-text-fill-color: var(--text-light);
}

.logo-container .brand-subtitle {
  color: var(--text-muted);
  opacity: 1;
}

/* Navigation Menu */
.nav-menu {
  display: flex;
  align-items: center;
  gap: 0.5rem;
}

.nav-link {
  display: flex;
  align-items: center;
  gap: 0.5rem;
  padding: 0.75rem 1.25rem;
  border-radius: 12px;
  text-decoration: none;
  font-weight: 500;
  font-size: 0.95rem;
  transition: all 0.3s ease;
  position: relative;
  overflow: hidden;
  color: var(--text-gray);
}

.nav-link:hover {
  color: var(--primary-blue);
  background: rgba(37, 99, 235, 0.08);
  transform: translateY(-1px);
  text-decoration: none;
}

.nav-link.router-link-active {
  color: var(--primary-blue);
  background: rgba(37, 99, 235, 0.1);
  font-weight: 600;
}

.nav-link-primary {
  background: linear-gradient(135deg, var(--primary-blue), var(--accent));
  color: white !important;
  font-weight: 600;
  box-shadow: 0 4px 15px rgba(37, 99, 235, 0.3);
}

.nav-link-primary:hover {
  transform: translateY(-2px);
  box-shadow: 0 8px 25px rgba(37, 99, 235, 0.4);
  background: linear-gradient(135deg, var(--dark-blue), var(--primary-blue));
  color: white !important;
}

.nav-link-primary.router-link-active {
  background: linear-gradient(135deg, var(--dark-blue), var(--primary-blue));
  color: white !important;
}

.nav-icon {
  font-size: 1rem;
  transition: transform 0.3s ease;
}

.nav-link:hover .nav-icon {
  transform: scale(1.1);
}

/* Mobile Menu Button */
.mobile-menu-btn {
  display: none;
  flex-direction: column;
  justify-content: center;
  align-items: center;
  width: 40px;
  height: 40px;
  background: transparent;
  border: none;
  cursor: pointer;
  z-index: 1001;
  transition: all 0.3s ease;
  border-radius: 8px;
  padding: 6px;
}

.mobile-menu-btn:hover {
  background: rgba(37, 99, 235, 0.1);
}

.hamburger-line {
  width: 24px;
  height: 2px;
  background: var(--text-dark);
  transition: all 0.3s ease;
  border-radius: 2px;
}

.hamburger-line:not(:last-child) {
  margin-bottom: 4px;
}

.mobile-menu-btn.active .hamburger-line:nth-child(1) {
  transform: rotate(45deg) translate(5px, 5px);
  background: var(--primary-blue);
}

.mobile-menu-btn.active .hamburger-line:nth-child(2) {
  opacity: 0;
}

.mobile-menu-btn.active .hamburger-line:nth-child(3) {
  transform: rotate(-45deg) translate(7px, -6px);
  background: var(--primary-blue);
}

/* Mobile Overlay */
.mobile-overlay {
  display: none;
  position: fixed;
  top: 0;
  left: 0;
  width: 100%;
  height: 100%;
  background: rgba(15, 23, 42, 0.5);
  backdrop-filter: blur(4px);
  z-index: 999;
  opacity: 0;
  transition: opacity 0.3s ease;
}

.mobile-overlay.active {
  opacity: 1;
}

/* Responsive Design */
@media (max-width: 768px) {
  .mobile-menu-btn {
    display: flex;
  }

  .mobile-overlay {
    display: block;
  }

  .nav-menu {
    position: fixed;
    top: 0;
    left: 0;
    width: 280px;
    height: 100vh;
    background: var(--bg-dark);
    backdrop-filter: blur(20px);
    flex-direction: column;
    align-items: stretch;
    padding: 5rem 1.5rem 2rem;
    box-shadow: 10px 0 30px var(--shadow-light);
    transition: transform 0.3s ease;
    z-index: 1000;
    gap: 0.75rem;
    transform: translateX(-100%);
  }

  .nav-menu-open {
    transform: translateX(0);
  }

  .nav-link {
    padding: 1rem 1.5rem;
    border-radius: 12px;
    font-size: 1rem;
    justify-content: flex-start;
  }

  .nav-icon {
    font-size: 1.1rem;
    width: 20px;
  }

  .brand-text {
    font-size: 1.3rem;
  }

  .brand-subtitle {
    font-size: 0.75rem;
  }

  .nav-menu .nav-link-primary {
    background: var(--bg-white);
    color: var(--primary-blue) !important;
    box-shadow: 0 4px 15px rgba(37, 99, 235, 0.15);
  }
  .nav-menu .nav-link-primary:hover,
  .nav-menu .nav-link-primary.router-link-active {
    background: var(--light-blue);
    color: white !important;
  }

  .logo-container .brand-text {
    background: none;
    color: var(--text-light);
    -webkit-text-fill-color: var(--text-light);
  }
  .logo-container .brand-subtitle {
    color: var(--text-muted);
    opacity: 1;
  }
}

@media (max-width: 480px) {
  .container {
    padding: 0 0.75rem;
  }

  .navbar-content {
    padding: 0.75rem 0;
  }

  .logo-icon {
    width: 42px;
    height: 42px;
    font-size: 18px;
  }

  .brand-text {
    font-size: 1.2rem;
  }

  .nav-menu {
    width: 100%;
    left: -100%;
    padding: 4rem 1rem 2rem;
  }
}

/* Scroll Effect */
.navbar.scrolled {
  background: rgba(248, 250, 252, 0.98);
  box-shadow: 0 8px 40px var(--shadow-medium);
}
</style>