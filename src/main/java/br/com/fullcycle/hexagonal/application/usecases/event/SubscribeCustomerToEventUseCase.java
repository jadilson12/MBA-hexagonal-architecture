package br.com.fullcycle.hexagonal.application.usecases.event;

import br.com.fullcycle.hexagonal.application.usecases.UseCase;
import br.com.fullcycle.hexagonal.application.domain.customer.CustomerId;
import br.com.fullcycle.hexagonal.application.domain.event.Event;
import br.com.fullcycle.hexagonal.application.domain.event.EventId;
import br.com.fullcycle.hexagonal.application.domain.partner.PartnerId;
import br.com.fullcycle.hexagonal.application.domain.event.ticket.Ticket;
import br.com.fullcycle.hexagonal.application.exceptions.ValidationException;
import br.com.fullcycle.hexagonal.application.repositories.CustomerRepository;
import br.com.fullcycle.hexagonal.application.repositories.EventRepository;
import br.com.fullcycle.hexagonal.application.repositories.PartnerRepository;
import br.com.fullcycle.hexagonal.application.repositories.TicketRepository;

import java.time.Instant;
import java.util.Objects;

public class SubscribeCustomerToEventUseCase extends UseCase<SubscribeCustomerToEventUseCase.Input, SubscribeCustomerToEventUseCase.Output> {



    private final CustomerRepository customerRepository;
    private final EventRepository eventRepository;
    private final TicketRepository ticketRepository;
    public SubscribeCustomerToEventUseCase(
            final TicketRepository ticketRepository,
            final CustomerRepository customerRepository,
            final EventRepository eventRepository
    ) {
        this.ticketRepository = Objects.requireNonNull(ticketRepository);
        this.customerRepository = Objects.requireNonNull(customerRepository) ;
        this.eventRepository = Objects.requireNonNull(eventRepository) ;
    }

    @Override
    public Output execute(Input input) {
        var aCustomer = customerRepository.customerOfId(CustomerId.with(input.customerId())).orElseThrow(() -> new ValidationException("Customer not found"));

        var anEvent = eventRepository.eventOfId(EventId.with(input.eventId)).orElseThrow(() -> new ValidationException("Event not found"));

        final Ticket ticket = anEvent.reserveTicket(aCustomer.customerId());
        ticketRepository.create(ticket);
        eventRepository.update(anEvent);

        return new Output(anEvent.eventId().value(),ticket.ticketId().value(), ticket.status().name(), ticket.reservedAt());
    }

    public record Input(String customerId, String eventId ){}
    public record Output(String eventId,String tickerId, String ticketStatus, Instant reservationDate){}

    public static class CreateEventUseCase extends UseCase<CreateEventUseCase.Input, CreateEventUseCase.Output> {
        private final EventRepository eventRepository;
        private final PartnerRepository partnerRepository;

        public CreateEventUseCase(final EventRepository eventRepository, final PartnerRepository partnerRepository) {
            this.eventRepository = Objects.requireNonNull(eventRepository);
            this.partnerRepository = Objects.requireNonNull(partnerRepository);
        }

        @Override
        public Output execute(final Input input) {

            final var aPartner = partnerRepository.partnerOfId(PartnerId.with(input.partnerId))
                    .orElseThrow(() -> new ValidationException("Partner not found"));

            final var anEvent =
                    eventRepository.create(Event.newEvent(input.name, input.date, input.totalSpots, aPartner));

            return new Output(
                    anEvent.eventId().value(),
                    input.date,
                    anEvent.name().value(),
                    anEvent.totalSpots(),
                    anEvent.partnerId().value()
            );
        }
        public record Input(String date, String name, String partnerId, Integer totalSpots) { }
        public record Output(String id, String date, String name, int totalSpots, String partnerId) { }



    }
}
