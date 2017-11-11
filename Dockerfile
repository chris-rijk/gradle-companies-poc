FROM gradle:4.3.0-jdk8-alpine AS BUILD_IMAGE
ENV APP_HOME=/home/gradle/project
RUN mkdir -p $APP_HOME
WORKDIR $APP_HOME
#COPY build.gradle gradlew $APP_HOME
#COPY gradle $APP_HOME/gradle
# download dependencies
#RUN ./gradle :companies-runtime-dev:jar
COPY *.gradle ./
COPY common common
COPY companies companies
RUN find .
#RUN rm -rf .gradle
RUN gradle --no-daemon --info --stacktrace :companies-runtime-dev:jar
FROM openjdk:8-jre-alpine
WORKDIR /root/
COPY --from=BUILD_IMAGE $APP_HOME/companies/runtime/dev/build/libs/companies-runtime-dev.jar .
EXPOSE 18080
CMD ["java","-jar","companies-runtime-dev.jar"]