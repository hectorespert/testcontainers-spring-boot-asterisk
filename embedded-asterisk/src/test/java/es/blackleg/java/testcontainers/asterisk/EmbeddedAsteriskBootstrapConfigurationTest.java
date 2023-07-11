package es.blackleg.java.testcontainers.asterisk;

import org.asteriskjava.AsteriskVersion;
import org.asteriskjava.manager.ManagerConnection;
import org.asteriskjava.manager.ManagerConnectionState;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;

import static org.assertj.core.api.Assertions.assertThat;


@SpringBootTest(classes = TestConfiguration.class)
class EmbeddedAsteriskBootstrapConfigurationTest {

    @Test
    void shouldBeLoaded(ApplicationContext context) {
        assertThat(context.containsBean("asterisk")).isTrue();
    }

    @Test
    void shouldBeAMIAvailable(ApplicationContext context) {
        var mangerConnection = context.getBean(ManagerConnection.class);
        assertThat(mangerConnection.getState()).isEqualTo(ManagerConnectionState.CONNECTED);
        assertThat(mangerConnection.getVersion()).isEqualTo(AsteriskVersion.ASTERISK_18);
    }

}