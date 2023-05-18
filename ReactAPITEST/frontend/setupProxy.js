const { createProxyMiddleware } = require('http-proxy-middleware');

module.exports = function(app) {
  app.use(
    '/images',
    createProxyMiddleware({
      target: 'http://localhost:3000',
      pathRewrite: {
        '^/images': '',
      },
      logLevel: 'debug',
    })
  );
};