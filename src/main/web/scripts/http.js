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
     //extract JSON from the http response
    return await response.text();
}


// Unique REST API calls //

async function getPollById() {
    let id = document.getElementById("pollIdInp").value; // CHANGE

    if(id===""){
        infoHer.innerHTML = "<h2>ERROR:</h2> <br> Please input ID";
        return;
    }

    let currUrl = url + "/polls/" + id;
    let retVal = await request(currUrl, 'GET', "");

    // if(!Object.keys(retVal).length){

    if (retVal === ""){
        printval = "No poll with this id.";
    }
    else{
        let parsedVal = JSON.parse(retVal);
        printval = parsePoll(parsedVal)
    }
    infoHer.innerHTML = "<h2>POLL WITH ID " + id + ":</h2><br>" + printval;
}

async function getPollByName(name) {
    console.log("name " + name);

    let currUrl = url + "/polls/name/" + name;
    console.log("url " + currUrl);
    let retVal = await request(currUrl, 'GET', "");
    printval = ""

    if (retVal === ""){
        return "No poll with this name.";
    }
    else{
        let printval = JSON.parse(retVal);
        return printval;
    }
}

async function getPersonIdByEmail(email) {
    let currUrl = url + "/persons/email/" + email;
    let retVal = await request(currUrl, 'GET', "");
    
    let parsedVal = JSON.parse(retVal);
    let person_id = JSON.stringify(parsedVal['id']);

    return person_id;
}

async function getAllPolls() {
    let currUrl = url + "/polls",
        retVal = await request(currUrl, 'GET', "");
    let myArr = JSON.parse(retVal);

    let printString = "";
    myArr.forEach(currPoll => {
        printString += parsePoll(currPoll);
    });

    infoHer.innerHTML = "<h2>ALL POLLS:</h2> <br> <p style='text-align: left; margin-left: 8px;' >"+printString+"</p>";
}

async function getAllPollsByUser(id) {
    let currUrl = url + "/polls/person/" + id;
    let retVal = await request(currUrl, 'GET', "");

    let myArr = JSON.parse(retVal);

    let printString = "";
    myArr.forEach(currPoll => {
        printString += parsePoll(currPoll);
    });

    infoHer.innerHTML = "<br> <p style='text-align: left; margin-left: 8px;' >"+printString+"</p>";
}

/** 
    async function getVotes(id) {
    let currUrl = url + "/polls/" + id + "/votes",
        retVal = await request(currUrl, 'GET', "");
    
    let myArr = JSON.parse(retVal);

    let printString = "";
    myArr.forEach(currVote => {
        printString += parseVotes(currVote);
    });
    infoHer.innerHTML = "<h2>ALL VOTES:</h2> <br> <p style='text-align: left; margin-left: 8px;' >"+printString+"</p>";
}
**/

async function getVotes(id) {
    let currUrl = url + "/polls/" + id + "/distribution";
    let retVal = await request(currUrl, 'GET', "");
    let votes = JSON.parse(retVal);
        
    return [votes['yes'], votes['no']]
}

async function createPoll(email, person_id) {
    let name = document.getElementById("pname").value;
    let pollJson = '{ \"name\": \"' + name + '\", ' +
                     '\"creator\": \"' + email + '\", ' +
                     '\"fkperson\": \"' + person_id + '\" }';
    let currUrl = url + "/polls";
    let retVal = await request(currUrl, 'POST', pollJson);
}

async function createPerson() {
    let email = document.getElementById("email_reg").value;
    email = email.toLowerCase();
    let pollJson = '{ \"email\": \"' + email + '\" }';
    let currUrl = url + "/persons";
    let retval = await request(currUrl, 'POST', pollJson);
}

async function createVote(yes, poll_id, person_id) {
    let pollJson = '{ \"yes\": \"' + yes + '\", ' +
                     '\"fkpoll\": \"' + poll_id + '\", ' +
                     '\"fkperson\": \"' + person_id + '\" }';
    let currUrl = url + "/votes";
    let retVal = await request(currUrl, 'POST', pollJson);
}

async function hasVoted(poll_id, person_id) {
    let currUrl = url + '/polls/' + poll_id + '/hasVoted/' + person_id;
    let retVal = await request(currUrl, 'GET', "");
    let bool = JSON.parse(retVal);
    return bool['hasVoted'];
}

function parsePoll(poll) {
    let printString = "";
    printString += "Name: " + JSON.stringify(poll['name']);
    printString += "<br>Votes: " + poll['votes'].length + "<br>";

    let redirect_url = "\"view_poll.html?id=" +  poll['id'] + "&name=" + poll['name'];
    printString += "<a href=" + redirect_url + " \">Vote</a><br><br>"
    return printString;
}

function parseVotes(vote){
    let printString = "";
    printString += "<a>" + vote['yes'] + "</a><br><br>"
    console.log(vote['yes']);
    return printString;
}