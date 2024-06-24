import * as cornerstone from '@cornerstonejs/core';
import * as cornerstoneDICOMImageLoader from '@cornerstonejs/dicom-image-loader';
import * as dicomParser from 'dicom-parser';

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