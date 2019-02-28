const functions = require('firebase-functions');
const admin = require('firebase-admin');

admin.initializeApp(functions.config().firebase);

/*exports.SendVote = functions.https.onRequest((request, response) => {
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
    });
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
});*/

function getDelta(event, child_name){
    if (event.before.exists()) {
        var prev = parseInt(event.before.child(child_name).val());
    }else{
        var prev = 0;
    }
    actual = parseInt(event.after.child(child_name).val());
    var delta = actual - prev;
    return delta;
}

exports.UpdateProfQual1 = functions.database.ref("/OpinionesProf/{profId}/{uid}")
    .onWrite((event , context) => {

        var deltaConocimiento = getDelta(event, "conocimiento");
        var deltaAmabilidad = getDelta(event, "amabilidad");
        var deltaClases = getDelta(event, "clases");
        var increment;



        if (event.before.exists()){
            increment = 1;
        }else{
            increment = 0;
        }
        const profScore = admin.database().ref('Prof/'+context.params.profId);

        profScore.once('value',snapshot => {
                var conocimiento = parseInt(snapshot.child("conocimiento").val());
                var amabilidad = parseInt(snapshot.child("amabilidad").val());
                var clases = parseInt(snapshot.child("clases").val());
                var count = parseInt(snapshot.child("count").val());

                console.log("deltaConocimiento = ", deltaConocimiento);
                profScore.update({
                    conocimiento: conocimiento + deltaConocimiento,
                    amabilidad : amabilidad + deltaAmabilidad,
                    clases : clases + deltaClases,
                    count : count + increment
                }).then(() => {
                    console.log('Successfully updated database');
                    return 0;
                }).catch(() => {
                    console.log('Error updating database');
                    return 0;
                });
            });
        return 1;
    }
);



exports.UpdateMatQual1 = functions.database.ref("/OpinionesMaterias/{matid}/{uid}")
	.onWrite((event , context) => {
		var deltaRank = getDelta(event, "valoracion");
		
        console.log("before = ",event.before);

		var increment;
        if (event.before.exists()){
            increment = 0;
        }else{
            increment = 1;
        }
        console.log("matid = ", context.params.matid);

        const matDatabase = admin.database().ref('Materias/'+context.params.matid);
        

        matDatabase.once("value", snapshot => {
        	console.log("deltaRank = ", deltaRank);

        	console.log("deltaIncrement = ", increment);

        	var totalScore = parseInt(snapshot.child("totalScore").val());
            var count = parseInt(snapshot.child("count").val());

        	matDatabase.update({
        		count : count + increment,
        		totalScore : totalScore + deltaRank
        	}).then(() => {
                console.log('Successfully updated database');
                return 0;
            }).catch(() => {
                console.log('Error updating database');
                return 0;
            });

        });
        return 1;
    }
);

/** agregar un nuevo profesor, o agregarle materias a un profesor **/

exports.UpdateAddProfRequest1 = functions.database.ref("/ProfAddRequests/{uid}/{rid}")
    .onWrite((event , context) => {
        materias = {};
        facultades = {};
        
        const arrMaterias = event.after.child("materias").val();
        var profId = event.after.child("profId").val();
        const name = event.after.child("profName").val();

        if (profId == "0"){console.firebase.google.com/
            const arrFacultades = event.after.child("facultades").val();
        
            for (var child in arrFacultades){
                facultades[child] = arrFacultades[child];
            }
            const searchName = name.toLowerCase()
            .replace(/á/g, "a")
            .replace(/é/g, "e")
            .replace(/í/g, "i")
            .replace(/ó/g, "o")
            .replace(/ú/g, "u");

            console.log("materias = ",arrMaterias);
            console.log("facultades = ",arrFacultades);
            console.log("profId = ",profId);
            console.log("profName = ",name);

            var newPostRef = admin.database().ref("Prof").push();
            newPostRef.set({
                Name : name,
                SearchName : searchName,
                amabilidad : 0,
                clases : 0,
                conocimiento : 0,
                count : 0,
                Mat : arrMaterias,
                Facultades : facultades
            }).then(() => {
                console.log('Successfully updated database');
                return 0;
            }).catch(() => {
                console.log('Error updating database');
                return 0;
            });
            profId = newPostRef.key;
            
        }else{
            console.log("Agregandole materias al profesor "+profId);
            console.log("path = ","Prof/"+profId+"/Mat");
            for (var child in arrMaterias){
                admin.database()
                .ref("Prof/"+profId+"/Mat")
                .child(child).set(arrMaterias[child]);
                admin.database()
                .ref("Prof/"+profId+"/Facultades")
                .child(arrMaterias[child]["facultad"])
                .set(arrMaterias[child]["facultad"]);
            }

            
        }
        for (var child in arrMaterias){
            admin.database().ref("Materias")
            .child(child)
            .child("Prof")
            .child(profId)
            .set(name).then(() => {
                console.log('Successfully updated database Materias/'+child+'/Prof/'+profId);
                return 0;
            }).catch(() => {
                console.log('Error updating database');
                return 0;
            });;
        }

        return 1;
    }
);

exports.UpdateAddClassRequest1 = functions.database.ref("/ClassAddRequests/{uid}/{rid}")
    .onWrite((event, context) => {
        const classId = event.after.child("classId").val();
        const arrProf = event.after.child("prof").val();
        
        const name = event.after.child("name").val();
        const facultadName = event.after.child("facultadName").val();

        if (classId == "0"){

            const facultadId = event.after.child("facultadId").val();

            console.log("Creando materia "+classId);
            var newPostRef = admin.database().ref("Materias").push();
            newPostRef.set({
                Name : name.toLowerCase()
                .replace(/á/g, "a")
                .replace(/é/g, "e")
                .replace(/í/g, "i")
                .replace(/ó/g, "o")
                .replace(/ú/g, "u"),
                ShownName : name,
                Facultad: facultadId,
                FacultadName : facultadName,
                count: 0,
                totalScore: 0,
                Prof: arrProf
            }).then(() => {
                console.log('Successfully updated Materias');
                return 0;
            }).catch(() => {
                console.log('Error updating database');
                return 0;
            });

            var id = newPostRef.key;
            
            admin.database()
            .ref("MateriasPorFacultad")
            .child(facultadId)
            .child(id)
            .set({
                Name: name.toLowerCase()
                .replace(/á/g, "a")
                .replace(/é/g, "e")
                .replace(/í/g, "i")
                .replace(/ó/g, "o")
                .replace(/ú/g, "u"),
                ShownName: name
            }).then(() => {
                console.log('Successfully updated MateriasPorFacultad');
                return 0;
            }).catch(() => {
                console.log('Error updating database');
                return 0;
            });

            

        }else{
        
            console.log("Agregandole profesores a la materia "+classId);
            for (var child in arrProf){
                admin.database()
                .ref("Materias/"+classId+"/Prof")
                .child(child).set(arrProf[child]);
            }
        }

        for (var prof in arrProf){
            admin.database()
            .ref("Prof")
            .child(prof)
            .child("Mat")
            .child(id)
            .set({
                facultad: facultadName,
                nombre: name
            }).then(() => {
                console.log('Successfully updated Prof/'+prof+'/Mat/'+id);
                return 0;
            }).catch(() => {
                console.log('Error updating database');
                return 0;
            });
        }
        return 1;
    }
);

exports.UpdateAddUniRequest1 = functions.database.ref("/UniAddRequests/{uid}/{rid}")
    .onWrite((event, context) => {
        const uniCompleteName = event.after.child("uniShortName").val();
        const uniShortName = event.after.child("uniCompleteName").val();

        admin.database().ref("Facultades").push().set({
            Name: uniShortName.toLowerCase(),
            uniCompleteName : uniCompleteName.toLowerCase()
            .replace(/á/g, "a")
            .replace(/é/g, "e")
            .replace(/í/g, "i")
            .replace(/ó/g, "o")
            .replace(/ú/g,"u"),
            ShownName : uniCompleteName
        })
        return 1;
    }
);
