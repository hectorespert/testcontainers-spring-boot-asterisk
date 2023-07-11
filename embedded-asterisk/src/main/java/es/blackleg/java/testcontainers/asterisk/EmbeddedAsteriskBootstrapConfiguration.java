package es.blackleg.java.testcontainers.asterisk;

import com.playtika.testcontainer.common.spring.DockerPresenceBootstrapConfiguration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.boot.autoconfigure.condition.ConditionalOnExpression;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.ConfigurableEnvironment;
import org.springframework.core.env.MapPropertySource;
import org.testcontainers.containers.GenericContainer;
import org.testcontainers.containers.Network;

import java.util.LinkedHashMap;
import java.util.Optional;

import static com.playtika.testcontainer.common.utils.ContainerUtils.configureCommonsAndStart;
import static com.playtika.testcontainer.common.utils.ContainerUtils.getDockerImageName;

@Configuration
@ConditionalOnExpression("${embedded.containers.enabled:true}")
@AutoConfigureAfter(DockerPresenceBootstrapConfiguration.class)
@ConditionalOnProperty(name = "embedded.asterisk.enabled", matchIfMissing = true)
@EnableConfigurationProperties(AsteriskProperties.class)
public class EmbeddedAsteriskBootstrapConfiguration {

    private final Logger logger = LoggerFactory.getLogger(EmbeddedAsteriskBootstrapConfiguration.class);

    @Bean(name = "asterisk", destroyMethod = "stop")
    public GenericContainer<?> asterisk(ConfigurableEnvironment environment,
                                          AsteriskProperties properties,
                                          Optional<Network> network) {

        GenericContainer<?> container = new GenericContainer<>(getDockerImageName(properties))
                .withNetwork(Network.SHARED)
                .withExposedPorts(5038);

        network.ifPresent(container::withNetwork);

        configureCommonsAndStart(container, properties, logger);

        registerEnvironment(container, environment);

        return container;
    }

    private void registerEnvironment(GenericContainer<?> asterisk,
                                     ConfigurableEnvironment environment) {

        String host = asterisk.getHost();
        LinkedHashMap<String, Object> map = new LinkedHashMap<>();
        map.put("embedded.asterisk.host", host);
        map.put("embedded.asterisk.ami.port", asterisk.getMappedPort(5038));
        map.put("embedded.asterisk.ami.user", "asterisk");
        map.put("embedded.asterisk.ami.password", "asterisk");

        logger.info("Started Asterisk server. Connection details: {}", map);

        MapPropertySource propertySource = new MapPropertySource("embeddedAsteriskInfo", map);
        environment.getPropertySources().addFirst(propertySource);
    }


}
