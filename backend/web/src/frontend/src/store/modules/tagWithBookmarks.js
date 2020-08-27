import TagService from '@/api/module/tag.js';
import { FETCH_TAG_WITH_BOOKMARKS, CREATE_TAG, DELETE_TAG, FETCH_MORE_BOOKMARKS, CLEAR_BOOKMARKS } from '@/store/share/actionTypes.js';
import { SET_TAG_BOOKMARKS } from '@/store/share/mutationTypes.js';
import { BOOKMARKS, TAG_ID, IS_BOOKMARKS_EMPTY } from '@/store/share/getterTypes.js';
import { ADD_MORE_BOOKMARKS, SET_TAG_BOOKMARKS } from '@/store/share/mutationTypes.js';

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
  [TAG_ID](state) {
    return state.tagBookmarks.id;
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
  async [FETCH_MORE_BOOKMARKS]({ commit }, { tagId, offset, limit }) {
    const res = await TagService.findBookmarksByTagId(tagId, offset, limit);
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
  async [CREATE_TAG](context, tagCreateRequest) {
    return TagService.create(tagCreateRequest);
  },
  async [DELETE_TAG](context, { tagId }) {
    return TagService.delete(tagId);
  },
};

export default {
  state,
  getters,
  actions,
  mutations,
};
