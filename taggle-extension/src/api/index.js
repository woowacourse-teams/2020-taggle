import axios from 'axios';
import parseIdFromUri from '../utils/idParser.js';

const AXIOS = axios.create({
  baseURL: process.env.baseUrl || 'http://localhost:8080' || 'http://3.34.203.89:8080',
});

const ApiService = {
  get(uri) {
    return AXIOS.get(uri).then(({ data }) => data);
  },
  post(uri, params) {
    return AXIOS.post(uri, params).then(({ data, headers }) => {
      return data.id || parseIdFromUri(headers.location);
    });
  },
  delete(uri) {
    return AXIOS.delete(uri);
  },
};

export default ApiService;
