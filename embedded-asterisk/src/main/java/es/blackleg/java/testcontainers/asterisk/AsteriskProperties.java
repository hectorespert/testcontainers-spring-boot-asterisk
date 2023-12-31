package es.blackleg.java.testcontainers.asterisk;

import com.playtika.testcontainer.common.properties.CommonContainerProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties("embedded.asterisk")
public class AsteriskProperties extends CommonContainerProperties {
    @Override
    public String getDefaultDockerImage() {
        return "blackleg/asterisk:edge";
    }
}
