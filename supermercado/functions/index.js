
'use strict';

var functions = require('firebase-functions');
const admin = require('firebase-admin');
admin.initializeApp(functions.config().firebase);
var cost,value;

exports.helloWorld = functions.https.onRequest((request, response) => {
  console.log('helloWorld called');
  if (!request.headers.authorization) {
      console.error('No Firebase ID token was passed');
      response.status(403).send('Unauthorized');
      return;
  }
  cost = -1,value = -1;

  admin.auth().verifyIdToken(request.headers.authorization).then(decodedIdToken => {
    console.log('ID Token correctly decoded, product selected = ' + request.headers.product, decodedIdToken);
    request.user = decodedIdToken;
    //response.send(request.body.name +', Hello from Firebase! );
    var onGot = (onGot = val =>{
      if (cost === -1 || value === -1) return 0;
      admin.database().ref("/users_money/"+decodedIdToken.uid).set(cost-value);
      
      return 1;
    });

    admin.database().ref("/products/"+request.headers.product).once("value",snapshot => {
      //console.log("value = ",snapshot.val());
      value = snapshot.val();
      onGot(0);
    });
    
    admin.database().ref("/users_money/"+decodedIdToken.uid).once("value",snapshot2 => {
      cost = snapshot2.val()
      onGot(0);
     // 
    })
    
    return decodedIdToken;
  }).catch(error => {
    console.error('Error while verifying Firebase ID token:', error);
    response.status(403).send('Unauthorized');
  });
});