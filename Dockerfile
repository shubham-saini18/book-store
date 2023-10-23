FROM openjdk:17-slim
EXPOSE 8080
COPY target/book-store.jar book-store.jar
CMD [ "java" , "-jar" , "/book-store.jar"]