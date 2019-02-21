const Sequelize = require('sequelize');  //importa modulo sequelize
const persistent= require('../persistent/sequelize.js')

let definition = {


	event_id: {
		type: Sequelize.INTEGER,
		primaryKey: true,
		autoIncrement: true,
		},

	event_name: {
 		type: Sequelize.STRING,
		},

	sport: {
		type: Sequelize.STRING,
		},

	price: {
                type: Sequelize.INTEGER
                },

    average_fitness: {
                type: Sequelize.INTEGER,
                },

    average_skill: {
                type: Sequelize.INTEGER,
                },

	 attending_number: {
                type: Sequelize.INTEGER,
                },


        attending_number_max: {
                type: Sequelize.INTEGER,
                },


	street_number: {
		type: Sequelize.STRING,
		},

	street_name: {
		type: Sequelize.STRING,
		},

	city: {
		type: Sequelize.STRING,
		},

	 country: {
                type: Sequelize.STRING,
                },


	zip_postcode: {
		type: Sequelize.STRING,
		},

	date: {
		type: Sequelize.DATE,
	}

	
};

let props = {
	freezeTableName: true,
	createdAt: false,
	updatedAt: false
};

module.exports=persistent.matchplayer.define('event', definition , props);

