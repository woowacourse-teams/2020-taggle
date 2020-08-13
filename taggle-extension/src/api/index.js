import axios from 'axios';

const AXIOS = axios.create({
  baseURL: process.env.baseUrl || 'http://localhost:8080',
  headers: { 'Access-Control-Expose-Headers': 'Location' },
});

const ApiService = {
  async post(uri, params) {
    const res = await AXIOS.post(uri, params);
    return res.headers.location;
  },
  async delete(uri) {
    await AXIOS.delete(uri);
  },
};

export default ApiService;
