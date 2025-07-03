<template>
  <div class="location-picker mb-4">
    <label class="form-label">
      <i class="fas fa-map-marker-alt me-1"></i>
      Localização <span class="text-danger">*</span>
    </label>
    <div class="d-flex align-items-center mb-2">
      <button type="button" class="btn btn-outline-primary me-2" @click="getCurrentLocation">
        <i class="fas fa-crosshairs"></i> Usar minha localização atual
      </button>
      <span v-if="latitude && longitude" class="text-success ms-2">
        ({{ latitude.toFixed(5) }}, {{ longitude.toFixed(5) }})
      </span>
    </div>
    <div v-if="latitude && longitude" class="mb-2 text-info" style="font-size: 0.95rem;">
      Arraste o marcador ou clique no mapa para ajustar a localização.
    </div>
    <div v-if="error" class="text-danger mb-2">{{ error }}</div>
    <div v-if="latitude && longitude" class="mb-2">
      <div ref="map" class="map-container"></div>
    </div>
    <input
      type="text"
      class="form-control mt-2"
      placeholder="Descrição do local (ex: Esquina da Av. Principal com Rua Secundária)"
      v-model="desc"
      @input="emitAll"
      maxlength="120"
    >
  </div>
</template>

<script>
export default {
  name: 'LocationPicker',
  props: {
    latitude: Number,
    longitude: Number,
    description: String
  },
  emits: ['update:latitude', 'update:longitude', 'update:description'],
  data() {
    return {
      error: '',
      map: null,
      marker: null,
      desc: this.description || ''
    }
  },
  watch: {
    latitude(val) {
      if (val && this.longitude) this.initMap();
    },
    longitude(val) {
      if (val && this.latitude) this.initMap();
    },
    description(val) {
      this.desc = val;
    }
  },
  mounted() {
    if (this.latitude && this.longitude) {
      this.initMap();
    }
    this.loadGoogleMaps();
  },
  methods: {
    emitAll() {
      this.$emit('update:latitude', this.latitude);
      this.$emit('update:longitude', this.longitude);
      this.$emit('update:description', this.desc);
    },
    getCurrentLocation() {
      this.error = '';
      if (!navigator.geolocation) {
        this.error = 'Geolocalização não suportada pelo navegador.';
        return;
      }
      navigator.geolocation.getCurrentPosition(
        (pos) => {
          this.$emit('update:latitude', pos.coords.latitude);
          this.$emit('update:longitude', pos.coords.longitude);
          setTimeout(() => {
            this.initMap(pos.coords.latitude, pos.coords.longitude);
          }, 200);
        },
        (err) => {
          this.error = 'Não foi possível obter a localização: ' + err.message;
        }
      );
    },
    loadGoogleMaps() {
      if (window.google && window.google.maps) return;
      const script = document.createElement('script');
      script.src = 'https://maps.googleapis.com/maps/api/js?key=AIzaSyDJoMcIhUH_t6qwCReV0XNrn1s85Zl4lwM';
      script.async = true;
      script.defer = true;
      script.onload = () => {
        if (this.latitude && this.longitude) this.initMap();
      };
      document.head.appendChild(script);
    },
    initMap(lat, lng) {
      const latitude = lat || this.latitude;
      const longitude = lng || this.longitude;
      if (!window.google || !window.google.maps || !latitude || !longitude) return;
      if (!this.$refs.map) return;
      if (this.map) {
        this.map.setCenter({ lat: latitude, lng: longitude });
        if (this.marker) {
          this.marker.setPosition({ lat: latitude, lng: longitude });
        }
        return;
      }
      this.map = new window.google.maps.Map(this.$refs.map, {
        center: { lat: latitude, lng: longitude },
        zoom: 16
      });
      this.marker = new window.google.maps.Marker({
        position: { lat: latitude, lng: longitude },
        map: this.map,
        draggable: true
      });
      this.marker.addListener('dragend', (e) => {
        this.$emit('update:latitude', e.latLng.lat());
        this.$emit('update:longitude', e.latLng.lng());
      });
      // Permitir clicar no mapa para mover o marcador
      this.map.addListener('click', (e) => {
        const lat = e.latLng.lat();
        const lng = e.latLng.lng();
        this.marker.setPosition({ lat, lng });
        this.$emit('update:latitude', lat);
        this.$emit('update:longitude', lng);
      });
    }
  }
}
</script>

<style scoped>
.map-container {
  width: 100%;
  height: 220px;
  border-radius: 8px;
  border: 1px solid #dee2e6;
  margin-bottom: 8px;
}
</style> 