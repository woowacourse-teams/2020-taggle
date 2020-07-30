import axios from 'axios';

const AXIOS = axios.create({
  baseURL: process.env.baseUrl || 'http://3.34.203.89:8080',
  headers: { 'Access-Control-Expose-Headers': 'Location' },
});

const ApiService = {
  post(uri, params) {
    return AXIOS.post(uri, params).then((res) => {
      console.log(res);
      return res.headers.location;
    });
  },
  delete(uri) {
    AXIOS.delete(uri);
  },
};

export default ApiService;
