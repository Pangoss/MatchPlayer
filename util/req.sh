Address=$1
curl -d '{"name":"David", "password":"test"}' -H "Content-Type: application/json" -X POST $1
