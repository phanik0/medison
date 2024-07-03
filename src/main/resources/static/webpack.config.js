
const path = require('path');
const NodePolyfillPlugin = require('node-polyfill-webpack-plugin');

module.exports = {
    entry: './script/study.js',
    output: {
        filename: 'bundle.js',
        path: path.resolve(__dirname, 'dist'),
        chunkFilename: '[name].[contenthash].js', // chunk 파일의 이름 형식 설정
    },
    resolve: {
        modules: ['node_modules'],
        extensions: ['.ts', '.js', '.json', '.wasm']
    },
    cache: {
        type: 'filesystem',
        cacheDirectory: path.resolve(__dirname, '.webpack_cache'),
    },
    mode: 'development',
    experiments: {
        asyncWebAssembly: true // 또는 syncWebAssembly: true
    },
    module: {
        rules: [
            {
                test: /\.wasm$/,
                type: 'webassembly/async' // 또는 'webassembly/sync'
            },
            {
                test: /\.js$/,
                use: 'babel-loader',
                exclude: /node_modules/
            },
            {
                test: /\.wasm$/,
                use: 'file-loader',
                type: 'javascript/auto'
            }
        ]
    },
    plugins: [
        new NodePolyfillPlugin()
    ]
};
