package br.com.talent4.customer.domain;

import br.com.talent4.shared.domain.TraceEntity;
import br.com.talent4.user.domain.User;
import jakarta.persistence.*;
import lombok.*;

@Entity(name = "TB_CUSTOMER")
@AllArgsConstructor @NoArgsConstructor
@Getter @Setter
@ToString
public class Customer extends TraceEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false, unique = true)
    private String email;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "address_id", referencedColumnName = "id")
    private Address address;

    @ManyToOne
    @JoinColumn(referencedColumnName = "id")
    private User lastAuthor;
}
