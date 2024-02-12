package br.com.fullcycle.application.partner;

import br.com.fullcycle.application.UseCase;
import br.com.fullcycle.domain.partner.PartnerId;
import br.com.fullcycle.domain.partner.PartnerRepository;

import java.util.Objects;
import java.util.Optional;

public class GetPartnerByIDUseCase extends UseCase<GetPartnerByIDUseCase.Input, Optional<GetPartnerByIDUseCase.Output>> {

    private final PartnerRepository partnerRepository;

    public GetPartnerByIDUseCase(final PartnerRepository partnerRepository) {
        this.partnerRepository = Objects.requireNonNull(partnerRepository);
    }

    @Override
    public Optional<Output> execute(final Input input) {
        return partnerRepository.partnerOfId(PartnerId.with(input.id))
                .map(p -> new Output(
                        p.partnerId().value(),
                        p.cnpj().value(),
                        p.name().value(),
                        p.email().value())
                );
    }

    public record Input(String id) {}
    public record Output(String id, String cnpj, String name, String email ) {}


}
