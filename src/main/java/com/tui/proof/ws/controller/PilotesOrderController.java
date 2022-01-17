package com.tui.proof.ws.controller;

import com.tui.proof.bean.*;
import com.tui.proof.exception.ClientNotFoundException;
import com.tui.proof.exception.OrderNotFoundException;
import com.tui.proof.exception.OrderTimeOutException;
import com.tui.proof.service.PilotesOrderService;
import com.tui.proof.service.PilotesValidationService;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/rest/pilotes")
@NoArgsConstructor
@AllArgsConstructor
public class PilotesOrderController {

    @Autowired
    private PilotesOrderService pilotesOrderService;
    @Autowired
    private PilotesValidationService validationService;

    @PostMapping("/order")
    public ResponseEntity<?> createOrder(@RequestBody CreateOrderRequest request) {
        List<String> msgs = validationService.validateCreateOrderReq(request);
        if(!msgs.isEmpty()){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(msgs);
        }
        Integer orderId = null;
        try {
            orderId = pilotesOrderService.createOrder(request);
        } catch (ClientNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("client not found");
        } catch (Exception e){
            return ResponseEntity.badRequest().build();
        }
        return ResponseEntity.ok(new CreateOrderResponse().setOrderId(orderId));
    }

    @PatchMapping("/order")
    public ResponseEntity<?> updateOrder(@RequestBody UpdateOrderRequest request){
        List<String> msgs = validationService.validateUpdateOrderReq(request);
        if(!msgs.isEmpty()){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(msgs);
        }
        Integer orderId = null;
        try {
            orderId = pilotesOrderService.updateOrder(request);
        } catch (OrderNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("order not found");
        } catch (OrderTimeOutException e) {
            return ResponseEntity.status(HttpStatus.REQUEST_TIMEOUT).body("time is up. Order can no longer be modified");
        }catch (Exception e){
            return ResponseEntity.badRequest().build();
        }
        return ResponseEntity.ok(new CreateOrderResponse().setOrderId(orderId));
    }

    @GetMapping("/orders")
    public ResponseEntity<?> searchOrders(@RequestParam(required = false) String username,
                                          @RequestParam(required = false) String firstName,
                                          @RequestParam(required = false) String lastName,
                                          @RequestParam(required = false) String telephone,
                                          @RequestParam(required = false) String eMail){
        List<OrderBean> orders = null;
        try{
            orders = pilotesOrderService.searchOrders(username, firstName, lastName, telephone, eMail);
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
        return ResponseEntity.ok(new SearchOrdersResponse().setOrders(orders));
    }
}
