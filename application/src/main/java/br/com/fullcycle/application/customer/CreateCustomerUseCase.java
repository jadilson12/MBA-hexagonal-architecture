package br.com.fullcycle.application.customer;

import br.com.fullcycle.application.UseCase;
import br.com.fullcycle.domain.customer.Customer;
import br.com.fullcycle.domain.customer.CustomerRepository;
import br.com.fullcycle.domain.exceptions.ValidationException;
import br.com.fullcycle.domain.person.Cpf;
import br.com.fullcycle.domain.person.Email;

import java.util.Objects;

public class CreateCustomerUseCase extends UseCase<CreateCustomerUseCase.input, CreateCustomerUseCase.Output> {

    private CustomerRepository customerRepository;

    public CreateCustomerUseCase(CustomerRepository customerRepository) {
        this.customerRepository = Objects.requireNonNull(customerRepository);
    }

    @Override
    public Output execute(final input input) {
        if (customerRepository.customerOfCpf(new Cpf(input.cpf)).isPresent()) {
            throw new ValidationException("Customer already exists");
        }
        if (customerRepository.customerOfEmail(new Email(input.email)).isPresent()) {
            throw new ValidationException("Customer already exists");
        }


        var customer = customerRepository.create(Customer.newCustomer(input.name, input.cpf, input.email ));

        return new Output(customer.customerId().value(),customer.name().value(), customer.cpf().value(), customer.email()
                .value());
    }

    public record input(String name, String cpf, String email) {
    }

    public record Output(String id, String name, String cpf, String email) {
    }

}
