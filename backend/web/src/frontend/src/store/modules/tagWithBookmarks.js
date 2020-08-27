import TagService from '@/api/module/tag.js';
import { FETCH_TAG_WITH_BOOKMARKS, CREATE_TAG, DELETE_TAG } from '@/store/share/actionTypes.js';
import { SET_TAG_BOOKMARKS } from '@/store/share/mutationTypes.js';
import { BOOKMARKS } from '@/store/share/getterTypes.js';

const state = {
  tagBookmarks: {
    id: '',
    name: '',
    bookmarks: [{ id: '', name: '', title: '', image: '', description: '' }],
  },
};
const getters = {
  [BOOKMARKS](state) {
    return state.tagBookmarks.bookmarks;
  },
};
const mutations = {
  [SET_TAG_BOOKMARKS](state, tagBookmark) {
    state.tagBookmarks = tagBookmark;
  },
};
const actions = {
  async [FETCH_TAG_WITH_BOOKMARKS]({ commit }, { tagId }) {
    const res = await TagService.findTagWithBookmarks(tagId);
    const bookmarks = res.data;
    commit(SET_TAG_BOOKMARKS, bookmarks);
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
