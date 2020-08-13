import Vue from 'vue';
import Vuex from 'vuex';
import auth from './modules/auth.js';
import bookmark from './modules/bookmark.js';
import category from './modules/category.js';

Vue.use(Vuex);

export default new Vuex.Store({
  modules: {
    auth,
    bookmark,
    category,
  },
});
