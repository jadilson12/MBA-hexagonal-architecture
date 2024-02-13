package br.com.fullcycle.infrastructure.jpa.entities;

import br.com.fullcycle.domain.customer.CustomerId;
import br.com.fullcycle.domain.event.EventId;
import br.com.fullcycle.domain.event.EventTicket;
import br.com.fullcycle.domain.event.EventTicketId;
import br.com.fullcycle.domain.event.ticket.TicketId;
import jakarta.persistence.*;

import java.util.Objects;
import java.util.UUID;

@Entity(name = "EventTicket")
@Table(name = "events_tickets")
public class EventTicketEntity {

    @Id
    private UUID eventTicketId;
    private UUID ticketId;

    private UUID customerId;

    private int ordering;

    @ManyToOne(fetch = FetchType.LAZY)
    private EventEntity event;

    public EventTicketEntity() {
    }

    public EventTicketEntity(
            final UUID eventTicketId,
            final UUID ticketId,
            final UUID customerId,
            final int ordering,
            final EventEntity event

    ) {
        this.eventTicketId = eventTicketId;
        this.ticketId = ticketId;
        this.customerId = customerId;
        this.ordering = ordering;
        this.event = event;

    }

    public static EventTicketEntity of(
            final EventEntity event,
            final EventTicket ev
    ) {
        return new EventTicketEntity(
                UUID.fromString(ev.eventTicketId().value()),
                ev.eventId() != null ? UUID.fromString(ev.ticketId().value()) : null,
                UUID.fromString(ev.customerId().value()),
                ev.ordering(),
                event
        );
    }

    public EventTicket toEventTicket() {
        return new EventTicket(
                EventTicketId.with(this.eventTicketId.toString()),
                EventId.with(this.event.id().toString()),
                CustomerId.with(this.customerId.toString()),
                this.ticketId != null ? TicketId.with(this.ticketId.toString()) : null,
                this.ordering
        );
    }

    public UUID eventTicketId() {
        return eventTicketId;
    }

    public EventTicketEntity setEventTicketId(UUID eventTicketId) {
        this.eventTicketId = eventTicketId;
        return this;
    }

    public UUID ticketId() {
        return ticketId;
    }

    public void setTicketId(UUID ticketId) {
        this.ticketId = ticketId;
    }

    public UUID customerId() {
        return customerId;
    }

    public void setCustomerId(UUID customerId) {
        this.customerId = customerId;
    }

    public int ordering() {
        return ordering;
    }

    public void setOrdering(int ordering) {
        this.ordering = ordering;
    }

    public EventEntity event() {
        return event;
    }

    public void setEvent(EventEntity event) {
        this.event = event;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        EventTicketEntity that = (EventTicketEntity) o;
        return Objects.equals(eventTicketId, that.eventTicketId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(eventTicketId);
    }
}