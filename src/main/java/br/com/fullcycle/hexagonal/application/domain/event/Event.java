package br.com.fullcycle.hexagonal.application.domain.event;

import br.com.fullcycle.hexagonal.application.domain.customer.CustomerId;
import br.com.fullcycle.hexagonal.application.domain.event.ticket.Ticket;
import br.com.fullcycle.hexagonal.application.domain.partner.Partner;
import br.com.fullcycle.hexagonal.application.domain.partner.PartnerId;
import br.com.fullcycle.hexagonal.application.domain.person.Name;
import br.com.fullcycle.hexagonal.application.exceptions.ValidationException;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Collections;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

public class Event {
    private static final int ONE = 1;
    private final EventId eventId;
    private Name name;

    public LocalDate date() {
        return date;
    }

    private LocalDate date;
    private int totalSpots;
    private PartnerId partnerId;

    private Set<EventTicket> tickets;


    public Event(final EventId eventId, final String name, final String date, final Integer totalSpots, final PartnerId partnerId) {
        this(eventId);
        this.setName(name);
        this.setDate(date);
        this.setTotalSpots(totalSpots);
        this.setPartnerId(partnerId);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Event event = (Event) o;
        return Objects.equals(eventId, event.eventId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(eventId);
    }

    public static Event newEvent(final String name, final String date, final Integer totalSpots, final Partner partner) {
        return new Event(EventId.unique(), name, date, totalSpots, partner.partnerId());
    }

    private Event(final EventId eventId) {
        if (eventId == null) {
            throw new ValidationException("Invalid eventId for Event");
        }
        this.eventId = eventId;
        this.tickets = new HashSet<>(0);
    }

    public Ticket reserveTicket(final CustomerId aCustomerId) {
        this.allTickets().stream()
                .filter(t -> t.customerId().equals(aCustomerId))
                .findFirst()
                .ifPresent(t -> {
                    throw new ValidationException("Customer already registered");
                });

        if (this.totalSpots() < this.allTickets().size() + ONE) {
            throw new ValidationException("Event sold out");
        }

        final var newTicker = Ticket.newTicket(aCustomerId, eventId());
        this.allTickets().add(new EventTicket(newTicker.ticketId(), eventId(), this.allTickets().size() + ONE, aCustomerId));

        return newTicker;
    }



    public Set<EventTicket> allTickets() {
        return Collections.unmodifiableSet(tickets);
    }

    public EventId eventId() {
        return eventId;
    }

    public Name name() {
        return name;
    }

    public int totalSpots() {
        return totalSpots;
    }

    public PartnerId partnerId() {
        return partnerId;
    }

    private void setPartnerId(PartnerId partnerId) {
        if (partnerId == null) {
            throw new ValidationException("Invalid partnerId for Event");
        }
        this.partnerId = partnerId;
    }

    private void setName(final String name) {
        if (name == null || name.isEmpty()) {
            throw new ValidationException("Invalid name for Event");
        }
        this.name = new Name(name);
    }

    private void setDate(final String date) {
        if (date == null || date.isEmpty()) {
            throw new ValidationException("Invalid date for Event");
        }
        try {
            this.date = LocalDate.parse(date, DateTimeFormatter.ISO_LOCAL_DATE);
        } catch (RuntimeException ex) {
            throw new ValidationException("Invalid date for Event", ex );
        }

    }

    private void setTotalSpots(final Integer totalSpots) {
        if (totalSpots == null || totalSpots <= 0) {
            throw new ValidationException("Invalid totalSpots for Event");
        }
        this.totalSpots = totalSpots;
    }

}


