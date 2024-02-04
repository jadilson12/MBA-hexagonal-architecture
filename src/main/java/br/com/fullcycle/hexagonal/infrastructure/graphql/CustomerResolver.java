package br.com.fullcycle.hexagonal.infrastructure.graphql;

import br.com.fullcycle.hexagonal.application.usecases.CreateCustomerUseCase;
import br.com.fullcycle.hexagonal.application.usecases.GetCustomerByIdUseCase;
import br.com.fullcycle.hexagonal.infrastructure.dtos.CustomerDTO;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.graphql.data.method.annotation.QueryMapping;

import java.util.Objects;

public class CustomerResolver {
    private final CreateCustomerUseCase createCustomerUseCase;
    private  final GetCustomerByIdUseCase getCustomerByIdUseCase;

    public CustomerResolver(
                            final CreateCustomerUseCase createCustomerUseCase,
                            final GetCustomerByIdUseCase getCustomerByIdUseCase) {
        this.createCustomerUseCase = Objects.requireNonNull(createCustomerUseCase);
        this.getCustomerByIdUseCase = Objects.requireNonNull(getCustomerByIdUseCase);

    }

    @MutationMapping
    public CreateCustomerUseCase.Output createCustomer(@Argument CustomerDTO input) {
        return createCustomerUseCase.execute(new CreateCustomerUseCase.input(input.getCpf(), input.getEmail(), input.getName()));
    }

    @QueryMapping
    public GetCustomerByIdUseCase.Output customerOfID(@Argument Long id) {
        return getCustomerByIdUseCase.execute(new GetCustomerByIdUseCase.Input(id)).orElse(null);
    }
}
