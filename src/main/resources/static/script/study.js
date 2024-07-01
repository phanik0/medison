import * as cornerstone from '@cornerstonejs/core'
import * as cornerstoneDICOMImageLoader from '@cornerstonejs/dicom-image-loader'
import * as dicomParser from 'dicom-parser';
window.onload = async function() {
    // Initialize Cornerstone and DICOM image loader
    cornerstoneDICOMImageLoader.external.cornerstone = cornerstone;
    cornerstoneDICOMImageLoader.external.dicomParser = dicomParser;
    cornerstoneDICOMImageLoader.configure({
        beforeSend: function(xhr) {
            // Add custom headers here (e.g., auth tokens)
        }
    });

    const content = document.getElementById('content');
    const element = document.createElement('div');
    element.style.width = '512px';
    element.style.height = '512px';
    element.style.border = '1px solid black';
    content.appendChild(element);

    // Initialize Cornerstone
    await cornerstone.init();

    // Set up the rendering engine and viewport
    const renderingEngineId = 'myRenderingEngine';
    const renderingEngine = new cornerstone.RenderingEngine(renderingEngineId);

    const viewportId = 'CT_AXIAL_STACK';
    const viewportInput = {
        viewportId,
        type: cornerstone.Enums.ViewportType.STACK,
        element,
        defaultOptions: {
            background: [0, 0, 0],
        },
    };

    renderingEngine.enableElement(viewportInput);
    const viewport = renderingEngine.getViewport(viewportId);

    // 이미지 렌더링 함수
    const render = async (imageId) => {
        try {
            const image = await cornerstone.imageLoader.loadImage(imageId);
            viewport.setStack([image.imageId]);
            viewport.render();
        } catch (err) {
            console.error('Error loading image:', err);
        }
    };

    // 스터디 정보를 가져와서 이미지를 렌더링하는 함수
    const fetchStudyInfo = async (studyKey) => {
        try {
            const response = await fetch(`/study/${studyKey}`);
            const study = await response.json();
            if (study.images && study.images.length > 0) {
                const imagePath = study.images[0].path;
                const imageId = `wadouri:/dicom-file?path=${encodeURIComponent(imagePath)}`;
                render(imageId);
            } else {
                console.error('No images found for this study.');
            }
        } catch (err) {
            console.error('Error fetching study info:', err);
        }
    };

    // 예시 사용법
    const studyKey = 1;  // 예시 스터디 키
    fetchStudyInfo(studyKey);
};
//
// import * as cornerstone from '@cornerstonejs/core'
// import * as cornerstoneDICOMImageLoader from '@cornerstonejs/dicom-image-loader'
// import * as dicomParser from 'dicom-parser'
//
// const input = document.getElementById("dicomImage");
//
// // 뷰 포트 생성
// const content = document.getElementById('content');
// const element = document.createElement('div');
// element.style.width = '500px';
// element.style.height = '500px';
//
// content.appendChild(element);

// 파일 리딩
// input.addEventListener("change", e => {
//     // 파일이 변화하면
//     // 파일을 읽고 -> 버퍼 (바이너리 데이터를 가져와)
//     // ImageId를 생성 (dicomweb://)
//     // 이미지 렌더링을 위한 render 메소드 완성
//
//     const files = e.target.files;
//
//     const reader = new FileReader();
//     reader.onload = (file) => {
//         const data = file.target.result;
//         render(data);
//     }
//     reader.readAsArrayBuffer(files[0]);
// })
//
// const render = (study,image,series) => {
//     // Get Cornerstone imageIds and fetch metadata into RAM
//
//     const imageId = `'wadouri:'${URL.createObjectURL(new Blob([arrayBuffer], {type : 'application/dicom'}))}`;
//     console.log('imageId : ', imageId);
//
//     const imageIds = [imageId];
//
//     const renderingEngineId = 'myRenderingEngine';
//     const viewportId = 'CT_AXIAL_STACK';
//     const renderingEngine = new cornerstone.RenderingEngine(renderingEngineId);
//
//     const viewportInput = {
//         viewportId,
//         element,
//         type: cornerstone.Enums.ViewportType.STACK,
//     };
//
//     renderingEngine.enableElement(viewportInput);
//
//     const viewport = renderingEngine.getViewport(viewportInput.viewportId);
//
//     viewport.setStack(imageIds, 1);
//
//     viewport.render();
// }
//
// const init = async () => {
//     await cornerstone.init();
//
//     cornerstoneDICOMImageLoader.external.cornerstone = cornerstone;
//     cornerstoneDICOMImageLoader.external.dicomParser = dicomParser;
//
//     var config = {
//         maxWebWorkers: navigator.hardwareConcurrency || 1,
//         startWebWorkersOnDemand: true,
//         taskConfiguration: {
//             decodeTask: {
//                 initializeCodecsOnStartup: false,
//             },
//             sleepTask: {
//                 sleepTime: 3000,
//             },
//         },
//     };
//     cornerstoneDICOMImageLoader.webWorkerManager.initialize(config);
// }
//
// init();