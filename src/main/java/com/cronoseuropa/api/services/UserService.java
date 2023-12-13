package com.cronoseuropa.api.services;

import com.cronoseuropa.api.exceptions.CronosInternalErrorException;
import com.cronoseuropa.api.exceptions.UserNotFoundException;
import com.cronoseuropa.api.models.UserEntity;
import com.cronoseuropa.api.models.dtos.SaveUserRequestDTO;
import com.cronoseuropa.api.models.dtos.UserResponseDTO;
import com.cronoseuropa.api.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    public UserResponseDTO create(SaveUserRequestDTO dto) {
        try {
            return new UserResponseDTO(userRepository.save(dto.toEntity()));

        } catch (RuntimeException ex) {
            throw new CronosInternalErrorException("Internal error", 5010);
        }
    }

    public Collection<UserResponseDTO> findAll() {
        var userEntities = userRepository.findAll();

        return userEntities.stream()
                .map(UserResponseDTO::new)
                .collect(Collectors.toSet());
    }

    public UserResponseDTO findOneById(Long userId) {
        var userEntity = userRepository.findById(userId);

        return new UserResponseDTO(userEntity
                .orElseThrow(UserNotFoundException::new));
    }

    public UserResponseDTO alterById(Long userId, SaveUserRequestDTO reqDto) {

        var entity = userRepository.findById(userId)
                .orElseThrow(UserNotFoundException::new);

        if (null != reqDto.getName()) entity.setName(reqDto.getName());
        if (null != reqDto.getSurname()) entity.setSurname(reqDto.getSurname());
        if (null != reqDto.getBirthDate()) entity.setBirthDate(reqDto.getBirthDate());

        userRepository.save(entity);

        return new UserResponseDTO(entity);
    }

    public UserResponseDTO alterFieldsById(Long userId, String name, String surname, String birthDate) {

        var dto = new SaveUserRequestDTO();

        if (null != name) dto.setName(name);
        if (null != surname) dto.setSurname(surname);
        if (null != birthDate) dto.setBirthDate(birthDate);


        return alterById(userId, dto);
    }

    public void deleteById(Long userId) {
        UserEntity userEntity = userRepository
                .findById(userId)
                .orElseThrow(UserNotFoundException::new);
        userRepository.delete(userEntity);
    }
}
