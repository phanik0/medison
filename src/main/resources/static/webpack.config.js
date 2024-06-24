// const path = require('path');
//
// module.exports = {
//     entry: './script/dicom-render.js',
//     output: {
//         filename: 'bundle.js',
//         path: path.resolve(__dirname, 'dist'),
//     },
//     resolve: {
//         modules: ['node_modules'],
//         extensions: ['.ts', '.js', '.json', '.wasm']
//     },
//     cache: {
//         type: 'filesystem',
//         cacheDirectory: path.resolve(__dirname, '.webpack_cache'),
//     },
//     mode: 'development'
// };
//

const path = require('path');

module.exports = {
    entry: './script/dicom-render.js',
    output: {
        filename: 'bundle.js',
        path: path.resolve(__dirname, 'dist'),
    },
    experiments: {
        topLevelAwait: true
    },
    module: {
        rules: [
            {
                test: /\.js$/,
                exclude: /node_modules/,
                use: {
                    loader: 'babel-loader',
                    options: {
                        presets: ['@babel/preset-env'],
                    },
                },
            },
        ],
    },
};

