# Asterisk Testcontainer Spring Boot

Asterisk testcontainer autoconfigured for Spring Based based into [PlaytikaOSS Test containers](https://github.com/PlaytikaOSS/testcontainers-spring-boot)

Asterisk image is generated here: [hectorespert/docker-asterisk](https://github.com/hectorespert/docker-asterisk)

## Usage

- Install and configure [PlaytikaOSS Test containers](https://github.com/PlaytikaOSS/testcontainers-spring-boot)

- Import as a test dependency

```xml
<dependency>
  <groupId>es.blackleg.java.testcontainers</groupId>
  <artifactId>embedded-asterisk</artifactId>
  <version>1.3.4</version>
  <scope>test</scope>
</dependency>
```



