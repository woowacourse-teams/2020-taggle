import ApiService from '../index.js';

const BASE_URL = '/api/v1/bookmarks';

const BookmarkService = {
  findBookmarkTags(bookmarkId) {
    return ApiService.get(`${BASE_URL}/${bookmarkId}/tags`);
  },
  save(newBookmark) {
    return ApiService.post(`${BASE_URL}`, newBookmark);
  },
  delete(id) {
    return ApiService.delete(`${BASE_URL}/${id}`);
  },
};

export default BookmarkService;
