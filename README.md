# Recruit task 
A simple REST service which will return details of given Github repository. Details should
include:

* full name of repository
* description of repository
* git clone url
* number of stargazers
* date of creation (ISO format)

The API of the service should look as follows:

```
GET /repositories/{owner}/{repository-name}
{
"fullName": "...",
"description": "...",
"cloneUrl": "...",
"stars": 0,
"createdAt": "..."
}
```

#Simplify
Hardcoded authorization in integration tests. 
Use basic authentication for access to github.
Duplicated header from controller to connect to github, in production this should by replaced by something like OAUTH 2.  

Integration test can run in verify lifecycle by maven with property *skipIntegrationTests* set to `false`

#Error handling
For client type errors returning by GitHub I pass them to a client.
When server error code means that GitHub is not available then I send to client 404, in other cases I pass an error to the client.

Every error in connection with GitHub is logged on error level.