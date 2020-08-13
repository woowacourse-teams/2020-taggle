import BookmarkService from '@/api/module/bookmark.js';
import { FETCH_BOOKMARKS } from '@/store/share/actionTypes.js';
import { SET_BOOKMARKS } from '@/store/share/mutationTypes.js';

const state = {
  bookmarks: [],
};
const getters = {
  bookmarks(state) {
    return state.bookmarks.bookmarks;
  },
};
const mutations = {
  [SET_BOOKMARKS](state, bookmarks) {
    state.bookmarks = bookmarks;
  },
};
const actions = {
  async [FETCH_BOOKMARKS]({ commit }, { tagId }) {
    const res = await BookmarkService.getAll(tagId);
    const bookmarks = res.data;
    commit(SET_BOOKMARKS, bookmarks);
  },
};

export default {
  state,
  getters,
  actions,
  mutations,
};
