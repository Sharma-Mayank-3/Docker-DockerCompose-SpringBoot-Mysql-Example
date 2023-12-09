# Docker-DockerCompose-SpringBoot-Mysql-Example

Docker Commads.

1. To see all the images
   docker images

2. To run the image and create a container
   docker run --name javacontainer -it -d openjdk
   above will name the container as javacontainer and -it means interactive mode always run or else it will close, -d means detached.

3. To see the container running
   docker ps


4. To go to the container
   docker exec -it javacontainer jshell
   javacontainer is a container name
   jshell is the command, when we do docker ps


5. To run all Commands of java in jshell
   System.out.println("hello testing");
   System.out.println("hello testing")
   string s = "hello"
   String s = "hello"
   s.lenght();
   s.length
   s.length()
   s.toUpperCase()
   /history

To Exit
/exit

6. To run mysql image and create container please see the documentation in docker hub, mysql
   docker run --name mysqlDB -e MYSQL_ROOT_PASSWORD=root -d mysql

7. To go to the conatiner of mysql
   docker exec -it mysqlDB bash
   mysql -p
   root

Inside mysql use the simple mysql commands.

8. TO stop the Container
   docker stop javacontainer

9. To restart the container
   docker restart javacontainer


10. Dockerfile commads
    create a file Dockerfile with no extension

FROM ubuntu

MAINTAINER "Mayank"

RUN apt update
This is to which commad to run when image is build. apt-update is to update ubuntu

CMD ["echo", "this is my first docker image"]
CMD is to run default commds when container run.

NOTE : Now to build the image, either open terminal in project or from outside go to the project directory and
docker build -t myubuntu .

Now We can run this image
docker run myubuntu

OR we can create a container and run
docker run --name LocalContainerUbuntu myubuntu




11. FOR JAVA

FROM openjdk

WORKDIR /user/src/app

COPY . /user/src/app

RUN javac Test.java

CMD ["java", "Test"]

docker build -t myjava .
docker run myjava || docker run --name myJavaContainer myjava



12. For Spring Boot Project
    first clean and install the project and lets create a jar file

FROM openjdk
WORKDIR /docker-example
COPY . /docker-example/
CMD ["java", "-jar", "/docker-example/target/docker-example-0.0.1-SNAPSHOT.jar"]
EXPOSE 9595

     OR

FROM openjdk
EXPOSE 9595
WORKDIR /app/opt (means iske niche ka code automatically is workingdir k ander hoga or  else,                 ENTRYPOINT ["java", "-jar", "/app/optdocker-example-0.0.1-SNAPSHOT.jar"]               )
ADD target/docker-example-0.0.1-SNAPSHOT.jar /app/opt
ENTRYPOINT ["java", "-jar", "docker-example-0.0.1-SNAPSHOT.jar"]

In application.properties also expose port is 9595

a. To Build an Image
docker build -t boot1 .

b. To create an container and run in background
docker run --name bootContainer -it -p 9595:9595 -d boot1
OR
docker run --name boot5container -it -p 8081:9595 -d boot5
means our docker will run on 8081 and local on 9595

c. To Check logs
docker logs bootContainer

Now hit the api and see the logs, great things is now project is running in docker not in local.
with Dockerfile 1 -> localhost:9595
with Dockerfile 2 -> localhost:8081
means our docker container runs on port 9595 but it mapped to 8081.



13. Remove a container if stopped
    docker rm containerId or containerName

14. Remove all the container
    docker rm $(docker ps -aq)

15. Share file between host and container, using volumes.
    download the ngnix image.
    docker run --name website -it -d -p 8081:80 nginx
    hit localhost:8081, default nginx page will get open.

create a index.html file in desktop, /Users/vc.mayank.sharma/Desktop this is having index.html
go to this folder in terminal
stop the above container
docker run --name website -it -d -p 8081:80 -p 3000:80 -v /Users/vc.mayank.sharma/Desktop/website:/usr/share/nginx/html:ro nginx
NOTE : make any change in index.html, no need to

16. Pushing the image to the repository of Docker
    create a docker repository private/public
    login first.
    docker login
    username : sharmamayank3
    pass : Shri Ram 3.0

create public repo name test-repo
change tag name to (docker push sharmamayank3/test-repo:tagname)
docker tag boot3 sharmamayank3/test-repo:1, this will create a new image with name sharmamayank3/test-repo:1
docker push sharmamayank3/test-repo:1, push the image to this public repo


17. Pull the tag from the above repository
    Inside docker hub search for sharmamayank and go to your repository, make sure to delete all the containers and the image which i am going to use
    docker pull sharmamayank3/test-repo , this will download the latest.
    docker pull sharmamayank3/test-repo:1, since i dont have the latest.
    docker run --name bootapp -it -p 9595:9595 -d sharmamayank3/test-repo:1
    and check localhost:9595

18. Docker Compose when we want both



############################## Kubernate #######################################

1. After building the image run the kubernate on that image
   kkubectl create deployment spring-boot-k8s --image=bootkuber --port=9595 , (bootkuber is the image name)

2. kubectl get deployment
3. kubectl describe deployment
4. kubectl get pod



############################## Spring boot app with docker mysql connection #########################
1. create a CRUD application, check if all the api's are working in local or not.
2. download the mysql image
3. Run the command to create a mysql container
   docker run -d -p 3307:3306 --name mysql-container -e MYSQL_ROOT_PASSWORD=root -e MYSQL_DATABASE=bootdocker -e MYSQL_USER=hello -e MYSQL_PASSWORD=root mysql:latest
4. Insdie mysql-container exec file
   mysql -u admin -p  
   root
5. our application.properties file should be
   spring.datasource.driver-class-name=com.mysql.cj.jdbc.Driver
   spring.datasource.url=jdbc:mysql://localhost:3307/bootdocker?useSSL=false
   spring.datasource.username=hello
   spring.datasource.password=root
   spring.jpa.properties.hibernate.dialect=org.hibernate.dialect.MySQL8Dialect
   spring.jpa.hibernate.ddl-auto=update


################docker compose file, Spring boot app with mysql connection all on docker ############
NOTE : Some how this is not working, connection refuse error.

1. application.properties file
   spring.datasource.driver-class-name=com.mysql.cj.jdbc.Driver
   spring.datasource.url=jdbc:mysql://${dbservice:localhost}:3306/bootdocker?useSSL=false
   spring.datasource.username=admin
   spring.datasource.password=root
   spring.jpa.properties.hibernate.dialect=org.hibernate.dialect.MySQL8Dialect
   spring.jpa.hibernate.ddl-auto=update

2. Dockerfile
   FROM openjdk
   EXPOSE 8080
   WORKDIR /opt
   COPY target/*.jar /opt/app.jar
   ENTRYPOINT ["java", "-jar", "app.jar"]

3. docker-compose.yml file

version: '3.8'

services:
mysql:
image: mysql:latest
restart: always
environment:
MYSQL_ROOT_PASSWORD: root
MYSQL_DATABASE: bootdocker
MYSQL_USER: admin
MYSQL_PASSWORD: root
ports:
- "3306:3306" # Expose MySQL port
networks:
bootapp:

spring-app:
build: .
ports:
- "8080:8080" # Expose Spring Boot app port
depends_on:
- mysql
environment:
SPRING_DATASOURCE_URL: jdbc:mysql://localhost:3306/bootdocker
SPRING_DATASOURCE_USERNAME: admin
SPRING_DATASOURCE_PASSWORD: root
networks:
bootapp:

networks:
bootapp:
