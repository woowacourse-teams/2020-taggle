import UserService from '@/api/module/user.js';
import {
  FETCH_USER,
  UPDATE_NOTIFICATION_EMAIL,
  UPDATE_NOTIFICATION_ENABLED,
  DELETE_USER,
} from '@/store/share/actionTypes.js';
import { SET_USER } from '@/store/share/mutationTypes.js';
import { USER } from '@/store/share/getterTypes.js';

const state = {
  user: {
    id: '',
    nickName: '',
    email: '',
    notificationEmail: '',
    phoneNumber: '',
    picture: '',
    notificationEnabled: '',
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
  // eslint-disable-next-line no-unused-vars
  async [UPDATE_NOTIFICATION_EMAIL]({ commit }, notificationEmail) {
    await UserService.updateNotificationEmail(notificationEmail);
  },
  // eslint-disable-next-line no-unused-vars
  async [UPDATE_NOTIFICATION_ENABLED]({ commit }, notificationEnabled) {
    await UserService.updateNotificationEnabled(notificationEnabled);
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
