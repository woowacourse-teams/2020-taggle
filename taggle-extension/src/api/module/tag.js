import ApiService from '../index.js';

const BASE_URL = '/api/v1/tags';

const TagService = {
  create(newTag) {
    return ApiService.post(`${BASE_URL}`, newTag).then((location) => {
      const locations = location.split('/');
      return locations[locations.length - 1];
    });
  },
  addBookmarkOnTag(bookmarkId, tagId) {
    ApiService.post(`${BASE_URL}/${tagId}/bookmarks/${bookmarkId}`);
  },
  removeBookmarkOnTag(bookmarkId, tagId) {
    ApiService.delete(`${BASE_URL}/${tagId}/bookmarks/${bookmarkId}`);
  },
};

export default TagService;
