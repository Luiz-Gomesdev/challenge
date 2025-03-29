package com.meetime.hubspot_integration.domain.models;

import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
@Builder
public class Company {
    private final String id;
    private final String name;
    private final String domain;
    private final List<Contact> contacts;

    public void addContact(Contact contact) {
        this.contacts.add(contact);
    }
}