import BookmarkPage from '@/views/bookmark/BookmarkPage.vue';

const bookmarkRoutes = [
  {
    name: 'bookmarks',
    path: '/bookmarks?tag=:id',
    component: BookmarkPage,
  },
];

export default bookmarkRoutes;
