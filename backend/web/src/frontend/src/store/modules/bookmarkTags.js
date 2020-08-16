import BookmarkService from '@/api/module/bookmark.js';
import { FETCH_BOOKMARK_TAGS } from '@/store/share/actionTypes.js';
import { SET_BOOKMARK_TAGS } from '@/store/share/mutationTypes.js';

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
  bookmarkTags(state) {
    return state.bookmarkTags.tags;
  },
};
const mutations = {
  [SET_BOOKMARK_TAGS](state, tagBookmark) {
    state.tagBookmarks = tagBookmark;
  },
};
const actions = {
  async [FETCH_BOOKMARK_TAGS]({ commit }, bookmarkId) {
    const res = await BookmarkService.findBookmarkWithTags(bookmarkId);
    const bookmarkTags = res.data;
    commit(SET_BOOKMARK_TAGS, bookmarkTags);
  },
};

export default {
  state,
  getters,
  actions,
  mutations,
};
