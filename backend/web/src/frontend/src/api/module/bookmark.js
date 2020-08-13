import ApiService from '@/api/index.js';

const BASE_URL = '/api/v1/tags';

const BookmarkService = {
  getAll(tagId) {
    return ApiService.get(`${BASE_URL}/${tagId}/bookmarks`);
  },
};

export default BookmarkService;
