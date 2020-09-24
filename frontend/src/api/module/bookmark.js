import ApiService from '@/api/index.js';

const BASE_URL = '/api/v1/bookmarks';

const BookmarkService = {
  findBookmarks(offset, limit) {
    return ApiService.get(`${BASE_URL}?offset=${offset}&limit=${limit}`);
  },
  findBookmarksByTagId(tagId, offset, limit) {
    return ApiService.get(`${BASE_URL}?tag=${tagId}&offset=${offset}&limit=${limit}`);
  },
  findBookmarksWithUntagged(offset, limit) {
    return ApiService.get(`${BASE_URL}?tag=none&offset=${offset}&limit=${limit}`);
  },
  findBookmarkDetail(bookmarkId) {
    return ApiService.get(`${BASE_URL}/${bookmarkId}`);
  },
  create(bookmarkCreateRequest) {
    return ApiService.post(BASE_URL, bookmarkCreateRequest).then(({ data }) => data.id);
  },
  delete(bookmarkId) {
    return ApiService.delete(`${BASE_URL}/${bookmarkId}`);
  },
  createTagBookmark(bookmarkId, tagId) {
    return ApiService.post(`${BASE_URL}/${bookmarkId}/tags/${tagId}`);
  },
  deleteTagBookmark(bookmarkId, tagId) {
    return ApiService.delete(`${BASE_URL}/${bookmarkId}/tags/${tagId}`);
  },
};

export default BookmarkService;
