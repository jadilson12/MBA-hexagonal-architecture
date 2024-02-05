package br.com.fullcycle.hexagonal.application.usecases;

import br.com.fullcycle.hexagonal.application.UseCase;
import br.com.fullcycle.hexagonal.application.entities.Customer;
import br.com.fullcycle.hexagonal.application.exceptions.ValidationException;
import br.com.fullcycle.hexagonal.application.repositories.CustomerRepository;

public class CreateCustomerUseCase extends UseCase<CreateCustomerUseCase.input, CreateCustomerUseCase.Output> {

    private CustomerRepository customerRepository;

    public CreateCustomerUseCase(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    @Override
    public Output execute(final input input) {
        if (customerRepository.customerOfCpf(input.cpf).isPresent()) {
            throw new ValidationException("Customer already exists");
        }
        if (customerRepository.customerOfEmail(input.email).isPresent()) {
            throw new ValidationException("Customer already exists");
        }


        var customer = customerRepository.create(Customer.newCustomer(input.cpf, input.email,  input.name));

        return new Output(customer.customerId().value(), customer.cpf().value(), customer.email()
                .value(), customer.name().value());
    }

    public record input(String cpf, String email, String name) {
    }

    public record Output(String id, String cpf, String email, String name) {
    }

}
