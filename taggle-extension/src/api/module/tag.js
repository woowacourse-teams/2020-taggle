import ApiService from '../index.js';

const BASE_URL = '/api/v1/tags';

const TagService = {
  create(newTag) {
    return ApiService.post(`${BASE_URL}`, newTag);
  },
};

export default TagService;
