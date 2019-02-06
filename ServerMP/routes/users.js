var express = require('express');
var router = express.Router();
var util = require('./util');
var jwt = require('jsonwebtoken');
var bcrypt = require('bcryptjs');
var config = require('../config');
var crypto = require('crypto');
var User = require('../model/user');
var bodyParser = require('body-parser');
router.use(bodyParser.urlencoded({ extended: true }));
router.use(bodyParser.json());

/* GET users listing. */
router.get('/dBtest', function (req,res) {
   
  User.findAll().then(users => {
      res.send(users);
  });
});



/* Token gen and log verification */
router.post('/login', function(req, res) {
  
 //console.log('the Username is', req.body.name);    Uncomment for debug to see if user and pwd arrives to the serrver
 // console.log('the Password is', req.body.password);

 //var hashedPassword =req.body.password /* bcrypt.hashSync(req.body.Password, 4) */

   User.findAll({ where: {
    	name : req.body.name,
    	password : req.body.password
  }}).then(user =>{

    if (user.length != 0)
           {var token = jwt.sign({ id: user[0].ID }, config.secret, {
            expiresIn: 86400 // expires in 24 hours
             });
            res.status(200).send({auth: true, token: token, id: user[0].ID });}
    else
           res.status(404).send('Wrong Username/Password.')
  
     }).catch(err => {
    if (err) return res.status(500).send("Server error")
   });}
);


router.get('/authTest', function (req,res) {
   
  var token = req.headers['x-access-token'];


  util.tokenStatus(token)
	.then(function (data) {console.log(data)})
	.catch(function (err) {console.log(err)});

});








module.exports = router;
