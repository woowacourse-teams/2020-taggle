import BookmarkService from '@/api/module/bookmark.js';
import { CREATE_TAG_BOOKMARK, DELETE_TAG_BOOKMARK, FETCH_BOOKMARK_DETAIL } from '@/store/share/actionTypes.js';
import { RESET_BOOKMARK_WITH_TAGS, SET_BOOKMARK_TAGS } from '@/store/share/mutationTypes.js';
import { BOOKMARK_WITH_TAGS, GET_TAG_ID_BY_NAME } from '@/store/share/getterTypes.js';

const state = {
  bookmarkTags: {
    id: '',
    url: '',
    title: '',
    image: '',
    description: '',
    tags: [{ id: '', name: '' }],
  },
};
const getters = {
  [BOOKMARK_WITH_TAGS](state) {
    return state.bookmarkTags.tags;
  },
  [GET_TAG_ID_BY_NAME]: (state, getter) => (name) => {
    const tag = getter.bookmarkTags.find((item) => item.name === name);
    return tag ? tag.id : undefined;
  },
};
const mutations = {
  [SET_BOOKMARK_TAGS](state, bookmarkTags) {
    state.bookmarkTags = bookmarkTags;
  },
  [RESET_BOOKMARK_WITH_TAGS](state) {
    state.bookmarkTags = {};
  },
};
const actions = {
  async [FETCH_BOOKMARK_DETAIL]({ commit }, { bookmarkId }) {
    const res = await BookmarkService.findBookmarkDetail(bookmarkId);
    const bookmarkTags = res.data;
    commit(SET_BOOKMARK_TAGS, bookmarkTags);
  },
  async [CREATE_TAG_BOOKMARK](context, { bookmarkId, tagId }) {
    return BookmarkService.createTagBookmark(bookmarkId, tagId);
  },
  async [DELETE_TAG_BOOKMARK](context, { bookmarkId, tagId }) {
    return BookmarkService.deleteTagBookmark(bookmarkId, tagId);
  },
};

export default {
  state,
  getters,
  actions,
  mutations,
};
