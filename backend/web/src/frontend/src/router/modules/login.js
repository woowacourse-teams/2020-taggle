import LoginPage from '@/views/auth/LoginPage.vue';

const loginRoutes = [
  {
    name: 'login',
    path: '/signin',
    component: LoginPage,
    meta: { requiresAuth: false },
  },
];

export default loginRoutes;
