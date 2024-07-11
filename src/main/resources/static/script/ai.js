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
    if (!overlayData) {
        console.error('No overlay data found.');
        return;
    }
    console.log(overlayData);
    const canvas = document.createElement('canvas');
    canvas.width = element.clientWidth;
    canvas.height = element.clientHeight;
    const context = canvas.getContext('2d');

    overlayData.forEach((data, index) => {
        console.log(data);
        context.beginPath();
        const graphicObjectSequence = data.GraphicObjectSequence;
        console.log(graphicObjectSequence);
        if (graphicObjectSequence) {
            graphicObjectSequence.forEach(graphic => {
                const point = graphic.GraphicData;

                const lineStyle = graphic.LineStyleSequence;
                const CIE = lineStyle[0].PatternOnColorCIELabValue;
                const graphicType = graphic.GraphicType;
                const numberOfPoint = graphic.NumberOfGraphicPoints;

                const color = cielabToRgb(CIE.L, CIE.a, CIE.b);
                context.strokeStyle = color;
                context.lineWidth = lineStyle.LineThickness;

                if (graphicType === 'POLYLINE') {
                    point.forEach((point, index) => {
                        const x = point.column / canvas.width * canvas.clientWidth;
                        const y = point.row / canvas.height * canvas.clientHeight;
                        console.log(x);
                        console.log(y);
                        if (index === 0) {
                            context.moveTo(x, y);
                        } else {
                            context.lineTo(x, y);
                        }
                    });
                } else if (graphicType === 'ELLIPSE') {
                    const { centerX, centerY, a, b, theta } = calculateEllipseParams(point);
                    context.ellipse(centerX, centerY, a, b, theta, 0, 2 * Math.PI);
                }

                context.stroke();
            })
        }

    });

    element.appendChild(canvas);
}

document.addEventListener("DOMContentLoaded", () => {

})
$(document).ready(function() {
    // DOM이 완전히 로드된 후 실행할 코드
    const studyKey = 25;
    const overlay = $('#overlay');
    drawOverlay(overlay, studyKey)
        .then(() => {
            console.log('Overlay drawn successfully');
        })
        .catch(error => {
            console.error('Error drawing overlay:', error);
        });
    console.log("DOM이 준비되었습니다!");
});
