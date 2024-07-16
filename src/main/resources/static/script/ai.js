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

    return { L, a, b };
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

    let r = x *  3.2406 + y * -1.5372 + z * -0.4986;
    let g = x * -0.9689 + y *  1.8758 + z *  0.0415;
    let b = x *  0.0557 + y * -0.2040 + z *  1.0570;

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

    const covXX = points.reduce((sum, p) => sum + Math.pow(p.Column - xMean, 0) ,0) / points.length;
    const covXY = points.reduce((sum, p) => sum + (p.Column - xMean) * (p.Row - yMean), 0) / points.length;
    const covYY = points.reduce((sum, p) => sum + Math.pow(p.Row - yMean, 2), 0) / points.length;

    const lambda1 = (covXX + covYY) / 2 + Math.sqrt(Math.pow((covXX - covYY) / 2, 2) + Math.pow(covXY, 2));
    const lambda2 = (covXX + covYY) / 2 - Math.sqrt(Math.pow((covXX - covYY) / 2, 2) + Math.pow(covXY, 2));

    const a = 2 * Math.sqrt(lambda1);
    const b = 2 * Math.sqrt(lambda2);

    const theta = Math.atan2(2 * covXY, covXX - covYY) / 2;

    return { centerX: xMean, centerY: yMean, a, b, theta };
}

// Updated drawOverlay function
async function drawOverlay(element, studyKey) {
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

    const canvas = document.createElement('canvas');
    canvas.width = 2000;
    canvas.height = 2000;
    element.appendChild(canvas);
    console.log('Canvas created:', canvas);  // 캔버스 생성 확인

    const context = canvas.getContext('2d');


    overlayData.forEach((data, index) => {
        console.log(`Processing data ${index}:`, data);  // 각 데이터 처리 로그
        const graphicObjectSequence = data.GraphicObjectSequence;
        if (graphicObjectSequence) {
            graphicObjectSequence.forEach(graphic => {
                const points = graphic.GraphicData;
                const lineStyle = graphic.LineStyleSequence;
                const CIE = lineStyle[0].PatternOnColorCIELabValue;
                const graphicType = graphic.GraphicType;
                const encodedCIE = lineStyle[0].PatternOnColorCIELabValue;
                const decodedCIE = decodeCIELab(encodedCIE.L, encodedCIE.a, encodedCIE.b);
                console.log('Decoded CIE LAB:', decodedCIE);

                const color = cielabToRgb(decodedCIE.L, decodedCIE.a, decodedCIE.b);
                console.log('RGB Color:', color);

                context.strokeStyle = color;
                context.lineWidth = lineStyle.LineThickness
                console.log('Drawing settings:', { color, lineWidth: lineStyle.LineThickness, graphicType });

                context.beginPath();
                if (graphicType === 'POLYLINE') {
                    points.forEach((point, idx) => {
                        const x = point.Column;
                        const y = point.Row;
                        console.log(`Point ${idx}:`, { x, y });  // 각 점의 좌표 로그
                        if (idx === 0) {
                            context.moveTo(x, y);
                        } else {
                            context.lineTo(x, y);
                        }
                    });
                } else if (graphicType === 'ELLIPSE') {
                    const { centerX, centerY, a, b, theta } = calculateEllipseParams(points);
                    console.log('Ellipse params:', { centerX, centerY, a, b, theta });
                    context.ellipse(centerX, centerY, a, b, theta, 0, 2 * Math.PI);
                }
                context.stroke();
                console.log('Shape drawn');
            });
        }
    });
}

window.addEventListener('load', () => {
    console.log('Window loaded');
    const overlay = document.getElementById('overlay');
    const studyKey = 25;  // 적절한 값으로 설정
    drawOverlay(overlay, studyKey).catch(console.error);
});