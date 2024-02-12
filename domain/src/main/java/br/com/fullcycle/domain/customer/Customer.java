package br.com.fullcycle.domain.customer;

import br.com.fullcycle.domain.exceptions.ValidationException;
import br.com.fullcycle.domain.person.Cpf;
import br.com.fullcycle.domain.person.Email;
import br.com.fullcycle.domain.person.Name;

import java.util.Objects;
import java.util.Optional;

public class Customer {
    private final CustomerId customerId;
    private Name name;
    private Email email;
    private Cpf cpf;
    public Customer(final CustomerId customerId,final String name, final String cpf, final String email) {
        if (customerId == null) {
            throw new ValidationException("Invalid customerId for Customer");
        }
        this.customerId = customerId;
        this.setName(name);
        this.setCpf(cpf);
        this.setEmail(email);
    }

    public  static Customer newCustomer(
            final String name,
            final String cpf,
            final String email
    ) {
        return new Customer(CustomerId.unique(),name, cpf, email);
    }
    public CustomerId customerId() {
        return customerId;
    }

    public Name name() {
        return name;
    }

    public Email email() {
        return email;
    }

    public Cpf cpf() {
        return cpf;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Customer customer = (Customer) o;
        return Objects.equals(customerId, customer.customerId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(customerId);
    }

    private void setCpf(final String cpf) {
        this.cpf = new Cpf(cpf);
    }

    private void setEmail(final String email) {
        this.email = new Email(email);
    }

    private void setName(final String name) {
        this.name = new Name(name);
    }

    public static interface CustomerRepository {
        Optional<Customer> customerOfId(CustomerId customerId);
        Optional<Customer> customerOfCpf(Cpf cpf);
        Optional<Customer> customerOfEmail(Email email);
        Customer create(Customer customer);
        Customer update(Customer customer);
        void deleteAll();
    }
}
