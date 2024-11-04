package br.com.talent4.domain.customer;


import br.com.talent4.domain.shared.TraceEntity;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
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

    @OneToOne(mappedBy = "address")
    @JsonIgnore
    @JsonManagedReference
    private Customer customer;

    @Column
    private String state;

    @Column
    private String city;

    @Column
    private String address;
}
