import ApiService from '@/api/index.js';

const BASE_URL = '/api/v1/tags';

const TagService = {
  create(newTag) {
    return ApiService.post(BASE_URL, newTag).then(({ data }) => data.id);
  },
  addBookmarkOnTag(bookmarkId, tagId) {
    return ApiService.post(`${BASE_URL}/${tagId}/bookmarks/${bookmarkId}`);
  },
  deleteBookmarkOnTag(bookmarkId, tagId) {
    return ApiService.delete(`${BASE_URL}/${tagId}/bookmarks/${bookmarkId}`);
  },
};

export default TagService;
