import Vue from 'vue';
import App from './App';
import vuetify from './../plugins/vuetify';

global.browser = require('webextension-polyfill');
Vue.prototype.$browser = global.browser;

/* eslint-disable no-new */
new Vue({
  el: '#app',
  vuetify,
  render: h => h(App),
});
np;
