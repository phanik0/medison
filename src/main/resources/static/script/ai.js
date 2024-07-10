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

    overlayData.forEach(data => {
        context.beginPath();
        const graphicObjectSequence = data.GraphicObjectSequence;
        console.log(graphicObjectSequence);
        if(!graphicObjectSequence) {
            graphicObjectSequence.forEach(graphic => {
                const point = graphic.GraphicData;
                const CIE = graphic.LineStyleSequence.PatternOnColorCIELabValue;
                const color = cielabToRgb(CIE.color.l, CIE.color.a, CIE.color.b);
                const graphicType = graphic.GraphicType;
                const numberOfPoint = graphic.NumberOfGraphicPoints;
                context.strokeStyle = color;
                context.lineWidth = graphic.LineThickness;

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
        }

    });

    element.appendChild(canvas);
}
document.addEventListener("DOMContentLoaded", () => {
    const studyKey = 25;
    const overlay = document.getElementById('overlay');
    drawOverlay(overlay, studyKey).then(err,console.log(err));

})
