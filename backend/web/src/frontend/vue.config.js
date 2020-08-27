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
  indexPath: path.resolve(__dirname, '../main/resources/templates/index.html'),
  outputDir: path.resolve(__dirname, '../main/resources/static'),
  configureWebpack: {
    resolve: {
      alias: {
        '@': path.join(__dirname, 'src/'),
      },
    },
  },
  chainWebpack: (config) => {
    config.plugin('html').tap((args) => {
      args[0].title = 'taggle';
      return args;
    });
  },
};
