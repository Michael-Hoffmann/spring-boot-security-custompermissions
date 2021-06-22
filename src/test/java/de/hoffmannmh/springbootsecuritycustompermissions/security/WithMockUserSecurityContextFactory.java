package de.hoffmannmh.springbootsecuritycustompermissions.security;

import com.neovisionaries.i18n.CountryCode;
import de.hoffmannmh.springbootsecuritycustompermissions.domain.security.CustomGrantedAuthority;
import de.hoffmannmh.springbootsecuritycustompermissions.domain.security.CustomRole;
import de.hoffmannmh.springbootsecuritycustompermissions.domain.security.CustomUserInfo;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.test.context.support.WithSecurityContextFactory;
import org.springframework.security.web.authentication.preauth.PreAuthenticatedAuthenticationToken;

import java.util.Arrays;
import java.util.Collections;
import java.util.stream.Collectors;

public class WithMockUserSecurityContextFactory implements WithSecurityContextFactory<WithMockUser> {

    @Override
    public SecurityContext createSecurityContext(WithMockUser mockUser) {
        CustomGrantedAuthority customGrantedAuthority = CustomGrantedAuthority
                .builder()
                .role(CustomRole.byName(mockUser.role()))
                .countryCodes(Arrays.stream(mockUser.countries()).map(code -> CountryCode.valueOf(code)).collect(Collectors.toList()))
                .build();

        PreAuthenticatedAuthenticationToken authentication = new PreAuthenticatedAuthenticationToken(CustomUserInfo
                .builder()
                .aud("Audience")
                .userType("EMP")
                .sub("john.doe@metro.digital")
                .build(),
                null, Collections.singleton(customGrantedAuthority));

        SecurityContext context = SecurityContextHolder.createEmptyContext();
        context.setAuthentication(authentication);
        return context;
    }

}
