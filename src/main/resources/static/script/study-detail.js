import * as cornerstone from '@cornerstonejs/core';
import * as cornerstoneTools from '@cornerstonejs/tools';
import * as cornerstoneDICOMImageLoader from '@cornerstonejs/dicom-image-loader';
import * as dicomParser from 'dicom-parser';
import {RenderingEngine} from "@cornerstonejs/core";

const {
    ZoomTool, ToolGroupManager,
    Enums: csToolsEnums,
    LengthTool, AngleTool,
    MagnifyTool,
    StackScrollMouseWheelTool
} = cornerstoneTools;
const {MouseBindings} = csToolsEnums;

const toolGroupId = 'myToolGroup';
cornerstoneTools.init();
cornerstoneTools.addTool(ZoomTool);
cornerstoneTools.addTool(LengthTool);
cornerstoneTools.addTool(AngleTool);
cornerstoneTools.addTool(MagnifyTool);
cornerstoneTools.addTool(StackScrollMouseWheelTool);

const toolGroup = ToolGroupManager.createToolGroup(toolGroupId);
toolGroup.addTool(ZoomTool.toolName);
toolGroup.addTool(LengthTool.toolName);
toolGroup.addTool(AngleTool.toolName);
toolGroup.addTool(MagnifyTool.toolName);
toolGroup.addTool(StackScrollMouseWheelTool.toolName);

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
};

window.onload = () => {
    const studyKey = document.getElementById('studyKey').value; // studyKey 값 가져오기
    console.log(studyKey);
    init();
    onload(studyKey);
}

const onload = async function (studyKey) {
    const fetchDetailData = await fetch(`/images/${studyKey}`);
    const studyInfo = await fetchDetailData.json();

    const loadFiles = async (filePath) => {
        try {
            const normalizedPath = filePath.replace(/\\/g, '/');
            const path = `/dicom-file?path=${encodeURIComponent(normalizedPath)}`;
            const response = await fetch(path);
            const arrayBuffer = await response.arrayBuffer();
            return arrayBuffer;
        } catch (error) {
            console.error('Error loading local DICOM file:', error);
            return null;
        }
    };

    if (studyInfo) {
        const thumbnailList = document.getElementById('thumbnail-list');
        const viewportGrid = document.getElementById('viewportGrid');

        const createImageBySeies = async function () {
            const imageIdsBySeries = [];
            for (const series of studyInfo) {
                const seriesImageIds = [];
                for (const image of series) {
                    const filePath = image.path + image.fname;
                    const arrayBuffer = await loadFiles(filePath);
                    const imageId = `dicomweb:${URL.createObjectURL(new Blob([arrayBuffer], {type: 'application/dicom'}))}`;
                    seriesImageIds.push(imageId);
                }
                imageIdsBySeries.push(seriesImageIds);
            }
            return imageIdsBySeries;
        }

        const imageIdsBySeries = await createImageBySeies();

        // 썸네일 랜더링
        const thumnailRenderingEngine = new RenderingEngine('thumbnailRenderingEngine');

        for (let i = 0; i < imageIdsBySeries.length; i++) {
            const thumnailImageId = imageIdsBySeries[i][0];
            const element = document.createElement('div');
            element.className = 'thumbnail';
            element.style.width = '150px';
            element.style.height = '150px';

            thumbnailList.append(element);

            const thumbnailViewportId = 'thumbnailViewport' + i;
            thumnailRenderingEngine.enableElement({
                viewportId: thumbnailViewportId,
                element: element,
                type: cornerstone.Enums.ViewportType.STACK,
            })

            const thumbnailViewport = thumnailRenderingEngine.getViewport(thumbnailViewportId);
            if (!thumbnailViewport) {
                console.error(`Thumbnail viewport not found: ${thumbnailViewportId}`);
                return;
            }
            thumbnailViewport.setStack([thumnailImageId]);
            await thumbnailViewport.render();

        }

        // 메인 랜더링
        const mainRenderingEngineId = "mainRenderingEngine";
        const mainRenderingEngine = new RenderingEngine(mainRenderingEngineId);
        for (let i = 0; i < imageIdsBySeries.length; i++) {
            const mainImageIds = imageIdsBySeries[i];
            const element = document.createElement('div');
            element.oncontextmenu = (e) => e.preventDefault();
            element.className = 'viewport';
            element.style.width = '45%';
            element.style.height = '45%';

            viewportGrid.append(element);

            const mainViewportId = 'CT_' + i;

            mainRenderingEngine.enableElement({
                viewportId: mainViewportId,
                element: element,
                type: cornerstone.Enums.ViewportType.STACK,
            })

            // const mainViewport = mainRenderingEngine.getViewport(mainViewportId);
            // mainViewport.setStack(mainImageIds);

            const mainViewport = mainRenderingEngine.getViewport(mainViewportId);
            if (!mainViewport) {
                console.error(`Main viewport not found: ${mainViewportId}`);
                return;
            }
            mainViewport.setStack(mainImageIds);

            cornerstoneTools.utilities.stackPrefetch.enable(mainViewport.element);
            await mainViewport.render();

        }

        viewportGrid.addEventListener('dblclick', (event) => {
            toolGroup.removeViewports(mainRenderingEngineId);

            const targetUid = event.target.parentElement.parentElement.dataset.viewportUid;
            event.target.parentElement.parentElement.style.border = '1px solid red';

            console.log("targetUid : ",targetUid);

            toolGroup.addViewport(targetUid, mainRenderingEngineId);

            toolGroup.setToolActive(ZoomTool.toolName, {
                bindings: [{ mouseButton: MouseBindings.Primary }],
            });// 좌클릭으로 줌
            toolGroup.setToolActive(MagnifyTool.toolName, {
                bindings: [{ mouseButton: MouseBindings.Secondary }],
            });// 우클 길이
            toolGroup.setToolActive(AngleTool.toolName, {
                bindings: [{ mouseButton: MouseBindings.Auxiliary }],
            });
            toolGroup.setToolActive(StackScrollMouseWheelTool.toolName);



        });

    }

}