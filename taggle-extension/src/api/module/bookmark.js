import ApiService from '../index.js';

const BASE_URL = '/api/v1/bookmarks';

const BookmarkService = {
  save(newBookmark) {
    return ApiService.post(`${BASE_URL}`, newBookmark);
  },
};

export default BookmarkService;
