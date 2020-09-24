import ApiService from '@/api/index.js';

const BASE_URL = '/api/v1/bookmarks';

const BookmarkService = {
  getBookmarkWithTags(bookmarkId) {
    return ApiService.get(`${BASE_URL}/${bookmarkId}`).then(({ data }) => data);
  },
  create(newBookmark) {
    return ApiService.post(BASE_URL, newBookmark).then(({ data }) => data.id);
  },
  delete(bookmarkId) {
    return ApiService.delete(`${BASE_URL}/${bookmarkId}`);
  },
};

export default BookmarkService;
