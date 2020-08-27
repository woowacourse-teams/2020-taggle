import Vue from 'vue';
import vueGtag from './plugins/vue-gtag.js';
import App from './App.vue';
import router from './router/index.js';
import store from './store/index.js';
import vuetify from './plugins/vuetify.js';

Vue.config.productionTip = false;

new Vue({
  vueGtag,
  router,
  store,
  vuetify,
  render: (h) => h(App),
}).$mount('#app');
