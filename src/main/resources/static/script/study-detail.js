import * as cornerstone from '@cornerstonejs/core';
import * as cornerstoneTools from '@cornerstonejs/tools';
import * as cornerstoneDICOMImageLoader from '@cornerstonejs/dicom-image-loader';
import * as dicomParser from 'dicom-parser';
import {RenderingEngine} from "@cornerstonejs/core";

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
    PlanarRotateTool,                       // 이미지 회전,
    StackScrollTool                         // 이미지 스크롤
} = cornerstoneTools;
const {MouseBindings} = csToolsEnums;
const toolGroupId = 'myToolGroup';
cornerstoneTools.init();
cornerstoneTools.addTool(StackScrollTool);
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
toolGroup.addTool(StackScrollTool.toolName);
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

window.onload = async () => {
    const studyKey = document.getElementById('studyKey').value; // studyKey 값 가져오기
    const userId = document.getElementById('userId').value;
    await fetch(`/log?userId=${userId}&studykey=${studyKey}`,{
        method:'POST',
    })
        .then(response=>{
            if(response){
                onload(studyKey);
            }else{
                alert("로그 남기기 실패.")
                window.location.href = '/main';
            }
        })
}

const onload = async function (studyKey) {
    await init();
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
                    const imageId = `dicomweb:${URL.createObjectURL(new Blob([arrayBuffer], {type: 'application/dicom'}))}`;
                    seriesImageIds.push(imageId);
                }
                imageIdsBySeries.push(seriesImageIds);
            });
            await Promise.all(promises);
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

            if (renderingEngine.id === mainRenderingEngineId) {
                toolGroupInitailize(viewportId, renderingEngine.id);
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
            const seriesDiv = document.createElement('div');
            const element = document.createElement('div');

            element.className = 'thumbnail';
            element.setAttribute("id", thumbnailViewportId);
            element.style.width = '150px';
            element.style.height = '150px';

            const p = document.createElement('p');
            p.style.fontSize = '12px';
            p.innerHTML = `시리즈 번호 : ${i+1}`;

            seriesDiv.style.display = 'flex';
            seriesDiv.style.flexDirection = 'column';
            seriesDiv.style.alignItems = 'center';

            seriesDiv.append(element);
            seriesDiv.append(p);


            thumbnailList.append(seriesDiv);

            await renderImageInViewport(thumbnailViewportId, [thumnailImageId], thumnailRenderingEngine);
        }

        let currentTool = StackScrollTool.toolName;
        document.getElementById(currentTool).style.backgroundColor = 'lightgrey';

        const toolGroupInitailize = function (viewportId, rederingEngineId) {
            toolGroup.addViewport(viewportId, rederingEngineId);

            toolGroup.setToolActive(currentTool, {
                bindings: [{mouseButton: MouseBindings.Primary}],
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
        }

        const renderSizeChange = async (row, column) => {
            clearAndCreateViewports(row, column);
            await renderMainImages(row, column);
            const targetViewport = document.getElementById(targetViewportId);
            if (targetViewport) {
                targetViewport.style.border = '1px solid red';
            }
        }

        await renderSizeChange(1, 1);

        viewportGrid.addEventListener('mousedown', (event) => {
            if (event.target.id === "viewportGrid")
                return;

            if (event.target.nodeName === "CANVAS") {
                const targetElement = event.target.parentElement.parentElement;
                const targetUid = targetElement.dataset.viewportUid;

                if (targetViewportId) {
                    const targetViewport = document.getElementById(targetViewportId);
                    if (targetViewport) {
                        targetViewport.style.border = '1px solid #fff';
                    }
                }

                targetElement.style.border = '1px solid red';

                targetViewportId = targetUid;
            } else {
                if (targetViewportId) {
                    const targetViewport = document.getElementById(targetViewportId);
                    if (targetViewport) {
                        targetViewport.style.border = '1px solid #fff';
                    }
                }
                targetViewportId = event.target.id;
                document.getElementById(targetViewportId).style.border = '1px solid red';
            }
        });

        thumbnailList.addEventListener('dblclick', (e) => {
            if (e.target.nodeName === "CANVAS" && targetViewportId) {
                const basicThumbnailViewportUID = 'thumbnailViewport';
                const thumnailUID = e.target.parentElement.parentElement.dataset.viewportUid;

                const wishIndex = parseInt(thumnailUID.slice(basicThumbnailViewportUID.length, thumnailUID.length));
                renderImageInViewport(targetViewportId, imageIdsBySeries[wishIndex], mainRenderingEngine);
            }
        });


        const convertTools = function (selectedTool) {
            toolGroup.setToolPassive(currentTool);

            toolGroup.setToolActive(selectedTool, {
                bindings: [{mouseButton: MouseBindings.Primary}],
            });
            document.getElementById(currentTool).style.backgroundColor = 'transparent';
            currentTool = selectedTool;
            document.getElementById(currentTool).style.backgroundColor = 'grey';

        }

        const toolistSection = document.getElementById("top-bar");

        toolistSection.addEventListener('click', (e) => {
            if (e.target.className === "tools") {
                convertTools(e.target.id);
            }
        })

        const seriesRowColumnCheckBox = document.getElementById("check-row-column-by-series");

        const gridItems = document.querySelectorAll('.row-column-box');

        seriesRowColumnCheckBox.addEventListener('click', (e) => {
            if (e.target.className === "row-column-box highlight") {
                const row = parseInt(e.target.dataset.row);
                const col = parseInt(e.target.dataset.column);
                renderSizeChange(row, col);
            }
        })

        // 각 gridItem에 마우스 이벤트 리스너 추가
        gridItems.forEach(item => {
            // 마우스를 올리면 해당 셀 강조
            item.addEventListener('mouseenter', () => {
                const row = parseInt(item.dataset.row);
                const col = parseInt(item.dataset.column);
                highlightCells(row, col);
            });

            // 마우스를 떼면 강조 해제
            item.addEventListener('mouseleave', () => {
                clearHighlights();
            });
        });

        // 셀 강조
        function highlightCells(row, col) {
            gridItems.forEach(item => {
                const itemRow = parseInt(item.dataset.row);
                const itemCol = parseInt(item.dataset.column);

                if (itemRow <= row && itemCol <= col) {
                    item.classList.add('highlight');
                } else {
                    item.classList.remove('highlight');
                }
            });
        }

        // 강조 해제
        function clearHighlights() {
            gridItems.forEach(item => {
                item.classList.remove('highlight');
            });
        }

        document.getElementById("annotation-list").addEventListener('click', () => {
            const annotationBox = document.getElementById("annotation-tool");
            if (annotationBox.style.display === 'none' || annotationBox.style.display === '') {
                annotationBox.style.display = 'flex';
                annotationBox.style.flexDirection = 'row';
            } else {
                annotationBox.style.display = 'none';
            }
        });

        document.getElementById("row-column-button").addEventListener('click', () => {
            const rowColumnBox = document.getElementById("check-row-column-by-series");
            if (rowColumnBox.style.display === 'none' || rowColumnBox.style.display === '') {
                rowColumnBox.style.display = 'flex';
            } else {
                rowColumnBox.style.display = 'none';
            }
        });

    }
}



