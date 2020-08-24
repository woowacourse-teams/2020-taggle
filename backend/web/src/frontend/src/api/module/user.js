import ApiService from '@/api/index.js';

const BASE_URL = '/api/v1/users';

const UserService = {
  getUserInfo() {
    return ApiService.get(`${BASE_URL}/user-info`);
  },
};

export default UserService;
