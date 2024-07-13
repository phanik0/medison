// function printNote() {
//     const noteContent = document.getElementById('noteContent');
//
//     html2canvas(noteContent, {
//         useCORS: true,
//         scale: 2
//     }).then(canvas => {
//         const imgData = canvas.toDataURL('image/png');
//         const pdf = new jsPDF({
//             orientation: 'portrait',
//             unit: 'mm',
//             format: 'a4'
//         });
//         const imgWidth = 210; // A4 width in mm
//         const pageHeight = 297; // A4 height in mm
//         const imgHeight = canvas.height * imgWidth / canvas.width;
//
//         // If the image height exceeds the page height, scale it down
//         const scale = Math.min(1, pageHeight / imgHeight);
//         pdf.addImage(imgData, 'PNG', 0, 0, imgWidth * scale, imgHeight * scale);
//
//         pdf.save('note.pdf');
//     }).catch(error => {
//         console.error('Error capturing the note content:', error);
//     });
// }

function printNote(){
    window.print();
}

window.onafterprint = function (){
    location.href = "/main";
}

// Call the print function when the page loads
window.onload = function() {
    printNote();
}