package com.world2meet.pruebatecnica.entity;



import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
//@Table
public class SuperHeroes {

    @Id
    private String id;

    @Column(name = "name")
    private String name;

    @Column(name = "descriptionRowers")
    private String descriptionRowers;
}
