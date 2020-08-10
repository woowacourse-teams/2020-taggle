import Vue from 'vue';
import Vuex from 'vuex';
import BookmarkService from '../api/module/bookmark.js';
import CategoryService from '../api/module/category.js';
import { FETCH_BOOKMARKS, FETCH_CATEGORIES } from './share/actionType.js';
import { SET_BOOKMARKS, SET_CATEGORIES } from './share/mutationsType.js';

Vue.use(Vuex);

export default new Vuex.Store({
  state: {
    bookmarks: [],
    categories: [],
  },
  getters: {
    bookmarks(state) {
      return state.bookmarks.bookmarks;
    },
    categories(state) {
      return state.categories;
    },
  },
  mutations: {
    [SET_BOOKMARKS](state, bookmarks) {
      state.bookmarks = bookmarks;
    },
    [SET_CATEGORIES](state, categories) {
      state.categories = categories;
    },
  },
  actions: {
    async [FETCH_BOOKMARKS]({ commit }, { tagId }) {
      const res = await BookmarkService.getAll(tagId);
      const bookmarks = res.data;
      commit(SET_BOOKMARKS, bookmarks);
    },
    async [FETCH_CATEGORIES]({ commit }) {
      const res = await CategoryService.getAll();
      const categories = res.data;
      commit(SET_CATEGORIES, categories);
    },
  },
});
