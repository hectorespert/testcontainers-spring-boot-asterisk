package es.blackleg.java.testcontainers.asterisk;

import com.playtika.testcontainer.common.spring.DockerPresenceBootstrapConfiguration;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.boot.autoconfigure.condition.ConditionalOnExpression;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConditionalOnExpression("${embedded.containers.enabled:true}")
@AutoConfigureAfter(DockerPresenceBootstrapConfiguration.class)
@ConditionalOnProperty(name = "embedded.asterisk.enabled", matchIfMissing = true)
@EnableConfigurationProperties(AsteriskProperties.class)
public class EmbeddedAsteriskBootstrapConfiguration {


}
