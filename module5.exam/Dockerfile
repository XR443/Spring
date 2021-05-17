FROM openjdk:15

LABEL source="https://github.com/HardLOLMaster/Spring" \
      docker="https://hub.docker.com/repository/docker/hardlolmaster/property.insurance.test"

RUN mkdir /usr/src/property.insurance
COPY ./build/libs/exam.jar /usr/src/property.insurance
WORKDIR /usr/src/property.insurance
EXPOSE 8080
CMD ["java", "-jar", "exam.jar"]