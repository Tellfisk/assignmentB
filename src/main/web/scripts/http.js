
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
        return JSON.parse(retVal);
    }
}

async function getPersonIdByEmail(email) {
    let currUrl = url + "/persons/email/" + email;
    console.log("GET from: " + currUrl);
    let retVal = await request(currUrl, 'GET', "");
    
    let parsedVal = JSON.parse(retVal);
    return JSON.stringify(parsedVal['id']);
}

async function getAllPolls() {
    let currUrl = url + "/polls",
        retVal = await request(currUrl, 'GET', "");
    let myArr = JSON.parse(retVal);

    let printString = "";
    myArr.forEach(currPoll => {
        printString += parsePoll(currPoll);
    });

    infoHer.innerHTML = "<div class='grid'>"+printString+"</div>";
}

async function getAllPollsByUser(id) {
    let currUrl = url + "/polls/person/" + id;
    let retVal = await request(currUrl, 'GET', "");

    let myArr = JSON.parse(retVal);

    let printString = "";
    myArr.forEach(currPoll => {
        printString += parseMyPoll(currPoll);
    });

    infoHer.innerHTML = "<div class='grid'>"+printString+"</div>";
}

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
                     '\"closed\": false, ' +
                     '\"fkperson\": \"' + person_id + '\" }';
    let currUrl = url + "/polls";
    return await request(currUrl, 'POST', pollJson);
}

async function createPerson() {
    let email = document.getElementById("email_reg").value;
    email = email.toLowerCase();
    let pollJson = '{ \"email\": \"' + email + '\" }';
    let currUrl = url + "/persons";
    return await request(currUrl, 'POST', pollJson);
}

async function createVote(yes, poll_id, person_id) {
    let pollJson = '{ \"yes\": \"' + yes + '\", ' +
                     '\"fkpoll\": \"' + poll_id + '\", ' +
                     '\"fkperson\": \"' + person_id + '\" }';
    let currUrl = url + "/votes";
    return await request(currUrl, 'POST', pollJson);
}

async function hasVoted(poll_id, person_id) {
    let currUrl = url + '/polls/' + poll_id + '/hasVoted/' + person_id;
    let retVal = await request(currUrl, 'GET', "");
    let bool = JSON.parse(retVal);
    return bool['hasVoted'];
}

function parsePoll(poll) {
    let printString = "";
    printString += "<div class='item'><a>" + poll['name']+ "</a><br>";
    printString += "Votes: " + poll['votes'].length + "<br>";

    let redirect_url = "\"view_poll.html?id=" +  poll['id'] + "&name=" + poll['name']
        + "&by=" + poll['creator'] + "&closed=" + poll['closed'];
    if (! poll['closed']) {
        printString += "<a class='colored' href=" + redirect_url + " \">Vote</a>";
    } else {
        printString += "<a class='colored' href=" + redirect_url + " \">View</a>";
    }
    printString += "</div>";
    return printString;
}

async function closePoll(id) {
    let currUrl = url + "/polls/" + id + "/close";
    console.log("POST from: " + currUrl);
    return await request(currUrl, 'POST', "");
}

function parseMyPoll(poll) {
    let printString = "";
    printString += "<div class='item'><a>" + poll['name'] + "</a><br>";
    printString += "Votes: " + poll['votes'].length + "<br>";

    let redirect_url = "\"view_poll.html?id=" + poll['id'] + "&name=" + poll['name']
        + "&by=" + poll['creator'] + "&closed=" + poll['closed'];
    if (! poll['closed']) {
        printString += "<a class='colored' href=" + redirect_url + " \">Vote</a>";
        printString += "<br><a class='colored' href=\"#\" onclick='closeThisPoll(" + poll['id'] + ")'>Close</a>";
    } else {
        printString += "<a class='colored' href=" + redirect_url + " \">View</a>";
    }
    printString += "</div>";
    return printString;
}

function parseVotes(vote){
    let printString = "";
    printString += "<a>" + vote['yes'] + "</a><br><br>"
    console.log(vote['yes']);
    return printString;
}