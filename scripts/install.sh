curl -X PUT http://localhost:8181/v1/data/myapi/acl --data-binary @arun-acl.json
curl -X PUT http://localhost:8181/v1/policies/myapi --data-binary @arun.rego

curl -X POST http://localhost:8181/v1/data/myapi/policy/whocan  --data-binary '{ "input": { "method": "GET"} }'
curl -X POST http://localhost:8181/v1/data/myapi/policy/allow --data-binary '{ "input": { "user": "user", "method": "GET"} }'
