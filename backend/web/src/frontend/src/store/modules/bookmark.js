import BookmarkService from '@/api/module/bookmark.js';
import { FETCH_BOOKMARKS, CREATE_BOOKMARKS, DELETE_BOOKMARKS } from '@/store/share/actionTypes.js';
import { SET_BOOKMARKS } from '@/store/share/mutationTypes.js';

const state = {
  tagBookmarks: [],
  bookmark: {},
};
const getters = {
  bookmarks(state) {
    return state.tagBookmarks.bookmarks;
  },
};
const mutations = {
  [SET_BOOKMARKS](state, bookmarks) {
    state.tagBookmarks = bookmarks;
  },
};
const actions = {
  async [FETCH_BOOKMARKS]({ commit }, { tagId }) {
    const res = await BookmarkService.getAll(tagId);
    const bookmarks = res.data;
    commit(SET_BOOKMARKS, bookmarks);
  },
  async [CREATE_BOOKMARKS](context, newBookmark) {
    return BookmarkService.post(newBookmark);
  },

  async [DELETE_BOOKMARKS](context, bookmarkId) {
    return BookmarkService.delete(bookmarkId);
  },
};

export default {
  state,
  getters,
  actions,
  mutations,
};
