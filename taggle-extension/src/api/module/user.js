import ApiService from '@/api/index.js';

const BASE_URL = '/api/v1';

const UserService = {
  loggedIn() {
    return ApiService.get(`${BASE_URL}/me`);
  },
};

export default UserService;
