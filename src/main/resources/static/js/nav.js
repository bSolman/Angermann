function vibrate(){
    window.navigator.vibrate(200);
}

function showMenu(){
    if (document.getElementById("dropdown-content").style.display === "block"){
        document.getElementById("dropdown-content").style.display = "none";
    }
    else {
        document.getElementById("dropdown-content").style.display = "block";
    }

}