FROM  openjdk:11

RUN mkdir -p /dormitory

WORKDIR /dormitory

ARG JAR_FILE=target/dormitory-*.jar

COPY ${JAR_FILE} app.jar

EXPOSE 8888

ENV TZ=Asia/Shanghai JAVA_OPTS="-Xms128m -Xmx256m -Djava.security.egd=file:/dev/./urandom"

CMD sleep 5; java -jar app.jar $JAVA_OPTS