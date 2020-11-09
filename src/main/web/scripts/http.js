var url = "http://84.215.98.118:8080",
    infoHer = document.getElementById("infoHer");


// Generic HTTP Request //

let request = async (nowUrl, method, body) => {
    let response;
    if (method === "GET" && body === "") {
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

    var currUrl = url + "/polls/" + id;
    var retVal = await request(currUrl, 'GET', "");

    // if(!Object.keys(retVal).length){

    if (retVal === ""){
        printval = "No poll with this id.";
    }
    else{
        var parsedVal = JSON.parse(retVal);
        printval = parsePoll(parsedVal)
    }
    infoHer.innerHTML = "<h2>POLL WITH ID " + id + ":</h2><br>" + printval;
}

async function getPersonIdByEmail(email) {
    var currUrl = url + "/persons/email/" + email;
    var retVal = await request(currUrl, 'GET', "");
    
    var parsedVal = JSON.parse(retVal);
    var person_id = JSON.stringify(parsedVal['id']);

    return person_id;
}

async function getAllPolls() {
    var currUrl = url + "/polls",
        retVal = await request(currUrl, 'GET', "");
    var myArr = JSON.parse(retVal);

    var printString = "";
    myArr.forEach(currPoll => {
        printString += parsePoll(currPoll);
    });

    infoHer.innerHTML = "<h2>ALL POLLS:</h2> <br> <p style='text-align: left; margin-left: 8px;' >"+printString+"</p>";
}

async function getAllPollsByUser(id) {
    var currUrl = url + "/polls/person/" + id;
    var retVal = await request(currUrl, 'GET', "");

    var myArr = JSON.parse(retVal);

    var printString = "";
    myArr.forEach(currPoll => {
        printString += parsePoll(currPoll);
    });

    infoHer.innerHTML = "<br> <p style='text-align: left; margin-left: 8px;' >"+printString+"</p>";
}

/** 
    async function getVotes(id) {
    var currUrl = url + "/polls/" + id + "/votes",
        retVal = await request(currUrl, 'GET', "");
    
    var myArr = JSON.parse(retVal);

    var printString = "";
    myArr.forEach(currVote => {
        printString += parseVotes(currVote);
    });
    infoHer.innerHTML = "<h2>ALL VOTES:</h2> <br> <p style='text-align: left; margin-left: 8px;' >"+printString+"</p>";
}
**/

async function getVotes(id) {
    var currUrl = url + "/polls/" + id + "/distribution";
    var retVal = await request(currUrl, 'GET', "");
    var votes = JSON.parse(retVal);
        
    return [votes['yes'], votes['no']]
}

async function createPoll(email, person_id) {
    var name = document.getElementById("pname").value;
    var pollJson = '{ \"name\": \"' + name + '\", ' +
                     '\"creator\": \"' + email + '\", ' +
                     '\"fkperson\": \"' + person_id + '\" }';
    var currUrl = url + "/polls";
    var retVal = await request(currUrl, 'POST', pollJson);
}

async function createPerson() {
    var email = document.getElementById("email_reg").value;
    email = email.toLowerCase();
    var pollJson = '{ \"email\": \"' + email + '\" }';
    var currUrl = url + "/persons";
    var retval = await request(currUrl, 'POST', pollJson);
}

async function createVote(yes, poll_id, person_id) {
    var pollJson = '{ \"yes\": \"' + yes + '\", ' +
                     '\"fkpoll\": \"' + poll_id + '\", ' +
                     '\"fkperson\": \"' + person_id + '\" }';
    var currUrl = url + "/votes";
    var retVal = await request(currUrl, 'POST', pollJson);
}

async function hasVoted(poll_id, person_id) {
    var currUrl = url + '/polls/' + poll_id + '/hasVoted/' + person_id;
    var retVal = await request(currUrl, 'GET', "");
    var bool = JSON.parse(retVal);
    return bool['hasVoted'];
}

function parsePoll(poll) {
    var printString = "";
    printString += "Name: " + JSON.stringify(poll['name']);
    printString += "<br>Votes: " + poll['votes'].length + "<br>";

    url = "\"view_poll.html?id=" +  poll['id'] + "&name=" + poll['name'];
    printString += "<a href=" + url + " \">Vote</a><br><br>"
    return printString;
}

function parseVotes(vote){
    var printString = "";
    printString += "<a>" + vote['yes'] + "</a><br><br>"
    console.log(vote['yes']);
    return printString;
}