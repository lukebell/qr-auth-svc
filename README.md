QR Code Authentication API
==========================================================

This Application generates and validates QR Codes and UUID 

Contents
--------

The mapping of the URI path space is presented in the following table:

URI path                        | Resource class            | HTTP methods
------------------------------- | ------------------------- | --------------
**_/qr/generate_**              | QRCodeEndpoint            | POST
**_/qr/validate_**              | QRCodeEndpoint            | POST
**_/config_**                   | ConfigEndpoint            | GET
**_/config/{id}_**              | ConfigEndpoint            | GET
**_/config_**                   | ConfigEndpoint            | POST
**_/config/{id}_**              | ConfigEndpoint            | PUT
**_/config/{id}_**              | ConfigEndpoint            | DELETE

Application is configured:

- Without web.xml with SpringBoot and Jersey
- Jersey/Spring annotations registers resources
- HikariCP manage database pool connection
- SpringJDBCTemplate to save and retrieve data from MySQL Database 
- EHCache to cache configurations
- Swagger as Rest API Documentation

Running the API
-------------------

Run the API as follows:

>     mvn clean package spring-boot:run

Once deployed, the application is ready to serve the following resources:

-   <http://0.0.0.0:8090/api/qr/generate>
-   <http://0.0.0.0:8090/api/qr/validate>
-   <http://0.0.0.0:8090/api/config>
-   <http://0.0.0.0:8090/api/config/{id}>


Swagger Documentation
---------------------

- <http://0.0.0.0:8090/api/swagger.json>

SwaggerUI
---------------------

- <http://0.0.0.0:8090/swagger/index.html>


Docker Configuration
---------------------

### QR Auth API

The following instructions will guide you for the basic usage of this image

* First build project
    
        mvn clean install

* Start the API

		docker run -d --name=qr-auth-api --restart=always --cpuset-cpus="0-3" --memory="2048m" --log-opt max-file=3 --log-opt max-size=50m  qr-auth-api:latest


* Alternatively you could run the executable jar: 

        java -jar qr-auth-svc-0.0.1-SNAPSHOT.jar -Xms512m -Xmx1024m -XX:MaxPermSize=1024m -XX:PermSize=1024
    
        or
    
        nohup java -jar qr-auth-svc-0.0.1-SNAPSHOT.jar -Xms512m -Xmx1024m -XX:MaxPermSize=1024m -XX:PermSize=1024 &        


### MySQL

>       docker run --name mysql --restart=always -v /tmp/mysql/data/:/var/lib/mysql -d -p 3306:3306 -e MYSQL_ROOT_PWD=toor -e MYSQL_USER=swAuth -e MYSQL_USER_PWD=sw4uth -e MYSQL_USER_DB=auth leafney/docker-alpine-mysql


SUPPORT
---------------------

Contact me:  
##### lucas.campana@globalmeaning.com 
