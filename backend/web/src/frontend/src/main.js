import Vue from 'vue';
import vueGtag from "vue-gtag";
import App from './App.vue';
import router from './router/index.js';
import store from './store/index.js';
import vuetify from './plugins/vuetify.js';

Vue.config.productionTip = false;

Vue.use(vueGtag, {
  config: {id: "UA-175962196-1"}
}, router);

new Vue({
  router,
  store, 
  vuetify,
  render: (h) => h(App),
}).$mount('#app');
