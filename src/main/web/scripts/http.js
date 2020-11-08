var url = "http://84.215.98.118:8080",
    infoHer = document.getElementById("infoHer");


// Generic HTTP Request //

let request = async (nowUrl, method, body) => {
    console.log("button pressed");
    let response;
    if (body === "") {
        response = await fetch(nowUrl, {
            method: method,
            headers: {
                'Content-Type': 'application/json'
            }
        });
    } else {
        response = await fetch(nowUrl, {
            method: method,
            body: body, // string or object
            headers: {
                'Content-Type': 'application/json'
            }
        });
    }
    const myJson = await response.text(); //extract JSON from the http response
    return myJson;
}


// Unique REST API calls //

async function getSinglePoll() {
    var id = document.getElementById("pollIdInp").value; // CHANGE

    if(id===""){
        infoHer.innerHTML = "<h2>ERROR:</h2> <br> Please input ID";
        return;
    }

    var currUrl = url + "/polls/"+id,
        retVal = await request(currUrl, 'GET', "");
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

async function getPersonByEmail(email) {

}

async function getAllPolls() {
    var currUrl = url + "/polls",
        retVal = await request(currUrl, 'GET', "");
    console.log(retVal);
    var myArr = JSON.parse(retVal);

    console.log(myArr);
    var printString = "";
    myArr.forEach(currPoll => {
        printString += parsePoll(currPoll);
    });

    infoHer.innerHTML = "<h2>ALL POLLS:</h2> <br> <p style='text-align: left; margin-left: 8px;' >"+printString+"</p>";

}

async function getAllPollsByUser(id) {
    var currUrl = url + "/polls/person/",
        retVal = await request(currUrl, 'GET', "");
    console.log(retVal);
    var myArr = JSON.parse(retVal);

    console.log(myArr);
    var printString = "";
    myArr.forEach(currPoll => {
        printString += parsePoll(currPoll);
    });

    infoHer.innerHTML = "<h2>ALL POLLS:</h2> <br> <p style='text-align: left; margin-left: 8px;' >"+printString+"</p>";

}

async function createPoll(email) {
    var name = document.getElementById("pname").value;
    var pollJson = "{ \"name\": \"" + name + "\", \"email\": \"" + email + "\" }";
    var currUrl = url + "/polls",
        retVal = await request(currUrl, 'POST', pollJson);
    console.log(retVal.value);
}

async function createPerson() {
    var email = document.getElementById("email_reg").value;
    var pollJson = "{ \"email:\": \"" + email + "\" }";
    var currUrl = url + "/persons",
        retVal = await request(currUrl, 'POST', pollJson);
    console.log(retVal.value);
}

function parsePoll(poll) {
    var printString = "";
    printString += "ID: " + poll['id'] + "<br>";
    printString += "Pollname: " + JSON.stringify(poll['name']);
    printString += "<br>Votes: " + poll['votes'].length + "<br><br>";
    return printString;
}