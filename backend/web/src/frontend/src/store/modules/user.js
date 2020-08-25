import UserService from '@/api/module/user.js';
import { FETCH_USER, DELETE_USER } from '@/store/share/actionTypes.js';
import { SET_USER } from '@/store/share/mutationTypes.js';
import { USER } from '@/store/share/getterTypes.js';

const state = {
  user: {
    id: '',
    nickName: '',
    email: '',
    phoneNumber: '',
    picture: '',
    role: '',
  },
};
const getters = {
  [USER](state) {
    return state.user;
  },
};
const mutations = {
  [SET_USER](state, user) {
    state.user = user;
  },
};
const actions = {
  async [FETCH_USER]({ commit }) {
    const res = await UserService.getUserInfo();
    const user = res.data;
    commit(SET_USER, user);
  },
  async [DELETE_USER]() {
    await UserService.deleteUser();
  },
};

export default {
  state,
  getters,
  mutations,
  actions,
};
