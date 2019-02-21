var jwt = require('jsonwebtoken');
var config = require('../config');



function tokenStatus(token) {
 
    if (!token) 
return new Promise((res, err) => {
res(1)
});
  

return new Promise(function(resolve, reject){
	jwt.verify(token, config.secret, function(err, decode){

		if (err){
			reject(err)
	        	return 
		 }

		resolve(decode)
	});
});

} 
module.exports = {
    tokenStatus: tokenStatus, }
