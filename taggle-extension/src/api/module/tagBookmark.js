import ApiService from '../index.js';

const BASE_URL = `/api/v1/tags`;

const TagBookmarkService = {
  create(tagId, bookmarkId) {
    return ApiService.post(`${BASE_URL}/${tagId}/bookmarks/${bookmarkId}`);
  },
};

export default TagBookmarkService;
