package br.com.fullcycle.hexagonal.application.domain.customer;

import br.com.fullcycle.hexagonal.application.exceptions.ValidationException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class CustomerTest {
    @DisplayName("Deve instanciar um Customer com sucesso")
    @Test
    public void testCreateCustomer() {
        // given
        final var expectedCPF = "123.456.789-01";
        final var expectedEmail = "john.doe@gmail.com";
        final var expectedName = "John Doe";

        // when
        final var actualCustomer = Customer.newCustomer(expectedName, expectedCPF, expectedEmail);

        // then
        Assertions.assertNotNull(actualCustomer.customerId());
        Assertions.assertEquals(expectedCPF, actualCustomer.cpf().value());
        Assertions.assertEquals(expectedEmail, actualCustomer.email().value());
        Assertions.assertEquals(expectedName, actualCustomer.name().value());

    }

    @DisplayName("Deve lançar exceção ao tentar instanciar um Customer com CPF inválido")
    @Test
    public void testCreateCustomerWithInvalidCPF() {
        // given
        final var expectedEmail = "john.doe@gmail.com";
        final var expectedName = "John Doe";

        // when
        final var actualException = Assertions.assertThrows(ValidationException.class, () -> {
            Customer.newCustomer(expectedName, "123", expectedEmail);
        });

        // then
        Assertions.assertEquals("Invalid value for Cpf", actualException.getMessage());
    }

    @DisplayName("Deve lançar exceção ao tentar instanciar um Customer com Email inválido")
    @Test
    public void testCreateCustomerWithInvalidEmail() {
        // given
        final var expectedCPF = "123.456.789-01";
        final var expectedName = "John Doe";

        // when
        final var actualException = Assertions.assertThrows(ValidationException.class, () -> {
            Customer.newCustomer(expectedName, expectedCPF, "john.doe");
        });

        // then
        Assertions.assertEquals("Invalid value for Email", actualException.getMessage());
    }


    @DisplayName("Deve lançar exceção ao tentar instanciar um Customer com Nome inválido")
    @Test
    public void testCreateCustomerWithInvalidName() {
        // given
        final var expectedCPF = "123.456.789-01";
        final var expectedEmail = "jadilson@gmail.com";

        // when
        final var actualException = Assertions.assertThrows(ValidationException.class, () -> {
            Customer.newCustomer(null, expectedCPF, expectedEmail);
        });

        // then
        Assertions.assertEquals("Name is required", actualException.getMessage());
    }

}
