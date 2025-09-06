package com.maugallo.munify_backend.incidentMedia.dto;

import com.maugallo.munify_backend.incidentMedia.IncidentMediaType;
import jakarta.validation.constraints.*;

/* Medias ya subidos al storage pero en zona de staging,
* todavía no asociados con ningún incidente. */

public record IncidentMediaRequestDTO(
    @NotBlank(message = "storageKey es requerido")
    @Pattern(regexp = "^municipalities/\\d+/(staging)/.+$", message = "storageKey inválido")
    String storageKey,

    @NotNull(message = "type es requerido")
    IncidentMediaType type,

    @NotBlank(message = "mime es requerido")
    @Pattern(regexp = "^(image/(jpeg|png|webp|heic)|video/(mp4|quicktime))$", message = "mime no permitido")
    String mime,

    @NotNull(message = "size es requerido")
    @Positive(message = "size debe ser positivo")
    @Max(value = 50L * 1024 * 1024, message = "size excede el máximo permitido") // 50 MiB
    Long size
) { }
