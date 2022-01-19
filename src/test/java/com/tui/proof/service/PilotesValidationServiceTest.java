package com.tui.proof.service;

import com.tui.proof.bean.CreateOrderRequest;
import com.tui.proof.bean.UpdateOrderRequest;
import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;


import java.util.Arrays;
import java.util.List;

public class PilotesValidationServiceTest {

    private PilotesValidationService pilotesValidationService;

    @Before
    public void setUp() {
        pilotesValidationService = new PilotesValidationService()
                .setAllowedPilotesQuantity(Arrays.asList(5, 10, 15));
    }

    @Test
    public void validateCreateOrderReq_OK() {
        List<String> msgs = pilotesValidationService.validateCreateOrderReq(
                new CreateOrderRequest()
                        .setClientUsername("client1")
                        .setQuantity(5)
                        .setCity("city1")
                        .setCountry("country1")
                        .setPostcode("postcode1")
                        .setStreet("street1")
        );
        Assertions.assertTrue(msgs.isEmpty());
    }

    @Test
    public void validateCreateOrderReq_KO() {
        List<String> msgs = pilotesValidationService.validateCreateOrderReq(
                new CreateOrderRequest()
                        .setClientUsername("client1")
                        .setQuantity(7)
                        .setCity(null)
                        .setCountry("country1")
                        .setPostcode("postcode1")
                        .setStreet("street1")
        );
        Assertions.assertFalse(msgs.isEmpty());
        Assertions.assertArrayEquals(
                new String[]{"city cannot be blank", "invalid quantity. Choose between 5, 10, 15"},
                msgs.toArray()
        );

    }

    @Test
    public void validateUpdateOrderReq_OK() {
        List<String> msgs = pilotesValidationService.validateUpdateOrderReq(
                new UpdateOrderRequest()
                        .setOrderId(2)
                        .setQuantity(5)
                        .setCity("city2")
                        .setCountry("country2")
                        .setPostcode("postcode2")
                        .setStreet("street2")
        );
        Assertions.assertTrue(msgs.isEmpty());
    }

    @Test
    public void validateUpdateOrderReq_KO() {
        List<String> msgs = pilotesValidationService.validateUpdateOrderReq(
                new UpdateOrderRequest()
                        .setOrderId(null)
                        .setQuantity(5)
                        .setCity("city2")
                        .setCountry("country2")
                        .setPostcode("postcode2")
                        .setStreet("")
        );
        Assertions.assertArrayEquals(
                new String[]{"orderId cannot be null", "street cannot be blank"},
                msgs.toArray()
        );
    }
}