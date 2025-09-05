package com.maugallo.munify_backend.incident.dto;

import com.maugallo.munify_backend.incidentMedia.dto.IncidentMediaRequestDTO;
import jakarta.validation.Valid;
import jakarta.validation.constraints.*;

import java.util.List;

public record IncidentRequestDTO(
    @NotBlank(message = "title es requerido")
    @Size(min = 3, max = 40, message = "title fuera de rango")
    String title,

    @Size(max = 500, message = "description demasiado larga")
    String description,

    @NotNull(message = "latitude es requerida")
    @DecimalMin(value = "-90.0", inclusive = true, message = "latitude fuera de rango")
    @DecimalMax(value = "90.0", inclusive = true, message = "latitude fuera de rango")
    Double latitude,

    @NotNull(message = "longitude es requerida")
    @DecimalMin(value = "-180.0", inclusive = true, message = "longitude fuera de rango")
    @DecimalMax(value = "180.0", inclusive = true, message = "longitude fuera de rango")
    Double longitude,

    @NotBlank(message = "address es requerida")
    @Size(max = 80, message = "address demasiado larga")
    String address,

    @NotNull(message = "medias es requerido")
    @Size(max = 5, message = "máximo 5 medias por incidente")
    List<@Valid IncidentMediaRequestDTO> medias,

    @NotBlank(message = "citizenId es requerido")
    @Pattern(regexp = "\\d+", message = "citizenId debe ser numérico")
    String citizenId,

    @NotBlank(message = "municipalityId es requerido")
    @Pattern(regexp = "\\d+", message = "citizenId debe ser numérico")
    String municipalityId,

    @NotBlank(message = "categoryId es requerido")
    @Pattern(regexp = "\\d+", message = "citizenId debe ser numérico")
    String categoryId
) { }
