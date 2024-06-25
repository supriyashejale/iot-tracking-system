#
# Build stage
#
#FROM maven:3.6.0-jdk-11-slim AS build
#COPY src /home/usr/src
#COPY pom.xml /home/usr
#RUN mvn -f /home/usr/pom.xml clean package

# Pull base image.
FROM openjdk:17-jdk-slim-buster

EXPOSE 80
EXPOSE 443
RUN apt-get update && apt-get upgrade -y \
 && apt-get install -y --no-install-recommends curl jq \
 && rm -rf /var/lib/apt/lists/*

RUN mkdir -p /usr/src/iottrackingsystem
WORKDIR /usr/src/iottrackingsystem

EXPOSE 8084
ADD ./target/iottrackingsystem-0.0.1-SNAPSHOT.jar /usr/src/iottrackingsystem/iottrackingsystem.jar
#COPY --from=build /home/usr/target/iottrackingsystem-0.0.1-SNAPSHOT.jar /usr/src/iottrackingsystem/iottrackingsystem.jar
CMD java -jar /usr/src/iottrackingsystem/iottrackingsystem.jar ‐Dlog4j2.formatMsgNoLookups=True

HEALTHCHECK --start-period=30s --interval=10s --timeout=3s --retries=3 \
            CMD curl --silent --fail --request GET http://iottrackingsystem/actuator/health \
                | jq --exit-status '.status == "UP"' || exit 1


