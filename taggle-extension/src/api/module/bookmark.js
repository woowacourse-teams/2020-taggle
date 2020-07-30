import ApiService from '../index.js';

const BASE_URL = '/api/v1/bookmarks';

const BookmarkService = {
  save(newBookmark) {
    return ApiService.post(`${BASE_URL}`, newBookmark).then((location) => {
      const locations = location.split('/');
      return locations[locations.length - 1];
    });
  },
  delete(id) {
    ApiService.delete(`${BASE_URL}/${id}`);
  },
};

export default BookmarkService;
