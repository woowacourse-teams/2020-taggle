import CategoryService from '@/api/module/category.js';
import {
  CREATE_CATEGORY,
  DELETE_CATEGORY,
  EDIT_CATEGORY,
  EDIT_TAG,
  FETCH_CATEGORIES,
} from '@/store/share/actionTypes.js';
import { SET_CATEGORIES } from '@/store/share/mutationTypes.js';
import { CATEGORIES, ALL_CATEGORIES_FOR_SELECT } from '@/store/share/getterTypes.js';

const state = {
  categories: [],
};
const getters = {
  [CATEGORIES](state) {
    return state.categories;
  },
  [ALL_CATEGORIES_FOR_SELECT](state) {
    return state.categories
      .filter((category) => category.id !== null)
      .map((category) => {
        return { text: category.title, value: category.id };
      });
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
  async [CREATE_CATEGORY](context, newCategory) {
    return CategoryService.create(newCategory);
  },
  async [EDIT_CATEGORY](context, { id, title }) {
    return CategoryService.edit(id, { title });
  },
  async [DELETE_CATEGORY](context, categoryId) {
    return CategoryService.delete(categoryId);
  },
  async [EDIT_TAG](context, { categoryId, tagId }) {
    return CategoryService.editTag(categoryId, tagId);
  },
};

export default {
  state,
  getters,
  actions,
  mutations,
};
