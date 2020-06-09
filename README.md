# Zup-Store-Challenge

**How to execute**

To execute this application, run the following commands

`mvn clean install`

`docker-compose build`

`docker-compose up`

**Documentations**

The Swagger documentation can be found in the following location:

`src\main\resources\static\swagger-store.yaml`

The Postman collection to run the tests is located at:

`Zup Challenge.postman_collection.json`

**Tracing**

Once you start using the application the tracing logs can be found on Jaeger Panel (http://localhost:16686/) under the name of "zup-challenge-store" service