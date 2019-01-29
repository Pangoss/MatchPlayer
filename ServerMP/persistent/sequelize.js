const Sequelize = require('sequelize');  //importa modulo sequelize
const sequelize = new Sequelize('matchplayer', 'matchapp', 'pavia1234', {
  host: 'matchappdb.cojewhwavpf4.us-east-1.rds.amazonaws.com',
  dialect: 'mysql',
  operatorsAliases: false,
  pool: {
    max: 5,
    min: 0,
    acquire: 30000,
    idle: 10000
  }
});

exports.matchplayer = sequelize;
