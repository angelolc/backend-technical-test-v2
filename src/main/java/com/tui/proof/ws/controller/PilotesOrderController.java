package com.tui.proof.ws.controller;

import com.tui.proof.bean.*;
import com.tui.proof.exception.ClientNotFoundException;
import com.tui.proof.exception.OrderNotFoundException;
import com.tui.proof.exception.OrderTimeOutException;
import com.tui.proof.service.PilotesOrderService;
import com.tui.proof.service.PilotesValidationService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/pilotes")
@NoArgsConstructor
@AllArgsConstructor
public class PilotesOrderController {

    @Autowired
    private PilotesOrderService pilotesOrderService;
    @Autowired
    private PilotesValidationService validationService;

    @PostMapping("/order")
    @Operation(summary = "Create a new Pilotes Order",
            description = "Create a new Pilotes Order given a CreateOrderRequest")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Order created, its id is returned",
                    content = { @Content(mediaType = "application/json", schema = @Schema(implementation = Integer.class)) }),
            @ApiResponse(responseCode = "400", description = "Invalid request", content = @Content),
            @ApiResponse(responseCode = "404", description = "Client not found", content = @Content),
            @ApiResponse(responseCode = "500", description = "Internal server error", content = @Content)})
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
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
        return ResponseEntity.ok(new CreateOrderResponse().setOrderId(orderId));
    }

    @PatchMapping("/order")
    @Operation(summary = "Updates a Pilotes Order",
            description = "Updates an existing Pilotes Order with an UpdateOrderRequest")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Order updated, its id is returned",
                    content = { @Content(mediaType = "application/json", schema = @Schema(implementation = Integer.class)) }),
            @ApiResponse(responseCode = "400", description = "Invalid request", content = @Content),
            @ApiResponse(responseCode = "404", description = "Order not found", content = @Content),
            @ApiResponse(responseCode = "408", description = "Request timeout", content = @Content),
            @ApiResponse(responseCode = "500", description = "Internal server error", content = @Content)})
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
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
        return ResponseEntity.ok(new CreateOrderResponse().setOrderId(orderId));
    }

    @GetMapping("/orders")
    @Operation(summary = "Search for Pilotes Orders",
            description = "Search all Pilotes Orders whose client matches the given parameters. Authentication required.",
    security =@SecurityRequirement(name = "basicAuth") )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Orders retrieved",
                    content = { @Content(mediaType = "application/json", schema = @Schema(implementation = SearchOrdersResponse.class)) }),
            @ApiResponse(responseCode = "500", description = "Internal server error", content = @Content)})
    @SecurityRequirement(name = "Basic Authentication")
    public ResponseEntity<?> searchOrders(@RequestParam(required = false) String username,
                                          @RequestParam(required = false) String firstName,
                                          @RequestParam(required = false) String lastName,
                                          @RequestParam(required = false) String telephone,
                                          @RequestParam(required = false) String eMail){
        List<OrderBean> orders = null;
        try{
            orders = pilotesOrderService.searchOrders(username, firstName, lastName, telephone, eMail);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
        return ResponseEntity.ok(new SearchOrdersResponse().setOrders(orders));
    }
}
