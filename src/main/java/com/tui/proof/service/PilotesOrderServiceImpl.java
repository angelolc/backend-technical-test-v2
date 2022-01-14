package com.tui.proof.service;

import com.tui.proof.bean.CreateOrderRequest;
import com.tui.proof.bean.UpdateOrderRequest;
import com.tui.proof.exception.ClientNotFoundException;
import com.tui.proof.exception.OrderNotFoundException;
import com.tui.proof.exception.OrderTimeOutException;
import com.tui.proof.model.Address;
import com.tui.proof.model.Client;
import com.tui.proof.model.Order;
import com.tui.proof.repository.AddressRepository;
import com.tui.proof.repository.ClientRepository;
import com.tui.proof.repository.OrderRepository;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.Optional;

@Service
public class PilotesOrderServiceImpl implements PilotesOrderService{

    @Autowired
    private ClientRepository clientRepository;
    @Autowired
    private OrderRepository orderRepository;
    @Autowired
    private AddressRepository addressRepository;

    @Value("${pilotes.unit.price}")
    private double pilotesUnitPrice;

    @Value("${max.allowed.minutes.update}")
    private double maxAllowedMinsUpdate;

    @Override
    public Integer createOrder(CreateOrderRequest req) throws ClientNotFoundException {
        Optional<Client> one = clientRepository.findOne(Example.of(new Client().setUsername(req.getClientUsername())));
        if(one.isPresent()){
            Address address = addressRepository.save(new Address()
                    .setStreet(req.getStreet())
                    .setCity(req.getCity())
                    .setCountry(req.getCountry())
                    .setPostcode(req.getPostcode()));
            Order order = new Order()
                    .setClient(one.get())
                    .setQuantity(req.getQuantity())
                    .setCreationDate(LocalDateTime.now())
                    .setDeliveryAddress(address)
                    .setOrderTotal(calcOrderTotal(req.getQuantity()));
            return orderRepository.save(order).getId();
        }
        throw new ClientNotFoundException(req.getClientUsername());
    }

    @Override
    public Integer updateOrder(UpdateOrderRequest request) throws OrderNotFoundException, OrderTimeOutException {

        Optional<Order> one = orderRepository.findById(request.getOrderId());
        if(one.isPresent()){
            Order order = one.get();
            if (isUpdateInTime(order.getCreationDate())){
                return orderRepository.save(updateOrder(order, request)).getId();
            }
            throw new OrderTimeOutException();
        }
        throw new OrderNotFoundException(request.getOrderId().toString());
    }

    private Order updateOrder(Order order, UpdateOrderRequest req) {
        if (req.getQuantity() != null){
            order.setQuantity(req.getQuantity());
            order.setOrderTotal(calcOrderTotal(req.getQuantity()));
        }
        Address deliveryAddress = order.getDeliveryAddress();
        if (StringUtils.isNotBlank(req.getStreet())){
            deliveryAddress.setStreet(req.getStreet());
        }
        if (StringUtils.isNotBlank(req.getCity())){
            deliveryAddress.setCity(req.getCity());
        }
        if (StringUtils.isNotBlank(req.getCountry())){
            deliveryAddress.setCity(req.getCountry());
        }
        if (StringUtils.isNotBlank(req.getPostcode())){
            deliveryAddress.setPostcode(req.getPostcode());
        }
        return order;
    }

    private boolean isUpdateInTime(LocalDateTime creationDate) {
        return creationDate.until(LocalDateTime.now(), ChronoUnit.MINUTES) <= maxAllowedMinsUpdate;
    }

    //round up to 3 decimal places
    private double calcOrderTotal(Integer quantity) {
        return Math.round((quantity * pilotesUnitPrice) * 1000.0) / 1000.0;
    }
}
