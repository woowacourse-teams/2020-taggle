import Vue from 'vue';
import Vuex from 'vuex';
import bookmarkTags from '@/store/modules/bookmarkWithTags.js';
import tagBookmarks from '@/store/modules/tagWithBookmarks.js';
import category from '@/store/modules/category.js';
import snackbar from '@/store/modules/snackbar.js';
import user from '@/store/modules/user.js';

Vue.use(Vuex);

export default new Vuex.Store({
  modules: {
    bookmarkTags,
    tagBookmarks,
    category,
    snackbar,
    user,
  },
});
