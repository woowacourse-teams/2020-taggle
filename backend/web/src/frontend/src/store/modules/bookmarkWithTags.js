import BookmarkService from '@/api/module/bookmark.js';
import TagService from '@/api/module/tag.js';
import {
  FETCH_BOOKMARK_WITH_TAGS,
  CREATE_BOOKMARK,
  ADD_TAG_ON_BOOKMARK,
  DELETE_TAG_ON_BOOKMARK,
} from '@/store/share/actionTypes.js';
import { SET_BOOKMARK_TAGS, RESET_BOOKMARK_WITH_TAGS } from '@/store/share/mutationTypes.js';
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
  async [FETCH_BOOKMARK_WITH_TAGS]({ commit }, { bookmarkId }) {
    const res = await BookmarkService.findBookmarkWithTags(bookmarkId);
    const bookmarkTags = res.data;
    commit(SET_BOOKMARK_TAGS, bookmarkTags);
  },
  async [CREATE_BOOKMARK](context, bookmarkCreateRequest) {
    return BookmarkService.post(bookmarkCreateRequest);
  },
  async [ADD_TAG_ON_BOOKMARK](context, { bookmarkId, tagId }) {
    return TagService.addBookmarkOnTag(tagId, bookmarkId);
  },
  async [DELETE_TAG_ON_BOOKMARK](context, { bookmarkId, tagId }) {
    return TagService.deleteBookmarkOnTag(tagId, bookmarkId);
  },
};

export default {
  state,
  getters,
  actions,
  mutations,
};
