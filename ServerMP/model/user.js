const Sequelize = require('sequelize');  //importa modulo sequelize
const persistent= require('../persistent/sequelize.js')

let definition = {


	ID: {
		type: Sequelize.INTEGER,
		primaryKey: true,
		autoIncrement: true,
		},
 

	name: {
 		type: Sequelize.STRING,
		},
 

	surname: {
		type: Sequelize.STRING,
		},


	email: {
		type: Sequelize.STRING,
		unique: true,
		},


	password: {
		type: Sequelize.STRING
		},


	


	phonenumber: {
		type: Sequelize.INTEGER,
		primaryKey: true,	},

  
	created: {
		type: Sequelize.INTEGER,
		defaultValue: '1'
		}
};

let props = {
	freezeTableName: true,
	createdAt: false,
	updatedAt: false
};

module.exports=persistent.matchplayer.define('user', definition , props);



