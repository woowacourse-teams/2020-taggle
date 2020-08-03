export const bookmarks = [
  {
    id: 1,
    url: 'https://www.naver.com',
  },
  {
    id: 2,
    url: 'http://www.vue-tags-input.com/#/',
  },
  {
    id: 3,
    url: 'https://github.com/next-step/atdd-subway-favorite/blob/favorite-sample/frontend/src/store/modules/line.js',
  },
];

export const tagMock = [
  {
    id: 1,
    name: '프론트',
  },
  {
    id: 2,
    name: '백엔드',
  },
  {
    id: 3,
    name: '포털',
  },
  {
    id: 4,
    name: '레퍼런스',
  },
  {
    id: 5,
    name: 'ssl',
  },
  {
    id: 6,
    name: '네이버',
  },
  {
    id: 7,
    name: 'vue',
  },
];

export const tagBookmarks = [
  {
    id: 1,
    url: 'https://www.naver.com',
    tags: [
      {
        id: 3,
        name: '포털',
      },
      {
        id: 6,
        name: '네이버',
      },
    ],
  },
  {
    id: 2,
    url: 'http://www.vue-tags-input.com/#/',
    tags: [],
  },
  {
    id: 3,
    url: 'https://github.com/next-step/atdd-subway-favorite/blob/favorite-sample/frontend/src/store/modules/line.js',
    tags: [
      {
        id: 1,
        name: '프론트',
      },
      {
        id: 4,
        name: '레퍼런스',
      },
      {
        id: 7,
        name: 'vue',
      },
    ],
  },
];