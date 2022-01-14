package com.tui.proof.bean;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UpdateOrderRequest implements Serializable {

    private Integer orderId;
    private Integer quantity;
    private String street;
    private String postcode;
    private String city;
    private String country;
}
