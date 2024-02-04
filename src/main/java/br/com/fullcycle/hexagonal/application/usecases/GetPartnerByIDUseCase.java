package br.com.fullcycle.hexagonal.application.usecases;

import br.com.fullcycle.hexagonal.application.UseCase;
import br.com.fullcycle.hexagonal.infrastructure.services.PartnerService;

import java.util.Objects;
import java.util.Optional;

public class GetPartnerByIDUseCase extends UseCase<GetPartnerByIDUseCase.Input, Optional<GetPartnerByIDUseCase.Output>> {

    private final PartnerService partnerService;

    public GetPartnerByIDUseCase(final PartnerService partnerService) {
        this.partnerService = Objects.requireNonNull(partnerService);
    }

    @Override
    public Optional<Output> execute(final Input input) {
        return partnerService.findById(input.id()).map(p -> new Output(p.getId(), p.getCnpj(), p.getName(),p.getEmail()));
    }

    public record Input(Long id) {}
    public record Output(Long id, String cnpj, String name, String email ) {}


}
