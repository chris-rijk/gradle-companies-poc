FROM gradle:4.3.0-jdk8 AS BUILD_IMAGE
WORKDIR /home/gradle/project

USER root
RUN chown gradle .
USER gradle
RUN git clone https://github.com/chris-rijk/gradle-companies-poc
WORKDIR /home/gradle/project/gradle-companies-poc

RUN gradle --no-daemon --info --stacktrace :companies-runtime-dev:jar

FROM openjdk:8-jre-alpine
WORKDIR /root/
COPY --from=BUILD_IMAGE /home/gradle/project/gradle-companies-poc/companies/runtime/dev/build/libs/companies-runtime-dev-1.0.jar .
EXPOSE 18080
CMD ["java","-jar","companies-runtime-dev-1.0.jar"]