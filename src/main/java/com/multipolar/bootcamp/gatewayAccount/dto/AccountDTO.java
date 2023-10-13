package com.multipolar.bootcamp.gatewayAccount.dto;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;

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
public class AccountDTO implements Serializable {
    private String id;
    private String accountNumber;
    private AccountTypeDTO accountType;
    private AccountStatusDTO accountStatus;
    private AccountHolderDTO accountHolder;
    private Double balance;
    private LocalDateTime openingDate = LocalDateTime.now();
    private LocalDate closingDate;
}
