package com.maugallo.munify_backend.incidentMedia;

import com.maugallo.munify_backend.config.GlobalMapperConfig;
import com.maugallo.munify_backend.incidentMedia.dto.IncidentMediaResponseDTO;
import com.maugallo.munify_backend.media.MediaLinkBuilder;
import org.mapstruct.Context;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

@Mapper(config = GlobalMapperConfig.class)
public interface IncidentMediaMapper {

    /* Medias helper without readable urls. */
    @Named("noUrl")
    @Mapping(target = "id", source = "id")
    @Mapping(target = "type", source = "type")
    @Mapping(target = "mime", source = "mime")
    @Mapping(target = "size", source = "size")
    @Mapping(target = "storageKey", source = "storageKey")
    @Mapping(target = "url", ignore = true)
    IncidentMediaResponseDTO toResponse(IncidentMedia media);

    /* Medias helper with readable urls. */
    @Named("withUrl")
    @Mapping(target = "id", source = "id")
    @Mapping(target = "type", source = "type")
    @Mapping(target = "mime", source = "mime")
    @Mapping(target = "size", source = "size")
    @Mapping(target = "storageKey", source = "storageKey")
    @Mapping(target = "url", expression = "java(linkBuilder != null ? linkBuilder.buildTemporalUrl(media.getStorageKey()) : null)")
    IncidentMediaResponseDTO toResponse(IncidentMedia media, @Context MediaLinkBuilder linkBuilder);

}
