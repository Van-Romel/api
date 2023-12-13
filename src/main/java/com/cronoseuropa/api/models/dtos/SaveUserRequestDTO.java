package com.cronoseuropa.api.models.dtos;

import com.cronoseuropa.api.exceptions.CronosBadRequestException;
import com.cronoseuropa.api.models.UserEntity;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;

import java.time.LocalDate;

@Getter
public class SaveUserRequestDTO {

    private String name;
    private String surname;
    @JsonFormat (shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private LocalDate birthDate;

    public UserEntity toEntity() {
        var entity = new UserEntity();
        entity.setName(this.name);
        entity.setSurname(this.surname);
        entity.setBirthDate(this.birthDate);

        return entity;
    }

    public void setName(String name) {
        if (null == name || name.isBlank())
            throw new CronosBadRequestException("Invalid name.", 1001);
        this.name = name;
    }

    public void setSurname(String surname) {
        if (surname.isBlank())
            throw new CronosBadRequestException("Invalid surname.", 1002);
        this.surname = surname;
    }

    public void setBirthDate(LocalDate birthDate) {
        this.birthDate = birthDate;
    }

    public void setBirthDate(String birthDate) {
        try {
            this.birthDate = LocalDate.parse(birthDate);
        } catch (RuntimeException ex) {
            throw new CronosBadRequestException("Invalid date format. Please use 'yyyy-MM-dd'", 1003);
        }
    }

}
