const functions = require('firebase-functions');

const admin = require('firebase-admin');
admin.initializeApp(functions.config().firebase);

exports.SendVote = functions.https.onRequest((request, response) => {
  console.log("Peticion de voto \n");

  /// verificamos login del usuario
  admin.auth().verifyIdToken(request.headers.authorization).then(decodedIdToken => {
    console.log("User: ",decodedIdToken.name);
    console.log("CA: " , request.headers.ca);
    console.log("CE: " , request.headers.ce);
    console.log("A : " , request.headers.a);
    console.log("Professor Id: " , request.headers.profid);
    console.log("Subject Id: ", request.headers.matid);
    
    /*admin.database().ref('/Puntajes/'+request.headers.profid+'_'+request.headers.matid).transaction(data => {
        console.log('Initial data: ', data);
        data.A += request.headers.a;
        data.CA += request.headers.ca;
        data.CE += request.headers.cb;
        data.CNT += 1;
        return data;
    }).then(response => {
        console.log('Success query ', response);
        return 1;
    }).catch(error => {
        console.log('Error query ', error);
        return 1;
    });*/
    const score = admin.database().ref('/Puntajes/'+request.headers.profid+'_'+request.headers.matid);
    score.once('value', snapshot => {
        values = snapshot.val();
        score.update({
            A: values.A + parseInt(request.headers.a),
            CA: values.CA + parseInt(request.headers.ca),
            CE: values.CE + parseInt(request.headers.ce),
            CNT: values.CNT + 1
        }).then(() => {
            console.log('Successfully updated database');
            return 0;
        }).catch(() => {
            console.log('Error updating database');
            return 0;
        });
    });

    return 1;
    
  }).catch(error => {
    console.error('Error while verifying Firebase ID token:', error);
    response.status(403).send('Unauthorized');
  });
  return 1;
});