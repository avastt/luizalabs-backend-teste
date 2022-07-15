FROM openjdk:11
COPY target/*.jar wishlist.jar
ENTRYPOINT ["java", "-jar", "/wishlist.jar"]