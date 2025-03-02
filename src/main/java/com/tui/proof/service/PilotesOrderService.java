package com.tui.proof.service;

import com.tui.proof.bean.CreateOrderRequest;
import com.tui.proof.bean.OrderBean;
import com.tui.proof.bean.UpdateOrderRequest;
import com.tui.proof.exception.ClientNotFoundException;
import com.tui.proof.exception.OrderNotFoundException;
import com.tui.proof.exception.OrderTimeOutException;

import java.util.List;

public interface PilotesOrderService {
    Integer createOrder(CreateOrderRequest req) throws ClientNotFoundException;
    Integer updateOrder(UpdateOrderRequest request) throws OrderNotFoundException, OrderTimeOutException;
    List<OrderBean> searchOrders(String username, String firstName, String lastName, String telephone, String eMail);
}
