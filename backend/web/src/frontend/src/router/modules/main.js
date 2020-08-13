import MainPage from '@/views/main/MainPage.vue';
import bookmarkRoutes from '@/router/modules/bookmark.js';

const mainChild = [...bookmarkRoutes];

const mainRoutes = [
  {
    name: 'main',
    path: '/',
    component: MainPage,
    meta: { requiresAuth: true },
    children: mainChild,
  },
];

export default mainRoutes;
