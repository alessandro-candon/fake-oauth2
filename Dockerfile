FROM amazoncorretto:21-alpine as builder

COPY gradle gradle
COPY build.gradle .
COPY settings.gradle .
COPY gradlew .
COPY src src

RUN ./gradlew build -x test

FROM amazoncorretto:21-alpine as corretto-deps

COPY --from=builder ./build/libs/*-SNAPSHOT.jar /app/app.jar

RUN unzip /app/app.jar -d temp &&  \
    jdeps  \
      --print-module-deps \
      --ignore-missing-deps \
      --recursive \
      --multi-release 21 \
      --class-path="./temp/BOOT-INF/lib/*" \
      --module-path="./temp/BOOT-INF/lib/*" \
      /app/app.jar > /modules.txt

FROM amazoncorretto:21-alpine as corretto-jdk

COPY --from=corretto-deps /modules.txt /modules.txt
RUN apk add --no-cache binutils && \
    jlink \
     --verbose \
     --add-modules "$(cat /modules.txt),jdk.crypto.ec,jdk.crypto.cryptoki" \
     --strip-debug \
     --no-man-pages \
     --no-header-files \
     --compress=2 \
     --output /jre

FROM alpine:latest as runner
USER root
RUN /bin/echo -n "" > /etc/fstab \
 && /bin/sed -i 's/root:\/bin\/ash/root:\/sbin\/nologin/' /etc/passwd

MAINTAINER "alessandro-candon"
ENV JAVA_OPTS=${JAVA_OPTS:-""}
ENV LOG_FOLDER=${LOG_FOLDER:-"/logs"}
ENV APP_TIMEZONE=UTC
ENV TZ=UTC

VOLUME ["${LOG_FOLDER}"]

ARG JAR_FILE

FROM runner
USER root

ENV JAVA_HOME=/jre
ENV PATH="${JAVA_HOME}/bin:${PATH}"
COPY --from=corretto-jdk /jre $JAVA_HOME
COPY --from=builder ./build/libs/*-SNAPSHOT.jar app.jar

RUN ln -snf /usr/share/zoneinfo/$TZ /etc/localtime && \
    echo $TZ > /etc/timezone

RUN addgroup -S spring --gid 3000 && adduser -S spring -G spring --uid 1000

USER spring:spring
CMD java ${JAVA_OPTS} -Djava.security.egd=file:/dev/./urandom -jar /app.jar