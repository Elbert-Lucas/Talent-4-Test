package br.com.talent4.customer.domain;


import br.com.talent4.shared.domain.TraceEntity;
import jakarta.persistence.*;
import lombok.*;

@Entity(name = "TB_ADDRESS")
@AllArgsConstructor @NoArgsConstructor
@Getter @Setter
@ToString
public class Address extends TraceEntity {

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
