package br.com.fullcycle.hexagonal.application.repository;

import br.com.fullcycle.hexagonal.application.domain.customer.Customer;
import br.com.fullcycle.hexagonal.application.domain.customer.CustomerId;
import br.com.fullcycle.hexagonal.application.repositories.CustomerRepository;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;

public class InMemoryCustomerRepository implements CustomerRepository {
    private final Map<String, Customer> customers;
    private final Map<String, Customer> customersByEmail;
    private final Map<String, Customer> customersByCpf;

    public InMemoryCustomerRepository() {
        this.customers = new HashMap<>();
        this.customersByEmail = new HashMap<>();
        this.customersByCpf = new HashMap<>();
    }

    @Override
    public Optional<Customer> customerOfId(CustomerId customerId) {
        return Optional.ofNullable(this.customers.get(Objects.requireNonNull(customerId).value().toString()));
    }

    @Override
    public Optional<Customer> customerOfCpf(String cpf) {
        return Optional.ofNullable(this.customersByCpf.get(cpf));
    }

    @Override
    public Optional<Customer> customerOfEmail(String email) {
        return Optional.ofNullable(this.customersByEmail.get(email));
    }

    @Override
    public Customer create(Customer customer) {
        this.customers.put(customer.customerId().toString(), customer);
        this.customersByCpf.put(customer.cpf().value(), customer);
        this.customersByEmail.put(customer.email().value(), customer);
        return customer;
    }

    @Override
    public Customer update(Customer customer) {
        this.customers.put(customer.customerId().toString(), customer);
        this.customersByCpf.put(customer.cpf().value(), customer);
        this.customersByEmail.put(customer.email().value(), customer);
        return customer;
    }
}