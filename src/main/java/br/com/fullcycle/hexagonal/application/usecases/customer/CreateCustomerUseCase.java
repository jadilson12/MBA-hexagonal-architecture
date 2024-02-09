package br.com.fullcycle.hexagonal.application.usecases.customer;

import br.com.fullcycle.hexagonal.application.domain.person.Cpf;
import br.com.fullcycle.hexagonal.application.domain.person.Email;
import br.com.fullcycle.hexagonal.application.usecases.UseCase;
import br.com.fullcycle.hexagonal.application.domain.customer.Customer;
import br.com.fullcycle.hexagonal.application.exceptions.ValidationException;
import br.com.fullcycle.hexagonal.application.repositories.CustomerRepository;

public class CreateCustomerUseCase extends UseCase<CreateCustomerUseCase.input, CreateCustomerUseCase.Output> {

    private CustomerRepository customerRepository;

    public CreateCustomerUseCase(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
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

        return new Output(customer.customerId().value(), customer.cpf().value(), customer.email()
                .value(), customer.name().value());
    }

    public record input(String name, String cpf, String email) {
    }

    public record Output(String id, String cpf, String email, String name) {
    }

}
