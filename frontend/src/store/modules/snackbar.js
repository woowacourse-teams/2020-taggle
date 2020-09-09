import { SHOW_SNACKBAR } from '@/store/share/mutationTypes.js';
import { IS_SHOWN_SNACKBAR, SNACKBAR_MESSAGE } from '@/store/share/getterTypes.js';

const state = {
  isShow: false,
  message: '',
};

const getters = {
  [IS_SHOWN_SNACKBAR](state) {
    return state.isShow;
  },
  [SNACKBAR_MESSAGE](state) {
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
