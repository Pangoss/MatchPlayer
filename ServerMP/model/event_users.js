const Sequelize = require('sequelize');  //importa modulo sequelize
const persistent= require('../persistent/sequelize.js');
const User = require('./user.js');
const Event = require('./event.js');

let definition = {
  id_user: {
    type: Sequelize.INTEGER,
    primaryKey:true,
    references: {
      // This is a reference to another model
      model: User,
      // This is the column name of the referenced model
      key: 'ID',
      
     
    }
  },
  
 id_event: {
    type: Sequelize.INTEGER,
          primaryKey:true,

    references: {
      // This is a reference to another model
      model: Event,
      // This is the column name of the referenced model
      key: 'id_event',
      
    }
  },
  
 
}

let props = {
  freezeTableName: true,
  createdAt: false,
  updatedAt: false
};

  
module.exports=persistent.matchplayer.define('event_users', definition , props);
