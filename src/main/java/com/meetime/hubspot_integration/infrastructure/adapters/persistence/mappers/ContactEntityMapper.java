package com.meetime.hubspot_integration.infrastructure.adapters.persistence.mappers;

import com.meetime.hubspot_integration.core.model.Contact;
import com.meetime.hubspot_integration.core.model.valueobjects.PhoneNumber;
import com.meetime.hubspot_integration.infrastructure.adapters.persistence.entities.ContactEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.mapstruct.factory.Mappers;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Mapper
public interface ContactEntityMapper {

    ContactEntityMapper INSTANCE = Mappers.getMapper(ContactEntityMapper.class);

    String COMMA_SEPARATOR = ",";

    @Mapping(target = "company", ignore = true)
    @Mapping(target = "tags", source = "tags", qualifiedByName = "stringToList")
    @Mapping(target = "phone", source = "phone", qualifiedByName = "stringToPhoneNumber")
    Contact toDomain(ContactEntity entity);

    @Mapping(target = "tags", source = "tags", qualifiedByName = "listToString")
    @Mapping(target = "phone", source = "phone", qualifiedByName = "phoneNumberToString")
    ContactEntity toEntity(Contact domain);

    @Named("stringToPhoneNumber")
    default PhoneNumber stringToPhoneNumber(String value) {
        return (value != null && !value.isBlank()) ? PhoneNumber.fromFullNumber(value.trim()) : null;
    }

    @Named("phoneNumberToString")
    default String phoneNumberToString(PhoneNumber phoneNumber) {
        return phoneNumber != null ? phoneNumber.formattedValue() : null;
    }

    @Named("listToString")
    default String listToString(List<String> tags) {
        return (tags != null && !tags.isEmpty()) ? String.join(COMMA_SEPARATOR, tags) : null;
    }

    @Named("stringToList")
    default List<String> stringToList(String tags) {
        if (tags == null || tags.isBlank()) {
            return Collections.emptyList();
        }
        return Arrays.stream(tags.split(COMMA_SEPARATOR))
                .map(String::trim)
                .filter(tag -> !tag.isEmpty())
                .collect(Collectors.toList());
    }
}
