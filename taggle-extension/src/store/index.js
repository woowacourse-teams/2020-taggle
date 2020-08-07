import Vue from 'vue';
import Vuex from 'vuex';
import {
  ADD_TAG_BOOKMARK,
  CREATE_TAG,
  DELETE_BOOKMARK,
  DELETE_TAG_BOOKMARK,
  CREATE_BOOKMARK,
} from './share/actionsType.js';
import TagService from '../api/module/tag.js';
import BookmarkService from '../api/module/bookmark.js';
import { RESET_BOOKMARK, SET_BOOKMARK } from './share/mutationsType.js';

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
  },
  getters: {
    bookmarkId(state) {
      return state.tagBookmark.id;
    },
    tagBookmarks(state) {
      return state.tagBookmark.tags;
    },
    getTagIdByName: (state) => (name) => {
      const find = state.tagBookmark.tags.find((tag) => tag.name === name);
      console.log(find);
      return find ? find.id : undefined;
    },
  },
  actions: {
    [CREATE_TAG]({ commit }, newTag) {
      return TagService.create(newTag);
    },
    [ADD_TAG_BOOKMARK]({ commit }, { bookmarkId, tagId }) {
      return TagService.addBookmarkOnTag(bookmarkId, tagId);
    },
    [DELETE_TAG_BOOKMARK]({ commit }, { bookmarkId, tagId }) {
      return TagService.removeBookmarkOnTag(bookmarkId, tagId);
    },
    async [CREATE_BOOKMARK]({ commit }, bookmark) {
      const bookmarkId = await BookmarkService.save(bookmark);
      const bookmarkResponse = await BookmarkService.find(bookmarkId);
      commit(SET_BOOKMARK, bookmarkResponse);
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
  },
});
