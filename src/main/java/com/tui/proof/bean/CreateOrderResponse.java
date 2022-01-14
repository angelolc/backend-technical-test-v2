package com.tui.proof.bean;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.io.Serializable;
@Data
@Accessors(chain = true)
@NoArgsConstructor
@AllArgsConstructor
public class CreateOrderResponse implements Serializable {

    private Integer orderId;

}
