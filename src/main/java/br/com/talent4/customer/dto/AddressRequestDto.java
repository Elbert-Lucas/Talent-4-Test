package br.com.talent4.customer.dto;

import br.com.talent4.shared.annotation.State;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonView;
import jakarta.validation.constraints.NotEmpty;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@JsonIgnoreProperties(ignoreUnknown = true)
@Getter @Setter
@ToString
public class AddressRequestDto {

    @JsonView({CustomerDto.CreateCustomerView.class})
    @State(message = "{invalid.state}")
    private String state;

    @JsonView({CustomerDto.CreateCustomerView.class})
    @NotEmpty(message = "{invalid.city}")
    private String city;

    @JsonView({CustomerDto.CreateCustomerView.class})
    @NotEmpty(message = "{invalid.street}")
    private String street;

}
