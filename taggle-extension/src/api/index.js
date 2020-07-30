import axios from 'axios';

const ApiService = {
  post(uri, params) {
    return axios.post(uri, params).then((res) => res.headers.location);
  },
};

export default ApiService;
