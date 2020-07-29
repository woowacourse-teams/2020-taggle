import axios from 'axios';

const ApiService = {
  post(uri, params) {
    return axios.post(uri, params);
  },
};

export default ApiService;
