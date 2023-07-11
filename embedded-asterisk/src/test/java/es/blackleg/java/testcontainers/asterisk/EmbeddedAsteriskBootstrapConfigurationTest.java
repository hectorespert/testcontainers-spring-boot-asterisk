package es.blackleg.java.testcontainers.asterisk;

import org.asteriskjava.AsteriskVersion;
import org.asteriskjava.manager.*;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;


import static org.assertj.core.api.Assertions.assertThat;


@SpringBootTest(classes = EmbeddedAsteriskBootstrapConfigurationTest.TestConfiguration.class)
class EmbeddedAsteriskBootstrapConfigurationTest {

    @Test
    void shouldBeLoaded(ApplicationContext context) {
        assertThat(context.containsBean("asterisk")).isTrue();
    }

    @Test
    void shouldBeAMIAvailable(@Value("${embedded.asterisk.host}") String host,
                              @Value("${embedded.asterisk.ami.port}") int port,
                              @Value("${embedded.asterisk.ami.user}") String user,
                              @Value("${embedded.asterisk.ami.password}") String pass) throws Exception {
        assertThat(host).isNotNull();
        assertThat(port).isNotZero();
        assertThat(user).isEqualTo("asterisk");
        assertThat(pass).isEqualTo("asterisk");

        var managerConnectionFactory = new ManagerConnectionFactory(host, port, user, pass);
        var managerConnection = managerConnectionFactory.createManagerConnection();
        managerConnection.login();
        assertThat(managerConnection.getState()).isEqualTo(ManagerConnectionState.CONNECTED);
        assertThat(managerConnection.getVersion()).isEqualTo(AsteriskVersion.ASTERISK_18);
        managerConnection.logoff();
    }

    @Test
    @Disabled
    void shouldBeARIAvailable(@Value("${embedded.asterisk.host}") String host,
                              @Value("${embedded.asterisk.ari.port}") int port,
                              @Value("${embedded.asterisk.ari.user}") String user,
                              @Value("${embedded.asterisk.ari.password}") String pass) {
        assertThat(host).isNotNull();
        assertThat(port).isNotZero();
        assertThat(user).isEqualTo("asterisk");
        assertThat(pass).isEqualTo("asterisk");

        WebClient webClient = WebClient.builder()
                .defaultHeaders(header -> header.setBasicAuth(user, pass))
                .baseUrl("http://" + host + ":" + port)
                .build();

        var response = webClient
                .get()
                .uri("/ari/asterisk/ping")
                .retrieve()
                .bodyToMono(String.class)
                .block();
        assertThat(response).isEqualTo("Asterisk REST Interface");
    }

    @EnableAutoConfiguration
    @Configuration
    static class TestConfiguration {}

}