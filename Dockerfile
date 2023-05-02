FROM openjdk

WORKDIR /app

ADD ./out/artifacts/CarManagment_jar/CarManagment.jar .

CMD ["java", "-jar", "CarManagment.jar"]