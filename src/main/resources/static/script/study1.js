import * as cornerstone from '@cornerstonejs/core';
import {imageLoader, RenderingEngine} from '@cornerstonejs/core';
import * as cornerstoneTools from '@cornerstonejs/tools';
import * as cornerstoneDICOMImageLoader from '@cornerstonejs/dicom-image-loader';
import * as dicomParser from 'dicom-parser';

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
//
// window.onload = async () => {
//     const studyKey = document.getElementById('studyKey').value; // studyKey 값 가져오기
//     const userId = document.getElementById('userId').value;
// await fetch(`/log?userId=${userId}&studykey=${studyKey}`,{
//     method:'POST',
// })
//     .then(response=>{
//         if(response){
//             onload(studyKey);
//         }else{
//             alert("로그 남기기 실패.")
//             window.location.href = '/main';
//         }
//     })
//     const overlay = document.getElementById('overlay');
//     onload(studyKey).then(() => drawOverlay(overlay, studykey))
// }

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
                console.log(series.imagecnt)
                if(series.imagecnt !== 0) {
                    for (const image of series) {
                        if (image.fname) {
                            const filePath = image.path + image.fname;
                            console.log(filePath);
                            const arrayBuffer = await loadFiles(filePath);

                            const imageId = `dicomweb:${URL.createObjectURL(new Blob([arrayBuffer], {type: 'application/dicom'}))}`;
                            if (imageId) {
                                seriesImageIds.push(imageId);
                            }
                        }
                    }
                    imageIdsBySeries.push(seriesImageIds);
                }
            });
            await Promise.all(promises);
            return imageIdsBySeries;
        }
        const getImageSizes = async function () {
            const imageSizesBySeries = [];
            const promises = studyInfo.map(async (series) => {
                const seriesImageSizes = [];
                for (const image of series) {
                    if (image.fname) {
                        const width = image.pixelcolumns;
                        const height = image.pixelrows;
                        const size = {width, height};
                        console.log(size);

                        seriesImageSizes.push(size);
                    }
                }
                imageSizesBySeries.push(seriesImageSizes);
            });
            return imageSizesBySeries;
        }
        const imageIdsBySeries = await createImageBySeries();
        console.log(imageIdsBySeries);
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
            p.innerHTML = `시리즈 번호 : ${i + 1}`;

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
            const overlay = document.getElementById('overlay');
            if (overlay) {
                viewportGrid.append(overlay);
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
                        targetViewport.style.border = '1px solid #ccc';
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
            }
        });

        document.getElementById("row-column-button").addEventListener('click', () => {
            const rowColumnBox = document.getElementById("check-row-column-by-series");
            if (rowColumnBox.style.display === 'none' || rowColumnBox.style.display === '') {
                rowColumnBox.style.display = 'flex';
            }
        });

        document.getElementById("show-absolute-row-column").addEventListener('mouseleave', () => {
            const rowColumnBox = document.getElementById("check-row-column-by-series");
            if (rowColumnBox.style.display === 'flex') {
                rowColumnBox.style.display = 'none';
            }
        })
        document.getElementById("show-absolute-tools").addEventListener('mouseleave', () => {
            const annotationBox = document.getElementById("annotation-tool");
            if (annotationBox.style.display === 'flex') {
                annotationBox.style.display = 'none';
            }
        })


// Function to draw overlay
        const fetchProcessedPrDataList = async (studyKey) => {
            try {
                const response = await fetch(`/ai/${studyKey}`);
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
        }

        function decodeCIELab(encodedL, encodedA, encodedB) {
            const L = (encodedL / 65535) * 100;
            const a = ((encodedA / 65535) * 255) - 128;
            const b = ((encodedB / 65535) * 255) - 128;

            return {L, a, b};
        }

        function cielabToRgb(l, a, bChannel) {
            let y = (l + 16) / 116;
            let x = a / 500 + y;
            let z = y - bChannel / 200;

            [x, y, z] = [x, y, z].map(value => {
                if (value ** 3 > 0.008856) {
                    return value ** 3;
                }
                return (value - 16 / 116) / 7.787;
            });

            [x, y, z] = [
                x * 0.95047,
                y * 1.00000,
                z * 1.08883
            ];

            let r = x * 3.2406 + y * -1.5372 + z * -0.4986;
            let g = x * -0.9689 + y * 1.8758 + z * 0.0415;
            let b = x * 0.0557 + y * -0.2040 + z * 1.0570;

            [r, g, b] = [r, g, b].map(value => {
                if (value > 0.0031308) {
                    return 1.055 * (value ** (1 / 2.4)) - 0.055;
                }
                return 12.92 * value;
            });

            r = Math.min(Math.max(Math.round(r * 255), 0), 255);
            g = Math.min(Math.max(Math.round(g * 255), 0), 255);
            b = Math.min(Math.max(Math.round(b * 255), 0), 255);

            return `rgb(${r}, ${g}, ${b})`;
        }

// Function to calculate ellipse parameters from 4 points
        function calculateEllipseParams(points) {
            const xMean = points.reduce((sum, p) => sum + p.Column, 0) / points.length;
            const yMean = points.reduce((sum, p) => sum + p.Row, 0) / points.length;

            const covXX = points.reduce((sum, p) => sum + Math.pow(p.Column - xMean, 0), 0) / points.length;
            const covXY = points.reduce((sum, p) => sum + (p.Column - xMean) * (p.Row - yMean), 0) / points.length;
            const covYY = points.reduce((sum, p) => sum + Math.pow(p.Row - yMean, 2), 0) / points.length;

            const lambda1 = (covXX + covYY) / 2 + Math.sqrt(Math.pow((covXX - covYY) / 2, 2) + Math.pow(covXY, 2));
            const lambda2 = (covXX + covYY) / 2 - Math.sqrt(Math.pow((covXX - covYY) / 2, 2) + Math.pow(covXY, 2));

            const a = 2 * Math.sqrt(lambda1);
            const b = 2 * Math.sqrt(lambda2);

            const theta = Math.atan2(2 * covXY, covXX - covYY) / 2;

            return {centerX: xMean, centerY: yMean, a, b, theta};
        }

        const loadImage = async (imageId) => {
            try {
                const image = await imageLoader.loadImage(imageId);
                return image;
            } catch (error) {
                console.error('Error loading image:', error);
                throw error;
            }
        };

// 이미지 크기 가져오기 함수
        const getImageSize = async (imageId) => {
            const image = await loadImage(imageId);
            return {width: image.width, height: image.height};
        };

// overlay와 viewport의 크기를 동기화하는 함수
//         const syncOverlayWithViewport = async (viewportId, imageId) => {
//             const viewport = document.getElementById(viewportId);
//             const overlay = document.getElementById('overlay');
//
//             const {width: imageWidth, height: imageHeight} = await getImageSize(imageId);
//             console.log(imageWidth, imageHeight);
//             const viewportSize = $('.viewport')[0];
//             const viewportWidth = viewportSize.offsetWidth;
//             const viewportHeight = viewportSize.offsetHeight;
//             // overlay 크기를 이미지 크기와 일치시키기
//             overlay.style.width =  `${viewportWidth}px`
//             overlay.style.height = `${viewportHeight}px`
//             const canvas = overlay.querySelector('canvas');
//             if (canvas) {
//                 canvas.width = viewportWidth;
//                 canvas.height = viewportHeight;
//             }
//         };


        const drawTextObject = (textObject, context,scaleX, scaleY) => {

            const unformattedTextValue = textObject.UnformattedTextValue;
            const textStyleSequence = textObject.TextStyleSequence;
            const boundingBoxTopLeftHandCorner = textObject.BoundingBoxTopLeftHandCorner;
            const boundingBoxBottomRightHandCorner = textObject.BoundingBoxBottomRightHandCorner;
            const anchorPoint = textObject.AnchorPoint;
            const anchorPointVisibility = textObject.AnchorPointVisibility;

            // Drawing the bounding box
            context.beginPath();
            context.rect(
                boundingBoxTopLeftHandCorner.Column*scaleX,
                boundingBoxTopLeftHandCorner.Row*scaleY,
                (boundingBoxBottomRightHandCorner.Column - boundingBoxTopLeftHandCorner.Column)*scaleX,
                (boundingBoxBottomRightHandCorner.Row - boundingBoxTopLeftHandCorner.Row)*scaleY
            );
            context.stroke();
            // 텍스트 스타일 설정
            if (textStyleSequence && textStyleSequence[0]) {
                const textStyle = textStyleSequence[0];
                context.font = `${textStyle.Bold === 'Y' ? 'bold' : ''} ${textStyle.Italic === 'Y' ? 'italic' : ''} ${textStyle.Underlined === 'Y' ? 'underline' : ''} ${textStyle.CSSFontName}`;
                const encodedCIE = textStyle.TextColorCIELabValue;
                const decodedCIE = decodeCIELab(encodedCIE.L, encodedCIE.a, encodedCIE.b);
                const textColor = cielabToRgb(decodedCIE.L, decodedCIE.a, decodedCIE.b);
                context.fillStyle = textColor;

                // 그림자 스타일 설정
                if (textStyle.ShadowStyle === 'OUTLINED') {
                    const shadowCIE = textStyle.ShadowColorCIELabValue;
                    const decodedShadowCIE = decodeCIELab(shadowCIE.L, shadowCIE.a, shadowCIE.b);
                    const shadowColor = cielabToRgb(decodedShadowCIE.L, decodedShadowCIE.a, decodedShadowCIE.b);
                    context.shadowColor = shadowColor;
                    context.shadowOffsetX = textStyle.ShadowOffsetX;
                    context.shadowOffsetY = textStyle.ShadowOffsetY;
                    context.shadowBlur = textStyle.ShadowOpacity * 10; // shadowOpacity를 블러로 변환
                }
            }
            // Drawing the text
            context.fillText(
                unformattedTextValue,
                boundingBoxTopLeftHandCorner.Column*scaleX,
                boundingBoxTopLeftHandCorner.Row*scaleY
            );

            // Drawing the anchor point if visible
            if (anchorPointVisibility === 'Y') {
                context.beginPath();
                context.arc(anchorPoint.Column*scaleX, anchorPoint.Row*scaleY, 5, 0, 2 * Math.PI);
                context.fill();
            }
        };

// Updated drawOverlay function
        async function drawOverlay(element, studyKey,imageId) {
            if (!element) {
                console.error('Overlay element not found.');
                return;
            }

            const overlayData = await fetchProcessedPrDataList(studyKey);
            console.log('Fetched data:', overlayData);  // 데이터 로그

            if (!overlayData || overlayData.length === 0) {
                console.error('No overlay data found.');
                return;
            }
            const {width: imageWidth, height: imageHeight} = await getImageSize(imageId);

            // viewport 크기를 가져오기
            const viewport = $('.viewport')[0];
            const viewportWidth = viewport.offsetWidth;
            const viewportHeight = viewport.offsetHeight;
            console.log('Viewport size:', viewportWidth, viewportHeight);


            // overlay 크기를 viewport 크기와 일치시키기
            const canvas = document.createElement('canvas');
            canvas.width = viewportWidth;
            canvas.height = viewportHeight;
            element.appendChild(canvas);
            console.log('Canvas size:', canvas.width, canvas.height);

            element.style.width =`${viewportWidth}px`
            element.style.height = `${viewportHeight}px`;
            element.appendChild(canvas);
            console.log('Canvas created:', canvas);  // 캔버스 생성 확인
            console.log('Overlay size:', element.offsetWidth, element.offsetHeight);
            console.log('Canvas size:', canvas.width, canvas.height);
            const context = canvas.getContext('2d');

            const scaleX = parseFloat($('.viewport').css('width')) / imageWidth;
            const scaleY = parseFloat($('.viewport').css('height')) / imageHeight;

            overlayData.forEach((data, index) => {
                console.log(`Processing data ${index}:`, data);  // 각 데이터 처리 로그
                const graphicObjectSequence = data.GraphicObjectSequence;
                const textObject = data.TextObjectSequence
                if (graphicObjectSequence) {
                    graphicObjectSequence.forEach(graphic => {
                        const points = graphic.GraphicData;
                        const lineStyle = graphic.LineStyleSequence;
                        const CIE = lineStyle[0].PatternOnColorCIELabValue;
                        const graphicType = graphic.GraphicType;
                        const encodedCIE = CIE;
                        const decodedCIE = decodeCIELab(encodedCIE.L, encodedCIE.a, encodedCIE.b);

                        const color = cielabToRgb(decodedCIE.L, decodedCIE.a, decodedCIE.b);

                        context.strokeStyle = color;
                        context.lineWidth = lineStyle[0].LineThickness
                        console.log('Drawing settings:', {color, lineWidth: lineStyle[0].LineThickness, graphicType});
                        context.beginPath();
                        if (graphicType === 'POLYLINE') {
                            points.forEach((point, idx) => {
                                const x = point.Column * scaleX;
                                const y = point.Row * scaleY;

                                if (idx === 0) {
                                    context.moveTo(x, y);
                                } else {
                                    context.lineTo(x, y);
                                }

                            });
                        } else if (graphicType === 'ELLIPSE') {

                            const {centerX, centerY, a, b, theta} = calculateEllipseParams(points);
                            context.ellipse(centerX * scaleX, centerY * scaleY, a * scaleX, b * scaleY, theta, 0, 2 * Math.PI);
                            // context.ellipse(centerX, centerY, a, b, theta, 0, 2 * Math.PI);
                            console.log('centerX : ' + centerX*scaleX, 'centerY :' + centerY*scaleY);
                        }
                        if (lineStyle.ShadowStyle === 'OUTLINED') {
                            const shadowCIE = lineStyle.ShadowColorCIELabValue;
                            const decodedShadowCIE = decodeCIELab(shadowCIE.L, shadowCIE.a, shadowCIE.b);
                            const shadowColor = cielabToRgb(decodedShadowCIE.L, decodedShadowCIE.a, decodedShadowCIE.b);
                            context.shadowColor = shadowColor;
                            context.shadowOffsetX = lineStyle.ShadowOffsetX;
                            context.shadowOffsetY = lineStyle.ShadowOffsetY;
                            context.shadowBlur = lineStyle.ShadowOpacity * 10; // shadowOpacity를 블러로 변환
                        }
                        context.stroke();
                        console.log('Shape drawn');
                    });
                }
                if (textObject) {
                    textObject.forEach(text => {
                        drawTextObject(text, context,scaleX,scaleY);
                    })
                }
            });
        }

        const main = async () => {
            let imageId = '';
            imageIdsBySeries.forEach(id => {
                if (id) {
                    imageId = id[0];
                }
                console.log(id);
            })
            console.log(imageId);
            const overlay = document.getElementById('overlay');
            // await syncOverlayWithViewport('viewport', imageId);
            await drawOverlay(overlay, studyKey, imageId);
        };

        main().catch(console.error);

        // cornerstone.events.addEventListener('cornerstoneimagerendered', function(event) {
        //     const viewport = event.detail.element;
        //     const imageId = event.detail.image.imageId;
        //
        //     const viewportWidth = viewport.offsetWidth;
        //     const viewportHeight = viewport.offsetHeight;
        //
        //     overlay.style.width = `${viewportWidth}px`;
        //     overlay.style.height = `${viewportHeight}px`;
        //
        //     canvas.width = viewportWidth;
        //     canvas.height = viewportHeight;
        //
        //     drawOverlay(overlay, studyKey, imageId);
        // });

    }
}

function updateOverlayPosition() {
    const viewport = document.querySelector('.viewport');
    const overlay = document.getElementById('overlay');

    if (viewport && overlay) {
        const rect = viewport.getBoundingClientRect();
        overlay.style.position = 'absolute';
        overlay.style.top = `${rect.top}px`;
        overlay.style.left = `${rect.left}px`;
        overlay.style.width = `${rect.width}px`;
        overlay.style.height = `${rect.height}px`;
    }
}

// 이벤트 리스너 추가
window.addEventListener('resize', updateOverlayPosition);
// cornerstone.events.addEventListener('cornerstoneimagerendered', updateOverlayPosition);
window.onload = async () => {
    const studyKey = document.getElementById('studyKey').value;
    // const userId = document.getElementById('userId').value;
    try {
        await onload(studyKey);
    } catch (error) {
        console.error('Error loading study:', error);
    }
};