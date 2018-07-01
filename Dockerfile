FROM maven:3-alpine
COPY . /app
WORKDIR /app
RUN mvn clean install -DskipTests=true
EXPOSE 8080
CMD ["mvn", "spring-boot:run"]