import Vue from 'vue';
import VueRouter from 'vue-router';
import bookmarkRoutes from '@/router/modules/bookmark.js';

Vue.use(VueRouter);

const routes = [...bookmarkRoutes];

const router = new VueRouter({
  mode: 'history',
  base: process.env.BASE_URL,
  routes,
});

export default router;
