const { createProxyMiddleware } = require('http-proxy-middleware');

module.exports = function(app) {
    app.use(
        '/festival/spectacol',
        createProxyMiddleware({
            target: 'http://localhost:8080', // Adaptează la URL-ul serverului tău Spring Boot
            changeOrigin: true,
        })
    );
};
