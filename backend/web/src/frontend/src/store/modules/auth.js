import { LOGIN, LOGOUT } from '@/store/share/actionTypes.js';
import { SET_ACCESS_TOKEN } from '@/store/share/mutationTypes.js';
import router from '@/router';

const state = {
  accessToken: null,
};
const getters = {
  isAuthenticated(state) {
    return !!state.accessToken;
  },
  accessToken(state) {
    return state.accessToken;
  },
};
const mutations = {
  [SET_ACCESS_TOKEN](state, accessToken) {
    state.accessToken = accessToken;
  },
};
const actions = {
  async [LOGIN]({ commit }) {
    const accessToken = 'JSESSIONTOKEN';
    // const accessToken = Cookie.getCookie('JSESSIONID');
    commit(SET_ACCESS_TOKEN, accessToken);
  },
  async [LOGOUT]({ commit }) {
    commit(SET_ACCESS_TOKEN, null);
    await router.push('/login');
  },
};

export default {
  state,
  getters,
  actions,
  mutations,
};
