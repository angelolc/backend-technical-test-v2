package com.tui.proof.converters;

import com.tui.proof.bean.AddressBean;
import com.tui.proof.model.Address;
import org.springframework.core.convert.converter.Converter;

public class AddressToAddressBeanConverter implements Converter<Address, AddressBean> {
    @Override
    public AddressBean convert(Address address) {
        return new AddressBean()
                .setId(address.getId())
                .setStreet(address.getStreet())
                .setCity(address.getCity())
                .setCountry(address.getCountry())
                .setPostcode(address.getPostcode());
    }
}
