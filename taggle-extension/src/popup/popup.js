import Vue from 'vue';
import App from './App';
import Buefy from 'buefy';
import 'buefy/dist/buefy.css';

Vue.use(Buefy);

global.browser = require('webextension-polyfill');
Vue.prototype.$browser = global.browser;

/* eslint-disable no-new */
new Vue({
  el: '#app',

  render: h => h(App),
});
np;
