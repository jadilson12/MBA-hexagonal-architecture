package br.com.fullcycle.hexagonal.infrastructure.configurations;

import br.com.fullcycle.hexagonal.application.repositories.CustomerRepository;
import br.com.fullcycle.hexagonal.application.repositories.EventRepository;
import br.com.fullcycle.hexagonal.application.repositories.PartnerRepository;
import br.com.fullcycle.hexagonal.application.usecases.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Objects;

@Configuration
public class UseCaseConfig {

    private final CustomerRepository customerRepository;
    private final PartnerRepository partnerRepository;

    private final EventRepository eventRepository;

    public UseCaseConfig(
            final CustomerRepository customerRepository,
            final PartnerRepository partnerRepository,
            final EventRepository eventRepository
    ) {
        this.customerRepository = Objects.requireNonNull(customerRepository);
        this.partnerRepository = Objects.requireNonNull(partnerRepository);
        this.eventRepository = Objects.requireNonNull(eventRepository);
    }

    @Bean
    public CreateCustomerUseCase createCustomerUseCase() {
        // TODO: Fix this dependency
        return new CreateCustomerUseCase(customerRepository);
    }

    @Bean
    public CreateEventUseCase createEventUseCase() {
        // TODO: Fix this dependency
        return new CreateEventUseCase(null, null);
    }

    @Bean
    public SubscribeCustomerToEventUseCase subscribeCustomerToEventUseCase() {
        return new SubscribeCustomerToEventUseCase(null, null);
    }

    @Bean
    public CreatePartnerUseCase createPartnerUseCase() {
        return new CreatePartnerUseCase(partnerRepository);
    }

    @Bean
    public GetCustomerByIdUseCase getCustomerByIdUseCase() {
        // TODO: Fix this dependency
        return new GetCustomerByIdUseCase(customerRepository);

    }

    @Bean
    public GetPartnerByIDUseCase getPartnerByIDUseCase() {
        return new GetPartnerByIDUseCase(partnerRepository);
    }


}
