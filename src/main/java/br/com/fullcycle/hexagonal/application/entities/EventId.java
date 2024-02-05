package br.com.fullcycle.hexagonal.application.entities;

import br.com.fullcycle.hexagonal.application.exceptions.ValidationException;

import java.util.UUID;

public record EventId(String value) {

    public EventId {
        if (value == null) {
            throw new ValidationException("EventId is required");
        }
    }
    public static EventId unique() {
        return new EventId(UUID.randomUUID().toString());
    }

    public static EventId with(String value) {
        try {
            return new EventId(UUID.fromString(value).toString());
        } catch (IllegalArgumentException e) {
            throw new ValidationException("Invalid value for CustomerId: " + value);
        }
    }
}
