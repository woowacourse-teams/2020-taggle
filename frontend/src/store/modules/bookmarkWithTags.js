import BookmarkService from '@/api/module/bookmark.js';
import { CREATE_TAG_BOOKMARK, DELETE_TAG_BOOKMARK, FETCH_BOOKMARK_DETAIL, FETCH_TOTAL_BOOKMARKS, FETCH_MORE_TOTAL_BOOKMARKS, CLEAR_TOTAL_BOOKMARKS } from '@/store/share/actionTypes.js';
import { RESET_BOOKMARK_WITH_TAGS, SET_BOOKMARK_TAGS, SET_TOTAL_BOOKMARKS, ADD_MORE_TOTAL_BOOKMARKS } from '@/store/share/mutationTypes.js';
import { BOOKMARK_WITH_TAGS, GET_TAG_ID_BY_NAME, IS_TAGS_EMPTY, TOTAL_BOOKMARKS, IS_TOTAL_BOOKMARKS_EMPTY } from '@/store/share/getterTypes.js';

const state = {
  bookmarkTags: {
    id: '',
    url: '',
    title: '',
    image: '',
    description: '',
    tags: [{ id: '', name: '' }],
  },
  totalBookmarks: [],
};
const getters = {
  [BOOKMARK_WITH_TAGS](state) {
    return state.bookmarkTags.tags;
  },
  [IS_TAGS_EMPTY](state) {
    return !state.bookmarkTags || state.bookmarkTags.tags.length === 0;
  },
  [GET_TAG_ID_BY_NAME]: (state, getter) => (name) => {
    const tag = getter.bookmarkTags.find((item) => item.name === name);
    return tag ? tag.id : undefined;
  },
  [TOTAL_BOOKMARKS](state) {
    return state.totalBookmarks;
  },
  [IS_TOTAL_BOOKMARKS_EMPTY](state) {
    return state.totalBookmarks.length === 0;
  },
};
const mutations = {
  [SET_BOOKMARK_TAGS](state, bookmarkTags) {
    state.bookmarkTags = bookmarkTags;
  },
  [RESET_BOOKMARK_WITH_TAGS](state) {
    state.bookmarkTags = {};
  },
  [SET_TOTAL_BOOKMARKS](state, totalBookmarks) {
    state.totalBookmarks = totalBookmarks;
  },
  [ADD_MORE_TOTAL_BOOKMARKS](state, totalBookmarks) {
    state.totalBookmarks = state.totalBookmarks.concat(totalBookmarks);
  },
};
const actions = {
  async [FETCH_TOTAL_BOOKMARKS]({ commit }, { offset, limit }) {
    const res = await BookmarkService.findBookmarks(offset, limit);
    const totalBookmarks = res.data;
    commit(SET_TOTAL_BOOKMARKS, totalBookmarks);
    return totalBookmarks;
  },
  async [FETCH_MORE_TOTAL_BOOKMARKS]({ commit }, { offset, limit }) {
    const res = await BookmarkService.findBookmarks(offset, limit);
    const totalBookmarks = res.data;
    commit(ADD_MORE_TOTAL_BOOKMARKS, totalBookmarks);
    return totalBookmarks;
  },
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
  [CLEAR_TOTAL_BOOKMARKS]({ commit }) {
    commit(SET_TOTAL_BOOKMARKS, []);
  },
};

export default {
  state,
  getters,
  actions,
  mutations,
};
