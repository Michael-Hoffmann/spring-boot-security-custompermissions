package de.hoffmannmh.springbootsecuritycustompermissions.domain.security;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class CustomUserInfo {

    private String sub;
    private String userType;
    private String aud;

}
