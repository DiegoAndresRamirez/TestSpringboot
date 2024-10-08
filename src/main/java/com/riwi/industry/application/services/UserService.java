package com.riwi.industry.application.services;

import com.riwi.industry.application.interfaces.IUserService;
import com.riwi.industry.domain.models.Load;
import com.riwi.industry.domain.models.Pallet;
import com.riwi.industry.domain.models.User;
import com.riwi.industry.domain.persistence.PalletRepository;
import com.riwi.industry.domain.persistence.UserRepository;
import com.riwi.industry.infrastructure.dto.palletDTOs.PalletLoadResponseDTO;
import com.riwi.industry.infrastructure.dto.palletDTOs.PalletResponseDTO;
import com.riwi.industry.infrastructure.dto.userDTOs.UserRequestDTO;
import com.riwi.industry.infrastructure.dto.userDTOs.UserResponseDTO;
import jakarta.persistence.EntityExistsException;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService implements IUserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PalletRepository palletRepository;

    @Override
    public UserResponseDTO create(UserRequestDTO userRequestDTO) {
        User user = userRepository.findByUsername(userRequestDTO.getUsername());
        if (user != null) {
            throw new EntityExistsException("User already exists");
        }

        Pallet pallet = palletRepository.findById(userRequestDTO.getPallet_id()).orElseThrow(() -> new IllegalArgumentException("Pallet not found"));

        List<PalletLoadResponseDTO> palletLoadResponseDTOS = pallet.getLoads().stream().map(
                load -> PalletLoadResponseDTO.builder()
                        .weight(load.getWeight())
                        .dimensions(load.getDimensions())
                        .status(load.getStatus())
                        .build()
        ).toList();

        User newUser = User.builder()
                .username(userRequestDTO.getUsername())
                .name(userRequestDTO.getName())
                .lastname(userRequestDTO.getLastname())
                .email(userRequestDTO.getEmail())
                .password(userRequestDTO.getPassword())
                .role(userRequestDTO.getRole())
                .pallet(pallet)
                .build();

        userRepository.save(newUser);


        PalletResponseDTO palletResponseDTO = PalletResponseDTO.builder()
                .status(pallet.getStatus())
                .ubication(pallet.getUbication())
                .type(pallet.getType())
                .code(pallet.getCode())
                .maxCapacity(pallet.getMaxCapacity())
                .carrier_id(pallet.getCarrier().getId())
                .loads_ids(palletLoadResponseDTOS)
                .build();

        return  UserResponseDTO.builder()
                .username(newUser.getUsername())
                .name(newUser.getName())
                .lastname(newUser.getLastname())
                .email(newUser.getEmail())
                .password(newUser.getPassword())
                .role(newUser.getRole())
                .pallet_id(palletResponseDTO)
                .build();
    }

    @Override
    public UserResponseDTO update(UserRequestDTO userRequestDTO, Long aLong) {
        User user = userRepository.findById(aLong).orElseThrow(() -> new IllegalArgumentException("User not found"));
        if (user == null) {
            throw new EntityExistsException("User not found");
        }
        user.setUsername(userRequestDTO.getUsername());
        user.setName(userRequestDTO.getName());
        user.setLastname(userRequestDTO.getLastname());
        user.setEmail(userRequestDTO.getEmail());
        user.setPassword(userRequestDTO.getPassword());
        user.setRole(userRequestDTO.getRole());
        user.setPallet(palletRepository.findById(userRequestDTO.getPallet_id()).orElseThrow(() -> new IllegalArgumentException("Pallet not found")));
        userRepository.save(user);

        List<PalletLoadResponseDTO> palletLoadResponseDTOS = palletRepository.findById(userRequestDTO.getPallet_id()).get().getLoads().stream().map(
                load -> PalletLoadResponseDTO.builder()
                        .weight(load.getWeight())
                        .dimensions(load.getDimensions())
                        .status(load.getStatus())
                        .build()
        ).toList();

        return UserResponseDTO.builder()
                .username(user.getUsername())
                .name(user.getName())
                .lastname(user.getLastname())
                .email(user.getEmail())
                .password(user.getPassword())
                .role(user.getRole())
                .pallet_id(PalletResponseDTO.builder()
                        .status(user.getPallet().getStatus())
                        .ubication(user.getPallet().getUbication())
                        .type(user.getPallet().getType())
                        .code(user.getPallet().getCode())
                        .maxCapacity(user.getPallet().getMaxCapacity())
                        .carrier_id(user.getPallet().getCarrier().getId())
                        .loads_ids(palletLoadResponseDTOS)
                        .build())
                .build();

    }

    @Override
    public UserResponseDTO getById(Long id) {
        // Buscar al usuario por ID, lanzando excepción si no se encuentra
        User user = userRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("User not found"));

        // Obtener el pallet del usuario
        Pallet pallet = user.getPallet();
        PalletResponseDTO palletResponseDTO = null;

        // Si el pallet no es nulo, construimos el DTO
        if (pallet != null) {
            List<PalletLoadResponseDTO> palletLoadResponseDTOS = pallet.getLoads().stream()
                    .map(load -> PalletLoadResponseDTO.builder()
                            .weight(load.getWeight())
                            .dimensions(load.getDimensions())
                            .status(load.getStatus())
                            .build())
                    .toList();

            palletResponseDTO = PalletResponseDTO.builder()
                    .status(pallet.getStatus())
                    .ubication(pallet.getUbication())
                    .type(pallet.getType())
                    .code(pallet.getCode())
                    .maxCapacity(pallet.getMaxCapacity())
                    .carrier_id(pallet.getCarrier() != null ? pallet.getCarrier().getId() : null) // Manejo de null
                    .loads_ids(palletLoadResponseDTOS)
                    .build();
        }

        // Construir y retornar el DTO de usuario
        return UserResponseDTO.builder()
                .username(user.getUsername())
                .name(user.getName())
                .lastname(user.getLastname())
                .email(user.getEmail())
                .password(user.getPassword())
                .role(user.getRole())
                .pallet_id(palletResponseDTO) // Retorna null si no hay pallet
                .build();
    }


    @Override
    public List<UserResponseDTO> getAll() {
        List<User> users = userRepository.findAll();

        if (users.isEmpty()) {
            throw new EntityNotFoundException("No users found");
        }


        return users.stream().map(user -> {
            Pallet pallet = user.getPallet(); // Asignar el pallet a una variable
            PalletResponseDTO palletResponseDTO = null;

            if (pallet != null) {
                List<PalletLoadResponseDTO> palletLoadResponseDTOS = pallet.getLoads().stream().map(load ->
                        PalletLoadResponseDTO.builder()
                                .weight(load.getWeight())
                                .dimensions(load.getDimensions())
                                .status(load.getStatus())
                                .build()
                ).toList();

                palletResponseDTO = PalletResponseDTO.builder()
                        .status(pallet.getStatus())
                        .ubication(pallet.getUbication())
                        .type(pallet.getType())
                        .code(pallet.getCode())
                        .maxCapacity(pallet.getMaxCapacity())
                        .carrier_id(pallet.getCarrier() != null ? pallet.getCarrier().getId() : null) // Manejo de null
                        .loads_ids(palletLoadResponseDTOS)
                        .build();
            }

            return UserResponseDTO.builder()
                    .username(user.getUsername())
                    .name(user.getName())
                    .lastname(user.getLastname())
                    .email(user.getEmail())
                    .password(user.getPassword())
                    .role(user.getRole())
                    .pallet_id(palletResponseDTO)
                    .build();
        }).toList();
    }


    @Override
    public String delete(Long aLong) {
        User user = userRepository.findById(aLong).orElseThrow(() -> new IllegalArgumentException("User not found"));
        if (user == null) {
            throw new EntityExistsException("User not found");
        }
        userRepository.deleteById(aLong);
        return "User deleted";
    }
}
