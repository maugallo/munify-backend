package com.maugallo.munify_backend.incidentMedia.dto.prepare;

import com.maugallo.munify_backend.incidentMedia.IncidentMediaType;
import jakarta.validation.constraints.*;

public record IncidentMediaPrepareItemRequestDTO(
        @NotBlank(message = "clientFileId es requerido")
        @Pattern(regexp = "^[0-9a-fA-F]{8}-[0-9a-fA-F]{4}-[0-9a-fA-F]{4}-[0-9a-fA-F]{4}-[0-9a-fA-F]{12}$", message = "clientFileId debe ser UUID")
        String clientFileId, // UUID del front para idempotencia por archivo

        @NotBlank(message = "fileName es requerido")
        @Size(max = 255, message = "fileName demasiado largo")
        String fileName,     // nombre original

        @NotBlank(message = "mime es requerido")
        @Pattern(regexp = "^(image/(jpeg|png|webp|heic)|video/(mp4|quicktime))$", message = "mime no permitido")
        String mime,         // MIME declarado

        @Positive(message = "size debe ser positivo")
        @Max(value = 50L * 1024 * 1024, message = "size excede el máximo permitido") // 50 MiB
        Long size,

        @NotNull(message = "type es requerido")
        IncidentMediaType type
) { }
