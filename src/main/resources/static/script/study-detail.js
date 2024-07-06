import * as cornerstone from '@cornerstonejs/core';
import * as cornerstoneTools from '@cornerstonejs/tools';
import * as cornerstoneDICOMImageLoader from '@cornerstonejs/dicom-image-loader';
import * as dicomParser from 'dicom-parser';
import { RenderingEngine } from "@cornerstonejs/core";

const {
    ZoomTool, ToolGroupManager,     // 줌
    Enums: csToolsEnums,
    LengthTool, AngleTool,                 //길이,각도
    MagnifyTool,                            // 돋보기
    StackScrollMouseWheelTool,              // 마우스 휠로 시리즈 돌기
    HeightTool,                             // 높이
    ProbeTool,                              // 프로브
    RectangleROITool,                       // 사각형
    EllipticalROITool,                      // 타원형
    CircleROITool,                          // 원
    BidirectionalTool,                      // 양방향 (십자)
    CobbAngleTool,                          // 코브앵글 짝이 되는 선으로 각도재기
    ArrowAnnotateTool,                      // 화살표
    PlanarFreehandROITool,                  // 연필 주석
    EraserTool,                             // 지우개
    PanTool,                                // 이미지 이동
    WindowLevelTool,                        // 윈도우 레벨
    PlanarRotateTool,                       // 이미지 회전
} = cornerstoneTools;
const { MouseBindings } = csToolsEnums;

const toolGroupId = 'myToolGroup';
cornerstoneTools.init();
cornerstoneTools.addTool(ZoomTool);
cornerstoneTools.addTool(StackScrollMouseWheelTool);
cornerstoneTools.addTool(MagnifyTool);
cornerstoneTools.addTool(PanTool);
cornerstoneTools.addTool(WindowLevelTool);
cornerstoneTools.addTool(PlanarRotateTool);
// 주석도구들
cornerstoneTools.addTool(LengthTool);
cornerstoneTools.addTool(AngleTool);
cornerstoneTools.addTool(HeightTool);
cornerstoneTools.addTool(ProbeTool);
cornerstoneTools.addTool(RectangleROITool);
cornerstoneTools.addTool(EllipticalROITool);
cornerstoneTools.addTool(CircleROITool);
cornerstoneTools.addTool(BidirectionalTool);
cornerstoneTools.addTool(CobbAngleTool);
cornerstoneTools.addTool(ArrowAnnotateTool);
cornerstoneTools.addTool(EraserTool);
cornerstoneTools.addTool(PlanarFreehandROITool);

const toolGroup = ToolGroupManager.createToolGroup(toolGroupId);
toolGroup.addTool(ZoomTool.toolName);
toolGroup.addTool(StackScrollMouseWheelTool.toolName);
toolGroup.addTool(MagnifyTool.toolName);
toolGroup.addTool(PanTool.toolName);
toolGroup.addTool(WindowLevelTool.toolName);
toolGroup.addTool(PlanarRotateTool.toolName);
// 주석도구들
toolGroup.addTool(LengthTool.toolName);
toolGroup.addTool(AngleTool.toolName);
toolGroup.addTool(HeightTool.toolName);
toolGroup.addTool(ProbeTool.toolName);
toolGroup.addTool(RectangleROITool.toolName);
toolGroup.addTool(EllipticalROITool.toolName);
toolGroup.addTool(CircleROITool.toolName);
toolGroup.addTool(BidirectionalTool.toolName);
toolGroup.addTool(CobbAngleTool.toolName);
toolGroup.addTool(ArrowAnnotateTool.toolName);
toolGroup.addTool(EraserTool.toolName);
toolGroup.addTool(PlanarFreehandROITool.toolName);

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
    init();
    onload(studyKey);
}

const onload = async function (studyKey) {
    const fetchDetailData = await fetch(`/images/${studyKey}`);
    const studyInfo = await fetchDetailData.json();
    console.log("파일 로드 완료");
    console.log(fetchDetailData);

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
        let targetViewportId = "CT_0";

        const mainRenderingEngineId = "mainRenderingEngine";
        const mainRenderingEngine = new RenderingEngine(mainRenderingEngineId);

        const createImageBySeries = async function () {
            const imageIdsBySeries = [];
            const promises = studyInfo.map(async (series) => {
                const seriesImageIds = [];
                for (const image of series) {
                    const filePath = image.path + image.fname;
                    const arrayBuffer = await loadFiles(filePath);
                    const imageId = `dicomweb:${URL.createObjectURL(new Blob([arrayBuffer], { type: 'application/dicom' }))}`;
                    seriesImageIds.push(imageId);
                }
                imageIdsBySeries.push(seriesImageIds);
            });
            await Promise.all(promises);
            console.log("이미지 아이디 생성완료");
            return imageIdsBySeries;
        }

        const imageIdsBySeries = await createImageBySeries();
        // 썸네일 랜더링
        const thumnailRenderingEngine = new RenderingEngine('thumbnailRenderingEngine');

        const renderImageInViewport = async function (viewportId, images, renderingEngine) {
            renderingEngine.enableElement({
                viewportId: viewportId,
                element: document.getElementById(viewportId),
                type: cornerstone.Enums.ViewportType.STACK,
            });

            if(renderingEngine.id===mainRenderingEngineId){
                toolGroupInitailize(viewportId,renderingEngine.id);
            }

            const targetViewport = renderingEngine.getViewport(viewportId);
            if (!targetViewport) {
                console.error(`Viewport not found: ${viewportId}`);
                return;
            }
            targetViewport.setStack(images);
            cornerstoneTools.utilities.stackPrefetch.enable(targetViewport.element);
            await targetViewport.render();
        }

        for (let i = 0; i < imageIdsBySeries.length; i++) {
            const thumnailImageId = imageIdsBySeries[i][0];
            const thumbnailViewportId = 'thumbnailViewport' + i;
            const element = document.createElement('div');

            element.className = 'thumbnail';
            element.setAttribute("id", thumbnailViewportId);
            element.style.width = '150px';
            element.style.height = '150px';

            thumbnailList.append(element);

            await renderImageInViewport(thumbnailViewportId, [thumnailImageId], thumnailRenderingEngine);
        }
        console.log("썸네일 렌더링 완료");

        const toolGroupInitailize = function (viewportId,rederingEngineId){
            toolGroup.addViewport(viewportId, rederingEngineId);

            toolGroup.setToolActive(ZoomTool.toolName, {
                bindings: [{ mouseButton: MouseBindings.Primary }],
            });
            toolGroup.setToolActive(MagnifyTool.toolName, {
                bindings: [{ mouseButton: MouseBindings.Secondary }],
            });
            toolGroup.setToolActive(AngleTool.toolName, {
                bindings: [{ mouseButton: MouseBindings.Auxiliary }],
            });
            toolGroup.setToolActive(StackScrollMouseWheelTool.toolName);
        }

        const mainViewportIds = [];

        const clearAndCreateViewports = function (row, column) {
            viewportGrid.textContent = "";      // 자식 삭제

            mainViewportIds.length = 0;  // 빈 배열로 만듬

            const total = row * column;

            const widthStyle = "" + (100 / column - 1.5) + "%";
            const heightStyle = "" + (100 / row - 1.5) + "%";

            // 뷰포트 생성
            for (let i = 0; i < total; i++) {
                const element = document.createElement('div');
                element.oncontextmenu = (e) => e.preventDefault();
                element.className = 'viewport';
                element.setAttribute("id", "CT_" + i);
                element.style.width = widthStyle;
                element.style.height = heightStyle;

                mainViewportIds.push("CT_" + i);

                viewportGrid.append(element);
            }

            console.log("메인 뷰포트 생성완료");
        }

        const renderMainImages = async function (row, column) {
            const total = row * column;
            const targetLength = imageIdsBySeries.length >= total ? total : imageIdsBySeries.length;

            const promises = [];
            for (let i = 0; i < targetLength; i++) {
                const mainImageIds = imageIdsBySeries[i];
                const mainViewportId = 'CT_' + i;
                promises.push(renderImageInViewport(mainViewportId, mainImageIds, mainRenderingEngine));
            }
            await Promise.all(promises);
            console.log("메인 이미지 렌더링 완료.");
        }

        const renderSizeChange = async (row, column) => {
            clearAndCreateViewports(row, column);
            await renderMainImages(row, column);
            document.getElementById(targetViewportId).style.border = '1px solid red';
        }

        await renderSizeChange(5, 5);

        viewportGrid.addEventListener('dblclick', (event) => {
            if (event.target.id === "viewportGrid")
                return;

            if (event.target.nodeName === "CANVAS") {
                const targetElement = event.target.parentElement.parentElement;
                const targetUid = targetElement.dataset.viewportUid;

                if (targetViewportId) {
                    document.getElementById(targetViewportId).style.border = '1px solid #fff';
                }

                targetElement.style.border = '1px solid red';

                targetViewportId = targetUid;
            } else {
                if (targetViewportId) {
                    document.getElementById(targetViewportId).style.border = '1px solid #fff';
                }
                targetViewportId = event.target.id;
                document.getElementById(targetViewportId).style.border = '1px solid red';
            }
        });

        thumbnailList.addEventListener('dblclick', (e) => {
            if (e.target.nodeName === "CANVAS" && targetViewportId) {
                const basicThumbnailViewportUID = 'thumbnailViewport';
                const thumnailUID = e.target.parentElement.parentElement.dataset.viewportUid;

                const wishIndex = parseInt(thumnailUID.slice(basicThumbnailViewportUID.length,thumnailUID.length));
                renderImageInViewport(targetViewportId, imageIdsBySeries[wishIndex], mainRenderingEngine);
            }
        });
    }
}



