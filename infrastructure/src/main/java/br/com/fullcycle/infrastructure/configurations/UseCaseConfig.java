package br.com.fullcycle.infrastructure.configurations;

import br.com.fullcycle.application.customer.CreateCustomerUseCase;
import br.com.fullcycle.application.customer.GetCustomerByIdUseCase;
import br.com.fullcycle.application.event.CreateEventUseCase;
import br.com.fullcycle.application.event.SubscribeCustomerToEventUseCase;
import br.com.fullcycle.application.partner.CreatePartnerUseCase;
import br.com.fullcycle.application.partner.GetPartnerByIDUseCase;
import br.com.fullcycle.domain.customer.CustomerRepository;
import br.com.fullcycle.domain.event.EventRepository;
import br.com.fullcycle.domain.event.ticket.TicketRepository;
import br.com.fullcycle.domain.partner.PartnerRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Objects;

@Configuration
public class UseCaseConfig {

    private final CustomerRepository customerRepository;
    private final PartnerRepository partnerRepository;
    private  final TicketRepository ticketRepository;
    private final EventRepository eventRepository;
    public UseCaseConfig(
            final CustomerRepository customerRepository,
            final PartnerRepository partnerRepository,
            final TicketRepository ticketRepository,
            final EventRepository eventRepository
    ) {
        this.customerRepository = Objects.requireNonNull(customerRepository);
        this.partnerRepository = Objects.requireNonNull(partnerRepository);
        this.ticketRepository = Objects.requireNonNull(ticketRepository);
        this.eventRepository = Objects.requireNonNull(eventRepository);
    }
    @Bean
    public CreateCustomerUseCase createCustomerUseCase() {
        return new CreateCustomerUseCase(customerRepository);
    }
    @Bean
    public CreateEventUseCase createEventUseCase() {
        return new CreateEventUseCase(eventRepository, partnerRepository);
    }
    @Bean
    public SubscribeCustomerToEventUseCase subscribeCustomerToEventUseCase() {
        return new SubscribeCustomerToEventUseCase(eventRepository, customerRepository,ticketRepository );
    }
    @Bean
    public CreatePartnerUseCase createPartnerUseCase() {
        return new CreatePartnerUseCase(partnerRepository);
    }

    @Bean
    public GetCustomerByIdUseCase getCustomerByIdUseCase() {
        return new GetCustomerByIdUseCase(customerRepository);
    }

    @Bean
    public GetPartnerByIDUseCase getPartnerByIDUseCase() {
        return new GetPartnerByIDUseCase(partnerRepository);
    }


}
