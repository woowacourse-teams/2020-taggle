import CategoryService from '@/api/module/category.js';
import { CREATE_CATEGORY, EDIT_TAG, FETCH_CATEGORIES } from '@/store/share/actionTypes.js';
import { SET_CATEGORIES } from '@/store/share/mutationTypes.js';

const state = {
  categories: [],
};
const getters = {
  categories(state) {
    return state.categories;
  },
};
const mutations = {
  [SET_CATEGORIES](state, categories) {
    state.categories = categories;
  },
};
const actions = {
  async [FETCH_CATEGORIES]({ commit }) {
    const res = await CategoryService.getAll();
    const categories = res.data;
    commit(SET_CATEGORIES, categories);
  },
  // eslint-disable-next-line no-unused-vars
  async [CREATE_CATEGORY]({ commit }, newCategory) {
    return CategoryService.create(newCategory);
  },
  // eslint-disable-next-line no-unused-vars
  async [EDIT_TAG]({ commit }, categoryId, tagId) {
    return CategoryService.changeTag(categoryId, tagId);
  },
};

export default {
  state,
  getters,
  actions,
  mutations,
};
