# TopSecret API 

API REST que permite localizar una nave imperial y su mensaje enviado utilizando 3 satelites rebeldes que se encuentran inicialmente en las siguientes posiciones:

## Host API :computer:
* https://topsecretapi-304122.uc.r.appspot.com/

## Documentación 📄
* [Swagger-ui](https://swagger.io/) - https://topsecretapi-304122.uc.r.appspot.com/swagger-ui/index.html

### Top Secret Rest

* [DecodeAndLocalize](TopSecretAPI/doc/decodeAndLocalize.md) : `POST /topsecret`

### Top Secret Split

* [SetDistanceAndMessage](TopSecretAPI/doc/setDistanceAndMessage.md) : `POST /topsecret_split/{satellite_name}`
* [DecodeAndLocalizeSplit](TopSecretAPI/doc/decodeAndLocalizeSplit.md) : `GET /topsecret_split`

### Position Rest Controller

* [ChangeSatellitePosition](TopSecretAPI/doc/changeSatellitePosition.md) : `PUT /position/{satellite_name}`
* [RestartPositionByDefault](TopSecretAPI/doc/restartPositionByDefault.md) : `POST /position/restart`
* [GetAllSatellitesPosition](TopSecretAPI/doc/getAllSatellitesPosition.md) : `GET /position`

## Construido con 🛠️

* [Maven](https://maven.apache.org/) - Dependencias
* [Springboot](https://spring.io/projects/spring-boot) - Spring Framework
* [Swagger](https://swagger.io/) - Documentación
* [JUnit](https://junit.org/) - Tests
* [Mockito](https://site.mockito.org/) - Test
