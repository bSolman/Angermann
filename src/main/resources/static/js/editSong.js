

$(document).ready(function() {
    loadData();
});

function loadData(){
    const textarea = document.getElementById('inputSong');
    for (let i = 0; i < songs.length; i++){
        textarea.value += songs[i] + '\n';
    }
}