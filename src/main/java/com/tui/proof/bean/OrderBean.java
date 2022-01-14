package com.tui.proof.bean;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.time.LocalDateTime;

@Data
@Accessors(chain = true)
@NoArgsConstructor
@AllArgsConstructor
public class OrderBean implements Serializable {

    private Integer id;
    private AddressBean deliveryAddress;
    private Integer quantity;
    private Double orderTotal;
    private LocalDateTime creationDate;
}
