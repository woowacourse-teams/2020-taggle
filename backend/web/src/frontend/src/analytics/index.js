import VueGtag from 'vue-gtag';
import router from '@/router';

Vue.use(VueGtag);

export default new VueGtag({
  config: {
    id: 'UA-175962196-1',
  },
  router,
});
