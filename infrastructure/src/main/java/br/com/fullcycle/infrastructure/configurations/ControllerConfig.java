package br.com.fullcycle.infrastructure.configurations;

import br.com.fullcycle.application.partner.CreatePartnerUseCase;
import br.com.fullcycle.application.partner.GetPartnerByIDUseCase;
import br.com.fullcycle.infrastructure.rest.PartnerFnController;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ControllerConfig {

    @Bean
    public PartnerFnController partnerFnController(
            final CreatePartnerUseCase createPartnerUseCase,
            final GetPartnerByIDUseCase getPartnerByIDUseCase
    ) {
        return new PartnerFnController(createPartnerUseCase, getPartnerByIDUseCase);
    }
}
