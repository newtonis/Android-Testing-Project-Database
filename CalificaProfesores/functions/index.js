const functions = require('firebase-functions');

// // Create and Deploy Your First Cloud Functions
// // https://firebase.google.com/docs/functions/write-firebase-functions
//
exports.SendVote = functions.https.onRequest((request, response) => {

  console.log('voted!');
  response.send("Hello from Firebase!");
});



