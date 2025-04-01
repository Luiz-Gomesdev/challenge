package com.meetime.hubspot_integration.application.dto;

import com.meetime.hubspot_integration.core.model.Company;
import com.meetime.hubspot_integration.core.model.valueobjects.PhoneNumber;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ContactDTO {

    private String hubspotId;

    @NotEmpty(message = "First name cannot be empty")
    private String firstName;

    @NotEmpty(message = "Last name cannot be empty")
    private String lastName;

    @Email(message = "Email should be valid")
    @NotEmpty(message = "Email cannot be empty")
    private String email;

    private PhoneNumber phone;

    private Company company;

    private List<String> tags;

    @Size(max = 50, message = "Status should be less than or equal to 50 characters")
    private String status;
}
