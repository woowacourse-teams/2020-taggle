import CategoryService from '@/api/module/category.js';
import { CREATE_CATEGORY, DELETE_CATEGORY, UPDATE_CATEGORY, FETCH_CATEGORIES } from '@/store/share/actionTypes.js';
import { SET_CATEGORIES } from '@/store/share/mutationTypes.js';
import { SEARCHED_CATEGORIES, TOTAL_CATEGORIES, ALL_CATEGORIES_FOR_SELECT } from '@/store/share/getterTypes.js';

const state = {
  categories: [],
};
const getters = {
  [TOTAL_CATEGORIES](state) {
    return state.categories;
  },
  [ALL_CATEGORIES_FOR_SELECT](state) {
    return state.categories
      .filter((category) => category.id !== null)
      .map((category) => {
        return { text: category.title, value: category.id };
      });
  },
  [SEARCHED_CATEGORIES]: (state) => (searchKeyword) =>
    state.categories.map((category) => {
      return {
        ...category,
        tags: category.tags.filter((tag) => tag.name.includes(searchKeyword)),
      };
    }),
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
  async [CREATE_CATEGORY](context, categoryCreateRequest) {
    return CategoryService.create(categoryCreateRequest);
  },
  async [UPDATE_CATEGORY](context, { categoryId, categoryUpdateRequest }) {
    return CategoryService.update(categoryId, categoryUpdateRequest);
  },
  async [DELETE_CATEGORY](context, categoryId) {
    return CategoryService.delete(categoryId);
  },
};

export default {
  state,
  getters,
  actions,
  mutations,
};
