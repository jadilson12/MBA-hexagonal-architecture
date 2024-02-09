package br.com.fullcycle.hexagonal.application.domain.event.ticket;

import br.com.fullcycle.hexagonal.application.domain.customer.CustomerId;
import br.com.fullcycle.hexagonal.application.domain.event.EventId;
import br.com.fullcycle.hexagonal.application.exceptions.ValidationException;

import java.time.Instant;
import java.util.Objects;

public class Ticket {
    private final TicketId ticketId;
    private EventId eventId;
    private CustomerId customerId;
    private TicketStatus status;
    private Instant paidAt;
    private Instant reservedAt;


    public Ticket(final TicketId ticketId, final CustomerId customerId, final EventId eventId,  final TicketStatus status, final Instant paidAt, final Instant reservedAt) {
        this.ticketId = ticketId;
        this.setEventId(eventId);
        this.setCustomerId(customerId);
        this.setStatus(status);
        this.setPaidAt(paidAt);
        this.setReservedAt(reservedAt);
    }

    public static Ticket newTicket(final CustomerId customerId, final EventId eventId ) {
        return new Ticket(TicketId.unique(),  customerId, eventId, TicketStatus.PENDING, null, Instant.now());
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Ticket ticket = (Ticket) o;
        return Objects.equals(ticketId, ticket.ticketId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(ticketId);
    }

    public TicketId ticketId() {
        return ticketId;
    }

    public EventId eventId() {
        return eventId;
    }

    public CustomerId customerId() {
        return customerId;
    }

    public TicketStatus status() {
        return status;
    }

    public Instant paidAt() {
        return paidAt;
    }
    private void setPaidAt(final Instant paidAt) {
        this.paidAt = paidAt;
    }

    public Instant reservedAt() {
        return reservedAt;
    }

    private void setEventId(final EventId eventId) {
        if(eventId == null) {
            throw new ValidationException("Invalid eventId for Ticket");
        }
        this.eventId = eventId;
    }

    private void setCustomerId(final CustomerId customerId) {
        if(customerId == null) {
            throw new ValidationException("Invalid customerId for Ticket");
        }
        this.customerId = customerId;
    }

    private void setStatus(final TicketStatus status) {
        this.status = status;
    }

    private void setReservedAt(final Instant reservedAt) {
        if(reservedAt == null) {
            throw new ValidationException("Invalid reservedAt for Ticket");
        }
        this.reservedAt = reservedAt;
    }

}
