package com.tui.proof.model;

import lombok.Data;
import lombok.experimental.Accessors;

import javax.persistence.*;
import java.time.LocalDateTime;

@Data
@Accessors(chain = true)
@Entity
@Table(name = "pilotes_order")
public class Order {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Integer id;

  @OneToOne
  @JoinColumn(name="address_id", nullable=false)
  private Address deliveryAddress;
  private int quantity;
  private double orderTotal;
  private LocalDateTime creationDate;

  @ManyToOne
  @JoinColumn(name="client_id", nullable=false)
  private Client client;

}
