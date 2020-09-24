import BookmarkPage from '@/views/bookmark/BookmarkPage.vue';

const bookmarkRoutes = [
  {
    name: 'bookmarks',
    path: '/bookmarks',
    component: BookmarkPage,
    props: (route) => ({
      tag: route.query.id,
    })
  },
];

export default bookmarkRoutes;
