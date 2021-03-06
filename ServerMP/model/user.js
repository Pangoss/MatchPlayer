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


	gender: {
                type: Sequelize.STRING
                },
	


	phonenumber: {
		type: Sequelize.INTEGER,
		},

  
	created: {
		type: Sequelize.INTEGER,
		defaultValue: '1'
		},

	street_number: {
                type: Sequelize.STRING
                },

	street_name: {
                type: Sequelize.STRING
                },

	city: {
                type: Sequelize.STRING
                },

	zip_postcode: {
                type: Sequelize.STRING
                },

	country: {
                type: Sequelize.STRING
                },
	
	 skill: {
                type: Sequelize.INTEGER,
		defaultValue: '1'
                },

         fitness: {
                type: Sequelize.INTEGER,
		defaultValue: '1'

                },

         reliability: {
                type: Sequelize.INTEGER,
		defaultValue: '1'
                },
	



};

let props = {
	freezeTableName: true,
	createdAt: false,
	updatedAt: false
};

module.exports=persistent.matchplayer.define('user', definition , props);



