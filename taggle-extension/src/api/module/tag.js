import ApiService from '../index.js';

const BASE_URL = '/api/v1/tags';

const TagService = {
  create(newTag) {
    return ApiService.post(`${BASE_URL}`, newTag);
  },
  addBookmarkOnTag(bookmarkId, tagId) {
    return ApiService.post(`${BASE_URL}/${tagId}/bookmarks/${bookmarkId}`);
  },
  removeBookmarkOnTag(bookmarkId, tagId) {
    return ApiService.delete(`${BASE_URL}/${tagId}/bookmarks/${bookmarkId}`);
  },
};

export default TagService;
