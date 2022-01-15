package com.tui.proof.converters;

import com.tui.proof.bean.AddressBean;
import com.tui.proof.bean.OrderBean;
import com.tui.proof.model.Address;
import com.tui.proof.model.Order;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.ConversionService;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Data
@Accessors(chain = true)
public class OrderToOrderBeanConverter implements Converter<Order, OrderBean> {

    @Override
    public OrderBean convert(Order order) {
        return new OrderBean()
                .setId(order.getId())
                .setCreationDate(order.getCreationDate())
                .setDeliveryAddress(convertAddress(order.getDeliveryAddress()))
                .setQuantity(order.getQuantity())
                .setOrderTotal(order.getOrderTotal());
    }

    private AddressBean convertAddress(Address address) {
        return new AddressBean()
                .setId(address.getId())
                .setStreet(address.getStreet())
                .setCity(address.getCity())
                .setCountry(address.getCountry())
                .setPostcode(address.getPostcode());
    }

}
