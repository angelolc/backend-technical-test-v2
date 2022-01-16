package com.tui.proof.service;

import com.tui.proof.bean.CreateOrderRequest;
import com.tui.proof.bean.UpdateOrderRequest;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Component
public class PilotesValidationService {

    private static final List<Integer> ALLOWED_PILOTES_QUANTITY = Arrays.asList(5, 10, 15);

    public List<String> validateCreateOrderReq(CreateOrderRequest req){
        List<String> msgs = new ArrayList<>();
        if(StringUtils.isBlank(req.getClientUsername())){
            msgs.add("username cannot be blank");
        }
        if(StringUtils.isBlank(req.getStreet())){
            msgs.add("street cannot be blank");
        }
        if(StringUtils.isBlank(req.getCity())){
            msgs.add("city cannot be blank");
        }
        if(StringUtils.isBlank(req.getPostcode())){
            msgs.add("postcode cannot be blank");
        }
        if(StringUtils.isBlank(req.getCountry())){
            msgs.add("country cannot be blank");
        }
        if(!ALLOWED_PILOTES_QUANTITY.contains(req.getQuantity())){
            msgs.add("invalid quantity. Choose between 5, 10, 15");
        }
        return msgs;
    }

    public List<String> validateUpdateOrderReq(UpdateOrderRequest req){
        List<String> msgs = new ArrayList<>();
        if(req.getOrderId() == null){
            msgs.add("orderId cannot be null");
        }
        if(StringUtils.isBlank(req.getStreet())){
            msgs.add("street cannot be blank");
        }
        if(StringUtils.isBlank(req.getCity())){
            msgs.add("city cannot be blank");
        }
        if(StringUtils.isBlank(req.getPostcode())){
            msgs.add("postcode cannot be blank");
        }
        if(StringUtils.isBlank(req.getCountry())){
            msgs.add("country cannot be blank");
        }
        if(!ALLOWED_PILOTES_QUANTITY.contains(req.getQuantity())){
            msgs.add("invalid quantity. Choose between 5, 10, 15");
        }
        return msgs;
    }
}
