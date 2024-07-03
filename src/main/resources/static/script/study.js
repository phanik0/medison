import * as cornerstone from '@cornerstonejs/core';
import * as cornerstoneDICOMImageLoader from '@cornerstonejs/dicom-image-loader';
import * as dicomParser from 'dicom-parser';
import { RenderingEngine, Enums, imageLoader, metaData } from '@cornerstonejs/core';

// Initialize Cornerstone and DICOM image loader
cornerstone.init();
cornerstoneDICOMImageLoader.external.cornerstone = cornerstone;
cornerstoneDICOMImageLoader.external.dicomParser = dicomParser;
cornerstoneDICOMImageLoader.configure({
    beforeSend: function(xhr) {
        // Add custom headers here (e.g., auth tokens)
    }
});

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
        if (event.deltaY > 0) {
            loadNextImage();
        } else {
            loadPrevImage();
        }
    });
};

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
                //wadors 서버에서 스트리밍방식 => 서버에서가져옴
                // dicomweb 이거는 로컬에서가져옴
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
                element.style.width = '100%';
                element.style.height = '50%';
                viewportGrid.appendChild(element);
                elements.push(element);
            }

            const renderingEngineId = 'myRenderingEngine';
            const renderingEngine = new RenderingEngine(renderingEngineId);

            const viewportIds = ['CT_AXIAL', 'CT_SAGITTAL', 'CT_CORONAL', 'CT_OBLIQUE'];

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
                viewport.setStack(imageIdsBySeries[i % imageIdsBySeries.length], 0); // Load images by series
            }

            renderingEngine.render();

            setupImageScroll(imageIdsBySeries.flat(), viewportGrid); // Flatten the image ID array for scrolling
        }
    }
};

// `window.onload` 이벤트에서 `loadDicom` 함수를 호출
window.onload = () => {
    const studyKey = document.getElementById('studyKey').value; // studyKey 값 가져오기
    loadDicom(studyKey); // loadDicom 함수 호출
};
