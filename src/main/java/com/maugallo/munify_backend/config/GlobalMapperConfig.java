package com.maugallo.munify_backend.config;

import org.mapstruct.InjectionStrategy;
import org.mapstruct.MapperConfig;
import org.mapstruct.NullValueMappingStrategy;
import org.mapstruct.ReportingPolicy;

@MapperConfig(
        componentModel = "spring",
        injectionStrategy = InjectionStrategy.CONSTRUCTOR,
        unmappedTargetPolicy = ReportingPolicy.ERROR,
        nullValueMappingStrategy = NullValueMappingStrategy.RETURN_DEFAULT
)
public interface GlobalMapperConfig { }
