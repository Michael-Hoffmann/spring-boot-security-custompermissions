package de.hoffmannmh.springbootsecuritycustompermissions.integration.oauth;

import org.springframework.core.convert.converter.Converter;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.stereotype.Component;

@Component
public class CustomAuthenticationTokenConverter implements Converter<Jwt, AbstractAuthenticationToken> {
    public AbstractAuthenticationToken convert(Jwt jwt) {
        CustomAuthentication customAuthentication = CustomAuthenticationBuilder.fromJWT(jwt);
        return new CustomAuthenticationToken(customAuthentication);
    }
}
