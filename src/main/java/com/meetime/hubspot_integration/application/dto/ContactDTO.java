package com.meetime.hubspot_integration.application.dto;

import lombok.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ContactDTO {
    private Long id;
    private String email;
    private String firstName;
    private String lastName;
    private String website;
    private String company;
    private String phone;
}