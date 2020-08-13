import ApiService from '@/api/index.js';

const BASE_URL = '/api/v1/categories';

const CategoryService = {
  getAll() {
    return ApiService.get(`${BASE_URL}`);
  },
  create(newCategory) {
    return ApiService.post(`${BASE_URL}`, newCategory);
  },
};

export default CategoryService;
