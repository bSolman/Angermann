function showUpper(){
    if (document.getElementById("upperForm").className === "form2"){
        document.getElementById("upperForm").className = "form1";
    }
    else {
        document.getElementById("upperForm").className = "form2";
    }
}

function showLower(){
    if (document.getElementById("lowerForm").className === "form2"){
        document.getElementById("lowerForm").className = "form1";
    }
    else {
        document.getElementById("lowerForm").className = "form2";
    }
}

function alterInput(){
    if (document.getElementById("inputFile").className === "inputDataFile"){
        document.getElementById("inputFile").className = "dataFile";
        document.getElementById("inputText").className = "inputDataFile";
    }
    else {
        document.getElementById("inputFile").className = "inputDataFile";
        document.getElementById("inputText").className = "";
    }
}

function showAddUser(){
    if (document.getElementById("addUser").className === "form2"){
        document.getElementById("addUser").className = "form1";
    }
    else {
        document.getElementById("addUser").className = "form2";
    }
}

function showAlterRole(){
    if (document.getElementById("alterAuthority").className === "form2"){
        document.getElementById("alterAuthority").className = "form1";
    }
    else {
        document.getElementById("alterAuthority").className = "form2";
    }
}

function showRemoveUser(){
    if (document.getElementById("removeUser").className === "form2"){
        document.getElementById("removeUser").className = "form1";
    }
    else {
        document.getElementById("removeUser").className = "form2";
    }
}