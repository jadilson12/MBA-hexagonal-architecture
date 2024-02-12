package br.com.fullcycle.domain.partner;

import br.com.fullcycle.domain.exceptions.ValidationException;

import java.util.UUID;

public record PartnerId(String value) {

    public PartnerId {
        if (value == null) {
            throw new ValidationException("PartnerId is required");
        }
    }
    public static PartnerId unique() {
        return new PartnerId(UUID.randomUUID().toString());
    }

    public static PartnerId with(String value) {
        try {
            return new PartnerId(UUID.fromString(value).toString());
        } catch (IllegalArgumentException e) {
            throw new ValidationException("Invalid value for CustomerId: " + value);
        }
    }
}
