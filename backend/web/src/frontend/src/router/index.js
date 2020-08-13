import Vue from 'vue';
import VueRouter from 'vue-router';

import bookmarkRoutes from '@/router/modules/bookmark.js';
import authRoutes from '@/router/modules/auth.js';

import store from '@/store';
import LoginPage from '@/views/auth/LoginPage.vue';

import store from '@/store';

Vue.use(VueRouter);

const routes = [
  ...bookmarkRoutes,
  ...authRoutes,
  {
    name: 'login',
    path: '/login',
    component: LoginPage,
  },
];

const authenticate = (to, from, next) => {
  if (to.matched.some((routeInfo) => routeInfo.meta.requiresAuth) && !store.getters.isAuthenticated) {
    next('/login');
    return;
  }
  next();
};

const router = new VueRouter({
  mode: 'history',
  base: process.env.BASE_URL,
  routes,
  beforeEach: authenticate,
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
