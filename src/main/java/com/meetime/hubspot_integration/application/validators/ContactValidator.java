package com.meetime.hubspot_integration.application.validators;

import com.meetime.hubspot_integration.application.exceptions.DomainValidationException;
import com.meetime.hubspot_integration.core.model.Contact;
import com.meetime.hubspot_integration.core.model.valueobjects.PhoneNumber;
import lombok.NonNull;
import org.apache.commons.lang3.StringUtils;

import java.util.HashSet;
import java.util.Set;

public class ContactValidator {

    public void validateForCreation(@NonNull Contact contact) throws DomainValidationException {
        Set<String> violations = new HashSet<>();

        if (StringUtils.isNotBlank(contact.getEmail()) && !contact.getEmail().endsWith("@company.com")) {
            violations.add("E-mail deve ser corporativo (@company.com)");
        }

        if (contact.getPhone() != null && !contact.getPhone().getCountryCode().equals("+55")) {
            violations.add("Apenas números brasileiros são aceitos");
        }

        if (StringUtils.length(contact.getFirstName()) > 100) {
            violations.add("Nome deve ter no máximo 100 caracteres");
        }

        if (!violations.isEmpty()) {
            throw new DomainValidationException(
                    "Validação falhou para contato: " + String.join(", ", violations),
                    String.join(", ", violations)
            );
        }
    }

    public void validateForHubSpotSync(@NonNull Contact contact) throws DomainValidationException {
        if (contact.getHubspotId() == null && StringUtils.isBlank(contact.getEmail())) {
            throw new DomainValidationException(
                    "E-mail é obrigatório para sincronização inicial com HubSpot",
                    "E-mail é obrigatório"
            );
        }
    }
}
