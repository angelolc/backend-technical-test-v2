package com.tui.proof.bean;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.io.Serializable;

@Data
@Accessors(chain = true)
@NoArgsConstructor
@AllArgsConstructor
public class SearchOrdersRequest implements Serializable {

    private String username;
    private String firstName;
    private String lastName;
    private String telephone;
    private String eMail;
}
