package br.com.talent4.customer.domain;


import jakarta.persistence.*;
import lombok.*;

import java.io.Serializable;

@Entity(name = "TB_ADDRESS")
@AllArgsConstructor @NoArgsConstructor
@Getter @Setter
@ToString
public class Address implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column
    private String state;

    @Column
    private String city;

    @Column
    private String street;
}
