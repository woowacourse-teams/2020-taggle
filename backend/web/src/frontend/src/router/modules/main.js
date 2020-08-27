import MainPage from '@/views/main/MainPage.vue';
import bookmarkRoutes from '@/router/modules/bookmark.js';
import profileRoutes from '@/router/modules/profile.js';

const mainChild = [...bookmarkRoutes, ...profileRoutes];

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
