import axios from 'axios';

const ApiService = {
  get(uri) {
    return axios.get(`${uri}`);
  },
};

export default ApiService;
