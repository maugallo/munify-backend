package com.maugallo.munify_backend.municipality;

import com.maugallo.munify_backend.exception.ResourceNotFoundException;
import com.maugallo.munify_backend.municipality.dto.MunicipalityRequestDTO;
import com.maugallo.munify_backend.municipality.dto.MunicipalityResponseDTO;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MunicipalityService {

    private final MunicipalityRepository municipalityRepository;
    private final MunicipalityMapper municipalityMapper;

    public MunicipalityService(MunicipalityRepository municipalityRepository, MunicipalityMapper municipalityMapper) {
        this.municipalityRepository = municipalityRepository;
        this.municipalityMapper = municipalityMapper;
    }

    public List<MunicipalityResponseDTO> getMunicipalities() {
        var municipalities = municipalityRepository.findAllByIsEnabledTrue();

        return municipalities.stream()
                .map(municipalityMapper::toResponse)
                .toList();
    }

    public MunicipalityResponseDTO getMunicipality(Long id) {
        var municipality = municipalityRepository.findByIdAndIsEnabledTrue(id)
                .orElseThrow(() -> new ResourceNotFoundException("No se encontró un municipio con ese ID"));
        return municipalityMapper.toResponse(municipality);
    }

    public MunicipalityResponseDTO createMunicipality(MunicipalityRequestDTO municipalityRequest) {
        var municipality = municipalityRepository.saveAndFlush(municipalityMapper.toEntity(municipalityRequest));
        return municipalityMapper.toResponse(municipality);
    }

}
