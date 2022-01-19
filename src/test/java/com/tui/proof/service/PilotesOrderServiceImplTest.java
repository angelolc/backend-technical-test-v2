package com.tui.proof.service;

import com.tui.proof.bean.AddressBean;
import com.tui.proof.bean.CreateOrderRequest;
import com.tui.proof.bean.OrderBean;
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
import org.assertj.core.api.Assertions;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.convert.ConversionService;
import org.springframework.core.convert.TypeDescriptor;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.test.context.junit4.SpringRunner;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.easymock.EasyMock.*;

public class PilotesOrderServiceImplTest {

    private PilotesOrderService pilotesOrderService;
    private ClientRepository clientRepository;
    private AddressRepository addressRepository;
    private OrderRepository orderRepository;
    private ConversionService conversionService;

    @Before
    public void setUp() {
        clientRepository = createMock(ClientRepository.class);
        addressRepository = createMock(AddressRepository.class);
        orderRepository = createMock(OrderRepository.class);
        conversionService = createMock(ConversionService.class);

        pilotesOrderService = new PilotesOrderServiceImpl()
                .setClientRepository(clientRepository)
                .setAddressRepository(addressRepository)
                .setOrderRepository(orderRepository)
                .setPilotesUnitPrice(1.33)
                .setMaxAllowedMinsUpdate(5)
                .setConversionService(conversionService);
    }

    @Test
    public void createOrder_OK() throws ClientNotFoundException {
        Client client = new Client().setUsername("clientX").setId(1);
        Address address = new Address().setId(1);
        Order order = new Order().setId(1).setClient(client);

        expect(clientRepository.findOne(anyObject())).andReturn(Optional.of(client));
        expect(addressRepository.save(anyObject())).andReturn(address);
        expect(orderRepository.save(anyObject())).andReturn(order);

        replay(clientRepository);
        replay(addressRepository);
        replay(orderRepository);

        Integer orderId = pilotesOrderService.createOrder(new CreateOrderRequest().setClientUsername("clientX").setQuantity(5));

        Assertions.assertThat(orderId).isEqualTo(1);

        verify(clientRepository);
        verify(addressRepository);
        verify(orderRepository);

    }

    @Test(expected = ClientNotFoundException.class)
    public void createOrder_KO() throws ClientNotFoundException {
        expect(clientRepository.findOne(anyObject())).andReturn(Optional.empty());
        replay(clientRepository);
        pilotesOrderService.createOrder(new CreateOrderRequest().setClientUsername("clientY"));
        verify(clientRepository);
    }

    @Test
    public void updateOrder_OK() throws OrderNotFoundException, OrderTimeOutException {
        Integer orderId = 3;
        Order order = new Order().setId(orderId).setCreationDate(LocalDateTime.now()).setQuantity(5);
        Order updatedOrder = new Order().setId(orderId).setCreationDate(LocalDateTime.now()).setQuantity(10);
        expect(orderRepository.findById(orderId)).andReturn(Optional.of(order));
        expect(orderRepository.save(order)).andReturn(updatedOrder);
        replay(orderRepository);

        Integer updateOrderId = pilotesOrderService.updateOrder(new UpdateOrderRequest().setQuantity(10).setOrderId(orderId));
        Assertions.assertThat(updateOrderId).isEqualTo(orderId);
        verify(orderRepository);
    }

    @Test(expected = OrderNotFoundException.class)
    public void updateOrder_KO_NotFound() throws OrderNotFoundException, OrderTimeOutException {
        Integer orderId = 4;
        expect(orderRepository.findById(orderId)).andReturn(Optional.empty());
        replay(orderRepository);
        pilotesOrderService.updateOrder(new UpdateOrderRequest().setQuantity(10).setOrderId(orderId));
        verify(orderRepository);
    }

    @Test(expected = OrderTimeOutException.class)
    public void updateOrder_KO_TimeOut() throws OrderNotFoundException, OrderTimeOutException {
        Integer orderId = 5;
        Order order = new Order().setId(orderId).setCreationDate(LocalDateTime.now().minusMinutes(10)).setQuantity(5);
        expect(orderRepository.findById(orderId)).andReturn(Optional.of(order));
        replay(orderRepository);
        pilotesOrderService.updateOrder(new UpdateOrderRequest().setQuantity(10).setOrderId(orderId));
        verify(orderRepository);
    }

    @Test
   public void searchOrders_Mathes() {
        String firstname = "bo";
        Client client = new Client().setFirstName(firstname);

        ExampleMatcher exampleMatcher = ExampleMatcher.matching()
                .withMatcher("username", ExampleMatcher.GenericPropertyMatchers.contains().ignoreCase())
                .withMatcher("firstName", ExampleMatcher.GenericPropertyMatchers.contains().ignoreCase())
                .withMatcher("lastName", ExampleMatcher.GenericPropertyMatchers.contains().ignoreCase())
                .withMatcher("telephone", ExampleMatcher.GenericPropertyMatchers.contains().ignoreCase())
                .withMatcher("eMail", ExampleMatcher.GenericPropertyMatchers.contains().ignoreCase());

        List<Order> orders = Arrays.asList(
                new Order().setId(1).setQuantity(10).setOrderTotal(50d).setDeliveryAddress(new Address()),
                new Order().setId(2).setQuantity(15).setOrderTotal(60d).setDeliveryAddress(new Address())
        );

        List<OrderBean> expectedOrderBeans = Arrays.asList(
                new OrderBean().setId(1).setQuantity(10).setOrderTotal(50d).setDeliveryAddress(new AddressBean()),
                new OrderBean().setId(2).setQuantity(15).setOrderTotal(60d).setDeliveryAddress(new AddressBean())
        );

        List<Client> clients = Arrays.asList(new Client().setFirstName("bob").setOrders(orders));

        TypeDescriptor orderType = TypeDescriptor.collection(List.class, TypeDescriptor.valueOf(Order.class));
        TypeDescriptor orderBeanType = TypeDescriptor.collection(List.class, TypeDescriptor.valueOf(OrderBean.class));

        expect(clientRepository.findAll(Example.of(client, exampleMatcher))).andReturn(clients);
        expect(conversionService.convert(orders, orderType, orderBeanType)).andReturn(expectedOrderBeans);
        replay(clientRepository);
        replay(conversionService);

        List<OrderBean> orderBeans = pilotesOrderService.searchOrders(null, firstname, null, null, null);
        Assert.assertNotNull(orderBeans);
        Assert.assertEquals(2, orderBeans.size());
        Assert.assertEquals(1, orderBeans.get(0).getId().intValue());
        Assert.assertEquals(2, orderBeans.get(1).getId().intValue());

        verify(clientRepository);
        verify(conversionService);
    }

    @Test
    public void searchOrders_NoMathes() {
        String firstname = "foo";
        Client client = new Client().setFirstName(firstname);

        ExampleMatcher exampleMatcher = ExampleMatcher.matching()
                .withMatcher("username", ExampleMatcher.GenericPropertyMatchers.contains().ignoreCase())
                .withMatcher("firstName", ExampleMatcher.GenericPropertyMatchers.contains().ignoreCase())
                .withMatcher("lastName", ExampleMatcher.GenericPropertyMatchers.contains().ignoreCase())
                .withMatcher("telephone", ExampleMatcher.GenericPropertyMatchers.contains().ignoreCase())
                .withMatcher("eMail", ExampleMatcher.GenericPropertyMatchers.contains().ignoreCase());

        TypeDescriptor orderType = TypeDescriptor.collection(List.class, TypeDescriptor.valueOf(Order.class));
        TypeDescriptor orderBeanType = TypeDescriptor.collection(List.class, TypeDescriptor.valueOf(OrderBean.class));

        expect(clientRepository.findAll(Example.of(client, exampleMatcher))).andReturn(Collections.emptyList());
        expect(conversionService.convert(Collections.emptyList(), orderType, orderBeanType)).andReturn(Collections.emptyList());
        replay(clientRepository);
        replay(conversionService);

        List<OrderBean> orderBeans = pilotesOrderService.searchOrders(null, firstname, null, null, null);
        Assert.assertNotNull(orderBeans);
        Assert.assertEquals(0, orderBeans.size());

        verify(clientRepository);
        verify(conversionService);
    }
}