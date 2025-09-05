package com.maugallo.munify_backend.incidentMedia;

import com.maugallo.munify_backend.config.GlobalMapperConfig;
import com.maugallo.munify_backend.incidentMedia.dto.IncidentMediaResponseDTO;
import org.mapstruct.Context;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(config = GlobalMapperConfig.class)
public interface IncidentMediaMapper {

    /* Medias without readable urls. */
    @Mapping(target = "id", expression = "java(String.valueOf(src.getId()))")
    @Mapping(target = "type", source = "type")
    @Mapping(target = "mime", source = "mime")
    @Mapping(target = "size", source = "size")
    @Mapping(target = "storageKey", source = "storageKey")
    @Mapping(target = "url", ignore = true)
    IncidentMediaResponseDTO toResponse(IncidentMedia src);

    /* Medias with readable urls. */
    @Mapping(target = "id",         source = "id")
    @Mapping(target = "type",       source = "type")
    @Mapping(target = "mime",       source = "mime")
    @Mapping(target = "size",       source = "size")
    @Mapping(target = "storageKey", source = "storageKey")
    @Mapping(target = "url",        expression = "java(linkResolver != null ? linkResolver.resolve(src) : null)")
    IncidentMediaResponseDTO toDto(IncidentMedia src, @Context IncidentMediaLinkResolver linkResolver);

}
