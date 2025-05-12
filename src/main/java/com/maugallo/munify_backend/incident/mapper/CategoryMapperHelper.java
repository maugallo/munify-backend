package com.maugallo.munify_backend.incident.mapper;

import com.maugallo.munify_backend.incidentCategory.IncidentCategoryRepository;
import org.springframework.stereotype.Component;

@Component
public class CategoryMapperHelper {

    private final IncidentCategoryRepository incidentCategoryRepository;

    public CategoryMapperHelper(IncidentCategoryRepository incidentCategoryRepository) {
        this.incidentCategoryRepository = incidentCategoryRepository;
    }

}
