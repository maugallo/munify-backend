package com.maugallo.munify_backend.incidentCategory.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record IncidentCategoryRequestDTO(
        @NotBlank(message = "name es requerido")
        @Size(min = 3, max = 40, message = "name fuera de rango")
        String name
) { }
