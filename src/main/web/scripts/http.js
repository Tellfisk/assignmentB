var url = "http://84.215.98.118:8080",
    infoHer = document.getElementById("infoHer");


// Fetching function
let getFunc = async (nowUrl) => {
    console.log("button pressed");

    const response = await fetch(nowUrl, {
        method: 'GET',
        //body: myBody, // string or object
        headers: {
            'Content-Type': 'application/json'
        }
    });
    const myJson = await response.text(); //extract JSON from the http response
    return myJson;
}

// Specific functions
async function getSinglePoll() {
    var id = document.getElementById("pollIdInp").value; // CHANGE

    if(id===""){
        infoHer.innerHTML = "<h2>ERROR:</h2> <br> Please input ID";
        return;
    }

    var currUrl = url + "/polls/"+id,
        retVal = await getFunc(currUrl);
    console.log(retVal.value);

    // if(!Object.keys(retVal).length){
    console.log(retVal);

    if (retVal === ""){
        printval = "No poll with this id.";
    }
    else{
        var parsedVal = JSON.parse(retVal);
        printval = parsePoll(parsedVal)
    }
    infoHer.innerHTML = "<h2>POLL WITH ID " + id + ":</h2><br>" + printval;

}
async function getAllPolls() {
    var currUrl = url + "/polls",
        retVal = await getFunc(currUrl);
    console.log(retVal);
    var myArr = JSON.parse(retVal);

    console.log(myArr);
    var printString = "";
    myArr.forEach(currPoll => {
        printString += parsePoll(currPoll);
    });

    infoHer.innerHTML = "<h2>ALL POLLS:</h2> <br> <p style='text-align: left; margin-left: 8px;' >"+printString+"</p>";

}

function parsePoll(poll){
    var printString = "";
    printString += "ID: " + poll['id'] + "<br>";
    printString += "Pollname: " + JSON.stringify(poll['name']);
    printString += "<br>Votes: " + poll['votes'].length + "<br><br>";
    return printString;
}