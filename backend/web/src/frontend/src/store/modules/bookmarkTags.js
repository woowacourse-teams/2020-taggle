import BookmarkService from '@/api/module/bookmark.js';
import { FETCH_BOOKMARK_WITH_TAGS } from '@/store/share/actionTypes.js';
import { SET_BOOKMARK_TAGS } from '@/store/share/mutationTypes.js';
import { BOOKMARK_TAGS, GET_TAG_ID_BY_NAME } from '@/store/share/getterTypes.js';

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
  [BOOKMARK_TAGS](state) {
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
};
const actions = {
  async [FETCH_BOOKMARK_WITH_TAGS]({ commit }, bookmarkId) {
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
