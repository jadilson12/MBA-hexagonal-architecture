package br.com.fullcycle.domain.person;

import br.com.fullcycle.domain.exceptions.ValidationException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class EmailTest {
    @DisplayName("Deve lançar exceção ao tentar instanciar com Email inválido")
    @Test
    public void testCreateWithInvalidEmail() {
        // given
        final var expectedError = "Invalid value for Email";
        // when
        final var actualException = Assertions.assertThrows(
                ValidationException.class, () ->
                        new Email("aa@")
        );

        // then
        Assertions.assertEquals(expectedError, actualException.getMessage());
    }

    @DisplayName("Deve instanciar com Email válido")
    @Test
    public void testCreateWithValidEmail() {
        // given
        final var expectedEmail = "asasa@asas.com";
        // when
        final var actualEmail = new Email(expectedEmail);
        // then
        Assertions.assertEquals(expectedEmail, actualEmail.value());
    }

    @DisplayName("Deve lançar exceção ao tentar instanciar com Email nulo")
    @Test
    public void testCreateWithNullEmail() {
        // given
        final var expectedError = "Invalid value for Email";
        // when
        final var actualException = Assertions.assertThrows(
                ValidationException.class, () ->
                        new Email(null)
        );

        // then
        Assertions.assertEquals(expectedError, actualException.getMessage());
    }


}
