<template>
  <div class="container mt-5">
    <div class="row justify-content-center">
      <div class="col-md-6 text-center">
        <h1 class="display-1 text-danger">404</h1>
        <h2>Página não encontrada</h2>
        <p class="lead">Desculpe, mas a página que você está procurando não existe.</p>
        <router-link to="/" class="btn btn-primary btn-lg">
          Voltar para a página inicial
        </router-link>
      </div>
    </div>
  </div>
</template>

<script>
export default {
  name: 'NotFound'
}
</script>

<style scoped>
.display-1 {
  font-size: 10rem;
  margin-bottom: 1rem;
}
</style>
