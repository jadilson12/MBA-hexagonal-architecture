package br.com.fullcycle.domain.event.ticket;

import br.com.fullcycle.domain.exceptions.ValidationException;

import java.util.UUID;

public record TicketId(String value) {

    public TicketId {
        if (value == null) {
            throw new ValidationException("TicketId is required");
        }
    }
    public static TicketId unique() {
        return new TicketId(UUID.randomUUID().toString());
    }

    public static TicketId with(String value) {
        try {
            return new TicketId(UUID.fromString(value).toString());
        } catch (IllegalArgumentException e) {
            throw new ValidationException("Invalid value for TicketId: " + value);
        }
    }
}
