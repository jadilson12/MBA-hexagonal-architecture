package br.com.fullcycle.hexagonal.application.domain.partner;

import br.com.fullcycle.hexagonal.application.domain.person.Cnpj;
import br.com.fullcycle.hexagonal.application.domain.person.Email;
import br.com.fullcycle.hexagonal.application.domain.person.Name;
import br.com.fullcycle.hexagonal.application.exceptions.ValidationException;

import java.util.Objects;

public class Partner {
    private final PartnerId partnerId;
    private Name name;
    private Email email;
    private Cnpj cnpj;

    public Partner(final PartnerId partnerId, final String name, final String cnpj, final String email) {
        if (partnerId == null) {
            throw new ValidationException("partnerId is required");
        }
        this.partnerId = partnerId;
        this.setCnpj(cnpj);
        this.setEmail(email);
        this.setName(name);
    }

    public static Partner newPartner(
            final String name,
            final String cnpj,
            final String email

    ) {
        return new Partner(PartnerId.unique(),name, cnpj, email);
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


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Partner partner = (Partner) o;
        return Objects.equals(partnerId, partner.partnerId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(partnerId);
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
