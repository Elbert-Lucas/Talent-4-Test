package br.com.talent4.customer.domain;

import br.com.talent4.customer.enums.CRUD;
import jakarta.persistence.*;
import lombok.*;

import java.io.Serializable;

@Entity(name = "TB_AUDITION")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class Audition implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column
    @Enumerated(value = EnumType.STRING)
    private CRUD description;
}
