import Vue from 'vue';
import VueGtag from './analytics/index.js';
import App from './App.vue';
import router from './router/index.js';
import store from './store/index.js';
import vuetify from './plugins/vuetify.js';

Vue.config.productionTip = false;

new Vue({
  VueGtag,
  router,
  store,
  vuetify,
  render: (h) => h(App),
}).$mount('#app');
