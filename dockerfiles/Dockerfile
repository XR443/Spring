FROM openjdk:15
RUN mkdir /usr/src/myapp 
COPY ./module1.jar /usr/src/myapp
WORKDIR /usr/src/myapp
CMD ["java", "-jar", "module1.jar"]