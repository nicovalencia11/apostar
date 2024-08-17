package com.apostar.apostarbackendresultados.entities;

import jakarta.persistence.*;
import lombok.*;

import java.io.Serializable;
import java.time.LocalTime;


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

    @Lob
    @Column(nullable = true)
    private String background;

    @Column(nullable = false)
    private LocalTime time;

    @Column(nullable = false)
    private String emails;

    @Column(nullable = false)
    private String endPoint;
}
