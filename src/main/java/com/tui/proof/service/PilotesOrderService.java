package com.tui.proof.service;

import com.tui.proof.bean.CreateOrderRequest;
import com.tui.proof.bean.UpdateOrderRequest;
import com.tui.proof.exception.ClientNotFoundException;
import com.tui.proof.exception.OrderNotFoundException;
import com.tui.proof.exception.OrderTimeOutException;

public interface PilotesOrderService {
    Integer createOrder(CreateOrderRequest req) throws ClientNotFoundException;
    Integer updateOrder(UpdateOrderRequest request) throws OrderNotFoundException, OrderTimeOutException;
}
