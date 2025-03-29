package com.meetime.hubspot_integration.application.services;

import com.meetime.hubspot_integration.application.ports.*;
import com.meetime.hubspot_integration.domain.events.ContactCreatedEvent;
import com.meetime.hubspot_integration.domain.models.Contact;
import com.meetime.hubspot_integration.domain.models.OAuthToken;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class ContactService {

    private final ContactRepositoryPort repository;
    private final HubSpotClientPort hubSpotClient;
    private final EventPublisherPort eventPublisher;
    private final OAuthServicePort oauthService;

    @Transactional
    public Contact createContact(String email, String firstName, String lastName) {
        Contact contact = Contact.builder()
                .email(email)  // Aceita String e converte internamente
                .firstName(firstName)
                .lastName(lastName)
                .build();

        Contact savedContact = repository.save(contact);
        OAuthToken token = oauthService.getCurrentToken();

        hubSpotClient.createContact(savedContact, token);
        eventPublisher.publish(new ContactCreatedEvent(savedContact));  // Agora funciona

        return savedContact;
    }
}