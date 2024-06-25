// import * as cornerstone from '@cornerstonejs/core'
// import * as cornerstoneDICOMImageLoader from '@cornerstonejs/dicom-image-loader'
// import * as dicomParser from 'dicom-parser'
//
//
// // 뷰 포트 생성
//
// const content = document.getElementById('content');
// const element = document.createElement('div');
// element.style.width = '500px';
// element.style.height = '500px';
// content.appendChild(element);
//
// // 파일 리딩
// const input = document.getElementById("file");
// input.addEventListener("change", e =>{
//     // 파일이 변화하면 렌더링
//     // 파일을 읽고 -> 버퍼(바이너리 데이터를 가져와)
//     // imageId를 생성 (dicomweb://)
//     // 이미지 랜더링을 위한 render 메소드 완성
//
//     const files = e.target.files;
//
//     const reader = new FileReader();
//     reader.onload = (file) =>{
//         const data = file.target.result;
//         render(data);
//     }
//     reader.readAsArrayBuffer(files[0]);
// })
//
// const render =(arrayBuffer)=>{
//     const imageId = `dicomweb:${URL.createObjectURL(new Blob([arrayBuffer], {type : 'application/dicom'}))}`;
//     console.log('imageId : ',imageId);
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
//     viewport.setStack(imageIds, 0);
//
//     viewport.render();
// }
//
// const init = async ()=>{
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
//

import * as cornerstone from '@cornerstonejs/core';
import * as cornerstoneDICOMImageLoader from '@cornerstonejs/dicom-image-loader';
import * as dicomParser from 'dicom-parser';
import {addTool, ToolGroupManager, WindowLevelTool, ZoomTool} from "@cornerstonejs/tools";

const initRendering = async () => {
    const imageIds = await createImageIdsAndCacheMetaData({
        StudyInstanceUID: '1.3.6.1.4.1.14519.5.2.1.7009.2403.334240657131972136850343327463',
        SeriesInstanceUID: '1.3.6.1.4.1.14519.5.2.1.7009.2403.226151125820845824875394858561',
        wadoRsRoot: 'https://d3t6nz73ql33tx.cloudfront.net/dicomweb',
    });

    const content = document.getElementById('content');
    const element = document.createElement('div');

    element.oncontextmenu = (e) => e.preventDefault();
    element.style.width = '500px';
    element.style.height = '500px';

    content.appendChild(element);

    const renderingEngineId = 'myRenderingEngine';
    const renderingEngine = new RenderingEngine(renderingEngineId);

    const viewportId = 'CT_AXIAL_STACK';

    const viewportInput = {
        viewportId,
        element,
        type: ViewportType.STACK,
    };

    renderingEngine.enableElement(viewportInput);

    const viewport = renderingEngine.getViewport(viewportId);

    viewport.setStack(imageIds);

    viewport.render();

    addTool(ZoomTool);
    addTool(WindowLevelTool);

    const toolGroupId = 'myToolGroup';
    const toolGroup = ToolGroupManager.createToolGroup(toolGroupId);

    toolGroup.addTool(ZoomTool.toolName);
    toolGroup.addTool(WindowLevelTool.toolName);

    toolGroup.addViewport(viewportId, renderingEngineId);

    toolGroup.setToolActive(WindowLevelTool.toolName, {
        bindings: [
            {
                mouseButton: csToolsEnums.MouseBindings.Primary,
            },
        ],
    });

    toolGroup.setToolActive(ZoomTool.toolName, {
        bindings: [
            {
                mouseButton: csToolsEnums.MouseBindings.Secondary,
            },
        ],
    });
};

const init = async () => {
    await cornerstone.init();

    cornerstoneDICOMImageLoader.external.cornerstone = cornerstone;
    cornerstoneDICOMImageLoader.external.dicomParser = dicomParser;

    const config = {
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
    cornerstoneDICOMImageLoader.webWorkerManager.initialize(config);

    // Call the rendering initialization function
    await initRendering();
};

init();

