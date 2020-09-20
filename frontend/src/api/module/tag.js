import ApiService from '@/api/index.js';

const BASE_URL = '/api/v1/tags';

const TagService = {
  create(tagCreateRequest) {
    return ApiService.post(`${BASE_URL}`, tagCreateRequest).then(({ data }) => data.id);
  },
  update(tagId, tagUpdateRequest) {
    return ApiService.put(`${BASE_URL}/${tagId}`, tagUpdateRequest);
  },
  delete(tagId) {
    return ApiService.delete(`${BASE_URL}/${tagId}`);
  },
};

export default TagService;
