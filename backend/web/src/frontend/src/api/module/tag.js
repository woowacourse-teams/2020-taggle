import ApiService from '@/api/index.js';

const BASE_URL = '/api/v1/tags';

const TagService = {
  findTagWithBookmarks(tagId) {
    return ApiService.get(`${BASE_URL}/${tagId}`);
  },
  create(newTag) {
    return ApiService.post(`${BASE_URL}`, newTag).then(({ data }) => data.id);
  },
  addBookmarkOnTag(tagId, bookmarkId) {
    return ApiService.post(`${BASE_URL}/${tagId}/bookmarks/${bookmarkId}`);
  },
  deleteBookmarkOnTag(bookmarkId, tagId) {
    return ApiService.delete(`${BASE_URL}/${tagId}/bookmarks/${bookmarkId}`);
  },
};

export default TagService;
