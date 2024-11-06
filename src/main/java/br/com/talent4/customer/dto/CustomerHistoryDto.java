package br.com.talent4.customer.dto;

import br.com.talent4.customer.enums.CRUD;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;

import java.time.LocalDateTime;

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

    @JsonFormat(pattern="dd/MM/yyyy HH:mm:ss")
    private LocalDateTime date;
}
