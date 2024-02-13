package br.com.fullcycle.domain.event;

import br.com.fullcycle.domain.exceptions.ValidationException;

import java.util.Optional;
import java.util.UUID;

public record EventTicketId(String value) {

    public EventTicketId {
        if (value == null) {
            throw new ValidationException("EventTicketId is required");
        }
    }
    public static EventTicketId unique() {
        return new EventTicketId(UUID.randomUUID().toString());
    }

    public static EventTicketId with(String value) {
        try {
            return new EventTicketId(UUID.fromString(value).toString());
        } catch (IllegalArgumentException e) {
            throw new ValidationException("Invalid value for EventTicketId");
        }
    }

    public static interface EventRepository {
        Optional<Event> eventOfId(EventTicketId eventId);
        Event create(Event event);
        Event update(Event event);
        void deleteAll();
    }
}
