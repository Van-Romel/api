package com.cronoseuropa.api.models.dtos;

import com.cronoseuropa.api.models.UserEntity;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@AllArgsConstructor
public class UserResponseDTO {

    private String name;
    private String surname;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private LocalDate birthDate;

    public UserResponseDTO(UserEntity userEntity) {
        this.name = userEntity.getName();
        this.surname = userEntity.getSurname();
        this.birthDate = userEntity.getBirthDate();
    }
}
