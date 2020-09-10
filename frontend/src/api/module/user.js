import ApiService from '@/api/index.js';

const BASE_URL = '/api/v1/me';

const UserService = {
  getUserInfo() {
    return ApiService.get(`${BASE_URL}`);
  },
  updateProfile(profileUpdateRequest) {
    return ApiService.put(`${BASE_URL}`, profileUpdateRequest);
  },
  deleteUser() {
    return ApiService.delete(`${BASE_URL}`);
  },
};

export default UserService;
