import LoginPage from '@/views/auth/LoginPage.vue';

const authRoutes = [
  {
    name: 'login',
    path: '/login',
    component: LoginPage,
    meta: { requiresAuth: false },
  },
];

export default authRoutes;
