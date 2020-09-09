const path = require('path');

module.exports = {
  devServer: {
    proxy: {
      '/api': {
        target: 'http://localhost:8080',
        ws: true,
        changeOrigin: true,
      },
      '/oauth2': {
        target: 'http://localhost:8080',
        changeOrigin: false,
      },
    },
  },
  transpileDependencies: ['vuetify'],
  outputDir: path.resolve(__dirname, '../main/resources/static'),
  configureWebpack: {
    resolve: {
      alias: {
        '@': path.join(__dirname, 'src/'),
      },
    },
  },
};
