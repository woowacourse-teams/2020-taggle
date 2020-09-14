import Vue from 'vue';
import store from '@/store';
import App from '@/popup/App';
import vuetify from '@/plugins/vuetify.js';

global.browser = require('webextension-polyfill');

Vue.prototype.$browser = global.browser;

/* eslint-disable no-new */
new Vue({
  el: '#app',
  store,
  vuetify,
  render: (h) => h(App),
});
