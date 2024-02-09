package br.com.fullcycle.hexagonal.infrastructure.repositories;

import br.com.fullcycle.hexagonal.application.domain.customer.Customer;
import br.com.fullcycle.hexagonal.application.domain.customer.CustomerId;
import br.com.fullcycle.hexagonal.application.domain.person.Cpf;
import br.com.fullcycle.hexagonal.application.domain.person.Email;
import br.com.fullcycle.hexagonal.application.repositories.CustomerRepository;
import br.com.fullcycle.hexagonal.infrastructure.jpa.entities.CustomerEntity;
import br.com.fullcycle.hexagonal.infrastructure.jpa.repositories.CustomerJpaRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Component;

import java.util.Objects;
import java.util.Optional;
import java.util.UUID;

// interface Adapter
@Component
public class CustomerDatabaseRepository implements CustomerRepository {

    private final CustomerJpaRepository customerJpaRepository;

    public CustomerDatabaseRepository(final CustomerJpaRepository customerJpaRepository) {
        this.customerJpaRepository = Objects.requireNonNull(customerJpaRepository);
    }

    @Override
    public Optional<Customer> customerOfId(final  CustomerId anId) {
        Objects.requireNonNull(anId, "CustomerId cannot be null");
        return this.customerJpaRepository.findById(UUID.fromString(anId.value()))
                .map(CustomerEntity::toDomain);
    }

    @Override
    public Optional<Customer> customerOfCpf(Cpf cpf) {
        Objects.requireNonNull(cpf, "Cpf cannot be null");
        return this.customerJpaRepository.findByCpf(cpf.value())
                .map(CustomerEntity::toDomain);
    }

    @Override
    public Optional<Customer> customerOfEmail(Email email) {
        Objects.requireNonNull(email, "Email cannot be null");
        return this.customerJpaRepository.findByEmail(email.value())
                .map(CustomerEntity::toDomain);
    }

    @Override
    @Transactional
    public Customer create(Customer customer) {
        return this.customerJpaRepository.save(CustomerEntity.of(customer))
                .toCustomer();
    }

    @Override
    @Transactional
    public Customer update(Customer customer) {
        return this.customerJpaRepository.save(CustomerEntity.of(customer))
                .toCustomer();
    }
}
