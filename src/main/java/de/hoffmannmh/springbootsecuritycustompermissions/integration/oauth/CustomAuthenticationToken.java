package de.hoffmannmh.springbootsecuritycustompermissions.integration.oauth;

import lombok.Getter;
import org.springframework.security.authentication.AbstractAuthenticationToken;

@Getter
public class CustomAuthenticationToken extends AbstractAuthenticationToken {

    private CustomAuthentication customAuthentication;

    public CustomAuthenticationToken(CustomAuthentication customAuthentication) {
        super(customAuthentication.getAuthorization());
        this.customAuthentication = customAuthentication;
    }

    @Override
    public Object getCredentials() {
        return null;
    }

    @Override
    public Object getPrincipal() {
        return customAuthentication.getSub();
    }

}
