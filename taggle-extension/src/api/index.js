import axios from 'axios';
import { SERVICE_URL } from '@/utils/constants.js';

const AXIOS = axios.create({
  baseURL: SERVICE_URL,
  withCredentials: true,
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
