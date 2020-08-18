import axios from 'axios';

const AXIOS = axios.create({
  baseURL: process.env.baseUrl || 'https://taggle.kr',
});

const ApiService = {
  get(uri) {
    return AXIOS.get(uri);
  },
  post(uri, params) {
    return AXIOS.post(uri, params);
  },
  delete(uri) {
    return AXIOS.delete(uri);
  },
};

export default ApiService;
