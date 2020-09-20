import ApiService from '@/api/index.js';

const BASE_URL = '/api/v1/categories';

const CategoryService = {
  getAll() {
    return ApiService.get(`${BASE_URL}`);
  },
  create(categoryCreateRequest) {
    return ApiService.post(`${BASE_URL}`, categoryCreateRequest);
  },
  update(categoryId, categoryUpdateRequest) {
    return ApiService.put(`${BASE_URL}/${categoryId}`, categoryUpdateRequest);
  },
  delete(categoryId) {
    return ApiService.delete(`${BASE_URL}/${categoryId}`);
  },
};

export default CategoryService;
