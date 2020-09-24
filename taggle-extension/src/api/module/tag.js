import ApiService from '@/api/index.js';

const BASE_URL = '/api/v1';

const TagService = {
  create(newTag) {
    return ApiService.post(`${BASE_URL}/tags`, newTag).then(({ data }) => data.id);
  },
  addBookmarkOnTag(bookmarkId, tagId) {
    return ApiService.post(`${BASE_URL}/bookmarks/${bookmarkId}/tags/${tagId}`);
  },
  deleteBookmarkOnTag(bookmarkId, tagId) {
    return ApiService.delete(`${BASE_URL}/bookmarks/${bookmarkId}/tags/${tagId}`);
  },
};

export default TagService;
