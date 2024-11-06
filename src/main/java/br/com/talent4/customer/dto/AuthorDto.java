package br.com.talent4.customer.dto;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AuthorDto {
    private String name;
    private String email;
}
