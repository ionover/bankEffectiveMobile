FROM azul/zulu-openjdk-alpine:25-jre
LABEL MAINTAINER="ionov <java>"

RUN apk --no-cache add curl

COPY ./target/bankEffectiveMobile-0.0.1-SNAPSHOT-exec.jar bankEffectiveMobile.jar

EXPOSE 8084

HEALTHCHECK --interval=10s --timeout=5s --retries=6 --start-period=10s \
    CMD curl -f http://localhost:8084/actuator/health || exit 1

ENTRYPOINT exec java -jar bankEffectiveMobile.jar
