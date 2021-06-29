package de.hoffmannmh.springbootsecuritycustompermissions.integration.oauth;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.springframework.security.oauth2.jwt.Jwt;

public class CustomAuthenticationBuilder {

    public static CustomAuthentication fromJWT(Jwt jwt) {
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());
        CustomAuthentication customAuthentication = objectMapper.convertValue(jwt.getClaims(), CustomAuthentication.class);
        return customAuthentication;
    }

}
