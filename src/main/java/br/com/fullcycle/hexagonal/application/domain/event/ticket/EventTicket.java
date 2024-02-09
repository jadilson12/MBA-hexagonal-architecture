package br.com.fullcycle.hexagonal.application.domain.event.ticket;

import br.com.fullcycle.hexagonal.application.domain.customer.CustomerId;
import br.com.fullcycle.hexagonal.application.domain.event.EventId;
import br.com.fullcycle.hexagonal.application.exceptions.ValidationException;

public class EventTicket {
    private  final TicketId ticketId;
    private final EventId eventId;
    private  final CustomerId customerId;
    private  int ordering;

    public EventTicket(final TicketId ticketId, final EventId eventId, final Integer ordering, CustomerId customerId) {


        if(ticketId == null) {
            throw new ValidationException("Invalid ticketId for EventTicket");
        }
        if(eventId == null) {
            throw new ValidationException("Invalid eventId for EventTicket");
        }
        if(customerId == null) {
            throw new ValidationException("Invalid customerId for EventTicket");
        }
        this.customerId = customerId;
        this.ticketId = ticketId;
        this.eventId = eventId;
        this.setOrdering(ordering);
    }

    public TicketId ticketId() {
        return ticketId;
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

    public void setOrdering(final Integer ordering) {
        if(ordering == null) {
            throw new ValidationException("Invalid ordering for EventTicket");
        }
        this.ordering = ordering;
    }
}
