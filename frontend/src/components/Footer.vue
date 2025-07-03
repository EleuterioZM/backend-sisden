<template>
  <footer class="footer">
    <div class="container">
      <div class="footer-content">
        <div class="row">
          <div class="col-lg-4 col-md-6 mb-4">
            <div class="footer-section">
              <div class="footer-logo mb-3">
                <i class="fas fa-bullhorn"></i>
                <span class="ms-2">DenúnciaMZ</span>
              </div>
              <p class="footer-description">
                Sistema oficial de denúncias de Moçambique. Contribua para um país mais justo e transparente reportando irregularidades.
              </p>
              <div class="social-links">
                <a href="#" class="social-link" title="Facebook">
                  <i class="fab fa-facebook"></i>
                </a>
                <a href="#" class="social-link" title="Twitter">
                  <i class="fab fa-twitter"></i>
                </a>
                <a href="#" class="social-link" title="Instagram">
                  <i class="fab fa-instagram"></i>
                </a>
                <a href="#" class="social-link" title="WhatsApp">
                  <i class="fab fa-whatsapp"></i>
                </a>
              </div>
            </div>
          </div>
          
          <div class="col-lg-2 col-md-6 mb-4">
            <div class="footer-section">
              <h5 class="footer-title">Links Rápidos</h5>
              <ul class="footer-links">
                <li><a href="#">Nova Denúncia</a></li>
                <li><a href="#">Como Funciona</a></li>
                <li><a href="#">FAQ</a></li>
                <li><a href="#">Contato</a></li>
              </ul>
            </div>
          </div>
          
          <div class="col-lg-3 col-md-6 mb-4">
            <div class="footer-section">
              <h5 class="footer-title">Categorias</h5>
              <ul class="footer-links">
                <li><a href="#">Energia</a></li>
                <li><a href="#">Água</a></li>
                <li><a href="#">Transporte</a></li>
                <li><a href="#">Saúde</a></li>
              </ul>
            </div>
          </div>
          
          <div class="col-lg-3 col-md-6 mb-4">
            <div class="footer-section">
              <h5 class="footer-title">Contato</h5>
              <div class="contact-info">
                <div class="contact-item">
                  <i class="fas fa-phone"></i>
                  <span>+258 84 123 4567</span>
                </div>
                <div class="contact-item">
                  <i class="fas fa-envelope"></i>
                  <span>info@denunciamz.gov.mz</span>
                </div>
                <div class="contact-item">
                  <i class="fas fa-map-marker-alt"></i>
                  <span>Maputo, Moçambique</span>
                </div>
              </div>
            </div>
          </div>
        </div>
        
        <div class="footer-bottom">
          <div class="row align-items-center">
            <div class="col-md-6">
              <p class="copyright">&copy; {{ currentYear }} DenúnciaMZ. Todos os direitos reservados.</p>
            </div>
            <div class="col-md-6 text-md-end">
              <div class="footer-bottom-links">
                <a href="#">Política de Privacidade</a>
                <a href="#">Termos de Uso</a>
                <a href="#">Acessibilidade</a>
              </div>
            </div>
          </div>
        </div>
      </div>
    </div>
  </footer>
</template>

<script>
export default {
  name: 'Footer',
  computed: {
    currentYear() {
      return new Date().getFullYear();
    }
  }
}
</script>

<style scoped>
.footer {
  background: linear-gradient(135deg, var(--primary-blue), var(--dark-blue));
  color: white;
  padding: 3rem 0 1rem;
  margin-top: auto;
  position: relative;
  z-index: 100;
}

.footer-content {
  position: relative;
  z-index: 2;
}

.footer-section {
  margin-bottom: 1rem;
}

.footer-logo {
  display: flex;
  align-items: center;
  font-size: 1.5rem;
  font-weight: 700;
  color: white;
}

.footer-logo i {
  background: rgba(255, 255, 255, 0.2);
  padding: 8px;
  border-radius: 8px;
  font-size: 1.2rem;
}

.footer-description {
  color: rgba(255, 255, 255, 0.8);
  line-height: 1.6;
  margin-bottom: 1.5rem;
}

.footer-title {
  color: white;
  font-weight: 600;
  margin-bottom: 1rem;
  font-size: 1.1rem;
  position: relative;
  padding-bottom: 0.5rem;
}

.footer-title::after {
  content: '';
  position: absolute;
  bottom: 0;
  left: 0;
  width: 30px;
  height: 2px;
  background: rgba(255, 255, 255, 0.5);
  border-radius: 1px;
}

.footer-links {
  list-style: none;
  padding: 0;
  margin: 0;
}

.footer-links li {
  margin-bottom: 0.5rem;
}

.footer-links a {
  color: rgba(255, 255, 255, 0.8);
  text-decoration: none;
  transition: all 0.3s ease;
  display: inline-block;
  position: relative;
}

.footer-links a:hover {
  color: white;
  transform: translateX(5px);
}

.footer-links a::before {
  content: '→';
  position: absolute;
  left: -15px;
  opacity: 0;
  transition: all 0.3s ease;
}

.footer-links a:hover::before {
  opacity: 1;
  left: -20px;
}

.social-links {
  display: flex;
  gap: 12px;
  margin-top: 1rem;
}

.social-link {
  display: flex;
  align-items: center;
  justify-content: center;
  width: 40px;
  height: 40px;
  background: rgba(255, 255, 255, 0.1);
  border-radius: 50%;
  color: white;
  text-decoration: none;
  transition: all 0.3s ease;
  backdrop-filter: blur(10px);
}

.social-link:hover {
  background: rgba(255, 255, 255, 0.2);
  transform: translateY(-2px);
  color: white;
}

.contact-info {
  display: flex;
  flex-direction: column;
  gap: 0.75rem;
}

.contact-item {
  display: flex;
  align-items: center;
  gap: 10px;
  color: rgba(255, 255, 255, 0.8);
  font-size: 0.9rem;
}

.contact-item i {
  width: 16px;
  color: rgba(255, 255, 255, 0.6);
  font-size: 0.8rem;
}

.footer-bottom {
  border-top: 1px solid rgba(255, 255, 255, 0.1);
  padding-top: 1.5rem;
  margin-top: 2rem;
}

.copyright {
  margin: 0;
  color: rgba(255, 255, 255, 0.7);
  font-size: 0.9rem;
}

.footer-bottom-links {
  display: flex;
  gap: 1.5rem;
  justify-content: flex-end;
  flex-wrap: wrap;
}

.footer-bottom-links a {
  color: rgba(255, 255, 255, 0.7);
  text-decoration: none;
  font-size: 0.85rem;
  transition: color 0.3s ease;
}

.footer-bottom-links a:hover {
  color: white;
}

@media (max-width: 768px) {
  .footer {
    padding: 2rem 0 1rem;
  }
  
  .footer-bottom-links {
    justify-content: center;
    margin-top: 1rem;
  }
  
  .copyright {
    text-align: center;
  }
  
  .social-links {
    justify-content: center;
  }
}
</style>
