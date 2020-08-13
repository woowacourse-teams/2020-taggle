import Vue from 'vue';
import Vuex from 'vuex';
import auth from '@/store/modules/auth.js';
import bookmarkTags from '@/store/modules/bookmarkTags.js';
import tagBookmarks from '@/store/modules/tagBookmarks.js';
import category from '@/store/modules/category.js';
import snackbar from '@/store/modules/snackbar.js';

Vue.use(Vuex);

export default new Vuex.Store({
  modules: {
    auth,
    bookmarkTags,
    tagBookmarks,
    category,
    snackbar,
  },
});
