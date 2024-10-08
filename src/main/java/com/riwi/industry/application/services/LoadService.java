package com.riwi.industry.application.services;

import com.riwi.industry.application.interfaces.ILoadService;
import com.riwi.industry.domain.models.Load;
import com.riwi.industry.domain.models.Pallet;
import com.riwi.industry.domain.persistence.LoadRepository;
import com.riwi.industry.domain.persistence.PalletRepository;
import com.riwi.industry.infrastructure.dto.loadDTOs.LoadRequestDTO;
import com.riwi.industry.infrastructure.dto.loadDTOs.LoadResponseDTO;
import com.riwi.industry.infrastructure.dto.palletDTOs.PalletLoadResponseDTO;
import com.riwi.industry.infrastructure.dto.palletDTOs.PalletResponseDTO;
import jakarta.persistence.EntityExistsException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class LoadService implements ILoadService {

    @Autowired
    private LoadRepository loadRepository;

    @Autowired
    private PalletRepository palletRepository;

    @Override
    public LoadResponseDTO create(LoadRequestDTO loadRequestDTO) {
        Load load = loadRepository.findByCode(loadRequestDTO.getCode());
        if (load != null) {
            throw new EntityExistsException("Load already exists");
        }

        Pallet pallet = palletRepository.findById(loadRequestDTO.getPalletId())
                .orElseThrow(() -> new IllegalArgumentException("Pallet not found"));

        Load newLoad = Load.builder()
                .code(loadRequestDTO.getCode())
                .weight(loadRequestDTO.getWeight())
                .dimensions(loadRequestDTO.getDimensions())
                .status(loadRequestDTO.getStatus())
                .pallet(pallet)
                .build();

        loadRepository.save(newLoad);

        // Verificar pallet no sea null antes de acceder a sus propiedades
        PalletResponseDTO palletResponseDTO = (pallet != null) ?
                PalletResponseDTO.builder()
                        .status(pallet.getStatus())
                        .ubication(pallet.getUbication())
                        .type(pallet.getType())
                        .code(pallet.getCode())
                        .maxCapacity(pallet.getMaxCapacity())
                        .carrier_id(pallet.getCarrier() != null ? pallet.getCarrier().getId() : null)
                        .loads_ids(pallet.getLoads().stream().map(
                                l -> PalletLoadResponseDTO.builder()
                                        .weight(l.getWeight())
                                        .dimensions(l.getDimensions())
                                        .status(l.getStatus())
                                        .build()
                        ).collect(Collectors.toList()))
                        .build() : null;

        return LoadResponseDTO.builder()
                .id(newLoad.getId())
                .weight(newLoad.getWeight())
                .dimensions(newLoad.getDimensions())
                .status(newLoad.getStatus())
                .palletId(palletResponseDTO)
                .build();
    }


    @Override
    public LoadResponseDTO update(LoadRequestDTO loadRequestDTO, Long loadId) {
        // Buscar el Load por ID
        Load load = loadRepository.findById(loadId)
                .orElseThrow(() -> new IllegalArgumentException("Load not found"));

        // Verificar si el pallet existe
        Pallet pallet = palletRepository.findById(loadRequestDTO.getPalletId())
                .orElseThrow(() -> new IllegalArgumentException("Pallet not found"));

        // Actualizar los campos del Load
        load.setCode(loadRequestDTO.getCode());
        load.setWeight(loadRequestDTO.getWeight());
        load.setDimensions(loadRequestDTO.getDimensions());
        load.setStatus(loadRequestDTO.getStatus());
        load.setPallet(pallet); // Asegúrate de que el pallet no sea null

        // Guardar la carga actualizada
        loadRepository.save(load);

        // Construir la respuesta del pallet
        PalletResponseDTO palletResponseDTO = PalletResponseDTO.builder()
                .status(pallet.getStatus())
                .ubication(pallet.getUbication())
                .type(pallet.getType())
                .code(pallet.getCode())
                .maxCapacity(pallet.getMaxCapacity())
                .carrier_id(pallet.getCarrier() != null ? pallet.getCarrier().getId() : null)
                .loads_ids(pallet.getLoads().stream().map(
                        l -> PalletLoadResponseDTO.builder()
                                .weight(l.getWeight())
                                .dimensions(l.getDimensions())
                                .status(l.getStatus())
                                .build()
                ).collect(Collectors.toList()))
                .build();

        // Construir y devolver la respuesta
        return LoadResponseDTO.builder()
                .id(load.getId())
                .weight(load.getWeight())
                .dimensions(load.getDimensions())
                .status(load.getStatus())
                .palletId(palletResponseDTO)
                .build();
    }

    @Override
    public LoadResponseDTO getById(Long aLong) {
        Load load = loadRepository.findById(aLong).orElseThrow(() -> new IllegalArgumentException("Load not found"));

        PalletResponseDTO palletResponseDTO = (load.getPallet() != null) ?
                PalletResponseDTO.builder()
                        .status(load.getPallet().getStatus())
                        .ubication(load.getPallet().getUbication())
                        .type(load.getPallet().getType())
                        .code(load.getPallet().getCode())
                        .maxCapacity(load.getPallet().getMaxCapacity())
                        .carrier_id(load.getPallet().getCarrier() != null ? load.getPallet().getCarrier().getId() : null)
                        .loads_ids(load.getPallet().getLoads().stream().map(
                                l -> PalletLoadResponseDTO.builder()
                                        .weight(l.getWeight())
                                        .dimensions(l.getDimensions())
                                        .status(l.getStatus())
                                        .build()
                        ).collect(Collectors.toList()))
                        .build() : null;

        return LoadResponseDTO.builder()
                .id(load.getId())
                .weight(load.getWeight())
                .dimensions(load.getDimensions())
                .status(load.getStatus())
                .palletId(palletResponseDTO)
                .build();
    }


    @Override
    public List<LoadResponseDTO> getAll() {
        List<Load> loads = loadRepository.findAll();

        if (loads.isEmpty()) {
            // Opcional: Manejar el caso de lista vacía
            return new ArrayList<>(); // O lanzar una excepción si prefieres
        }

        return loads.stream()
                .map(load -> {
                    // Maneja el caso donde el pallet podría ser null
                    PalletResponseDTO palletResponseDTO = (load.getPallet() != null) ?
                            PalletResponseDTO.builder()
                                    .status(load.getPallet().getStatus())
                                    .ubication(load.getPallet().getUbication())
                                    .type(load.getPallet().getType())
                                    .code(load.getPallet().getCode())
                                    .maxCapacity(load.getPallet().getMaxCapacity())
                                    .carrier_id(load.getPallet().getCarrier() != null ? load.getPallet().getCarrier().getId() : null)
                                    .loads_ids(load.getPallet().getLoads().stream().map(
                                            l -> PalletLoadResponseDTO.builder()
                                                    .weight(l.getWeight())
                                                    .dimensions(l.getDimensions())
                                                    .status(l.getStatus())
                                                    .build()
                                    ).collect(Collectors.toList()))
                                    .build() : null;

                    return LoadResponseDTO.builder()
                            .id(load.getId())
                            .weight(load.getWeight())
                            .dimensions(load.getDimensions())
                            .status(load.getStatus())
                            .palletId(palletResponseDTO)
                            .build();
                })
                .collect(Collectors.toList());
    }




    @Override
    public String delete(Long aLong) {
        Load load = loadRepository.findById(aLong).orElseThrow(() -> new IllegalArgumentException("Load not found"));
        if (load == null) {
            throw new EntityExistsException("Load not found");
        }
        loadRepository.delete(load);
        return "Load deleted";
    }
}
