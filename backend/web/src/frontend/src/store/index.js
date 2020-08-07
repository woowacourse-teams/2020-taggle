import Vue from 'vue';
import Vuex from 'vuex';
import BookmarkService from '../api/module/bookmark';
import { FETCH_BOOKMARKS } from './share/actionType';
import { SET_BOOKMARKS } from './share/mutationsType';

Vue.use(Vuex);

export default new Vuex.Store({
  state: {
    bookmarks: [
      {
        id: '',
        url: '',
        tags: [{ id: '3', name: 'jordy' }],
      },
    ],
  },
  getters: {
    bookmarks(state) {
      return state.bookmarks;
    },
  },
  mutations: {
    [SET_BOOKMARKS](state, bookmarks) {
      state.bookmarks = bookmarks;
    },
  },
  actions: {
    async [FETCH_BOOKMARKS]({ commit }) {
      return BookmarkService.getAll().then(({ data }) => {
        commit(SET_BOOKMARKS, data);
        return data;
      });
    },
  },
});
