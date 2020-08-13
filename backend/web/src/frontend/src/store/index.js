import Vue from 'vue';
import Vuex from 'vuex';
import router from '@/router';
import Cookie from '@/utils/cookie.js';
import BookmarkService from '@/api/module/bookmark.js';
import CategoryService from '@/api/module/category.js';
import { FETCH_BOOKMARKS, FETCH_CATEGORIES, LOGIN, LOGOUT } from '@/store/share/actionTypes.js';
import { SET_ACCESS_TOKEN, SET_BOOKMARKS, SET_CATEGORIES } from '@/store/share/mutationTypes.js';

Vue.use(Vuex);

export default new Vuex.Store({
  state: {
    accessToken: null,
    bookmarks: [],
    categories: [],
  },
  getters: {
    isAuthenticated(state) {
      return !!state.accessToken;
    },
    accessToken(state) {
      return state.accessToken;
    },
    bookmarks(state) {
      return state.bookmarks.bookmarks;
    },
    categories(state) {
      return state.categories;
    },
  },
  mutations: {
    [SET_ACCESS_TOKEN](state, accessToken) {
      state.accessToken = accessToken;
    },
    [SET_BOOKMARKS](state, bookmarks) {
      state.bookmarks = bookmarks;
    },
    [SET_CATEGORIES](state, categories) {
      state.categories = categories;
    },
  },
  actions: {
    async [LOGIN]({ commit }) {
      const accessToken = Cookie.getCookie('JSESSIONID');
      console.log(accessToken);
      commit(SET_ACCESS_TOKEN, accessToken);
    },
    async [LOGOUT]({ commit }) {
      commit(SET_ACCESS_TOKEN, null);
      await router.push('/login');
    },
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
