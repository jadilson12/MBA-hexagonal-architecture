package br.com.fullcycle.hexagonal.application;

import br.com.fullcycle.hexagonal.application.entities.Partner;
import br.com.fullcycle.hexagonal.application.entities.PartnerId;
import br.com.fullcycle.hexagonal.application.repositories.PartnerRepository;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;

public class InMemoryPartnerRepository implements PartnerRepository {
    private final Map<String, Partner> partner;
    private final Map<String, Partner> partnerByEmail;
    private final Map<String, Partner> partnerByCnpj;

    public InMemoryPartnerRepository() {
        this.partner = new HashMap<>();
        this.partnerByEmail = new HashMap<>();
        this.partnerByCnpj = new HashMap<>();
    }

    @Override
    public Optional<Partner> partnerOfId(PartnerId partnerId) {
        return Optional.ofNullable(this.partner.get(Objects.requireNonNull(partnerId).value().toString()));
    }

    @Override
    public Optional<Partner> partnerOfCnpj(String cnpj) {
        return Optional.ofNullable(this.partnerByCnpj.get(cnpj));
    }

    @Override
    public Optional<Partner> partnerOfEmail(String email) {
        return Optional.ofNullable(this.partnerByEmail.get(email));
    }

    @Override
    public Partner create(Partner partner) {
        this.partner.put(partner.partnerId().value(), partner);
        this.partnerByCnpj.put(partner.cnpj().value(), partner);
        this.partnerByEmail.put(partner.email().value(), partner);
        return partner;
    }

    @Override
    public Partner update(Partner partner) {
        this.partner.put(partner.partnerId().value(), partner);
        this.partnerByCnpj.put(partner.cnpj().value(), partner);
        this.partnerByEmail.put(partner.email().value(), partner);
        return partner;
    }
}