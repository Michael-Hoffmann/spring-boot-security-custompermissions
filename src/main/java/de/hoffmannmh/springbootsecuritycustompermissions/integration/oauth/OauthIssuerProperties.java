package de.hoffmannmh.springbootsecuritycustompermissions.integration.oauth;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
@ConfigurationProperties(prefix = "app.security.oauth.issuer")
@Data
public class OauthIssuerProperties {

    private List<String> whitelist;

}
