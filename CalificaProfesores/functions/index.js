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
    if (event.before.val() !== null) {
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

        if (event.before.val() === null){
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
        if (event.before.val() !== null){
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

/** agregar un nuevo profesor **/

exports.CreateProfRequest1 = functions.database.ref("/ProfAddRequests/{uid}/{rid}")
    .onCreate((event , context) => {

        materias = {};
        facultades = {};
        
        const arrMaterias = event.child("materias").val();
        const name = event.child("profName").val();
        
        var profId = event.child("profId").val();

        const arrFacultades = event.child("facultades").val();
        
        for (var child in arrFacultades){
            facultades[arrFacultades[child]] = arrFacultades[child];
        }
        const searchName = name.toLowerCase()
        .replace(/á/g, "a")
        .replace(/é/g, "e")
        .replace(/í/g, "i")
        .replace(/ó/g, "o")
        .replace(/ú/g, "u");

        const create = event.child("create").val();

        if (create){
            console.log("Creando profesor ...");

            /*console.log("materias = ",arrMaterias);
            console.log("facultades = ",arrFacultades);
            console.log("profId = ",profId);
            console.log("profName = ",name);*/

            // primero agregamos el profesor
            admin.database().ref("Prof").child(profId)
            .set({
                Name : name,
                SearchName : searchName,
                amabilidad : 0,
                clases : 0,
                conocimiento : 0,
                count : 0,
                Mat : arrMaterias,
                Facultades : facultades
            }).then(() => {
                console.log('Successfully updated database Prof/'+profId);
                return 0;
            }).catch(() => {
                console.log('Error updating database');
                return 0;
            });
        }else{
            console.log("Agregandole materias al profesor "+profId);
            console.log("path = ","Prof/"+profId+"/Mat");
            for (var child in arrMaterias){
                admin.database()
                .ref("Prof/"+profId+"/Mat/"+child)
                .set(arrMaterias[child]);
                
                admin.database()
                .ref("Prof/"+profId+"/Facultades/"+arrMaterias[child]["facultad"])
                .set(arrMaterias[child]["facultad"])
                .then(() => {
                    console.log('Successfully updated database Prof/'+profId+'/Mat/'+child);
                    return 1;
                }).catch(() => {
                    console.log('Error updating database');
                    return 0;
                });
            }
        }
        // luego agregamos a las materias correspondientes la referencia al profesor
        for (var child in arrMaterias){
            admin.database().ref("Materias/"+child+"/Prof/"+profId)
            .set(name).then(() => {
                console.log('Successfully updated database Materias/'+child+'/Prof/'+profId);
                return 0;
            }).catch(() => {
                console.log('Error updating database');
                return 0;
            });
        }
        return 1;
    }
); // ok

/* Eliminar la solucitud para agregar 
un profesor o una adicion de materias a un profesor **/

exports.UpdateProfRequest1 = functions.database.ref("/ProfAddRequests/{uid}/{rid}")
    .onUpdate((event , context) => {
        console.log("Updating admin action on prof ",context.params.rid);

        materias = {};
        facultades = {};
        
        const arrMaterias = event.after.child("materias").val();
        const create = event.after.child("create").val(); 
        // se llama profId pero deberia ser create(true)/add(false)
        const profId = event.after.child("profId").val();

        const name = event.after.child("profName").val();
        const erase = event.after.child("erase").val();

        
        if (erase){
            console.log("Eliminando profesor");
            // hay dos casos. 
            // Que estemos rechazando la solucitud para agregarle materias a un profesor (caso 1)
            // Que estemos rechazando la solucitud para crear un profesor (caso 2)

            if (create){ // caso 1
                /// Eliminamos links de materias al profesor
                for (var child in arrMaterias){
                    console.log("Deleting link Materias/"+child+"/Prof/"+profId);
                    admin.database()
                    .ref("Materias/"+child+"/Prof/"+profId).remove()
                    .then(() => {
                        console.log("Successfully updated database Materias/"+child+"/Prof/"+profId);
                        return 1;
                    }).catch(() => {
                        console.log('Error updating database');
                        return 0;
                    });
                }
                console.log("Deleting prof Prof/"+profId);

                /// Eliminamos el profesor
                admin.database()
                .ref("Prof/"+profId)
                .remove()
                .then(() => {
                    console.log("Successfully updated database Prof/"+profId);
                    return 1;
                }).catch(() => {
                    console.log('Error updating database');
                    return 0;
                });
            }else{ // caso 2
                //console.log("Eliminando adicion de universidades al prof Prof/"+profId);
                
                /** Para hacer: elimiar facultades un profesor no tiene mas **/

                console.log("Eliminando adicion de materias al prof Prof/"+profId);

                for (var child in arrMaterias){
                    admin.database()
                    .ref("Prof/"+profId+"/Mat/"+child).remove()
                    .then(() => {
                        console.log("Successfully updated database Prof/"+profId+"/Mat/"+child);
                        return 1;
                    }).catch(() => {
                        console.log('Error updating database');
                        return 0;
                    });


                    console.log("Deleting link "+child+"/Prof/"+profId);
                    admin.database()
                    .ref("Materias/"+child+"/Prof/"+profId).remove()
                    .then(() => {
                        console.log("Successfully updated database Materias"+child+"/Prof/"+profId);
                        return 1;
                    }).catch(() => {
                        console.log('Error updating database');
                        return 0;
                    });
                }


            }
        }else{
            console.log("Situacion inesperada");
        }
        console.log("Eliminando request "+context.params.uid+"/"+context.params.rid);

        admin.database()
        .ref("ProfAddRequests/"+context.params.uid+"/"+context.params.rid)
        .remove()
        .then(() => {
            console.log("Successfully removed request "+context.params.uid+"/"+context.params.rid);
            return 1;
        }).catch(() => {
            console.log('Error updating database');
            return 0;
        });

        return 1;
    }
); // ok

exports.AddClassRequest1 = functions.database.ref("/ClassAddRequests/{uid}/{rid}")
    .onCreate((event, context) => {
        const classId = event.child("classId").val();
        const arrProf = event.child("prof").val();
        const name = event.child("name").val();
        const facultadName = event.child("facultadName").val();
        const facultadId = event.child("facultadId").val();

        console.log("Creando materia "+name);
        
        var id = context.params.rid;

        admin.database().ref("Materias/"+id).set({
            Name : name.toLowerCase()
            .replace(/á /g, "a")
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
            console.log('Exitosamente actualizando materia '+ name + " (id="+id+")");
            return 1;
        }).catch(() => {
            console.log('Error actualizando database');
            return 0;
        });

        console.log("Actualizando MateriasPorFacultad/"+facultadId+"/"+id);
        
        admin.database()
        .ref("MateriasPorFacultad/"+facultadId+"/"+id)
        .set({
            Name: name.toLowerCase()
            .replace(/á/g, "a")
            .replace(/é/g, "e")
            .replace(/í/g, "i")
            .replace(/ó/g, "o")
            .replace(/ú/g, "u"),
            ShownName: name
        }).then(() => {
            console.log("Exitosamente actualizando MateriasPorFacultad/"+facultadId+"/"+id);
            return 1;
        }).catch(() => {
            console.log('Error actualizando database');
            return 0;
        });

        for (var prof in arrProf){
            console.log("Creando link Prof/"+prof+"/Mat/"+id);
            admin.database()
            .ref("Prof/"+prof+"/Mat/"+id) // actualizamos materias del profesor
            .set({
                facultad: facultadName,
                nombre: name
            }).then(() => {
                console.log("Exitosamente actualizado link Prof/"+prof+"/Mat/"+id);
                return 1;
            }).catch(() => {
                console.log('Error actualizando database');
                return 0;
            });

            /***** CODIGO AGREGADO REVISIÓN 25/08/2019 *****/
                console.log("Actualizando universidad del profesor Prof/"+prof+"/Mat/"+id);
                admin.database() /// actualizamos facultades del profesor
                .ref("Prof/"+prof+"/Facultades/"+facultadName).set(facultadName)
                .then(() => {
                    console.log("Exitosamiente actualizada universidad Prof/"+prof+"/Facultades/"+facultadName);
                    return 1;
                }).catch(() => {
                    console.log('Error actualizando database');
                    return 0;
                });
            /***** FIN DE CÓDIGO AGREGADO REVISIÓN 25/08/2019 *****/

        }
        return 1;
    }
); 

exports.UpdateClassRequest1 = functions.database.ref("/ClassAddRequests/{uid}/{rid}")
    .onUpdate((event, context) => {
        const classId = event.after.child("classId").val();
        //const arrProf = event.after.child("prof").val();
        const name = event.after.child("name").val();
        const facultadName = event.after.child("facultadName").val();
        const facultadId = event.after.child("facultadId").val();
        const id = event.after.key;
        

        /** admin.database().ref('Prof/'+context.params.profId);

        profScore.once('value',snapshot => {
                var conocimiento = parseInt(snapshot.child("conocimiento").val()); **/

        console.log("Consiguiendo arrProf de Materias/"+id+"/Prof");

        admin.database()
        .ref("Materias/"+id+"/Prof")     
        .once('value', snapshot => {
            var arrProf = snapshot.val();
            console.log("arrProf = ", arrProf);
            
            console.log("Eliminando links de la materia "+name + " (id="+id+")"); 

            for (var prof in arrProf){
                console.log("creando link Prof/"+prof+"/Mat/"+id);
                admin.database()
                .ref("Prof/"+prof+"/Mat/"+id)
                .remove().then(() => {
                    console.log("Exitosamente actualizado link Prof/"+prof+"/Mat/"+id);
                    return 1;
                }).catch(() => {
                    console.log('Error actualizando database');
                    return 0;
                });
            }
    
            console.log("Eliminando materia "+ name + " (id="+id+")");
    
            admin.database()
            .ref("Materias/"+id)
            .remove().then(() => {
                console.log("Exitosamente eliminando Materias/"+id);
                return 1;
            }).catch(() => {
                console.log('Error actualizando database');
                return 0;
            });

        });

        console.log("Eliminando MateriasPorFacultad/"+facultadId+"/"+id);

        admin.database()
        .ref("MateriasPorFacultad/"+facultadId+"/"+id)
        .remove()
        .then(() => {
            console.log("Exitosamente eliminando MateriasPorFacultad/"+facultadId+"/"+id);
            return 1;
        }).catch(() => {
            console.log('Error actualizando database');
            return 0;
        });

        console.log("Eliminando query "+context.params.uid+"/"+context.params.rid);

        admin.database()
        .ref("ClassAddRequests/"+context.params.uid+"/"+context.params.rid)
        .remove()
        .then(() => {
            console.log("ClassAddRequests/"+context.params.uid+"/"+context.params.rid);
            return 1;
        }).catch(() => {
            console.log('Error actualizando database');
            return 0;
        });
            
        return 1;
    }
); 

exports.UpdateAddUniRequest1 = functions.database.ref("/UniAddRequests/{uid}/{rid}")
    .onCreate((event, context) => {
        console.log("Creando universidad "+context.params.rid);
        const uniCompleteName = event.child("uniShortName").val();
        const uniShortName = event.child("uniCompleteName").val();
                // create uni
        admin.database().ref("Facultades/"+context.params.rid).set({
            Name: uniShortName.toLowerCase(),
            uniCompleteName : uniCompleteName.toLowerCase()
            .replace(/á/g, "a")
            .replace(/é/g, "e")
            .replace(/í/g, "i")
            .replace(/ó/g, "o")
            .replace(/ú/g,"u"),
            ShownName : uniCompleteName
        });
        return 1;
    }
); // ok

exports.UpdateAdminActionUniRequest1 = functions.database.ref("/UniAddRequests/{uid}/{rid}")
    .onUpdate((event, context) => {       
        console.log("Actualizando admin action en la universidad ",context.params.rid);
        const uniCompleteName = event.after.child("uniShortName").val();
        const uniShortName = event.after.child("uniCompleteName").val();
        
        const remove = event.after.child("erase").val();

        if (remove){
            console.log("Eliminando universidad ...");
            admin.database()
            .ref("MateriasPorFacultad/"+event.after.key)
            .remove().then(() => {
                console.log('Successfully removed uni ' + event.after.key + ' links');
                return 1;
            }).catch(() => {
                console.log('Error removing uni');
                return 0;
            });
            // delete uni
            admin.database()
            .ref("Facultades/"+event.after.key)
            .remove().then(() => {
                console.log('Exitosamente eliminando uni ' + event.after.key + ' data');
                return 1;
            }).catch(() => {
                console.log('Error eliminando universidad');
                return 0;
            });

            admin.database()
            .ref("UniAddRequests/"+context.params.uid+"/"+context.params.rid)
            .remove().then(() => {
                console.log("Universidad "+event.before.key+" request eliminanda");
                return 1;
            }).catch(() => {
                console.log("Error eliminando universidad " + event.before.key+ + "request");
                return 0;
            });
            return 1;
        }else{
            console.log("No accion");
        }
    }
); // ok
