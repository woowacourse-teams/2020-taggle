import Vue from 'vue';
import VueRouter from 'vue-router';
import loginRoutes from '@/router/modules/login.js';
import mainRoutes from '@/router/modules/main.js';
import store from '@/store/index.js';
import { FETCH_USER } from '@/store/share/actionTypes.js';

Vue.use(VueRouter);

const routes = [...loginRoutes, ...mainRoutes];

const router = new VueRouter({
  mode: 'history',
  base: process.env.BASE_URL,
  routes,
});

router.beforeEach(async (to, from, next) => {
  if (to.matched.some((routeInfo) => routeInfo.meta.requiresAuth)) {
    try {
      await store.dispatch(FETCH_USER);
      if (to.name === 'main') {
        next('/bookmarks');
      } else {
        next();
      }
    } catch (e) {
      next('/signin');
    }
  } else {
    next();
  }
});

export default router;
