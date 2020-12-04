FROM openjdk:8
MAINTAINER luoshi luoshi@qmth.com.cn
ENV TZ Asia/Shanghai

ARG JAR_FILE
COPY ${JAR_FILE} /opt/app.jar
CMD java -XX:InitialRAMPercentage=50.0 -XX:MaxRAMPercentage=50.0 -XX:MetaspaceSize=128m -XX:MaxMetaspaceSize=128m $JAVA_OPTS -jar /opt/app.jar
