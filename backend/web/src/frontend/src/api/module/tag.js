import ApiService from '@/api/index.js';

const BASE_URL = '/api/v1/tags';

const TagService = {
  findTagWithBookmarks(tagId) {
    return ApiService.get(`${BASE_URL}/${tagId}/bookmarks`);
  },
  create(tag) {
    return ApiService.post(`${BASE_URL}`, tag).then(({data}) => data.id);
  },
  findBookmarksByTagId(tagId, offset, limit) {
    return ApiService.get(`${BASE_URL}/${tagId}/bookmarks?offset=${offset}&limit=${limit}`);
  },
  addBookmarkOnTag(tagId, bookmarkId) {
    return ApiService.post(`${BASE_URL}/${tagId}/bookmarks/${bookmarkId}`);
  },
  delete(tagId) {
    return ApiService.delete(`${BASE_URL}/${tagId}`);
  },
  deleteBookmarkOnTag(tagId, bookmarkId) {
    return ApiService.delete(`${BASE_URL}/${tagId}/bookmarks/${bookmarkId}`);
  },
};

export default TagService;
