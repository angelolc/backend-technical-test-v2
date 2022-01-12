package com.tui.proof.model;

import lombok.Data;
import lombok.experimental.Accessors;

import javax.persistence.*;
import java.util.List;

@Data
@Accessors(chain = true)
@Entity
@Table(name = "client")
public class Client {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Integer id;
  private String username;
  private String firstName;
  private String lastName;
  private String telephone;
  private String eMail;

  @OneToMany(mappedBy="client")
  private List<Order> orders;

}
