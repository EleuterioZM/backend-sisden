<template>
  <section class="about-section fade-in">
    <div class="container">
      <h1 class="about-title">Sobre a Plataforma DenúnciaMZ</h1>
      <p class="about-description">
        A <strong>DenúnciaMZ</strong> é uma plataforma moderna e intuitiva criada para facilitar o registro de denúncias sobre serviços públicos em Moçambique. Nosso objetivo é promover a transparência, a participação cidadã e a melhoria contínua dos serviços oferecidos à população.
      </p>

      <div class="about-block">
        <h2>Missão</h2>
        <p>
          Facilitar a comunicação entre cidadãos e órgãos públicos, promovendo a resolução rápida e eficiente de problemas que afetam a comunidade.
        </p>
      </div>
      <div class="about-block">
        <h2>Visão</h2>
        <p>
          Ser referência nacional em tecnologia cívica, contribuindo para um Moçambique mais justo, transparente e participativo.
        </p>
      </div>
      <div class="about-block">
        <h2>Valores</h2>
        <ul>
          <li>Transparência</li>
          <li>Segurança e Anonimato</li>
          <li>Inovação</li>
          <li>Compromisso Social</li>
          <li>Respeito ao cidadão</li>
        </ul>
      </div>

      <div class="about-block">
        <h2>Como Funciona?</h2>
        <ol>
          <li>Escolha a área do serviço público que deseja denunciar.</li>
          <li>Descreva o problema de forma clara e objetiva.</li>
          <li>Envie sua denúncia de forma anônima e segura.</li>
          <li>Acompanhe o andamento e a resolução do seu caso em tempo real.</li>
        </ol>
      </div>

      <div class="about-block">
        <h2>Compromisso com o Anonimato</h2>
        <p>
          Garantimos que todas as denúncias podem ser feitas de forma anônima. Seus dados pessoais não serão compartilhados sem o seu consentimento.
        </p>
      </div>
    </div>
  </section>
</template>

<script setup>
// Página estática, sem lógica JS
</script>

<style scoped>
.about-section {
  padding: 4rem 2rem;
  background: linear-gradient(135deg, rgba(30, 41, 59, 0.95), rgba(15, 23, 42, 0.95));
  border-radius: 24px;
  margin: 2rem auto;
  max-width: 900px;
  box-shadow: 0 8px 40px rgba(15, 23, 42, 0.15);
}

.container {
  max-width: 800px;
  margin: 0 auto;
}

.about-title {
  font-size: 2.5rem;
  font-weight: 800;
  margin-bottom: 1.5rem;
  background: linear-gradient(135deg, #ffffff, #cbd5e1);
  -webkit-background-clip: text;
  -webkit-text-fill-color: transparent;
  background-clip: text;
  text-align: center;
}

.about-description {
  font-size: 1.2rem;
  color: #cbd5e1;
  margin-bottom: 2.5rem;
  text-align: center;
}

.about-block {
  background: rgba(30, 41, 59, 0.8);
  border: 1px solid rgba(59, 130, 246, 0.15);
  border-radius: 16px;
  padding: 2rem 1.5rem;
  margin-bottom: 2rem;
  box-shadow: 0 4px 20px rgba(37, 99, 235, 0.05);
}

.about-block h2 {
  font-size: 1.3rem;
  font-weight: 700;
  margin-bottom: 0.75rem;
  color: #f8fafc;
}

.about-block p, .about-block ul, .about-block ol {
  color: #cbd5e1;
  font-size: 1.05rem;
  margin-bottom: 0.5rem;
}

.about-block ul, .about-block ol {
  padding-left: 1.5rem;
}

.about-block li {
  margin-bottom: 0.3rem;
}

@media (max-width: 768px) {
  .about-section {
    padding: 2rem 0.5rem;
    margin: 1rem 0.5rem;
  }
  .about-title {
    font-size: 2rem;
  }
}
</style> 