package de.hoffmannmh.springbootsecuritycustompermissions.domain.security;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

@Component
public class CustomUserInfoResolver {

    private CustomUserInfoConverter customUserInfoConverter;

    public CustomUserInfoResolver(CustomUserInfoConverter customUserInfoConverter) {
        this.customUserInfoConverter = customUserInfoConverter;
    }

    public CustomUserInfo get() {
        return customUserInfoConverter.convert(SecurityContextHolder.getContext().getAuthentication());
    }
}
