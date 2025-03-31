package com.meetime.hubspot_integration.application.ports.outbound;

import com.meetime.hubspot_integration.core.model.Contact;
import com.meetime.hubspot_integration.application.exceptions.HubSpotIntegrationException;

public interface HubSpotClientPort {

    Contact createContact(Contact contact) throws HubSpotIntegrationException;

    void updateContact(Contact contact) throws HubSpotIntegrationException;

    void deleteContact(String hubSpotId) throws HubSpotIntegrationException;
}
