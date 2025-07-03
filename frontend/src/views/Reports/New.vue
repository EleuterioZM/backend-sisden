<template>
  <div class="container-fluid py-4">
    <div class="row justify-content-center">
      <div class="col-lg-10 col-xl-8">
        <!-- Header -->
        <div class="d-flex align-items-center mb-4 flex-wrap flex-md-nowrap header-responsive">
          <button v-if="currentStep === 1" @click="goBack" class="btn btn-primary btn-gradient me-3 mb-2 mb-md-0">
            <i class="fas fa-arrow-left me-2"></i>Voltar
          </button>
          <div class="flex-grow-1 text-center">
            <h2 class="mb-1 text-primary fw-bold header-title">Nova Denúncia</h2>
            <p class="text-primary mb-0 fw-semibold header-subtitle">Preencha os dados para registrar sua denúncia</p>
          </div>
        </div>

        <!-- Progress Steps -->
        <div class="card mb-4">
          <div class="card-body py-3">
            <div class="progress-steps">
              <div class="step" :class="{ active: currentStep >= 1, completed: currentStep > 1 }">
                <div class="step-number">1</div>
                <div class="step-label">Dados Pessoais</div>
              </div>
              <div class="step-line" :class="{ completed: currentStep > 1 }"></div>
              <div class="step" :class="{ active: currentStep >= 2, completed: currentStep > 2 }">
                <div class="step-number">2</div>
                <div class="step-label">Tipo de Denúncia</div>
              </div>
              <div class="step-line" :class="{ completed: currentStep > 2 }"></div>
              <div class="step" :class="{ active: currentStep >= 3, completed: currentStep > 3 }">
                <div class="step-number">3</div>
                <div class="step-label">Detalhes</div>
              </div>
            </div>
          </div>
        </div>

        <!-- Form Card -->
        <div class="card shadow-sm">
          <div class="card-body p-4">
            <form @submit.prevent="submitReport">
              
              <!-- Step 1: Dados Pessoais -->
              <div v-show="currentStep === 1" class="form-step">
                <h5 class="mb-4">
                  <i class="fas fa-user text-primary me-2"></i>
                  Dados Pessoais
                </h5>
                <div class="row">
                  <div class="col-md-6">
                    <div class="mb-3">
                      <label for="name" class="form-label">
                        Nome Completo <span class="text-danger">*</span>
                      </label>
                      <input 
                        type="text" 
                        class="form-control" 
                        :class="{ 'is-invalid': errors.name }"
                        id="name" 
                        v-model="formData.name" 
                        placeholder="Digite seu nome completo"
                        @blur="validateField('name')"
                      >
                      <div v-if="errors.name" class="invalid-feedback">{{ errors.name }}</div>
                    </div>
                  </div>
                  <div class="col-md-6">
                    <div class="mb-3">
                      <label class="form-label">
                        Telefones <span class="text-danger">*</span>
                      </label>
                      <div v-for="(phone, idx) in phoneFields" :key="idx" class="d-flex align-items-center mb-2">
                        <div class="input-with-flag">
                          <img :src="flagImage" alt="Moçambique" class="flag-inside-input">
                          <input
                            type="tel"
                            class="form-control phone-input"
                            :class="{ 'is-invalid': errors.phone && idx === 0 }"
                            v-model="phone.number"
                            placeholder="84 384 7642"
                            @input="formatPhoneNumber(idx)"
                            @blur="validateField('phone')"
                            maxlength="12"
                          >
                        </div>
                        <button v-if="phoneFields.length > 1" type="button" class="btn btn-link text-danger ms-2" @click="removePhoneField(idx)">
                          <i class="fas fa-minus-circle"></i>
                        </button>
                        <button v-if="idx === phoneFields.length - 1" type="button" class="btn btn-link text-success ms-2" @click="addPhoneField">
                          <i class="fas fa-plus-circle"></i>
                        </button>
                      </div>
                      <div v-if="errors.phone" class="invalid-feedback d-block">{{ errors.phone }}</div>
                    </div>
                  </div>
                  <div class="col-md-6">
                    <div class="mb-3">
                      <label for="email" class="form-label">
                        Email <span class="text-danger">*</span>
                      </label>
                      <input 
                        type="email" 
                        class="form-control" 
                        :class="{ 'is-invalid': errors.email }"
                        id="email" 
                        v-model="formData.email" 
                        placeholder="seu@email.com"
                        @blur="validateField('email')"
                      >
                      <div v-if="errors.email" class="invalid-feedback">{{ errors.email }}</div>
                    </div>
                  </div>
                </div>
              </div>

              <!-- Step 2: Tipo de Denúncia -->
              <div v-show="currentStep === 2" class="form-step">
                <h5 class="mb-4">
                  <i class="fas fa-exclamation-triangle text-warning me-2"></i>
                  Tipo de Denúncia
                </h5>
                <div class="row">
                  <div class="col-md-6">
                    <div class="mb-3">
                      <label for="title" class="form-label">
                        Título da Denúncia <span class="text-danger">*</span>
                      </label>
                      <input 
                        type="text" 
                        class="form-control" 
                        :class="{ 'is-invalid': errors.title }"
                        id="title" 
                        v-model="formData.title" 
                        placeholder="Título resumido da denúncia"
                        @blur="validateField('title')"
                      >
                      <div v-if="errors.title" class="invalid-feedback">{{ errors.title }}</div>
                    </div>
                  </div>
                  <div class="col-md-6">
                    <div class="mb-3">
                      <label for="institution" class="form-label">
                        Instituição <span class="text-danger">*</span>
                      </label>
                      <select 
                        class="form-select" 
                        :class="{ 'is-invalid': errors.institution }"
                        id="institution" 
                        v-model="formData.instituitionId"
                        :disabled="loadingInstitutions"
                        @change="validateField('institution')"
                      >
                        <option value="">{{ loadingInstitutions ? 'Carregando...' : 'Selecione uma instituição...' }}</option>
                        <option v-for="institution in institutions" :key="institution.id" :value="institution.id">
                          {{ institution.name }}
                        </option>
                      </select>
                      <div v-if="errors.institution" class="invalid-feedback">{{ errors.institution }}</div>
                    </div>
                  </div>
                  <div class="col-md-6">
                    <div class="mb-3">
                      <label for="reportType" class="form-label">
                        Categoria <span class="text-danger">*</span>
                      </label>
                      <select 
                        class="form-select" 
                        :class="{ 'is-invalid': errors.type }"
                        id="reportType" 
                        v-model="formData.reportTypeId"
                        :disabled="loadingReportTypes || !formData.instituitionId"
                        @change="onReportTypeChange"
                      >
                        <option value="">{{ loadingReportTypes ? 'Carregando...' : 'Selecione uma categoria...' }}</option>
                        <option v-for="type in reportTypes" :key="type.id" :value="type.id">
                          {{ type.name }}
                        </option>
                      </select>
                      <div v-if="errors.type" class="invalid-feedback">{{ errors.type }}</div>
                    </div>
                  </div>
                  <div class="col-md-6">
                    <div class="mb-3">
                      <label for="classification" class="form-label">
                        Classificação <span class="text-danger">*</span>
                      </label>
                      <select 
                        class="form-select" 
                        :class="{ 'is-invalid': errors.classification }"
                        id="classification" 
                        v-model="formData.classificationId"
                        @change="validateField('classification')"
                        :disabled="loadingClassifications"
                      >
                        <option value="">{{ loadingClassifications ? 'Carregando...' : 'Selecione uma classificação...' }}</option>
                        <option v-for="classification in classifications" :key="classification.id" :value="classification.id">
                          Gravidade: {{ classification.gravity }}
                        </option>
                      </select>
                      <div v-if="errors.classification" class="invalid-feedback">{{ errors.classification }}</div>
                    </div>
                  </div>
                </div>
              </div>

              <!-- Step 3: Detalhes -->
              <div v-show="currentStep === 3" class="form-step">
                <div class="details-card">
                  <h5 class="details-title mb-4">
                    <i class="fas fa-file-alt text-info me-2"></i>
                    <span>Detalhes da Denúncia</span>
                  </h5>

                  <!-- Location Picker -->
                  <div class="location-block mb-4 p-3">
                    <LocationPicker
                      v-model:latitude="formData.location.latitude"
                      v-model:longitude="formData.location.longitude"
                      v-model:description="formData.location.description"
                    />
                  </div>

                  <div class="mb-4">
                    <label for="description" class="form-label details-label">
                      <i class="fas fa-align-left me-1"></i>
                      Descrição Detalhada <span class="text-danger">*</span>
                    </label>
                    <textarea 
                      class="form-control details-textarea" 
                      :class="{ 'is-invalid': errors.description }"
                      id="description" 
                      rows="6" 
                      v-model="formData.description" 
                      placeholder="Descreva detalhadamente o que aconteceu, quando, onde e as pessoas envolvidas..."
                      @blur="validateField('description')"
                    ></textarea>
                    <div class="form-text details-counter">
                      <span :class="descriptionLengthClass">
                        {{ formData.description.length }}/2000 caracteres
                      </span>
                    </div>
                    <div v-if="errors.description" class="invalid-feedback">{{ errors.description }}</div>
                  </div>

                  <!-- File Upload -->
                  <div class="mb-4">
                    <label class="form-label details-label">
                      <i class="fas fa-paperclip me-1"></i>
                      Anexos (Fotos, documentos, vídeos)
                    </label>
                    <div class="upload-area details-upload" @drop="handleDrop" @dragover.prevent @dragenter.prevent>
                      <input 
                        type="file" 
                        class="form-control" 
                        @change="handleFiles" 
                        multiple 
                        accept="image/*,video/*,.pdf,.doc,.docx"
                        style="display: none"
                        ref="fileInput"
                      >
                      <div class="text-center py-4 upload-invite" @click="$refs.fileInput.click()">
                        <i class="fas fa-cloud-upload-alt fa-3x text-primary mb-3"></i>
                        <p class="mb-2">Clique aqui ou arraste arquivos para upload</p>
                        <small class="text-muted">Máximo 10MB por arquivo. Formatos: JPG, PNG, PDF, DOC, MP4</small>
                      </div>
                    </div>
                    <!-- File List -->
                    <div v-if="formData.files.length > 0" :class="['mt-3', 'file-list', formData.files.length > 1 ? 'file-list-scroll' : '']">
                      <div v-for="(file, index) in formData.files" :key="index" class="file-item details-file-item">
                        <div class="file-info">
                          <i :class="getFileIcon(file.type)" class="me-2"></i>
                          <span>{{ file.name }}</span>
                          <small class="text-muted ms-2">({{ formatFileSize(file.size) }})</small>
                        </div>
                        <button type="button" class="btn btn-sm btn-outline-danger" @click="removeFile(index)">
                          <i class="fas fa-times"></i>
                        </button>
                      </div>
                    </div>
                  </div>
                </div>
              </div>

              <!-- Navigation Buttons -->
              <div class="d-flex justify-content-between mt-5">
                <button 
                  v-if="currentStep > 1" 
                  type="button" 
                  class="btn btn-primary btn-gradient"
                  @click="previousStep"
                >
                  <i class="fas fa-arrow-left me-2"></i>Voltar
                </button>
                <div class="ms-auto">
                  <button 
                    v-if="currentStep < 3" 
                    type="button" 
                    class="btn btn-primary"
                    @click="nextStep"
                    :disabled="!canProceed"
                  >
                    Próximo<i class="fas fa-arrow-right ms-2"></i>
                  </button>
                  <button 
                    v-else 
                    type="submit" 
                    class="btn btn-success"
                    :disabled="submitting || !isFormValid"
                  >
                    <span v-if="submitting">
                      <i class="fas fa-spinner fa-spin me-2"></i>Enviando...
                    </span>
                    <span v-else>
                      <i class="fas fa-paper-plane me-2"></i>Enviar Denúncia
                    </span>
                  </button>
                </div>
              </div>
            </form>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
import '../../assets/styles/NewReport.css'
import flagImage from '../../assets/img/MZ.png'
import LocationPicker from '../../components/LocationPicker.vue'

export default {
  name: 'NewReport',
  components: {
    LocationPicker
  },
  data() {
    return {
      currentStep: 1,
      submitting: false,
      loadingInstitutions: false,
      loadingReportTypes: false,
      loadingClassifications: false,
      phoneFields: [
        { country: '+258', number: '' }
      ],
      flagImage: flagImage,
      formData: {
        // Dados Pessoais
        name: '',
        phoneNumbers: [], // será preenchido dinamicamente
        email: '',
        
        // Tipo de Denúncia
        title: '',
        description: '',
        instituitionId: '',
        reportTypeId: '',
        classificationId: '',
        
        // Detalhes
        files: [],
        location: {
          latitude: null,
          longitude: null,
          description: ''
        }
      },
      institutions: [], // agora será preenchido via API
      reportTypes: [], // agora será preenchido via API
      classifications: [], // NOVO ARRAY
      errors: {},
      today: new Date().toISOString().split('T')[0],
    }
  },
  computed: {
    filteredInstitutions() {
      // Sempre retorna apenas a instituição EDM
      return this.institutions;
    },
    canProceed() {
      if (this.currentStep === 1) {
        return this.formData.name && this.formData.phoneNumbers.length > 0 && this.formData.email;
      } else if (this.currentStep === 2) {
        return this.formData.title && this.formData.reportTypeId && this.formData.instituitionId && this.formData.classificationId;
      }
      return true;
    },
    isFormValid() {
      return this.canProceed && this.formData.description && this.formData.description.length >= 50;
    },
    descriptionLengthClass() {
      const length = this.formData.description.length;
      if (length < 50) return 'text-danger';
      if (length > 1800) return 'text-warning';
      return 'text-success';
    }
  },
  watch: {
    'formData.instituitionId': function(newId) {
      this.formData.reportTypeId = '';
      if (newId) {
        this.fetchReportTypesByInstituition(newId);
      } else {
        this.reportTypes = [];
      }
    }
  },
  created() {
    this.fetchInstitutions();
    this.fetchReportTypes();
    this.fetchClassifications();
    this.phoneInput = this.formData.phoneNumbers.join(', ');
  },
  methods: {
    goBack() {
      this.$router.push('/');
    },
    addPhoneField() {
      this.phoneFields.push({ country: '+258', number: '' });
    },
    removePhoneField(idx) {
      if (this.phoneFields.length > 1) {
        this.phoneFields.splice(idx, 1);
      }
    },
    formatPhoneNumber(idx) {
      // Remove tudo que não é número
      let value = this.phoneFields[idx].number.replace(/\D/g, '');
      
      // Limita a 9 dígitos
      if (value.length > 9) {
        value = value.substring(0, 9);
      }
      
      // Formata o número (XX XXX XXXX)
      if (value.length >= 6) {
        value = value.replace(/(\d{2})(\d{3})(\d{4})/, '$1 $2 $3');
      } else if (value.length >= 3) {
        value = value.replace(/(\d{2})(\d{3})/, '$1 $2');
      }
      
      this.phoneFields[idx].number = value;
    },
    updatePhoneNumbers() {
      // Atualiza formData.phoneNumbers com base nos campos dinâmicos
      this.formData.phoneNumbers = this.phoneFields
        .map(p => '+258' + p.number.replace(/\s/g, ''))
        .filter(num => {
          const digits = num.replace(/\D/g, '');
          return digits.length === 12; // +258 + 9 dígitos = 12
        });
    },
    
    nextStep() {
      if (this.validateCurrentStep()) {
        this.currentStep++;
      }
    },
    
    previousStep() {
      this.currentStep--;
    },
    
    validateCurrentStep() {
      this.errors = {};
      let isValid = true;
      
      if (this.currentStep === 1) {
        if (!this.formData.name) {
          this.errors.name = 'Nome é obrigatório';
          isValid = false;
        }
        this.updatePhoneNumbers();
        if (!this.formData.phoneNumbers || this.formData.phoneNumbers.length === 0) {
          this.errors.phone = 'Pelo menos um número de telefone válido é obrigatório (9 dígitos)';
          isValid = false;
        }
        if (!this.formData.email) {
          this.errors.email = 'Email é obrigatório';
          isValid = false;
        } else if (!this.isValidEmail(this.formData.email)) {
          this.errors.email = 'Email inválido';
          isValid = false;
        }
      } else if (this.currentStep === 2) {
        if (!this.formData.title) {
          this.errors.title = 'Título é obrigatório';
          isValid = false;
        }
        if (!this.formData.reportTypeId) {
          this.errors.type = 'Tipo de denúncia é obrigatório';
          isValid = false;
        }
        if (!this.formData.instituitionId) {
          this.errors.institution = 'Instituição é obrigatória';
          isValid = false;
        }
        if (!this.formData.classificationId) {
          this.errors.classification = 'Classificação é obrigatória';
          isValid = false;
        }
      } else if (this.currentStep === 3) {
        if (!this.formData.description || this.formData.description.length < 50) {
          this.errors.description = 'Descrição deve ter pelo menos 50 caracteres';
          isValid = false;
        }
        // Validação de localização
        if (!this.formData.location.latitude || !this.formData.location.longitude) {
          this.errors.location = 'Localização é obrigatória';
          isValid = false;
        }
        if (!this.formData.location.description || this.formData.location.description.length < 5) {
          this.errors.locationDescription = 'Descrição do local é obrigatória';
          isValid = false;
        }
      }
      
      return isValid;
    },
    
    validateField(fieldName) {
      switch (fieldName) {
        case 'name':
          if (!this.formData.name) {
            this.errors.name = 'Nome é obrigatório';
          } else {
            delete this.errors.name;
          }
          break;
        case 'email':
          if (!this.formData.email) {
            this.errors.email = 'Email é obrigatório';
          } else if (!this.isValidEmail(this.formData.email)) {
            this.errors.email = 'Email inválido';
          } else {
            delete this.errors.email;
          }
          break;
        case 'phone':
          this.updatePhoneNumbers();
          if (!this.formData.phoneNumbers || this.formData.phoneNumbers.length === 0) {
            this.errors.phone = 'Pelo menos um número de telefone válido é obrigatório (9 dígitos)';
          } else {
            delete this.errors.phone;
          }
          break;
        case 'title':
          if (!this.formData.title) {
            this.errors.title = 'Título é obrigatório';
          } else {
            delete this.errors.title;
          }
          break;
        case 'type':
          if (!this.formData.reportTypeId) {
            this.errors.type = 'Tipo de denúncia é obrigatório';
          } else {
            delete this.errors.type;
          }
          break;
        case 'institution':
          if (!this.formData.instituitionId) {
            this.errors.institution = 'Instituição é obrigatória';
          } else {
            delete this.errors.institution;
          }
          break;
        case 'classification':
          if (!this.formData.classificationId) {
            this.errors.classification = 'Classificação é obrigatória';
          } else {
            delete this.errors.classification;
          }
          break;
        case 'description':
          if (!this.formData.description || this.formData.description.length < 50) {
            this.errors.description = 'Descrição deve ter pelo menos 50 caracteres';
          } else {
            delete this.errors.description;
          }
          break;
      }
    },
    
    isValidEmail(email) {
      const re = /^[^\s@]+@[^\s@]+\.[^\s@]+$/;
      return re.test(email);
    },
    
    handleFiles(event) {
      const files = Array.from(event.target.files);
      this.addFiles(files);
    },
    
    handleDrop(event) {
      event.preventDefault();
      const files = Array.from(event.dataTransfer.files);
      this.addFiles(files);
    },
    
    addFiles(files) {
      const maxSize = 10 * 1024 * 1024; // 10MB
      const validFiles = files.filter(file => {
        if (file.size > maxSize) {
          this.$toast.error(`Arquivo ${file.name} é muito grande (máximo 10MB)`);
          return false;
        }
        return true;
      });
      
      this.formData.files = [...this.formData.files, ...validFiles];
    },
    
    removeFile(index) {
      this.formData.files.splice(index, 1);
    },
    
    getFileIcon(fileType) {
      if (fileType.startsWith('image/')) return 'fas fa-image text-success';
      if (fileType.startsWith('video/')) return 'fas fa-play-circle text-info';
      if (fileType.includes('pdf')) return 'fas fa-file-pdf text-danger';
      if (fileType.includes('word')) return 'fas fa-file-word text-primary';
      return 'fas fa-file text-secondary';
    },
    
    formatFileSize(bytes) {
      if (bytes === 0) return '0 Bytes';
      const k = 1024;
      const sizes = ['Bytes', 'KB', 'MB', 'GB'];
      const i = Math.floor(Math.log(bytes) / Math.log(k));
      return parseFloat((bytes / Math.pow(k, i)).toFixed(2)) + ' ' + sizes[i];
    },
    
    async fetchReportTypes() {
      this.loadingReportTypes = true;
      try {
        const res = await fetch('http://localhost:8080/sisden/api/report-type');
        this.reportTypes = await res.json();
      } catch (e) {
        this.$toast && this.$toast.error('Erro ao carregar tipos de denúncia');
      } finally {
        this.loadingReportTypes = false;
      }
    },
    async fetchInstitutions() {
      this.loadingInstitutions = true;
      try {
        const res = await fetch('http://localhost:8080/sisden/api/instituitions');
        this.institutions = await res.json();
      } catch (e) {
        this.$toast && this.$toast.error('Erro ao carregar instituições');
      } finally {
        this.loadingInstitutions = false;
      }
    },
    async fetchClassifications() {
      this.loadingClassifications = true;
      try {
        const res = await fetch('http://localhost:8080/sisden/api/report-classification');
        this.classifications = await res.json();
      } catch (e) {
        this.$toast && this.$toast.error('Erro ao carregar classificações');
      } finally {
        this.loadingClassifications = false;
      }
    },
    async fetchReportTypesByInstituition(instituitionId) {
      this.loadingReportTypes = true;
      try {
        const res = await fetch(`http://localhost:8080/sisden/api/report-type/instituition/${instituitionId}`);
        this.reportTypes = await res.json();
      } catch (e) {
        this.$toast && this.$toast.error('Erro ao carregar tipos de denúncia para a instituição');
        this.reportTypes = [];
      } finally {
        this.loadingReportTypes = false;
      }
    },
    async submitReport() {
      if (!this.validateCurrentStep()) return;
      
      this.submitting = true;
      try {
        // Convert files to base64
        const filesBase64 = await Promise.all(
          this.formData.files.map(async (file) => {
            return new Promise((resolve) => {
              const reader = new FileReader();
              reader.onload = (e) => {
                const base64Data = e.target.result.split(',')[1];
                resolve({
                  fileName: file.name,
                  contentType: file.type,
                  data: base64Data
                });
              };
              reader.readAsDataURL(file);
            });
          })
        );

        // Prepare the data structure according to API requirements
        const reportData = {
          name: this.formData.name.trim(),
          phoneNumbers: [...this.formData.phoneNumbers],
          email: this.formData.email.trim(),
          title: this.formData.title.trim(),
          description: this.formData.description.trim(),
          instituitionId: Number(this.formData.instituitionId),
          reportTypeId: Number(this.formData.reportTypeId),
          files: filesBase64,
          location: {
            latitude: this.formData.location.latitude,
            longitude: this.formData.location.longitude,
            description: this.formData.location.description
          }
        };

        const response = await fetch('http://localhost:8080/sisden/api/reports', {
          method: 'POST',
          headers: {
            'Content-Type': 'application/json'
          },
          body: JSON.stringify(reportData)
        });

        let responseData;
        try {
          responseData = await response.json();
        } catch (jsonError) {
          // Se não for JSON, tente ler como texto e mostrar mensagem amigável
          const text = await response.text();
          responseData = { message: 'Resposta inesperada do servidor', raw: text };
        }
        
        if (response.ok) {
          if (this.$toast) this.$toast.success(`Denúncia enviada com sucesso!`);
          // Espera para mostrar toast
          await new Promise(resolve => setTimeout(resolve, 2000));
          // Redireciona para página de sucesso, passando os nomes selecionados
          const instituitionName = this.institutions.find(i => i.id == this.formData.instituitionId)?.name || '';
          const reportTypeName = this.reportTypes.find(t => t.id == this.formData.reportTypeId)?.name || '';
          this.$router.push({ name: 'ReportSuccess', query: { instituitionName, reportTypeName } });
        } else {
          if (this.$toast) this.$toast.error(`Erro: ${responseData.message || 'Erro ao enviar denúncia'}\nStatus: ${response.status}`);
          throw new Error(responseData.message || 'Erro ao enviar denúncia');
        }
      } catch (error) {
        if (this.$toast) this.$toast.error(`Erro: ${error && error.message ? error.message : 'Erro ao enviar denúncia. Tente novamente.'}`);
      } finally {
        this.submitting = false;
      }
    },
    onReportTypeChange() {
      this.validateField('type');
    }
  }
}
</script>