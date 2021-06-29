package de.hoffmannmh.springbootsecuritycustompermissions.integration.oauth;

import de.hoffmannmh.springbootsecuritycustompermissions.domain.security.CustomUserInfo;
import de.hoffmannmh.springbootsecuritycustompermissions.domain.security.CustomUserInfoConverter;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

@Component
public class OauthCustomUserInfoConverter implements CustomUserInfoConverter {
    @Override
    public CustomUserInfo convert(Authentication authentication) {
        assert(authentication instanceof CustomAuthentication) : "Authentication must be of type CustomAuthentication";

        CustomAuthentication customAuthentication = (CustomAuthentication) authentication;
        return CustomUserInfo.builder()
                .aud(customAuthentication.getAud().isEmpty() ? null : customAuthentication.getAud().get(0))
                .userType(customAuthentication.getUserType())
                .sub(customAuthentication.getSub())
                .build();
    }
}
