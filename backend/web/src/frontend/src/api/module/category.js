import ApiService from '../index.js';

const BASE_URL = '/api/v1/categories/tags';

const CategoryService = {
  getAll() {
    return ApiService.get(`${BASE_URL}`);
  },
};

export default CategoryService;
