import TagService from '@/api/module/tag.js';
import { CLEAR_BOOKMARKS, FETCH_MORE_BOOKMARKS, FETCH_TAG_WITH_BOOKMARKS } from '@/store/share/actionTypes.js';
import { ADD_MORE_BOOKMARKS, SET_TAG_BOOKMARKS } from '@/store/share/mutationTypes.js';
import { BOOKMARKS, IS_BOOKMARKS_EMPTY } from '@/store/share/getterTypes.js';

const state = {
  tagBookmarks: {
    id: '',
    name: '',
    bookmarks: [],
  },
};
const getters = {
  [BOOKMARKS](state) {
    return state.tagBookmarks.bookmarks;
  },
  [IS_BOOKMARKS_EMPTY](state) {
    return state.tagBookmarks.bookmarks.length === 0;
  },
};
const mutations = {
  [SET_TAG_BOOKMARKS](state, tagBookmark) {
    state.tagBookmarks = tagBookmark;
  },
  [ADD_MORE_BOOKMARKS](state, bookmarks) {
    state.tagBookmarks.bookmarks = state.tagBookmarks.bookmarks.concat(bookmarks);
  },
};
const actions = {
  async [FETCH_TAG_WITH_BOOKMARKS]({ commit }, { tagId, offset, limit }) {
    const res = await TagService.findBookmarksByTagId(tagId, offset, limit);
    const bookmarks = res.data;
    commit(SET_TAG_BOOKMARKS, bookmarks);
    return bookmarks.bookmarks;
  },
  async [FETCH_MORE_BOOKMARKS]({ commit }, { tagId, start, display }) {
    const res = await TagService.findBookmarksByTagId(tagId, start, display);
    const { bookmarks } = res.data;
    commit(ADD_MORE_BOOKMARKS, bookmarks);
    return bookmarks;
  },
  [CLEAR_BOOKMARKS]({ commit }) {
    commit(SET_TAG_BOOKMARKS, {
      id: '',
      name: '',
      bookmarks: [],
    });
  },
};

export default {
  state,
  getters,
  actions,
  mutations,
};
