# Introduction

Modern solutions provide a better way for centralized logging e.g. ELK etc. However, that requires integration between applications and centralize logging components. Sometimes, support and operation teams have to deal with existing or legacy services, which are deployed on multiple servers and this very difficult to login to each server and find out related logs if the application itself does not provide any centralized logging feature. It is desirable to have centralized place to find the related logs for given search string on selected servers.

Rest Shell Commander (RSC) is a small java application built on spring boot. It exposes rest APIs to register the new users in database (file based, H2), create services with related configuration and allow the users to execute UNIX shell commands (currently only, Grep and AWK are supported).

# Services

## Signup

**URL:** /users/signup

**Method:** POST

This service allows new users to register themselves in the database in order to generate JWT token, required for secured APIs e.g. search.

### Sample Request
````
{
    "username":"username",
    "password":"password"
}
````

## Users
**URL:** /users/all

**Method:** Get

This service returns all existing users in the system.

## Login
**URL:** /login

**Method:** Post

This is login service, which returns the JWT token in Authorization response header.
### Sample Request
````
{
        "username": "username",
        "password": "password"
}
````

## Get Services
**URL:** /services/all

**Method:** Get

This service returns the services info for logged in user (based on JWT created with login service).

## Create Service
**URL:** /services/create

**Method:** Post

This api is used to create the service info for logged in user.
### Sample Request
````
{
    "name":"service name",
    "logPath":"/home/user/service/log",
    "archiveLogPath":"/home/user/service/log",
    "logFileName":"*.log",
    "serviceHosts":[
        "host1"," host2"," host3"
    ]
}
````

## Update Service
**URL:** /services/update

**Method:** Post

This api is used to update the service info.
### Sample Request
````
{
    "id":1,
    "name":"service name",
    "logPath":"/home/user/service/log",
    "archiveLogPath":"/home/user/service/log",
    "logFileName":"*.log",
    "serviceHosts":[
        "host1"," host2"," host3"
    ]
}
````

## Delete Service
**URL:** /services/delete

**Method:** Post

This api is used to delete the service info.
### Sample Request
````
{
    "id":1
}
````

## Search
**URL:** /search

**Method:** Post

This api is used to execute selected command with service info of selected service.
### Sample Request
````
{
    "searchCommand":"awk",
    "linesAfter":10,
    "linesBefore":10,
    "searchString":"search_string",
    "filePath":"/home/user/service/log",
    "fileName":"*.log",
    "timeoutSeconds":1
}
````

# Technology Stack

Spring Boot Framework 2.5.6
