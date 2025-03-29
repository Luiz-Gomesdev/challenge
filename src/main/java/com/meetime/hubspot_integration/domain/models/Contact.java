package com.meetime.hubspot_integration.domain.models;

import com.meetime.hubspot_integration.domain.shared.valueobjects.Email;
import com.meetime.hubspot_integration.domain.shared.valueobjects.PhoneNumber;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Builder
public class Contact {
    private final UUID id;
    private final Email email;  // Tipo Email
    private final String firstName;
    private final String lastName;
    private final PhoneNumber phone;
    private final String companyName;
    private final LocalDateTime createdAt;
    private final LocalDateTime updatedAt;

    public static class ContactBuilder {
        private UUID id = UUID.randomUUID();
        private LocalDateTime createdAt = LocalDateTime.now();
        private LocalDateTime updatedAt = LocalDateTime.now();

        // Método para aceitar String e converter para Email
        public ContactBuilder email(String email) {
            this.email = new Email(email);  // Conversão implícita
            return this;
        }

        // Método para aceitar Email diretamente
        public ContactBuilder email(Email email) {
            this.email = email;
            return this;
        }
    }
}