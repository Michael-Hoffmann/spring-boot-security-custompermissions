package de.hoffmannmh.springbootsecuritycustompermissions.integration.oauth;

import com.neovisionaries.i18n.CountryCode;
import com.nimbusds.jose.proc.BadJOSEException;
import com.nimbusds.jose.proc.SecurityContext;
import com.nimbusds.jwt.JWTClaimsSet;
import com.nimbusds.jwt.SignedJWT;
import com.nimbusds.jwt.proc.BadJWTException;
import com.nimbusds.jwt.proc.DefaultJWTProcessor;
import com.nimbusds.jwt.proc.JWTProcessor;
import de.hoffmannmh.springbootsecuritycustompermissions.domain.security.CustomGrantedAuthority;
import de.hoffmannmh.springbootsecuritycustompermissions.domain.security.CustomRole;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.jwt.NimbusJwtDecoder;

import java.text.ParseException;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

public class CustomAuthenticationTokenTest {

    public static final String TOKEN = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJlbWFpbCI6ImpvaG5AZG9lLmNvbSIsImNvdW50cnkiOiJERSIsImlhdCI6MTYyNDg2MTIxNywiYXVkIjoiSk9ITl9ET0VfQ0xJRU5UIiwidXNlclR5cGUiOiJFTVAiLCJpc3MiOiJodHRwczovL3d3dy5kb2UuY29tIiwiZXhwIjo0NjgzODk3Nzc2LCJhdXRob3JpemF0aW9uIjpbeyJNWV9ST0xFXzEiOlt7ImNvdW50cnkiOlsiREUiLCJCRSIsIkZSIl19XX0seyJNWV9ST0xFXzMiOltdfSx7Ik1ZX1JPTEVfMiI6W3siY291bnRyeSI6WyJQTCIsIkJFIiwiRlIiXX1dfV0sInJlYWxtIjoiR0xPQkFMX1JFQUxNIiwic3ViIjoiVV9oZjNnZjJnLWJkZTAtNGI2Ni04YmZhLXNkaGdmamc3MzJ6IiwidXBuIjoiam9obkBkb2UuY29tIn0.xrwfHZrw2HBJhWGkuox94nbwNXG3pXG7grhG_FyatYs";
    private NimbusJwtDecoder jwtDecoder = new NimbusJwtDecoder(withoutSigning());

    @Test
    public void test() {
        Jwt jwt = jwtDecoder.decode(TOKEN);
        CustomAuthenticationTokenConverter customAuthenticationTokenConverter = new CustomAuthenticationTokenConverter();
        AbstractAuthenticationToken abstractAuthenticationToken = customAuthenticationTokenConverter.convert(jwt);
        Assertions.assertThat(abstractAuthenticationToken.getPrincipal()).isEqualTo("U_hf3gf2g-bde0-4b66-8bfa-sdhgfjg732z");
        Assertions.assertThat(abstractAuthenticationToken).isInstanceOf(CustomAuthenticationToken.class);

        CustomAuthenticationToken customAuthenticationToken = (CustomAuthenticationToken) abstractAuthenticationToken;
        Assertions.assertThat(customAuthenticationToken.getCustomAuthentication()).isNotNull();

        CustomAuthentication customAuthentication = ((CustomAuthenticationToken) abstractAuthenticationToken).getCustomAuthentication();
        Assertions.assertThat(customAuthentication.getAud()).contains("JOHN_DOE_CLIENT");
        Assertions.assertThat(customAuthentication.getSub()).isEqualTo("U_hf3gf2g-bde0-4b66-8bfa-sdhgfjg732z");
        Assertions.assertThat(customAuthentication.getUserType()).isEqualTo("EMP");
        Assertions.assertThat(customAuthentication.getCountryCode()).isEqualTo(CountryCode.DE);
        Assertions.assertThat(customAuthentication.getEmail()).isEqualTo("john@doe.com");
        Assertions.assertThat(customAuthentication.getUpn()).isEqualTo("john@doe.com");
        Assertions.assertThat(customAuthentication.getIss()).isEqualTo("https://www.doe.com");
        Assertions.assertThat(customAuthentication.getRealm()).isEqualTo("GLOBAL_REALM");
        Assertions.assertThat(customAuthentication.getIat()).isEqualTo(Instant.parse("2021-06-28T06:20:17Z"));
        Assertions.assertThat(customAuthentication.getExp()).isEqualTo("2118-06-05T18:42:56Z");

        Assertions.assertThat(customAuthentication.getAuthorization()).containsExactlyInAnyOrderElementsOf(customGrantedAuthorities());

    }

    private static Collection<CustomGrantedAuthority> customGrantedAuthorities() {
        List<CustomGrantedAuthority> list = new ArrayList<>();
        list.add(CustomGrantedAuthority.builder()
                .role(CustomRole.MY_ROLE_1)
                .countryCodes(Arrays.asList(CountryCode.DE, CountryCode.BE, CountryCode.FR))
                .build()
        );
        list.add(CustomGrantedAuthority.builder()
                .role(CustomRole.MY_ROLE_2)
                .countryCodes(Arrays.asList(CountryCode.PL, CountryCode.BE, CountryCode.FR))
                .build()
        );
        list.add(CustomGrantedAuthority.builder()
                .role(CustomRole.MY_ROLE_3)
                .countryCodes(Arrays.asList())
                .build()
        );
        return list;

    }


    private static JWTProcessor<SecurityContext> withoutSigning() {
        return new MockJwtProcessor();
    }

    private static class MockJwtProcessor extends DefaultJWTProcessor<SecurityContext> {

        @Override
        public JWTClaimsSet process(SignedJWT signedJWT, SecurityContext context) throws BadJOSEException {
            try {
                return signedJWT.getJWTClaimsSet();
            } catch (ParseException ex) {
                // Payload not a JSON object
                throw new BadJWTException(ex.getMessage(), ex);
            }
        }

    }

}
