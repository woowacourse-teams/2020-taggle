import axios from 'axios';

const ApiService = {
  get(uri) {
    return axios.get(`http://localhost:8080${uri}`);
  },
};

export default ApiService;
