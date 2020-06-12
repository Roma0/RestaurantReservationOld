# Restaurant Reservation
A back-end web development project by Dan Yang

https://github.com/xchris1015/basketball
https://github.com/di1025/NationalResortBooking
https://github.com/cowboybebophan/SneakerShop

## Project Thoughts
In my leisure time, besides sporting, I'd like delicacies to relieve myself. Retrieving recommended foods and booking popular restaurants is a compulsory course to find the favorite. Then an idea to copy review and reservation function of the Yelp, the bigwigs of these two services, came out.
## Description
This application is developed in Spring Framework by using Spring boot, Spring data, Hibernate, Reids, Spring RESTful web services, Postman, Maven, PostgreSQL, Docker, Amazon SQS, Amazon S3.
## Assumption
1. Users are provided restaurant information with custom reviews then reserved the restaurant after sign in authorities.
2. The restaurant information need to be created before searching.
3. The relationship between restaurant and review, restaurant and reservation, user and review, and user and reservation are all "One to Many".
Approach
## Approach
## Build Project
1. Clone the project
   ```
    git clone https://github.com/Roma0/RestaurantReservation
   ```
2. Set PostgreSql database server using Postgres docker image
   ```
    docker pull postgres
    docker run --name ${DB_Container_Demo} -e POSTGRES_DB=${DB_Demo} -e POSTGRES_USER=${username} -e POSTGRES_PASSWORD=${password} -p 5432:5432 -d postgres
   ```
3. Create Unit database on PGAdmin for unit testing
   ```
    create database DB_unit_test;
   ```
4. Environment properties configuration
   ```
   # Application-unit.properties Location:
    ./src/main/resources/META-INF/env
    
      # Template:
        database.driverName=${driverName}
        database.url=${url}
        database.port=${port}
        database.name=${name}
        database.username=${username}
        database.password=${password}
   ```   
   ``` 
    # Application-unit.properties Location:
      ./src/main/resources/META-INF
      
     # Template:
        jwt.secret=
        jwt.expiration=
        jwt.header=
   ```
### compile
    Command:
   ```
   mvn compile -Dspring.profiles.active=${env}
   ```
### test
### run migration
### package

## API guideline

![Image of Yaktocat](https://octodex.github.com/images/yaktocat.png)

