# Restaurant Reservation
A back-end web development project by Dan Yang
## Description
This application is developed using Spring Boot, Spring Data, Spring RESTful web services, Maven, PostgreSql, Docker, Amazon SQS, Amazon S3.
## Assumption
1. Users are provided restaurant information with custom reviews then reserved the restaurant after sign in authorities.
2. The restaurant information need to be created before searching.
3. The relation between restaurant and review is "One to Many", the relationship between player and player statistics is "One to One".
Approach
## Approach
## Build Project
1. Clone the project
    ```
    git clone https://github.com/xchris1015/basketball
    ```
2. Set PostgreSql database server using Postgres docker image
    ```
    docker pull postgres
    docker run --name ${DB_Demo} -e POSTGRES_DB=${DB_Demo} -e POSTGRES_USER=${username} -e POSTGRES_PASSWORD=${password} -p 5432:5432 -d postgres
    ```
3. Create Unit database on PGAdmin for unit testing
    ```
    create database DB_Demo_unit;
    ```
### compile
### test
### run migration
### package

## API guideline

![Image of Yaktocat](https://octodex.github.com/images/yaktocat.png)

