
// Firebase config
var config = {
  apiKey: "AIzaSyDV0lSwCZ-lEqHVXujRWyjsYli5WRZ2Ppo",
  authDomain: "poll-bb906.firebaseapp.com",
  databaseURL: "https://poll-bb906.firebaseio.com",
  projectId: "poll-bb906",
  storageBucket: "poll-bb906.appspot.com",
  messagingSenderId: "44566474533",
  appId: "1:44566474533:web:323965d4278f0f2a6634cc",
  measurementId: "G-ZD7JCCTC9T"
};
firebase.initializeApp(config);
// Auth reference
const auth = firebase.auth();
var url = "http://84.215.98.118:8080",
    infoHer = document.getElementById("infoHer");

auth.onAuthStateChanged(function (user) {
  if (user) {
    // User is signed in
    emailnav.innerHTML = user.email;

  } else {
    // No user is signed in
    let currentURL = window.location.href.split('?')[0]; //Drops params from url
    let splitPath = currentURL.split("/");
    let lastPathElem = splitPath.pop();  //remove current file from path, to be replaced with login.html

    if (lastPathElem !== "login.html" && lastPathElem !== "register.html") {
      splitPath.push("login.html");
      let redirect_url = splitPath.join("/");
      window.location.replace(redirect_url);
    }
  }
});

function login() {

  var email = document.getElementById("email_field").value;
  var password = document.getElementById("password_field").value;

  auth.signInWithEmailAndPassword(email, password)
      .then(function (user) {
    // User signed in
    window.location.href = "dashboard.html";

  }).catch(function (error) {
    // Something went wrong
    var errorCode = error.code;
    var errorMessage = error.message;

    if (errorCode === 'auth/wrong-password') {
      alert('Wrong password.');
    } else {
      alert(errorMessage);
    }
    console.log(error);
  });
}

function logout() {
  auth.signOut().then(function () {
    window.location.href = "login.html";
  }, function (error) {
    console.error('Sign Out Error', error);
  });
}

function signup() {
  // Sign user up
  var email = document.getElementById("email_reg").value;
  var password = document.getElementById("password_reg").value;

  auth.createUserWithEmailAndPassword(email, password)
    .then(function success(userData) {
      // Potentially do something with userData

    }).catch(function failure(error) {

      var errorCode = error.code;
      var errorMessage = error.message;
      console.log(errorCode + " " + errorMessage);

    });
}
