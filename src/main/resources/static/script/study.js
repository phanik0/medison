
import * as cornerstone from '@cornerstonejs/core';
import * as cornerstoneDICOMImageLoader from '@cornerstonejs/dicom-image-loader';
import * as dicomParser from 'dicom-parser';
import {RenderingEngine, Enums, imageLoader, setVolumesForViewports} from '@cornerstonejs/core';

// Initialize Cornerstone and DICOM image loader
cornerstone.init();
cornerstoneDICOMImageLoader.external.cornerstone = cornerstone;
cornerstoneDICOMImageLoader.external.dicomParser = dicomParser;
cornerstoneDICOMImageLoader.configure({
    beforeSend: function(xhr) {
        // Add custom headers here (e.g., auth tokens)
    }
});

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
    container.appendChild(element);

    const renderingEngine = new RenderingEngine('thumbnailRenderingEngine');
    renderingEngine.enableElement({
        viewportId: 'thumbnailViewport',
        element: element,
        type: Enums.ViewportType.STACK,
    });

    const viewport = renderingEngine.getViewport('thumbnailViewport');
    const image = await imageLoader.loadImage(imageId);
    viewport.setStack([imageId], 0);
    renderingEngine.render();
};
// Function to load and display main image
const loadMainImage = async (imageId, element) => {
    const renderingEngine = new RenderingEngine('mainRenderingEngine');
    renderingEngine.enableElement({
        viewportId: 'mainViewport',
        element: element,
        type: Enums.ViewportType.STACK,
    });

    const viewport = renderingEngine.getViewport('mainViewport');
    const image = await imageLoader.loadImage(imageId);
    viewport.setStack([imageId], 0);
    renderingEngine.render();
};

// Function to setup image scrolling
const setupImageScroll = (imageIds, element) => {
    let currentIndex = 0;

    const loadNextImage = () => {
        if (currentIndex < imageIds.length - 1) {
            currentIndex++;
            loadMainImage(imageIds[currentIndex], element);
        }
    };

    const loadPrevImage = () => {
        if (currentIndex > 0) {
            currentIndex--;
            loadMainImage(imageIds[currentIndex], element);
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
        const imageIds = [];

        for (const series of studyInfo) {
            for (const image of series) {
                const filePath = image.path + image.fname;
                const arrayBuffer = await loadFiles(filePath);
                const imageId = `dicomweb:${URL.createObjectURL(new Blob([arrayBuffer], { type: 'application/dicom' }))}`;
                imageIds.push(imageId);
                await renderThumbnail(imageId, thumbnailList);
            }
        }

        if (imageIds.length > 0) {
            // Element for four viewports
            const element1 = document.createElement('div');
            element1.className = 'viewport';

            const element2 = document.createElement('div');
            element2.className = 'viewport';

            const element3 = document.createElement('div');
            element3.className = 'viewport';

            const element4 = document.createElement('div');
            element4.className = 'viewport';

            viewportGrid.appendChild(element1);
            viewportGrid.appendChild(element2);
            viewportGrid.appendChild(element3);
            viewportGrid.appendChild(element4);

            const renderingEngineId = 'myRenderingEngine';
            const renderingEngine = new RenderingEngine(renderingEngineId);

            const viewportId1 = 'CT_AXIAL';
            const viewportId2 = 'CT_SAGITTAL';
            const viewportId3 = 'CT_CORONAL';
            const viewportId4 = 'CT_OBLIQUE';

            const viewportInput = [
                {
                    viewportId: viewportId1,
                    element: element1,
                    type: Enums.ViewportType.ORTHOGRAPHIC,
                    defaultOptions: {
                        orientation: Enums.OrientationAxis.AXIAL,
                    },
                },
                {
                    viewportId: viewportId2,
                    element: element2,
                    type: Enums.ViewportType.ORTHOGRAPHIC,
                    defaultOptions: {
                        orientation: Enums.OrientationAxis.SAGITTAL,
                    },
                },
                {
                    viewportId: viewportId3,
                    element: element3,
                    type: Enums.ViewportType.ORTHOGRAPHIC,
                    defaultOptions: {
                        orientation: Enums.OrientationAxis.CORONAL,
                    },
                },
                {
                    viewportId: viewportId4,
                    element: element4,
                    type: Enums.ViewportType.ORTHOGRAPHIC,
                    defaultOptions: {
                        orientation: Enums.OrientationAxis.OBLIQUE,
                    },
                },
            ];

            renderingEngine.setViewports(viewportInput);

            // Render the images
            setVolumesForViewports(renderingEngine, [{ volumeId: imageIds[0] }], [viewportId1, viewportId2, viewportId3, viewportId4]);
            renderingEngine.renderViewports([viewportId1, viewportId2, viewportId3, viewportId4]);

            setupImageScroll(imageIds, viewportGrid);
        }
    }
};

// `window.onload` 이벤트에서 `loadDicom` 함수를 호출
window.onload = () => {
    const studyKey = document.getElementById('studyKey').value; // studyKey 값 가져오기
    loadDicom(studyKey); // loadDicom 함수 호출
};
