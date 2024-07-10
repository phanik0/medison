import * as cornerstone from '@cornerstonejs/core';
import * as cornerstoneTools from '@cornerstonejs/tools';
import * as cornerstoneDICOMImageLoader from '@cornerstonejs/dicom-image-loader';
import * as dicomParser from 'dicom-parser';
import { RenderingEngine, Enums, imageLoader, metaData } from '@cornerstonejs/core';

const {
    ZoomTool, ToolGroupManager,
    Enums: csToolsEnums,
    LengthTool, AngleTool,
    MagnifyTool,
    StackScrollMouseWheelTool
} = cornerstoneTools;
const {MouseBindings} = csToolsEnums;

const toolGroupId = 'myToolGroup';

// Initialize Cornerstone and DICOM image loader
cornerstone.init();
cornerstoneDICOMImageLoader.external.cornerstone = cornerstone;
cornerstoneDICOMImageLoader.external.dicomParser = dicomParser;
cornerstoneDICOMImageLoader.configure({
    beforeSend: function(xhr) {
        // Add custom headers here (e.g., auth tokens)
    }
});

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
// Add DICOM metadata provider
// metaData.addProvider(cornerstoneDICOMImageLoader.metaDataProvider);

// Function to fetch study info and return array buffer
const fetchStudyInfo = async (studyKey) => {
    try {
        const response = await fetch(`/images/${studyKey}`);
        const data = await response.json();
        if (data && data.length > 0) {
            return data;
        } else {
            console.error('No images found for this study.');
            return null;
        }
    } catch (err) {
        console.error('Error fetching study info:', err);
        return null;
    }
};

// Function to load files and return array buffer
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

// Function to render thumbnails
const renderThumbnail = async (imageId, container) => {
    const element = document.createElement('div');
    element.className = 'thumbnail';
    element.style.width = '150px';
    element.style.height = '150px';
    container.appendChild(element);

    const renderingEngine = new RenderingEngine('thumbnailRenderingEngine');
    renderingEngine.enableElement({
        viewportId: 'thumbnailViewport',
        element: element,
        type: Enums.ViewportType.STACK,
    });

    const viewport = renderingEngine.getViewport('thumbnailViewport');
    await imageLoader.loadImage(imageId);
    viewport.setStack([imageId], 0);
    renderingEngine.render();
};

// Function to load and display main image
const loadMainImage = async (imageIds, element) => {
    const renderingEngine = new RenderingEngine('mainRenderingEngine');
    renderingEngine.enableElement({
        viewportId: 'mainViewport',
        element: element,
        type: Enums.ViewportType.STACK,
    });

    const viewport = renderingEngine.getViewport('mainViewport');
    await imageLoader.loadImage(imageIds[0]);
    viewport.setStack(imageIds, 0);
    renderingEngine.render();

};

// Function to setup image scrolling
const setupImageScroll = (imageIds, element) => {
    let currentIndex = 0;

    const loadNextImage = () => {
        if (currentIndex < imageIds.length - 1) {
            currentIndex++;
            loadMainImage([imageIds[currentIndex]], element);
        }
    };

    const loadPrevImage = () => {
        if (currentIndex > 0) {
            currentIndex--;
            loadMainImage([imageIds[currentIndex]], element);
        }
    };

    element.addEventListener('wheel', (event) => {
        event.preventDefault();
        if (event.deltaY > 0) {
            loadNextImage();
        } else {
            loadPrevImage();
        }
    });
};
// Function to draw overlay
const fetchProcessedPrDataList = async (studyKey)=>{
    try {
        const response = await fetch(`/ai/${studyKey}`);
        const data = await response.json();
        if (data && data.length > 0) {
            return data;
        } else {
            console.error('No images found for this study.');
            return null;
        }
    }catch(err){
        console.error('Error fetching study info:', err);
        return null;
    }
}
// CIELab to RGB conversion function
function cielabToRgb(l, a, bChannel) {
    let y = (l + 16) / 116;
    let x = a / 500 + y;
    let z = y - bChannel / 200;
    const [x3, y3, z3] = [x, y, z].map(value => {
        const value3 = Math.pow(value, 3);
        return value3 > 0.008856 ? value3 : (value - 16 / 116) / 7.787;
    });
    [x, y, z] = [x3 * 0.95047, y3, z3 * 1.08883];
    const [r, g, b] = [x, y, z].map(value => {
        const gamma = value <= 0.0031308 ? 12.92 * value : 1.055 * Math.pow(value, 1 / 2.4) - 0.055;
        return Math.round(gamma * 255);
    });
    return `rgb(${r}, ${g}, ${b})`;
}

// Updated drawOverlay function
function drawOverlay(element, studyKey) {
    const overlayData = fetchProcessedPrDataList(studyKey);
    const canvas = document.createElement('canvas');
    canvas.width = element.clientWidth;
    canvas.height = element.clientHeight;
    const context = canvas.getContext('2d');

    overlayData.forEach(data => {
        context.beginPath();
        const graphicObjectSequence = data.graphicObjectSequence;
        graphicObjectSequence.forEach(obj => {
            const point = graphicObjectSequence.graphicData;
            const CIE = graphicObjectSequence.lineStyleSequence.patternOnColorCIELabValue;
            const color = cielabToRgb(CIE.color.l, CIE.color.a, CIE.color.b);
            const graphicType = graphicObjectSequence.graphicType;
            const numberOfPoint = graphicObjectSequence.numberOfGraphicPoints;
            context.strokeStyle = color;
            context.lineWidth = data.lineThickness;

            if (graphicType === 'POLYLINE') {
                point.forEach((point, index) => {
                    const x = point.column / canvas.width * canvas.clientWidth;
                    const y = point.row / canvas.height * canvas.clientHeight;
                    if (index === 0) {
                        context.moveTo(x, y);
                    } else {
                        context.lineTo(x, y);
                    }
                });
            } else if (graphicType === 'ELLIPSE') {
                const [x0, y0] = [point[0].column, point[0].row];
                const [x1, y1] = [point[1].column, point[1].row];
                const rx = Math.abs(x1 - x0) / 2;
                const ry = Math.abs(y1 - y0) / 2;
                const cx = (x0 + x1) / 2;
                const cy = (y0 + y1) / 2;
                context.ellipse(cx, cy, rx, ry, 0, 0, 2 * Math.PI);
            }

            context.stroke();
        })


    });

    element.appendChild(canvas);
}



// Function to load DICOM images and render viewports
const loadDicom = async function(studyKey) {
    const studyInfo = await fetchStudyInfo(studyKey);
    if (studyInfo) {
        const thumbnailList = document.getElementById('thumbnail-list');
        const viewportGrid = document.getElementById('viewportGrid');
        const imageIdsBySeries = [];

        for (const series of studyInfo) {
            const seriesImageIds = [];
            for (const image of series) {
                const filePath = image.path + image.fname;
                const arrayBuffer = await loadFiles(filePath);
                const imageId = `dicomweb:${URL.createObjectURL(new Blob([arrayBuffer], { type: 'application/dicom' }))}`;
                seriesImageIds.push(imageId);
            }
            imageIdsBySeries.push(seriesImageIds);
            await renderThumbnail(seriesImageIds[0], thumbnailList); // Render the first image of each series as a thumbnail
        }

        if (imageIdsBySeries.length > 0) {
            // Element for four viewports
            const elements = [];
            for (let i = 0; i < 4; i++) {
                const element = document.createElement('div');
                element.className = 'viewport';
                element.style.width = '45%';
                element.style.height = '45%';
                viewportGrid.appendChild(element);
                elements.push(element);
            }

            const renderingEngineId = 'myRenderingEngine';
            const renderingEngine = new RenderingEngine(renderingEngineId);

            const viewportIds = ['CT_01', 'CT_02', 'CT_03', 'CT_04'];

            const viewportInput = viewportIds.map((viewportId, index) => ({
                viewportId: viewportId,
                element: elements[index],
                type: Enums.ViewportType.STACK,
                defaultOptions: {
                    orientation: Enums.OrientationAxis.AXIAL,
                },
            }));
            renderingEngine.setViewports(viewportInput);
            for (let i = 0; i < viewportIds.length; i++) {
                const viewport = renderingEngine.getViewport(viewportIds[i]);
                viewport.setStack(imageIdsBySeries[i % imageIdsBySeries.length], 0);
            }
            renderingEngine.render();

            console.log("imageIdsBySeries : ",imageIdsBySeries);

            viewportGrid.addEventListener('dblclick', (event) => {
                const targetUid = event.target.parentElement.parentElement.dataset.viewportUid;
                console.log("targetUid : ",targetUid);
                const targetIndex = parseInt(targetUid.slice(3))-1;
                setupImageScroll(imageIdsBySeries[targetIndex], event.target.parentElement.parentElement); // Flatten the image ID array for scrolling
                loadMainImage(imageIdsBySeries[targetIndex],event.target.parentElement.parentElement);
            });
        }
    }
};

// `window.onload` 이벤트에서 `loadDicom` 함수를 호출
window.onload = async () => {
    const studyKey = document.getElementById('studyKey').value; // studyKey 값 가져오기
    const overlay = document.getElementById('overlay');
    await loadDicom(studyKey).then(drawOverlay(overlay, studyKey));
}



