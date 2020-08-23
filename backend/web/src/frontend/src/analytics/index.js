import Vue from 'vue';
import VueGtag from 'vue-gtag';
import router from '@/router';

Vue.use(
  VueGtag,
  {
    config: { id: 'UA-175962196-1' },
  },
  router,
);

export default VueGtag;
