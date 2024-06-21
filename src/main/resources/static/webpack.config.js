const path = require('path');
module.exports = {
    entry: './script/dicom-render.js',         // 시작 파일의 경로
    output: {
        filename: 'bundle.js',                 //출력파일 이름
        path: path.resolve(__dirname, 'dist'), //출력 디렉토리
    },
    resolve:{
        modules:['node_modules'],              // 모듈 검색 경로
        extensions: ['.js','ts','.json','.wasm']
    },
    mode: 'development',                        // 개발 모드

}