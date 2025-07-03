<template>
  <div class="app">
    <Header />
    <main class="main-content">
      <router-view></router-view>
    </main>

  </div>
</template>

<script>
import Header from './components/Header.vue'


export default {
  name: 'App',
  components: {
    Header,
   
  }
}
</script>

<style>
.app {
  min-height: 100vh;
  display: flex;
  flex-direction: column;
}

.main-content {
  flex: 1;
  padding: 0;
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
</style>
