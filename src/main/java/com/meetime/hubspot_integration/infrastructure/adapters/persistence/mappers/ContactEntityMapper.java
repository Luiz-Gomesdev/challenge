package com.meetime.hubspot_integration.infrastructure.adapters.persistence.mappers;

import com.meetime.hubspot_integration.core.model.Contact;
import com.meetime.hubspot_integration.infrastructure.adapters.persistence.entities.ContactEntity;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface ContactEntityMapper {

    ContactEntityMapper INSTANCE = Mappers.getMapper(ContactEntityMapper.class);

    Contact toDomain(ContactEntity entity);

    ContactEntity toEntity(Contact domain);
}