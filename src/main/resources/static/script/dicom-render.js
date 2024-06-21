// Get Cornerstone imageIds and fetch metadata into RAM
import * as cornerstone from "@cornerstonejs/core";
import * as cornerstoneDICOMImageLoader from'@cornerstonejs/dicom-image-loader'
import * as dicomPaser from 'dicom-parser'
// 뷰 포트 생성
const content = document.getElementById('content');
const element = document.createElement('div');
element.style.width = '500px';
element.style.height = '500px';

content.appendChild(element);
// 파일 리딩
const input = document.getElementById('input');
input.addEventListener('change', event => {
    // 파일이 변화하면
    // 파일을 읽고 -> 버퍼(바이너리 데이터를 가져와)
    // imageId를 생성 (dicomweb://)
    // 이미지 렌더링을 위한 render 메소드 완성

    const files = event.target.files;
    const reader = new FileReader();
    reader.onload = (file) => {
        const data = file.target.result;
        render(data);
    }
    reader.readAsArrayBuffer(files[0]);

})
const render = (arrayBuffer) =>{
    const imageId = `dicomweb:${URL.createObjectURL(new Blob([arrayBuffer], { type: 'application/dicom' }))}`;
    console.log('imageId',imageId);

    const imageIds  = [imageId];

// Final code

    const renderingEngineId = 'myRenderingEngine';
    const viewportId = 'CT_AXIAL_STACK';
    const renderingEngine = new cornerstone.RenderingEngine(renderingEngineId);

    const viewportInput = {
        viewportId,
        element,
        type: cornerstone.Enums.ViewportType.STACK,
    };

    renderingEngine.enableElement(viewportInput);

    const viewport = renderingEngine.getViewport(viewportInput.viewportId);

    viewport.setStack(imageIds, 60);

    viewport.render();
}
const init = async () =>{

   await cornerstone.init();
    cornerstoneDICOMImageLoader.external.cornerstone = cornerstone;
    cornerstoneDICOMImageLoader.external.dicomparser = dicomPaser;
    var config = {
        maxWebWorkers: navigator.hardwareConcurrency || 1,
        startWebWorkersOnDemand: true,
        taskConfiguration: {
            decodeTask: {
                initializeCodecsOnStartup: false,
            },
            sleepTask: {
                sleepTime: 3000,
            },
        },
    };
    cornerstoneWADOImageLoader.webWorkerManager.initialize(config);
    render();
}
init();
