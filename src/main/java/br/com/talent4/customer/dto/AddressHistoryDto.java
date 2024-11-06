package br.com.talent4.customer.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

@Getter @Setter
@AllArgsConstructor @NoArgsConstructor
@Builder
public class AddressHistoryDto {

    @JsonProperty("address_id")
    private Long addressId;

    private String state;

    private String city;

    private String street;

}
