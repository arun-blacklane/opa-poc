# opa-poc

Spring boot application with OPA integration

# Install OPA server MAC
`brew install opa`
[Other install options](https://www.openpolicyagent.org/docs/latest/#running-opa)

#Start OPA Server

`opa run --server`

#Add policies/data to OPA server
```
curl -X PUT http://localhost:8181/v1/data/myapi/acl --data-binary @scripts/arun-acl.json #Add data
curl -X PUT http://localhost:8181/v1/policies/myapi --data-binary @scripts/arun.rego #Add policy
```


#Run application
```
mvn clean install
java -jar 

```

#Test user/password
```
admin/password
user/password
```

#Test API Calls for Different Users
```
Try book creation with admin (allowed)
curl --location --request POST 'localhost:8080/books' \
--header 'Content-Type: application/json' \
-u admin:password \
--data-raw '{"name":"ABC",
"author":"mkyong",
"price":"8.88"}'

Try book creation with user (not allowed)
curl --location --request POST 'localhost:8080/books' \
--header 'Content-Type: application/json' \
-u user:password \
--data-raw '{"name":"ABC",
"author":"mkyong",
"price":"8.88"}'

```

#Key Files
`com.arun.voter.opa.OPAVoter.java`: Contains interceptor logic for authorization calling opa agent
`com.arun.config.SpringSecurityConfig`: Security Config


