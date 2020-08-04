import ApiService from '../index.js';

const BASE_URL = '/api/v1/bookmarks';

const BookmarkService = {
  getAll() {
    return ApiService.get(`${BASE_URL}`);
  },
};

export default BookmarkService;
