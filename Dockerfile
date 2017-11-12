FROM gradle:4.3.0-jdk8-alpine AS BUILD_IMAGE
WORKDIR /home/gradle/project

COPY *.gradle ./
COPY common common
COPY companies companies
USER root:root
RUN chown -R gradle:gradle .
USER gradle:gradle

RUN gradle --no-daemon --info --stacktrace :companies-runtime-dev:jar

FROM openjdk:8-jre-alpine
WORKDIR /root/
COPY --from=BUILD_IMAGE /home/gradle/project/companies/runtime/dev/build/libs/companies-runtime-dev-1.0.jar .
EXPOSE 18080
CMD ["java","-jar","companies-runtime-dev-1.0.jar"]