package com.meetime.hubspot_integration.core.model.valueobjects;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;

import java.util.Objects;
import java.util.regex.Pattern;

@Embeddable
@Getter
@NoArgsConstructor
public class PhoneNumber {

    @Column(name = "country_code", length = 5)
    private String countryCode;

    @Column(name = "phone_number", length = 20)
    private String number;

    public PhoneNumber(@NonNull String countryCode, @NonNull String number) {
        if (!isValidPhoneNumber(number)) {
            throw new IllegalArgumentException("Invalid phone number format");
        }
        this.countryCode = normalizeCountryCode(countryCode);
        this.number = number;
    }

    public String formattedValue() {
        String cleanNumber = number.replaceAll("[^0-9]", "");
        String cleanCountryCode = countryCode.replaceAll("[^0-9]", "");
        return "+" + cleanCountryCode + cleanNumber;
    }

    public String fullNumber() {
        return countryCode + " " + number;
    }

    public static PhoneNumber fromFullNumber(String fullNumber) {
        if (fullNumber == null || fullNumber.isBlank()) {
            throw new IllegalArgumentException("Phone number cannot be empty");
        }

        String cleaned = fullNumber.replaceAll("[^0-9+]", "");
        if (cleaned.startsWith("+")) {
            return new PhoneNumber(
                    "+" + cleaned.substring(1, 3),
                    cleaned.substring(3)
            );
        } else {
            return new PhoneNumber(
                    "+55",
                    cleaned
            );
        }
    }

    public static PhoneNumber fromLegacyFormat(String legacyNumber) {
        if (legacyNumber == null) return null;

        String cleaned = legacyNumber.replaceAll("[^0-9]", "");
        if (cleaned.length() > 11) {
            return new PhoneNumber(
                    "+" + cleaned.substring(0, 2),
                    cleaned.substring(2)
            );
        } else {
            return new PhoneNumber("+55", cleaned);
        }
    }

    @PrePersist
    @PreUpdate
    private void formatFields() {
        this.countryCode = normalizeCountryCode(this.countryCode);
        this.number = this.number.replaceAll("[^0-9]", "");
    }

    private String normalizeCountryCode(String code) {
        if (code == null) return "+55";
        return code.startsWith("+") ? code : "+" + code;
    }

    private boolean isValidPhoneNumber(String number) {
        if (number == null) return false;
        Pattern pattern = Pattern.compile("^(\\+\\d{1,3}[- ]?)?\\(?\\d{2,3}\\)?[- ]?\\d{4,5}[- ]?\\d{4}$");
        return pattern.matcher(number).matches();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PhoneNumber that = (PhoneNumber) o;
        return Objects.equals(formattedValue(), that.formattedValue());
    }

    @Override
    public int hashCode() {
        return Objects.hash(formattedValue());
    }

    @Override
    public String toString() {
        return formattedValue();
    }

    public String getValue() {
        return number;
    }
}
