FROM openjdk:8u171-jdk-stretch

RUN apt-get update & apt-get install git \
 && git clone https://github.com/alex2040/kafkamessages.git \
 && cd kafkamessages && ./gradlew :app:shadowJar

ENTRYPOINT java -jar /kafkamessages/app/build/libs/application.jar