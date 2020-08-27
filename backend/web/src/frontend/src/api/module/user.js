import ApiService from '@/api/index.js';

const BASE_URL = '/api/v1/users';

const UserService = {
  getUserInfo() {
    return ApiService.get(`${BASE_URL}/me`);
  },
  updateProfile(editingProfile) {
    return ApiService.put(`${BASE_URL}/me`, editingProfile);
  },
  deleteUser() {
    return ApiService.delete(`${BASE_URL}/me`);
  },
};

export default UserService;
