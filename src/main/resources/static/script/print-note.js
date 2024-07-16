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