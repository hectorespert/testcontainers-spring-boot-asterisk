package es.blackleg.java.testcontainers.asterisk;

import org.asteriskjava.manager.ManagerConnection;
import org.asteriskjava.manager.ManagerConnectionFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@EnableAutoConfiguration
@Configuration
public class TestConfiguration {


    @Bean
    public ManagerConnectionFactory managerConnectionFactory(@Value("${embedded.asterisk.host}") String host,
                                                             @Value("${embedded.asterisk.ami.port}") int port,
                                                             @Value("${embedded.asterisk.ami.user}") String user,
                                                             @Value("${embedded.asterisk.ami.password}") String pass){
        return new ManagerConnectionFactory(host, port, user, pass);
    }

    @Bean(initMethod = "login", destroyMethod = "logoff")
    public ManagerConnection managerConnection(ManagerConnectionFactory managerConnectionFactory) {
        return managerConnectionFactory.createManagerConnection();
    }


}
