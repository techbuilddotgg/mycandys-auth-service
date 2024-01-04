FROM maven:3.8.4-openjdk-17 as builder

WORKDIR /app

COPY pom.xml .

RUN mvn -B dependency:resolve-plugins dependency:resolve

COPY ./src ./src

RUN mvn package -DskipTests

FROM ibm-semeru-runtimes:open-17-jre

WORKDIR /app

COPY --from=builder /app/target/sua-auth-0.0.1-SNAPSHOT.jar ./sua-auth-0.0.1-SNAPSHOT.jar

EXPOSE ${PORT}

CMD ["java", "-jar", "/app/sua-auth-0.0.1-SNAPSHOT.jar"]