import Vue from 'vue';
import VueRouter from 'vue-router';
import loginRoutes from '@/router/modules/login.js';
import mainRoutes from '@/router/modules/main.js';
import UserService from '@/api/module/user.js';

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
      await UserService.getUserInfo();
      next();
    } catch (e) {
      next('/signin');
    }
  } else {
    next();
  }

  // try {
  //   await UserService.loggedIn();
  //   next();
  //   return;
  // } catch (e) {
  //   if (to.matched.some((routeInfo) => routeInfo.meta.requiresAuth)) {
  //     next({
  //       path: '/signin',
  //     });
  //   } else {
  //     next();
  //   }
  // }
});

export default router;
