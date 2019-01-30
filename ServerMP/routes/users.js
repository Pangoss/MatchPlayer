var express = require('express');
var router = express.Router();

var jwt = require('jsonwebtoken');
var bcrypt = require('bcryptjs');
var config = require('../config');
var crypto = require('crypto');
var User = require('../model/user');
var bodyParser = require('body-parser');
router.use(bodyParser.urlencoded({ extended: true }));
router.use(bodyParser.json());

/* GET users listing. */
router.get('/test', function (req,res) {
   
  User.findAll().then(users => {
      res.send(users);
  });
});

module.exports = router;

/* Token gen and log verification */
router.post('/login', function(req, res) {
  
 //console.log('the Username is', req.body.Username);    Uncomment for debug to see if user and pwd arrives to the serrver
 // console.log('the Password is', req.body.Password);

 var hashedPassword =req.body.Password /* bcrypt.hashSync(req.body.Password, 4) */

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

module.exports = router;
