import BookmarkPage from '@/views/bookmark/BookmarkPage.vue';

const bookmarkRoutes = [
  {
    name: 'bookmarks',
    path: '/tags/:id/bookmarks',
    component: BookmarkPage,
  },
];

export default bookmarkRoutes;
