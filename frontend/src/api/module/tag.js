import ApiService from '@/api/index.js';

const BASE_URL = '/api/v1/tags';

const TagService = {
  findTagWithBookmarks(tagId) {
    return ApiService.get(`${BASE_URL}/${tagId}/bookmarks`);
  },
  create(tagCreateRequest) {
    return ApiService.post(`${BASE_URL}`, tagCreateRequest).then(({ data }) => data.id);
  },
  findBookmarksByTagId(tagId, offset, limit) {
    return ApiService.get(`${BASE_URL}/${tagId}/bookmarks?offset=${offset}&limit=${limit}`);
  },
  update(tagId, tagUpdateRequest) {
    return ApiService.put(`${BASE_URL}/${tagId}`, tagUpdateRequest);
  },
  delete(tagId) {
    return ApiService.delete(`${BASE_URL}/${tagId}`);
  },
};

export default TagService;
