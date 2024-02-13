package br.com.fullcycle.domain.event;

import br.com.fullcycle.domain.customer.CustomerId;
import br.com.fullcycle.domain.event.ticket.TicketId;
import br.com.fullcycle.domain.exceptions.ValidationException;

public class EventTicket {
    private final EventTicketId eventTicketId;
    private final EventId eventId;
    private final CustomerId customerId;
    private int ordering;
    private TicketId ticketId;

    public EventTicket(final EventTicketId eventTicketId, final EventId eventId, CustomerId customerId,final TicketId ticketId, final Integer ordering) {
        if(eventTicketId == null) {
            throw new ValidationException("Invalid eventTicketId for eventTicket");
        }
        if(eventId == null) {
            throw new ValidationException("Invalid eventId for EventTicket");
        }
        if(customerId == null) {
            throw new ValidationException("Invalid customerId for EventTicket");
        }
        this.eventTicketId = eventTicketId;
        this.customerId = customerId;
        this.ticketId = ticketId;
        this.eventId = eventId;
        this.setOrdering(ordering);
    }

    public static EventTicket newTicket(final EventId eventId,final  CustomerId customerId, final Integer ordering){
        return new EventTicket(EventTicketId.unique(), eventId, customerId, null, ordering);
    }

    public TicketId ticketId() {
        return ticketId;
    }

    public EventTicketId eventTicketId() {
        return eventTicketId;
    }

    public EventId eventId() {
        return eventId;
    }

    public int ordering() {
        return ordering;
    }

    public CustomerId customerId() {
        return customerId;
    }

    public EventTicket associateTicket(final TicketId ticketId) {
        this.ticketId = ticketId;
        return this;
    }

    public void setOrdering(final Integer ordering) {
        if(ordering == null) {
            throw new ValidationException("Invalid ordering for EventTicket");
        }
        this.ordering = ordering;
    }
}
