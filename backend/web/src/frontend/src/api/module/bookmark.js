import ApiService from '@/api/index.js';
import axios from 'axios';

const BASE_URL = '/api/v1/tags';

const BookmarkService = {
  get(bookmarkId) {
    return ApiService.get(`/api/v1/bookmarks/${bookmarkId}/tags`);
  },
  getAll(tagId) {
    return ApiService.get(`${BASE_URL}/${tagId}/bookmarks`);
  },
  post(newBookmark) {
    return axios.post(`/api/v1/bookmarks`, newBookmark);
  },
  delete(bookmarkId) {
    return axios.delete(`/api/v1/bookmarks/${bookmarkId}`);
  },
};

export default BookmarkService;
