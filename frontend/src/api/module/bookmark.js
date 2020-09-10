import ApiService from '@/api/index.js';

const BASE_URL = '/api/v1/bookmarks';

const BookmarkService = {
  findBookmarkWithTags(bookmarkId) {
    return ApiService.get(`${BASE_URL}/${bookmarkId}/tags`);
  },
  post(bookmarkCreateRequest) {
    return ApiService.post(BASE_URL, bookmarkCreateRequest).then(({ data }) => data.id);
  },
  delete(bookmarkId) {
    return ApiService.delete(`${BASE_URL}/${bookmarkId}`);
  },
};

export default BookmarkService;
