package com.tui.proof.bean;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.io.Serializable;
@Data
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
public class CreateOrderRequest implements Serializable {

    private String clientUsername;
    private Integer quantity;
    private String street;
    private String postcode;
    private String city;
    private String country;

}
