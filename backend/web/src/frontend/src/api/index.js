import axios from 'axios';

const ApiService = {
  get(uri) {
    return axios.get(uri);
  },
  post(uri, params) {
    return axios.post(`${uri}`, params);
  },
  put(uri, params) {
    return axios.post(`${uri}`, params);
  },
  delete(uri) {
    return axios.delete(`${uri}`);
  },
};

export default ApiService;
