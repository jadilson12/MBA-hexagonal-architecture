package br.com.fullcycle.hexagonal.infrastructure.configurations;

import br.com.fullcycle.hexagonal.application.repositories.PartnerRepository;
import br.com.fullcycle.hexagonal.application.usecases.*;
import br.com.fullcycle.hexagonal.infrastructure.services.CustomerService;
import br.com.fullcycle.hexagonal.infrastructure.services.EventService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Objects;

@Configuration
public class UseCaseConfig {

    private final CustomerService customerService;
    private final PartnerRepository partnerRepository;


    private final EventService eventService;

    public UseCaseConfig(
            final CustomerService customerService,
            final PartnerRepository partnerService,
            final EventService eventService
    ) {
        this.customerService = Objects.requireNonNull(customerService);
        this.partnerRepository = Objects.requireNonNull(partnerService);
        this.eventService = Objects.requireNonNull(eventService);
    }

    @Bean
    public CreateCustomerUseCase createCustomerUseCase() {
        // TODO: Fix this dependency
        return new CreateCustomerUseCase(null);
    }

    @Bean
    public CreateEventUseCase createEventUseCase() {
        return new CreateEventUseCase(eventService, null);
    }

    @Bean
    public SubscribeCustomerToEventUseCase subscribeCustomerToEventUseCase() {
        return new SubscribeCustomerToEventUseCase(customerService, eventService);
    }

    @Bean
    public CreatePartnerUseCase createPartnerUseCase() {
        return new CreatePartnerUseCase(partnerRepository);
    }

    @Bean
    public GetCustomerByIdUseCase getCustomerByIdUseCase() {
        // TODO: Fix this dependency
        return new GetCustomerByIdUseCase(null);

    }

    @Bean
    public GetPartnerByIDUseCase getPartnerByIDUseCase() {
        return new GetPartnerByIDUseCase(partnerRepository);
    }


}
