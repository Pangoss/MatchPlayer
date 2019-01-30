var jwt = require('jsonwebtoken');
var config = require('../config');



function(token) {


  var token = req.headers['x-access-token'];
  if (!token) return 1;  //NO TOKEN
  
  jwt.verify(token, config.secret, function(err, decoded) {
    if (err) return 2; //AUTH FAILED, TOKEN IS WRONG OR EXPIRED
    
    return 0;  //AUTHENTICATED
  });
};  
