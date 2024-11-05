package br.com.talent4.customer.domain;

import jakarta.persistence.*;
import lombok.*;

import java.io.Serializable;

@Entity(name = "TB_ADDRESS_AUD")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class AddressAud implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "address_id", referencedColumnName = "id")
    private Address address;

    @Column
    private String state;

    @Column
    private String city;

    @Column
    private String street;

    @ManyToOne
    @JoinColumn(referencedColumnName = "id")
    private Audition changeType;
}
