var express = require('express');
const sequelize = require('sequelize');
var router = express.Router();
var util = require('./util');
var jwt = require('jsonwebtoken');
var bcrypt = require('bcryptjs');
var config = require('../config');
var crypto = require('crypto');
var Event = require('../model/event');
var User = require('../model/user');
var bodyParser = require('body-parser');
router.use(bodyParser.urlencoded({ extended: true }));
router.use(bodyParser.json());
var UserEvent = require('../model/event_users.js')
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
    	email : req.body.email,
    	password : req.body.password
  }}).then(user =>{

    if (user.length != 0)
           {var token = jwt.sign({ id: user[0].ID }, config.secret, {
            expiresIn: 86400 // expires in 24 hours
             });
            //res.status(200).send({auth: true, token: token, id: user[0].ID });}
           res.json({"auth":"true", "token":token, "id":user[0].ID});}
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

router.post('/register', function(req, res) {
  
// console.log('the Username is', req.body.Username);
//  console.log('the Password is', req.body.Password);

 var hashedPassword =req.body.password /* bcrypt.hashSync(req.body.Password, 4) */

   User.create({
    name : req.body.name,
    password : hashedPassword,
    phonenumber : req.body.phonenumber,
    email: req.body.email,
  }).then(user =>{
    
    // create a token
   
    res.status(200).send("Registrazione effettuata");
     }).catch(err => {
    if (err) return res.status(500).send("There was a problem registering the user.")
   });}
);


router.get('/events_by_id', function (req, res) {
  //add_token_verification
  var id = req.body.id;
    UserEvent.findAll({
  where: {
    id_user : id 
  }
}).then(userEvent => {
      res.send(userEvent);
  });
});
 

router.post('/update/myprofile', function (req, res) {
  //add_token_verification
  var id = req.body.id;
  
  User.update(
{where: {ID: id}},
{
  name: req.body.name,
  surname: req.body.surname,
  gender: req.body.gender,
  password: req.body.password,  
  phone_number: req.body.phone_number, 
  street_number: req.body.street_number,
  street_name: req.body.street_name,
  city: req.body.city,
  zip_postcode: req.body.city_postcode, 
  country: req.body.country,
  }).then(user =>{
    
 
    res.status(200).send("Update Successful");
     }).catch(err => {
    if (err) return res.status(500).send("There was a problem while updating the user.")
   });}
);


router.get('/myprofile', function (req, res) {
  //add_token_verification
  var id = req.body.id;
    User.findAll(
	{where: {ID: id}},
	{attributes: ['name', 'surname', 'gender', 'password','email', 'phone_number', 'street_number', 'street_name', 'city', 'zip_postcode', 'country']
  })
    .then(user => {
      return res.send(user);
  }).catch(err => {
    if (err) return res.status(500).send("Session error")
   });
});


router.post('/update/join', function(req, res) {
  
// console.log('the Username is', req.body.Username);
//  console.log('the Password is', req.body.Password);

 

	UserEvent.create({
    		id_user : req.body.id_user,
    		id_event : req.body.id_event
	})
	.then(userEvent  => {
		 Event.update(
                        {attending_number: sequelize.literal('attending_number + 1')}, {where: {event_id: req.body.id_event}}

                )
                .then(Event => {
                        return res.status(200).send("att upd successful");})
                .catch(err => {
                        if (err) return res.status(500).send("att upd error");});


    		return res.status(200).send("joined with success");})
     	.catch(err => {
    		if (err) return res.status(500).send("There was a problem while joining");
   	});



})

module.exports = router;
