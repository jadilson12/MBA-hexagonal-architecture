package br.com.fullcycle.hexagonal.infrastructure.graphql;

import br.com.fullcycle.hexagonal.application.usecases.CreatePartnerUseCase;
import br.com.fullcycle.hexagonal.application.usecases.GetPartnerByIDUseCase;
import br.com.fullcycle.hexagonal.infrastructure.dtos.CustomerDTO;
import br.com.fullcycle.hexagonal.infrastructure.services.PartnerService;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.graphql.data.method.annotation.QueryMapping;

import java.util.Objects;

public class PartnerResolver {

    private final PartnerService partnerService;

    public PartnerResolver(final PartnerService partnerService) {
        this.partnerService = Objects.requireNonNull(partnerService);
    }


    @MutationMapping
    public CreatePartnerUseCase.Output createPartner(@Argument CustomerDTO input) {
        final var useCase = new CreatePartnerUseCase(partnerService);
        return useCase.execute(new CreatePartnerUseCase.Input(input.getCpf(), input.getEmail(), input.getName()));
    }

    @QueryMapping
    public GetPartnerByIDUseCase.Output partnerOfID(@Argument Long id) {
        final var useCase = new GetPartnerByIDUseCase(partnerService);
        return useCase.execute(new GetPartnerByIDUseCase.Input(id)).orElse(null);
    }
}
