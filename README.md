# TalkDesk Challenge

### Table of Contents
- [Description](#description)
- [Technical Specification](#technical-specification)
- [Development Tools](#development-tools)
- [Getting Started](#getting-started)
- [API](#api)
    - [Verify](#verify)
    - [Aggregator API](#aggregator-api)    
    - [Code Documentation](#code-documentation)
    - [Docker Image](#docker-image)

## Description

The goal of this exercise is to build a phone information aggregator API. This
system takes a list of phone numbers obtained from user input and returns the
count of valid phones broken down per prefix and per business sector.

## Technical Specification

- Language: Java, version 8
- Framework: Spring Boot, version 2.1.0

## Development Tools

- Eclipse IDE 2018-12, version 4.9.0
- Apache Maven, version 3.6.0
- Apache Maven Javadoc Plugin, version 3.0.1
- Docker Maven Plugin, version 0.20.1

## Getting Started

### API

The app exposes the two following endpoints.

#### Verify

    /verify


This URL will return message if the service is working:
```
Talkdesk Test RESTService Successfully started..
```

#### Aggregator API

    /aggregate
    
The API must accept `POST` requests to the `/aggregate` endpoint
with a body that complies with the following schema:    
```
{
  "$schema": "http://json-schema.org/draft-07/schema#",
  "type": "array",
  "items": {
    "type": "string"
  }
}
```

i.e.
``'["+1983248", "001382355", "+147 8192", "+4439877"]'``

The API should return:

```
{
  "1": {
    "Clothing": 1,
    "Technology": 2
  },
  "44": {
    "Banking": 1
  }
}

```



### Code Documentation

To acess the code documentation is just run the server then the documentation will be accessible
at:

[![N|Solid](https://i.ibb.co/BnPzVhy/aggregate-sw.png)](https://i.ibb.co/BnPzVhy/aggregate-sw.png)

    http://localhost:8080/swagger-ui.html



### Docker Image

To run:

    docker-compose up web



