package com.tui.proof.ws.controller;

import com.tui.proof.bean.CreateOrderRequest;
import com.tui.proof.bean.CreateOrderResponse;
import com.tui.proof.bean.UpdateOrderRequest;
import com.tui.proof.exception.ClientNotFoundException;
import com.tui.proof.exception.OrderNotFoundException;
import com.tui.proof.exception.OrderTimeOutException;
import com.tui.proof.service.PilotesOrderService;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/rest/pilotes")
@NoArgsConstructor
@AllArgsConstructor
public class PilotesOrderController {

    @Autowired
    private PilotesOrderService pilotesOrderService;

    @PostMapping("/order")
    public ResponseEntity<?> createOrder(@RequestBody CreateOrderRequest request) {
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
}
