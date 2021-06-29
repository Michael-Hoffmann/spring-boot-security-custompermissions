package de.hoffmannmh.springbootsecuritycustompermissions.integration.oauth;


import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.neovisionaries.i18n.CountryCode;
import de.hoffmannmh.springbootsecuritycustompermissions.domain.security.CustomGrantedAuthority;
import lombok.Data;

import java.time.Instant;
import java.util.List;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class CustomAuthentication {

    private String sub;
    private String realm;
    private String upn;
    private String email;
    @JsonAlias("country")
    private CountryCode countryCode;
    private Instant iat;
    private List<String> aud;
    private String userType;
    private String iss;
    private Instant exp;

    @JsonDeserialize(contentConverter = CustomGrantedAuthorityConverter.class)
    private List<CustomGrantedAuthority> authorization;

}
