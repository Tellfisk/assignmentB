
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

auth.onAuthStateChanged(function (user) {
  if (user) {
    // User is signed in

    var user = auth.currentUser;
  
    if (user != null) {
      // Valid user

    }

  } else {
    // No user is signed in

  }
});

function setmail(mail) {
  email = mail;
}

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
      window.location.href = "dashboard.html";
    }).catch(function failure(error) {

      var errorCode = error.code;
      var errorMessage = error.message;
      console.log(errorCode + " " + errorMessage);

    });

}
