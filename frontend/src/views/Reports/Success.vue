<template>
  <div class="container-fluid success-container py-5">
    <div class="row justify-content-center">
      <div class="col-lg-8 col-xl-6">
        <div class="text-center px-3">
          <div class="mb-4 icon-container">
            <div class="icon-circle">
              <i class="fas fa-check-circle text-success"></i>
              <div class="circle-animation"></div>
            </div>
          </div>
          <h2 class="mb-3 fw-bold text-gradient-success">Obrigado por sua colaboração!</h2>
          <p class="lead mb-4 text-muted">
            Sua denúncia foi registrada com sucesso e será analisada por nossa equipe.<br>
            Sua participação é fundamental para construirmos uma sociedade mais justa e transparente.
          </p>

          <div class="card shadow-lg mb-4 border-0 success-card">
            <div class="card-body p-4">
              <div class="d-flex align-items-center mb-3">
                <div class="icon-bg-success rounded-circle p-2 me-3">
                  <i class="fas fa-shield-alt text-white"></i>
                </div>
                <h5 class="card-title mb-0 fw-bold">Denúncia recebida!</h5>
              </div>
              
              <div class="ps-1">
                <div class="d-flex mb-2">
                  <span class="text-muted me-2 flex-shrink-0 fw-bold">Instituição:</span>
                  <span class="fw-medium">{{ instituitionName }}</span>
                </div>
                <div class="d-flex">
                  <span class="text-muted me-2 flex-shrink-0 fw-bold">Tipo:</span>
                  <span class="fw-medium">{{ reportTypeName }}</span>
                </div>
                
                <div class="alert alert-info mt-3 mb-0 d-flex align-items-center" role="alert">
                  <i class="fas fa-envelope me-3 fs-5"></i>
                  <div>
                    Você receberá atualizações sobre o andamento da sua denúncia por e-mail.
                  </div>
                </div>
              </div>
            </div>
          </div>

          <div class="text-center mt-4">
            <router-link to="/" class="btn btn-success btn-gradient px-4 py-2">
              <i class="fas fa-home me-2"></i>Voltar para Home
            </router-link>
          </div>

          <div class="mt-5 pt-3">
            <small class="text-muted">
              Caso precise de suporte, entre em contato pelo e-mail <a href="mailto:suporte@edm.co.mz" class="text-decoration-underline">suporte@edm.co.mz</a>.<br>
              Agradecemos por contribuir para um serviço melhor!
            </small>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
export default {
  name: 'ReportSuccess',
  computed: {
    instituitionName() {
      return this.$route.query.instituitionName || 'Não especificado';
    },
    reportTypeName() {
      return this.$route.query.reportTypeName || 'Não especificado';
    }
  }
}
</script>

<style scoped>
.success-container {
  background-color: #f8f9fa;
  min-height: 100vh;
}

.success-card {
  border-radius: 12px;
  background: #ffffff;
  border-top: 4px solid #198754;
  transition: transform 0.3s ease, box-shadow 0.3s ease;
}

.success-card:hover {
  transform: translateY(-5px);
  box-shadow: 0 10px 20px rgba(25, 135, 84, 0.1) !important;
}

.icon-container {
  display: flex;
  justify-content: center;
  align-items: center;
}

.icon-circle {
  width: 120px;
  height: 120px;
  background: #fff;
  border-radius: 50%;
  box-shadow: 0 4px 20px rgba(25, 135, 84, 0.10);
  display: flex;
  align-items: center;
  justify-content: center;
  position: relative;
}

.icon-circle .fa-check-circle {
  font-size: 5rem;
  z-index: 2;
  position: relative;
  animation: popIn 0.7s cubic-bezier(0.23, 1, 0.32, 1);
}

.icon-circle .circle-animation {
  position: absolute;
  top: -10px;
  left: -10px;
  right: -10px;
  bottom: -10px;
  background: rgba(25, 135, 84, 0.1);
  border-radius: 50%;
  z-index: 1;
  animation: pulse 2s infinite;
}

.icon-bg-success {
  background: linear-gradient(135deg, #198754, #2ecc71);
}

.text-gradient-success {
  background: linear-gradient(to right, #198754, #2ecc71);
  -webkit-background-clip: text;
  background-clip: text;
  color: transparent;
  display: inline-block;
}

.btn-gradient {
  background: linear-gradient(to right, #198754, #2ecc71);
  color: white;
  border: none;
  box-shadow: 0 4px 15px rgba(25, 135, 84, 0.3);
  transition: all 0.3s ease;
}

.btn-gradient:hover {
  transform: translateY(-2px);
  box-shadow: 0 6px 20px rgba(25, 135, 84, 0.4);
  color: white;
}

.alert-info {
  background-color: #f0f9ff;
  color: #0c5460;
  border: none;
  border-radius: 8px;
  padding: 12px 16px;
}

@keyframes popIn {
  0% {
    transform: scale(0.5);
    opacity: 0;
  }
  80% {
    transform: scale(1.1);
    opacity: 1;
  }
  100% {
    transform: scale(1);
    opacity: 1;
  }
}

@keyframes pulse {
  0% {
    transform: scale(0.95);
    opacity: 0.7;
  }
  70% {
    transform: scale(1.3);
    opacity: 0;
  }
  100% {
    transform: scale(0.95);
    opacity: 0;
  }
}

@media (max-width: 768px) {
  .success-card {
    margin-left: 15px;
    margin-right: 15px;
  }
  .icon-circle {
    width: 80px;
    height: 80px;
  }
  .icon-circle .fa-check-circle {
    font-size: 4rem;
  }
}
</style>