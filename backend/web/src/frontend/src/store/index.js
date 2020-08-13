import Vue from 'vue';
import Vuex from 'vuex';
import BookmarkService from '@/api/module/bookmark.js';
import CategoryService from '@/api/module/category.js';
import { FETCH_BOOKMARKS, FETCH_CATEGORIES } from '@/store/share/actionTypes.js';
import { SET_BOOKMARKS, SET_CATEGORIES } from '@/store/share/mutationTypes.js';

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
      const response = await BookmarkService.getAll(tagId);
      const bookmarks = response.data;
      commit(SET_BOOKMARKS, bookmarks);
    },
    async [FETCH_CATEGORIES]({ commit }) {
      const response = await CategoryService.getAll();
      const categories = response.data;
      commit(SET_CATEGORIES, categories);
    },
  },
});
