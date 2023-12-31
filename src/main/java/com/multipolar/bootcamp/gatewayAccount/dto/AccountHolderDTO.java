package com.multipolar.bootcamp.gatewayAccount.dto;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
@EqualsAndHashCode
public class AccountHolderDTO implements Serializable {
    private String nik;
    private String name;
    private String address;
}
