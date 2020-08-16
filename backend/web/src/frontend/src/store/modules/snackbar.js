import { SHOW_SNACKBAR } from '@/store/share/mutationTypes.js';
import { IS_SHOW, MESSAGE } from '@/store/share/getterTypes.js';

const state = {
  isShow: false,
  message: '',
};

const getters = {
  [IS_SHOW](state) {
    return state.isShow;
  },
  [MESSAGE](state) {
    return state.message;
  },
};

const mutations = {
  [SHOW_SNACKBAR](state, message) {
    state.isShow = !state.isShow;
    state.message = message;
  },
};

export default {
  state,
  getters,
  mutations,
};
