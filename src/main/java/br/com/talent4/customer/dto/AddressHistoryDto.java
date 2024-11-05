package br.com.talent4.customer.dto;

import lombok.*;

@Getter @Setter
@AllArgsConstructor @NoArgsConstructor
@Builder
public class AddressHistoryDto {

    private Long addressId;

    private String state;

    private String city;

    private String street;

}
