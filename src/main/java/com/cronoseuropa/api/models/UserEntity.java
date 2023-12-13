package com.cronoseuropa.api.models;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDate;

@Entity(name = "_user")
@Setter @Getter
public class UserEntity {

    @Id
    @Column(name = "usr_id")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @Column(name = "usr_name")
    private String name;
    @Column(name = "usr_surname")
    private String surname;
    @Column(name = "usr_birth_date")
    private LocalDate birthDate;

    public UserEntity() {

    }
}
