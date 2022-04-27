function loadLogo(){
    let cssClass = document.querySelectorAll("categories::before");
    for (var i=0; i<cssClass.length; i++){
        cssClass[i].style.content = "stuff";
        cssClass[i].style.transform = "scale(0.1)";
    }
    alert("logo")
}