/* Javascipt used to improve portfolio's page*/
"Use strict";
function getDate(){
    var today = new Date();
    document.getElementById("Date").innerHTML = today.toString();
    setTimeout("getDate()", 1000);
}
