import TagService from '@/api/module/tag.js';
import {
  CLEAR_BOOKMARKS,
  CREATE_BOOKMARK,
  CREATE_TAG,
  DELETE_BOOKMARK,
  DELETE_TAG,
  UPDATE_TAG,
  FETCH_MORE_BOOKMARKS,
  FETCH_TAG_WITH_BOOKMARKS,
} from '@/store/share/actionTypes.js';
import { BOOKMARKS, IS_BOOKMARKS_EMPTY, TAG_ID } from '@/store/share/getterTypes.js';
import { ADD_MORE_BOOKMARKS, SET_BOOKMARKS, SET_TAG_BOOKMARKS } from '@/store/share/mutationTypes.js';
import BookmarkService from '@/api/module/bookmark.js';

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
  [SET_BOOKMARKS](state, bookmarks) {
    state.tagBookmarks.bookmarks = bookmarks;
  },
  [ADD_MORE_BOOKMARKS](state, bookmarks) {
    state.tagBookmarks.bookmarks = state.tagBookmarks.bookmarks.concat(bookmarks);
  },
};
const actions = {
  async [FETCH_TAG_WITH_BOOKMARKS]({ commit }, { tagId, offset, limit }) {
    const res = await BookmarkService.findBookmarksByTagId(tagId, offset, limit);
    const bookmarks = res.data;
    commit(SET_TAG_BOOKMARKS, bookmarks);
    return bookmarks.bookmarks;
  },
  async [FETCH_MORE_BOOKMARKS]({ commit }, { tagId, offset, limit }) {
    const res = await BookmarkService.findBookmarksByTagId(tagId, offset, limit);
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
  async [UPDATE_TAG](context, { updateTagRequest, tagId }) {
    return TagService.update(tagId, updateTagRequest);
  },
  async [CREATE_BOOKMARK](context, bookmarkCreateRequest) {
    return BookmarkService.create(bookmarkCreateRequest);
  },
  async [DELETE_BOOKMARK](context, { bookmarkId }) {
    const response = await BookmarkService.delete(bookmarkId);
    const filteredBookmarks = context.state.tagBookmarks.bookmarks.filter((bookmark) => bookmark.id !== bookmarkId);
    context.commit(SET_BOOKMARKS, filteredBookmarks);
    return response;
  },
};

export default {
  state,
  getters,
  actions,
  mutations,
};
