package br.com.fullcycle.hexagonal.application.usecases;

import br.com.fullcycle.hexagonal.application.exceptions.ValidationException;
import br.com.fullcycle.hexagonal.infrastructure.models.Customer;
import br.com.fullcycle.hexagonal.infrastructure.services.CustomerService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.Optional;
import java.util.UUID;

import static org.mockito.Mockito.when;


public class CreateCustomerUseCaseTest {
    @DisplayName("Should create a customer")
    @Test
    public void testCreateCustomer() {
        // given
        final var expectedCPF = "12345678901";
        final var expectedEmail = "john.doe@gmail.com";
        final var expectedName = "John Doe";

        final var createCustomerInput = new CreateCustomerUseCase.input(expectedCPF, expectedEmail, expectedName);
        // when
        final var customerService = Mockito.mock(CustomerService.class);
        when(customerService.findByCpf(expectedCPF)).thenReturn(java.util.Optional.empty());
        when(customerService.findByEmail(expectedEmail)).thenReturn(java.util.Optional.empty());
        when(customerService.save(Mockito.any())).thenAnswer(invocationOnMock -> {
            final var customer = invocationOnMock.getArgument(0, Customer.class);
            customer.setId(UUID.randomUUID().getMostSignificantBits());
            return customer;
        });
        final var useCase = new CreateCustomerUseCase(customerService);
        final var output = useCase.execute(createCustomerInput);

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
        final var expectedCPF = "12345678901";
        final var expectedEmail = "johon.joe@gmail.com";
        final var expectedName = "John Doe";
        final var expectedError = "Customer already exists";

        final var createCustomerInput = new CreateCustomerUseCase.input(expectedCPF, expectedEmail, expectedName);

        final var aCustomer = new Customer();
        aCustomer.setId(UUID.randomUUID().getMostSignificantBits());
        aCustomer.setCpf(expectedCPF);
        aCustomer.setEmail(expectedEmail);
        aCustomer.setName(expectedName);
        // when
        final var customerService = Mockito.mock(CustomerService.class);
        when(customerService.findByCpf(expectedCPF)).thenReturn(Optional.of(aCustomer));


        final var useCase = new CreateCustomerUseCase(customerService);
        final var actualException = Assertions.assertThrows(ValidationException.class, () -> useCase.execute(createCustomerInput));

        // then
        Assertions.assertEquals(expectedError, actualException.getMessage());

    }

    @Test
    @DisplayName("Não deve cadastrar um cliente com e-mail duplicado")
    public void testCreateWithDuplicatedEmailShouldFail() throws Exception {
        // given
        final var expectedCPF = "12345678901";
        final var expectedEmail = "johon.joe@gmail.com";
        final var expectedName = "John Doe";
        final var expectedError = "Customer already exists";

        final var createCustomerInput = new CreateCustomerUseCase.input(expectedCPF, expectedEmail, expectedName);

        final var aCustomer = new Customer();
        aCustomer.setId(UUID.randomUUID().getMostSignificantBits());
        aCustomer.setCpf(expectedCPF);
        aCustomer.setEmail(expectedEmail);
        aCustomer.setName(expectedName);
        // when
        final var customerService = Mockito.mock(CustomerService.class);
        when(customerService.findByEmail(expectedEmail)).thenReturn(Optional.of(aCustomer));


        final var useCase = new CreateCustomerUseCase(customerService);
        final var actualException = Assertions.assertThrows(ValidationException.class, () -> useCase.execute(createCustomerInput));

        // then
        Assertions.assertEquals(expectedError, actualException.getMessage());

    }



}
