package com.maugallo.munify_backend.incidentCategory;

import com.maugallo.munify_backend.incidentCategory.dto.IncidentCategoryRequestDTO;
import com.maugallo.munify_backend.incidentCategory.dto.IncidentCategoryResponseDTO;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class IncidentCategoryService {

    private final IncidentCategoryRepository incidentCategoryRepository;
    private final IncidentCategoryMapper incidentCategoryMapper;

    public IncidentCategoryService(IncidentCategoryRepository incidentCategoryRepository, IncidentCategoryMapper incidentCategoryMapper) {
        this.incidentCategoryRepository = incidentCategoryRepository;
        this.incidentCategoryMapper = incidentCategoryMapper;
    }

    public List<IncidentCategoryResponseDTO> getCategories() {
        var categories = incidentCategoryRepository.findAll();

        return categories.stream()
                .map(incidentCategoryMapper::toDto)
                .toList();
    }

    public IncidentCategoryResponseDTO createCategory(IncidentCategoryRequestDTO categoryRequest) {
        var category = incidentCategoryRepository.saveAndFlush(incidentCategoryMapper.toEntity(categoryRequest));
        return incidentCategoryMapper.toDto(category);
    }

}
