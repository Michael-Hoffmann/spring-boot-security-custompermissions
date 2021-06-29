package de.hoffmannmh.springbootsecuritycustompermissions.integration.oauth;

import com.fasterxml.jackson.databind.util.StdConverter;
import com.neovisionaries.i18n.CountryCode;
import de.hoffmannmh.springbootsecuritycustompermissions.domain.security.CustomGrantedAuthority;
import de.hoffmannmh.springbootsecuritycustompermissions.domain.security.CustomRole;

import java.util.*;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.toList;

public class CustomGrantedAuthorityConverter extends StdConverter<Object, CustomGrantedAuthority> {
    @Override
    public CustomGrantedAuthority convert(Object o) {
        if( o instanceof LinkedHashMap) {
            LinkedHashMap<String, ArrayList<LinkedHashMap<String, ArrayList>>> map = (LinkedHashMap<String, ArrayList<LinkedHashMap<String, ArrayList>>>) o;
            Set<Map.Entry<String, ArrayList<LinkedHashMap<String, ArrayList>>>> entries = map.entrySet();
            if(entries.size()==1) {
                Map.Entry<String, ArrayList<LinkedHashMap<String, ArrayList>>> entry = entries.stream().findAny().get();
                return CustomGrantedAuthority.builder()
                        .role(CustomRole.byName(entry.getKey()))
                        .countryCodes(getCountryCodes(entry))
                        .build();
            } else {
                throw new IllegalStateException("Exactly one entry supported");
            }

        } else {
            throw new IllegalStateException("Type not supported");
        }
    }

    private List<CountryCode> getCountryCodes(Map.Entry<String, ArrayList<LinkedHashMap<String, ArrayList>>> entry) {
        return entry.getValue().stream()
                .filter(m -> m.containsKey("country"))
                .map(m -> countryCodesFromList(m.get("country")))
                .flatMap(Collection<CountryCode>::stream).collect(toList());
    }

    private Collection<CountryCode> countryCodesFromList(List<Object> list) {
        return list.stream().map(o -> CountryCode.valueOf(o.toString())).collect(Collectors.toCollection(ArrayList<CountryCode>::new));
    }

}
