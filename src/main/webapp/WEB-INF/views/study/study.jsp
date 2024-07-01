<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Study DICOM Viewer</title>
    <script src="https://unpkg.com/@cornerstonejs/core@2.3.0/dist/cornerstone.min.js"></script>
    <script src="https://unpkg.com/@cornerstonejs/dicom-image-loader@3.1.0/dist/cornerstoneDICOMImageLoader.min.js"></script>
    <script src="https://unpkg.com/dicom-parser@1.8.3/dist/dicomParser.min.js"></script>
</head>
<body>
<h1>Study DICOM Viewer</h1>
<div id="content"></div>
<script>
    window.onload = async function() {
        // Initialize Cornerstone and DICOM image loader
        cornerstoneDICOMImageLoader.external.cornerstone = cornerstone;
        cornerstoneDICOMImageLoader.external.dicomParser = dicomParser;
        cornerstoneDICOMImageLoader.configure({
            beforeSend: function(xhr) {
                // Add custom headers here (e.g., auth tokens)
            }
        });

        const content = document.getElementById('content');
        const element = document.createElement('div');
        element.style.width = '512px';
        element.style.height = '512px';
        element.style.border = '1px solid black';
        content.appendChild(element);

        // Initialize Cornerstone
        await cornerstone.init();

        // Set up the rendering engine and viewport
        const renderingEngineId = 'myRenderingEngine';
        const renderingEngine = new cornerstone.RenderingEngine(renderingEngineId);

        const viewportId = 'CT_AXIAL_STACK';
        const viewportInput = {
            viewportId,
            type: cornerstone.Enums.ViewportType.STACK,
            element,
            defaultOptions: {
                background: [0, 0, 0],
            },
        };

        renderingEngine.enableElement(viewportInput);
        const viewport = renderingEngine.getViewport(viewportId);

        // 이미지 렌더링 함수
        const render = async (imageId) => {
            try {
                const image = await cornerstone.loadImage(imageId);
                viewport.setStack([image.imageId]);
                viewport.render();
            } catch (err) {
                console.error('Error loading image:', err);
            }
        };

        // 스터디 정보를 가져와서 이미지를 렌더링하는 함수
        const fetchStudyInfo = async () => {
            const studyKey = '${study.studykey}';
            const images = ${images};
            if (images.length > 0) {
                const imagePath = images[0].path;
                const imageId = `wadouri:/dicom-file?path=${encodeURIComponent(imagePath)}`;
                render(imageId);
            } else {
                console.error('No images found for this study.');
            }
        };

        // 페이지가 열릴 때 fetchStudyInfo 호출
        fetchStudyInfo();
    };
</script>
</body>
</html>
