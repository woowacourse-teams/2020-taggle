import Vue from 'vue';
import App from './App';
import store from '../store';
import vuetify from '../plugins/vuetify.js';

global.browser = require('webextension-polyfill');

Vue.prototype.$browser = global.browser;

/* eslint-disable no-new */
new Vue({
  el: '#app',
  store,
  vuetify,
  render: (h) => h(App),
});
