package br.com.talent4.shared.domain;

import jakarta.persistence.Column;
import jakarta.persistence.MappedSuperclass;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;
import org.hibernate.annotations.CreationTimestamp;

import java.io.Serializable;
import java.time.LocalDateTime;

@MappedSuperclass
@SuperBuilder
@AllArgsConstructor @NoArgsConstructor
@Getter @Setter
public class TraceEntity implements Serializable {
    @Column(updatable = false)
    @CreationTimestamp
    private LocalDateTime createdAt;
}
