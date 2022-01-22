package com.tui.proof.bean;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
public class UpdateOrderRequest implements Serializable {

    @Schema(description = "order id")
    private Integer orderId;
    @Schema(description = "pilotes quantity", allowableValues = {"5", "10", "15"})
    private Integer quantity;
    @Schema(description = "Street Address")
    private String street;
    @Schema(description = "Postcode Address")
    private String postcode;
    @Schema(description = "City Address")
    private String city;
    @Schema(description = "Country Address")
    private String country;
}
