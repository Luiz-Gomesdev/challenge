package com.meetime.hubspot_integration.application.dto;

import com.meetime.hubspot_integration.core.model.Company;
import com.meetime.hubspot_integration.core.model.valueobjects.PhoneNumber;
import lombok.*;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ContactDTO {
    private String hubspotId;
    private String firstName;
    private String lastName;
    private String email;
    private PhoneNumber phone;
    private Company company;
    private List<String> tags;
    private String status;
}