package br.com.fullcycle.hexagonal.application.domain.person;

import br.com.fullcycle.hexagonal.application.exceptions.ValidationException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class CpfTest {
    @DisplayName("Deve lançar exceção ao tentar instanciar com Cpf inválido")
    @Test
    public void testCreateWithInvalidCpf() {
        // given
        final var expectedError = "Invalid value for Cpf";
        // when
        final var actualException = Assertions.assertThrows(
                ValidationException.class, () ->
                        new Cpf("123")
        );

        // then
        Assertions.assertEquals(expectedError, actualException.getMessage());
    }

    @DisplayName("Deve instanciar com Cpf válido")
    @Test
    public void testCreateWithValidCpf() {
        // given
        final var expectedCpf = "548.865.878-88";
        // when
        final var actualCpf = new Cpf(expectedCpf);
        // then
        Assertions.assertEquals(expectedCpf, actualCpf.value());
    }

    @DisplayName("Deve lançar exceção ao tentar instanciar com Cpf nulo")
    @Test
    public void testCreateWithNullCpf() {
        // given
        final var expectedError = "Invalid value for Cpf";
        // when
        final var actualException = Assertions.assertThrows(
                ValidationException.class, () ->
                        new Cpf(null)
        );

        // then
        Assertions.assertEquals(expectedError, actualException.getMessage());
    }


}
