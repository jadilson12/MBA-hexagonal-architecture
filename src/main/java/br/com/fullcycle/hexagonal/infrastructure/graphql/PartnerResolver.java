package br.com.fullcycle.hexagonal.infrastructure.graphql;

import br.com.fullcycle.hexagonal.application.usecases.partner.CreatePartnerUseCase;
import br.com.fullcycle.hexagonal.application.usecases.partner.GetPartnerByIDUseCase;
import br.com.fullcycle.hexagonal.infrastructure.dtos.NewCustomerDTO;
import br.com.fullcycle.hexagonal.infrastructure.services.PartnerService;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.stereotype.Controller;

import java.util.Objects;

// Adapter
@Controller
public class PartnerResolver {

    private final PartnerService partnerService;

    public PartnerResolver(final PartnerService partnerService) {
        this.partnerService = Objects.requireNonNull(partnerService);
    }


    @MutationMapping
    public CreatePartnerUseCase.Output createPartner(@Argument NewCustomerDTO input) {
        final var useCase = new CreatePartnerUseCase(null);
        return useCase.execute(new CreatePartnerUseCase.Input(input.cpf(), input.email(), input.name()));
    }

    @QueryMapping
    public GetPartnerByIDUseCase.Output partnerOfID(@Argument String id) {
        final var useCase = new GetPartnerByIDUseCase(null);
        return useCase.execute(new GetPartnerByIDUseCase.Input(id)).orElse(null);
    }
}
