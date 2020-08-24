import Vue from 'vue';
import Vuex from 'vuex';
import {
  ADD_TAG_ON_BOOKMARK,
  CREATE_TAG,
  DELETE_BOOKMARK,
  DELETE_TAG_ON_BOOKMARK,
  CREATE_BOOKMARK,
  FETCH_BOOKMARK_WITH_TAGS,
} from '@/store/share/actionsType.js';
import TagService from '@/api/module/tag.js';
import BookmarkService from '@/api/module/bookmark.js';
import { RESET_BOOKMARK, SET_BOOKMARK, SHOW_SNACKBAR } from '@/store/share/mutationsType.js';
import { BOOKMARK_ID, IS_SHOW, MESSAGE, GET_TAG_ID_BY_NAME, TAGS_WITH_BOOKMARK } from '@/store/share/gettersType.js';

Vue.use(Vuex);

export default new Vuex.Store({
  state: {
    bookmarkWithTags: {
      id: '',
      tags: [
        {
          id: '',
          name: '',
        },
      ],
    },
    isShow: false,
    message: '',
  },
  getters: {
    [BOOKMARK_ID](state) {
      return state.bookmarkWithTags.id;
    },
    [GET_TAG_ID_BY_NAME]: (state) => (name) => {
      const tag = state.bookmarkWithTags.tags.find((item) => item.name === name);
      return tag ? tag.id : undefined;
    },
    [TAGS_WITH_BOOKMARK](state) {
      return state.bookmarkWithTags.tags;
    },
    [IS_SHOW](state) {
      return state.isShow;
    },
    [MESSAGE](state) {
      return state.message;
    },
  },
  actions: {
    async [FETCH_BOOKMARK_WITH_TAGS]({ commit }, bookmarkId) {
      const bookmarkResponse = await BookmarkService.getBookmarkWithTags(bookmarkId);
      commit(SET_BOOKMARK, bookmarkResponse);
    },
    async [CREATE_TAG](context, newTag) {
      return TagService.create(newTag);
    },
    async [ADD_TAG_ON_BOOKMARK](context, { bookmarkId, tagId }) {
      return TagService.addBookmarkOnTag(bookmarkId, tagId);
    },
    async [DELETE_TAG_ON_BOOKMARK](context, { bookmarkId, tagId }) {
      return TagService.deleteBookmarkOnTag(bookmarkId, tagId);
    },
    async [CREATE_BOOKMARK](context, bookmark) {
      return BookmarkService.create(bookmark);
    },
    async [DELETE_BOOKMARK](context) {
      await BookmarkService.delete(context.state.bookmarkWithTags.id);
      context.commit(RESET_BOOKMARK);
    },
  },
  mutations: {
    [SET_BOOKMARK](state, bookmark) {
      state.bookmarkWithTags = bookmark;
    },
    [RESET_BOOKMARK](state) {
      state.bookmarkWithTags = {};
    },
    [SHOW_SNACKBAR](state, message) {
      state.isShow = !state.isShow;
      state.message = message;
    },
  },
});
