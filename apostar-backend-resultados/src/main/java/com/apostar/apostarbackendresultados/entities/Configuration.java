package com.apostar.apostarbackendresultados.entities;

import jakarta.persistence.*;
import lombok.*;

import java.io.Serializable;
import java.time.LocalTime;
import java.util.List;


@Entity
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class Configuration implements Serializable {

    @Id
    @EqualsAndHashCode.Include
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer code;

    @Column(nullable = false)
    private byte[] background;

    @Column(nullable = false)
    private LocalTime time;

    @ElementCollection(fetch = FetchType.LAZY)
    @CollectionTable(name = "emails", joinColumns = @JoinColumn(name = "code"))
    @Column(nullable = false)
    private List<String> emails;

    @Column(nullable = false)
    private String endPoint;
}
