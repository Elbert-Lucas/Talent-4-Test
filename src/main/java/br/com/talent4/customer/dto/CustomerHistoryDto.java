package br.com.talent4.customer.dto;

import br.com.talent4.customer.enums.CRUD;
import lombok.*;

import java.sql.Timestamp;

@Getter @Setter
@AllArgsConstructor @NoArgsConstructor
@Builder
public class CustomerHistoryDto {

    private Long id;

    private Long customerId;

    private String name;

    private String email;

    private AddressHistoryDto addressHistory;

    private CRUD changeType;

    private Timestamp date;
}
