package br.com.fullcycle.domain.person;

import br.com.fullcycle.domain.exceptions.ValidationException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class CnpjTest {
    @DisplayName("Deve lançar exceção ao tentar instanciar com CNPJ inválido")
    @Test
    public void testCreateWithInvalidCNPJ() {
        // given
        final var expectedError = "Invalid value for Cnpj";
        // when
        final var actualException = Assertions.assertThrows(
                ValidationException.class, () ->
                        new Cnpj("123")
        );

        // then
        Assertions.assertEquals(expectedError, actualException.getMessage());
    }

    @DisplayName("Deve instanciar com CNPJ válido")
    @Test
    public void testCreateWithValidCNPJ() {
        // given
        final var expectedCNPJ = "41.536.538/0001-00";
        // when
        final var actualCnpj = new Cnpj(expectedCNPJ);
        // then
        Assertions.assertEquals(expectedCNPJ, actualCnpj.value());
    }

    @DisplayName("Deve lançar exceção ao tentar instanciar com CNPJ nulo")
    @Test
    public void testCreateWithNullCNPJ() {
        // given
        final var expectedError = "Invalid value for Cnpj";
        // when
        final var actualException = Assertions.assertThrows(
                ValidationException.class, () ->
                        new Cnpj(null)
        );

        // then
        Assertions.assertEquals(expectedError, actualException.getMessage());
    }


}
