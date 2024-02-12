package br.com.fullcycle.domain.event;

import br.com.fullcycle.domain.exceptions.ValidationException;

import java.util.Optional;
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

    public static interface EventRepository {
        Optional<Event> eventOfId(EventId eventId);
        Event create(Event event);
        Event update(Event event);
        void deleteAll();
    }
}
