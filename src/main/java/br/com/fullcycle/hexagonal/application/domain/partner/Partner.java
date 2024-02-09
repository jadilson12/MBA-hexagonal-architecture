package br.com.fullcycle.hexagonal.application.domain.partner;

import br.com.fullcycle.hexagonal.application.domain.person.Cnpj;
import br.com.fullcycle.hexagonal.application.domain.person.Email;
import br.com.fullcycle.hexagonal.application.domain.person.Name;
import br.com.fullcycle.hexagonal.application.exceptions.ValidationException;

public class Partner {
    private final PartnerId partnerId;
    private Name name;
    private Email email;
    private Cnpj cnpj;

    public Partner(final PartnerId partnerId, final String cnpj, final String email, final String name) {

        if (partnerId == null) {
            throw new ValidationException("partnerId is required");
        }

        this.partnerId = partnerId;
        this.setCnpj(cnpj);
        this.setEmail(email);
        this.setName(name);

    }

    public PartnerId partnerId() {
        return partnerId;
    }

    public Name name() {
        return name;
    }

    public Email email() {
        return email;
    }
    public Cnpj cnpj() {
        return cnpj;
    }

    public static Partner newPartner(
            final String name,
            final String cnpj,
            final String email

    ) {
        return new Partner(PartnerId.unique(), cnpj, email, name);
    }

    private void setName(String name) {
        this.name = new Name(name);
    }

    private void setCnpj(String cnpj) {
        this.cnpj = new Cnpj(cnpj);
    }

    private void setEmail(String email) {
        this.email = new Email(email);
    }
}
