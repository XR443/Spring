FROM openjdk:15
RUN mkdir /usr/src/myapp 
COPY ./module4.homework6.jar /usr/src/myapp
WORKDIR /usr/src/myapp
EXPOSE 8080 27017
CMD ["java", "-jar", "module4.homework6.jar"]