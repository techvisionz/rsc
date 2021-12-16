# Introduction

Modern solutions provide a better way for centralized logging e.g. ELK etc. However, that requires integration between applications and centralize logging components. Sometimes, support and operation teams have to deal with existing or legacy services, which are deployed on multiple servers and this very difficult to login to each server and find out related logs if the application itself does not provide any centralized logging feature. It is desirable to have centralized place to find the related logs for given search string on selected servers.

Rest Shell Commander (RSC) is a small java application built on spring boot. It exposes rest APIs to register the new users in database (file based, H2), create services with related configuration and allow the users to execute UNIX shell commands (currently only, Grep and AWK are supported).


# Technology Stack

Spring Boot Framework 2.5.6
