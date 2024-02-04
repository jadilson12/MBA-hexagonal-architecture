package br.com.fullcycle.hexagonal.application.usecases;

import br.com.fullcycle.hexagonal.application.UseCase;
import br.com.fullcycle.hexagonal.infrastructure.services.CustomerService;

import java.util.Optional;

public class GetCustomerByIdUseCase extends UseCase<GetCustomerByIdUseCase.Input, Optional<GetCustomerByIdUseCase.Output>> {


    public GetCustomerByIdUseCase(CustomerService customerService) {
        this.customerService = customerService;
    }

    public record Input(Long id){}

    public record Output(Long id, String cpf, String email, String name ){}

    private final CustomerService customerService;
    public Optional<Output> execute(final Input input){
        return customerService.findById(input.id()).map(c -> new Output(c.getId(), c.getCpf(),c.getEmail(),c.getName()));
    }
}
