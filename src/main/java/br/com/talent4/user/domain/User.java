package br.com.talent4.user.domain;

import br.com.talent4.shared.domain.TraceEntity;
import jakarta.persistence.*;
import lombok.*;


@Entity(name = "TB_USER")
@AllArgsConstructor @NoArgsConstructor
@Getter @Setter
@ToString
public class User extends TraceEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private String name;

    @Column(unique = true)
    private String email;

    @Column
    private String password;
}
