import axios from 'axios';

const AXIOS = axios.create({
  baseURL: process.env.baseUrl || 'http://3.34.203.89:8080',
  headers: { 'Access-Control-Expose-Headers': 'Location' },
});

const ApiService = {
  get(uri) {
    return AXIOS.get(uri).then(({ data }) => data);
  },
  post(uri, params) {
    return AXIOS.post(uri, params).then(({ headers }) => {
      return headers.location;
    });
  },
  delete(uri) {
    AXIOS.delete(uri);
  },
};

export default ApiService;
