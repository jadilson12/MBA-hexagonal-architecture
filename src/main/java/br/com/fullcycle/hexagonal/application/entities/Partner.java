package br.com.fullcycle.hexagonal.application.entities;

import br.com.fullcycle.hexagonal.application.exceptions.ValidationException;

public class Partner {
    private PartnerId partnerId;
    private Name name;
    private Email email;
    private Cnpj cnpj;
    public Partner( final PartnerId partnerId, final String cnpj, final String email,  final String name) {

        if (partnerId == null) {
            throw new ValidationException("partnerId is required");
        }

        this.partnerId = partnerId;
        this.setCnpj(cnpj);
        this.setEmail(email);
        this.setName(name);

    }

    public PartnerId getPartnerId() {
        return partnerId;
    }

    public void setPartnerId(PartnerId customerId) {
        this.partnerId = customerId;
    }

    public Name name() {
        return name;
    }

    public void setName(String name) {
        this.name = new Name(name);
    }

    public Email email() {
        return email;
    }

    public void setEmail(String email) {
        this.email = new Email(email);
    }

    public Cnpj cnpj() {
        return cnpj;
    }

    public void setCnpj(String cnpj) {
        this.cnpj = new Cnpj(cnpj);
    }



    public  static Partner newPartner(
            final String cnpj,
            final String email,
            final String name
    ) {
        return new Partner(PartnerId.unique(),cnpj, email,name );
    }

}
