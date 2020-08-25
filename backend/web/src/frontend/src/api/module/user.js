import ApiService from '@/api/index.js';

const BASE_URL = '/api/v1/users';

const UserService = {
  getUserInfo() {
    return ApiService.get(`${BASE_URL}/user-info`);
  },
  updateNotificationEmail(notificationEmail) {
    return ApiService.put(`${BASE_URL}/notification-enabled`, notificationEmail);
  },
  updateNotificationEnabled(notificationEnabled) {
    return ApiService.put(`${BASE_URL}/notification-enabled`, notificationEnabled);
  },
  deleteUser() {
    return ApiService.delete(BASE_URL);
  },
};

export default UserService;
