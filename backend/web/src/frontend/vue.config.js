const path = require('path');

module.exports = {
  devServer: {
    proxy: {
      '/api': {
        target: 'http://localhost:8080',
        ws: true,
        changeOrigin: true,
      },
    },
  },
  transpileDependencies: ['vuetify'],
  indexPath: path.resolve(__dirname, '../main/resources/templates/index.html'),
  outputDir: path.resolve(__dirname, '../main/resources/static'),
  configureWebpack: {
    resolve: {
      alias: {
        '@': path.join(__dirname, 'src/'),
      },
    },
  },
};
