package br.com.fullcycle.hexagonal.application.usecases.customer;

import br.com.fullcycle.hexagonal.IntegrationTest;
import br.com.fullcycle.hexagonal.application.exceptions.ValidationException;
import br.com.fullcycle.hexagonal.application.repository.InMemoryCustomerRepository;
import br.com.fullcycle.hexagonal.infrastructure.models.Customer;
import br.com.fullcycle.hexagonal.infrastructure.repositories.CustomerRepository;
import br.com.fullcycle.hexagonal.infrastructure.services.CustomerService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.UUID;

public class CreateCustomerUseCaseIT  extends IntegrationTest {

    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private CustomerService customerService;

    @Autowired
    private CreateCustomerUseCase useCase;

    @AfterEach
    void tearDown() {
        customerRepository.deleteAll();
    }
    @DisplayName("Deve criar um evento")
    @Test
    public void testCreateCustomer() {
        // given
        final var expectedCPF = "123.456.789-01";
        final var expectedEmail = "john.doe@gmail.com";
        final var expectedName = "John Doe";

        final var createCustomerInput = new CreateCustomerUseCase.input(expectedCPF, expectedEmail, expectedName);
        // when

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
        final var expectedCPF = "123.456.789-01";
        final var expectedEmail = "johon.joe@gmail.com";
        final var expectedName = "John Doe";
        final var expectedError = "Customer already exists";

        createCustomer(expectedCPF, expectedEmail, expectedName);

        final var createCustomerInput = new CreateCustomerUseCase.input(expectedCPF, expectedEmail, expectedName);

        // when
        final var actualException = Assertions.assertThrows(ValidationException.class, () -> useCase.execute(createCustomerInput));

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

        createCustomer("12345600901", expectedEmail, expectedName);
        final var createCustomerInput = new CreateCustomerUseCase.input(expectedCPF, expectedEmail, expectedName);

        // when
        final var actualException = Assertions.assertThrows(ValidationException.class, () -> useCase.execute(createCustomerInput));

        // then
        Assertions.assertEquals(expectedError, actualException.getMessage());

    }

    private Customer createCustomer(final String cpf, final String email,final String name) {
        final var aCustomer = new Customer();
        aCustomer.setCpf(cpf);
        aCustomer.setEmail(email);
        aCustomer.setName(name);
        return customerService.save(aCustomer);
    }

    static class GetCustomerByIdUseCaseTest {

        @Test
        @DisplayName("Deve obter um cliente por id")
        public void testGetById() throws Exception {

            // given
            final var expectedCPF = "123.456.789-01";
            final var expectedEmail = "john.doe@gmail.com";
            final var expectedName = "John Doe";

            final var aCustomer = br.com.fullcycle.hexagonal.application.domain.customer.Customer.newCustomer(expectedCPF, expectedEmail, expectedName);
            final var customerRepository = new InMemoryCustomerRepository();
            customerRepository.create(aCustomer);

            final var expectedID = aCustomer.customerId().value();

            final var input = new GetCustomerByIdUseCase.Input(expectedID);
            // when

            final var useCase = new GetCustomerByIdUseCase(customerRepository);
            final var output = useCase.execute(input).get();

            // then
            Assertions.assertEquals(expectedID, output.id());
            Assertions.assertEquals(expectedCPF, output.cpf());
            Assertions.assertEquals(expectedEmail, output.email());
            Assertions.assertEquals(expectedName, output.name());

        }

        @Test
        @DisplayName("Deve obter vazio quando tentar recuperar um cliente que não existe por id")
        public void testGetByIdWithInvalidID() throws Exception {

            // given
            final var expectedID = UUID.randomUUID().toString();

            final var input = new GetCustomerByIdUseCase.Input(expectedID);
            // when
            final var customerRepository = new InMemoryCustomerRepository();


            final var useCase = new GetCustomerByIdUseCase(customerRepository);
            final var output = useCase.execute(input);

            // then
            Assertions.assertTrue(output.isEmpty());

        }

    }
}
