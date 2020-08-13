import Vue from 'vue';
import VueRouter from 'vue-router';

import bookmarkRoutes from '@/router/modules/bookmark.js';

import store from '@/store';

Vue.use(VueRouter);

const routes = [...bookmarkRoutes];

const router = new VueRouter({
  mode: 'history',
  base: process.env.BASE_URL,
  routes,
});

router.beforeEach(async (to, from, next) => {
  await store.dispatch('login');
  if (!store.getters.isAuthenticated) {
    next();
  } else {
    next();
  }
});

export default router;
