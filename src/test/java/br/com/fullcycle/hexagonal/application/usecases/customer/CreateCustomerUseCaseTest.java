package br.com.fullcycle.hexagonal.application.usecases.customer;

import br.com.fullcycle.hexagonal.application.repository.InMemoryCustomerRepository;
import br.com.fullcycle.hexagonal.application.domain.customer.Customer;
import br.com.fullcycle.hexagonal.application.exceptions.ValidationException;
import br.com.fullcycle.hexagonal.application.usecases.customer.CreateCustomerUseCase;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;


public class CreateCustomerUseCaseTest {
    @DisplayName("Should create a customer")
    @Test
    public void testCreateCustomer() {
        // given
        final var expectedCPF = "123.456.789-01";
        final var expectedEmail = "john.doe@gmail.com";
        final var expectedName = "John Doe";

        final var customerRepository = new InMemoryCustomerRepository();
        final var createInput = new CreateCustomerUseCase.input(expectedCPF, expectedEmail, expectedName);
        // when
        final var useCase = new CreateCustomerUseCase(customerRepository);
        final var output = useCase.execute(createInput);

        // then
        Assertions.assertNotNull(output.id());
        Assertions.assertEquals(expectedCPF, output.cpf());
        Assertions.assertEquals(expectedEmail, output.email());
        Assertions.assertEquals(expectedName, output.name());

    }

    @Test
    @DisplayName("Não deve cadastrar um cliente com CPF duplicado")
    public void testCreateWithDuplicatedCPFShouldFail() throws Exception {
        // given
        final var expectedCPF = "123.456.789-01";
        final var expectedEmail = "johon.joe@gmail.com";
        final var expectedName = "John Doe";
        final var expectedError = "Customer already exists";

        final var aCustomer = Customer.newCustomer(expectedCPF, expectedEmail, expectedName);
        final var customerRepository = new InMemoryCustomerRepository();
        customerRepository.create(aCustomer);

        final var createInput = new CreateCustomerUseCase.input(expectedCPF, expectedEmail, expectedName);

        // when
        final var useCase = new CreateCustomerUseCase(customerRepository);
        final var actualException = Assertions.assertThrows(ValidationException.class, () -> useCase.execute(createInput));

        // then
        Assertions.assertEquals(expectedError, actualException.getMessage());

    }

    @Test
    @DisplayName("Não deve cadastrar um cliente com e-mail duplicado")
    public void testCreateWithDuplicatedEmailShouldFail() throws Exception {
        // given
        final var expectedCPF = "123.456.789-01";
        final var expectedEmail = "johon.joe@gmail.com";
        final var expectedName = "John Doe";
        final var expectedError = "Customer already exists";
        final var aCustomer = Customer.newCustomer(expectedCPF, expectedEmail, expectedName);
        final var customerRepository = new InMemoryCustomerRepository();
        customerRepository.create(aCustomer);

        final var createCustomerInput = new CreateCustomerUseCase.input(expectedCPF, expectedEmail, expectedName);


        // when
        final var useCase = new CreateCustomerUseCase(customerRepository);
        final var actualException = Assertions.assertThrows(ValidationException.class, () -> useCase.execute(createCustomerInput));

        // then
        Assertions.assertEquals(expectedError, actualException.getMessage());

    }
}

