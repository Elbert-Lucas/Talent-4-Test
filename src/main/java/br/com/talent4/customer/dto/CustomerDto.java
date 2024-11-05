package br.com.talent4.customer.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonView;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@JsonIgnoreProperties(ignoreUnknown = true)
@Getter @Setter
@ToString
public class CustomerDto {

    public interface CreateCustomerView {};
    public interface ListCustomerView {};

    @JsonView({ListCustomerView.class})
    private Long id;

    @JsonView({CreateCustomerView.class, ListCustomerView.class})
    @NotEmpty
    @Size(min = 3, message = "{minimumSize.name}")
    private String name;

    @JsonView({CreateCustomerView.class, ListCustomerView.class})
    @NotEmpty
    @Email(message = "{invalid.email}")
    private String email;

    @JsonView({CreateCustomerView.class, ListCustomerView.class})
    @NotNull
    @Valid
    private AddressRequestDto address;

}
