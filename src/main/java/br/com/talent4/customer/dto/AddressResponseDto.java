package br.com.talent4.customer.dto;

import jakarta.validation.constraints.NotEmpty;

import java.util.Date;

public class AddressResponseDto extends AddressRequestDto{
    private Integer id;
    private Date date;
}
