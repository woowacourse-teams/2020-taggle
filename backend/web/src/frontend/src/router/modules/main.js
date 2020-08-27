import MainPage from '@/views/main/MainPage.vue';
import bookmarkRoutes from '@/router/modules/bookmark.js';
import ErrorPage from '@/views/common/component/ErrorPage.vue';

const mainChild = [...bookmarkRoutes];

const mainRoutes = [
  {
    name: 'main',
    path: '/',
    component: MainPage,
    meta: { requiresAuth: true },
    children: mainChild,
  },
  {
    path: '*',
    redirect: '/404',
  },
  {
    path: '/404',
    component: ErrorPage,
  },
];

export default mainRoutes;
