package com.world2meet.pruebatecnica.entity;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class SuperHeroes {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "descriptionRowers")
    private String descriptionRowers;

    public SuperHeroes(String name, String descriptionRowers) {
        this.name = name;
        this.descriptionRowers = descriptionRowers;
    }
}
