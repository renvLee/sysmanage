

module.exports = {


    // 代理配置
    devServer: {
        port: 2048,
        proxy: {
            '/v2/api': {
                target: 'http://localhost:8989',
                changeOrigin: true,
                pathRewrite: {
                    '^/v2/api': ''
                }
            }
        }
    }
};