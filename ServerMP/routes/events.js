var express = require('express');
var router = express.Router();
const Event = require('../model/event.js');
var bodyParser = require('body-parser');

router.use(bodyParser.urlencoded({ extended: true }));
router.use(bodyParser.json());

/* GET users listing. */
router.get('/getall', function (req,res) {

  Event.findAll().then(events => {
      res.send(events);
  });

});



router.post('/create', function (req,res) {


Event.create({
    event_name: req.body.event_name,
    sport: req.body.sport,
    attending_number: req.body.attending_number,
    attending_max_number: req.body.attending_max_number,
    street_number: req.body.street_number,
    street_name: req.body.street_name,
    date: req.body.date
    
  }).then(event =>{
    
    // create a token
   
    res.status(200).send("Created");
     }).catch(err => {
    if (err) return res.status(500).send("There was a problem ")
   });}
);



module.exports = router;


