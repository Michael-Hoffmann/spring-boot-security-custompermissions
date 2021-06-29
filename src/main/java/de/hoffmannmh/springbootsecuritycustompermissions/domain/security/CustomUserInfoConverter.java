package de.hoffmannmh.springbootsecuritycustompermissions.domain.security;

import org.springframework.core.convert.converter.Converter;
import org.springframework.security.core.Authentication;

public interface CustomUserInfoConverter extends Converter<Authentication, CustomUserInfo> {

}
