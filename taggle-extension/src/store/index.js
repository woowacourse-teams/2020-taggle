import Vue from 'vue';
import Vuex from 'vuex';
import {
  ADD_TAG_BOOKMARK,
  CREATE_TAG,
  DELETE_BOOKMARK,
  DELETE_TAG_BOOKMARK,
  CREATE_BOOKMARK,
  FETCH_TAG_BOOKMARK,
} from './share/actionsType.js';
import TagService from '../api/module/tag.js';
import BookmarkService from '../api/module/bookmark.js';
import { RESET_BOOKMARK, SET_BOOKMARK, SHOW_SNACKBAR } from './share/mutationsType.js';

Vue.use(Vuex);

export default new Vuex.Store({
  state: {
    tagBookmark: {
      id: 0,
      tags: [
        {
          id: 0,
          name: '',
        },
      ],
    },
    isShow: false,
    message: '',
  },
  getters: {
    bookmarkId(state) {
      return state.tagBookmark.id;
    },
    getTagIdByName: (state) => (name) => {
      const tag = state.tagBookmark.tags.find((item) => item.name === name);
      return tag ? tag.id : undefined;
    },
    isShow(state) {
      return state.isShow;
    },
    message(state) {
      return state.message;
    },
  },
  actions: {
    async [FETCH_TAG_BOOKMARK]({ commit }, bookmarkId) {
      const bookmarkResponse = await BookmarkService.findBookmarkTags(bookmarkId);
      commit(SET_BOOKMARK, bookmarkResponse);
    },
    [CREATE_TAG](context, newTag) {
      return TagService.create(newTag);
    },
    [ADD_TAG_BOOKMARK](context, { bookmarkId, tagId }) {
      return TagService.addBookmarkOnTag(bookmarkId, tagId);
    },
    [DELETE_TAG_BOOKMARK](context, { bookmarkId, tagId }) {
      return TagService.deleteBookmarkOnTag(bookmarkId, tagId);
    },
    [CREATE_BOOKMARK](context, bookmark) {
      return BookmarkService.save(bookmark);
    },
    async [DELETE_BOOKMARK](context) {
      await BookmarkService.delete(context.state.tagBookmark.id);
      context.commit(RESET_BOOKMARK);
    },
  },
  mutations: {
    [SET_BOOKMARK](state, bookmark) {
      state.tagBookmark = bookmark;
    },
    [RESET_BOOKMARK](state) {
      state.tagBookmark = {};
    },
    [SHOW_SNACKBAR](state, message) {
      state.isShow = !state.isShow;
      state.message = message;
    },
  },
});
