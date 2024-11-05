package br.com.talent4.customer.domain;

import br.com.talent4.shared.domain.TraceEntity;
import jakarta.persistence.*;
import lombok.*;


@Entity(name = "TB_CUSTOMER_AUD")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class CustomerAud extends TraceEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "customer_id", referencedColumnName = "id")
    private Customer customer;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false, unique = true)
    private String email;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "address_id", referencedColumnName = "id")
    private AddressAud addressAud;

    @ManyToOne
    @JoinColumn(referencedColumnName = "id")
    private Audition changeType;

}
