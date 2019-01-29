var express = require('express');
var router = express.Router();
const User = require('../model/user.js');

/* GET users listing. */
router.get('/', function (req,res) {
   
  User.findAll().then(users => {
      res.send(users);
  });
});

module.exports = router;
