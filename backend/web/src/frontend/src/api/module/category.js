import ApiService from '@/api/index.js';

const BASE_URL = '/api/v1/categories';

const CategoryService = {
  getAll() {
    return ApiService.get(`${BASE_URL}`);
  },
  create(newCategory) {
    return ApiService.post(`${BASE_URL}`, newCategory);
  },
  edit(id, category) {
    return ApiService.put(`${BASE_URL}/${id}`, category);
  },
  delete(categoryId) {
    return ApiService.delete(`${BASE_URL}/${categoryId}`);
  },
  editTag(categoryId, tagId) {
    return ApiService.put(`${BASE_URL}/${categoryId}/tags/${tagId}`);
  },
};

export default CategoryService;
