import ApiService from '@/api/index.js';

const BASE_URL = '/api/v1/users';

const UserService = {
  loggedIn() {
    return ApiService.get(`${BASE_URL}/loggedIn`);
  },
};

export default UserService;
