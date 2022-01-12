package com.tui.proof.model;

import lombok.Data;
import lombok.experimental.Accessors;

import javax.persistence.*;

@Data
@Accessors(chain = true)
@Entity
@Table(name = "address")
public class Address {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Integer id;
  private String street;
  private String postcode;
  private String city;
  private String country;

  @OneToOne(mappedBy = "deliveryAddress")
  private Order order;
}
