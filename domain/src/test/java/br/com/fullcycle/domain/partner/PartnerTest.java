package br.com.fullcycle.domain.partner;

import br.com.fullcycle.domain.exceptions.ValidationException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class PartnerTest {
    @DisplayName("Deve instanciar um partner com sucesso")
    @Test
    public void testCreatePartner() {
        // given
        final var expectedCNPJ = "41.536.538/0001-00";
        final var expectedEmail = "john.doe@gmail.com";
        final var expectedName = "John Doe";

        // when
        final var actualPartner = Partner.newPartner(expectedName, expectedCNPJ, expectedEmail);

        // then
        Assertions.assertNotNull(actualPartner.partnerId());
        Assertions.assertEquals(expectedCNPJ, actualPartner.cnpj().value());
        Assertions.assertEquals(expectedEmail, actualPartner.email().value());
        Assertions.assertEquals(expectedName, actualPartner.name().value());

    }

    @DisplayName("Deve lançar exceção ao tentar instanciar um partner com CNPJ inválido")
    @Test
    public void testCreatePartnerWithInvalidCNPJ() {
        // given
        final var expectedEmail = "jadilson@gmail.com";
        final var expectedName = "Jadilson";

        // when
        final var actualException = Assertions.assertThrows(ValidationException.class, () -> {
            Partner.newPartner(expectedName, "123", expectedEmail);
        });

        // then
        Assertions.assertEquals("Invalid value for Cnpj", actualException.getMessage());
    }

    @DisplayName("Deve lançar exceção ao tentar instanciar um partner com Email inválido")
    @Test
    public void testCreatePartnerWithInvalidEmail() {
        // given
        final var expectedCNPJ = "41.536.538/0001-00";
        final var expectedName = "John Doe";

        // when
        final var actualException = Assertions.assertThrows(ValidationException.class, () -> {
            Partner.newPartner(expectedName, expectedCNPJ, "john.doe");
        });

        // then
        Assertions.assertEquals("Invalid value for Email", actualException.getMessage());
    }


    @Test
    @DisplayName("Deve lançar exceção ao tentar instanciar um partner com Nome inválido")
    public void testCreatePartnerWithInvalidName() {
        // given
        final var expectedCNPJ = "41.536.538/0001-00";
        final var expectedEmail = "jadilson@gmail.com";

        // when
        final var actualException = Assertions.assertThrows(ValidationException.class, () -> {
            Partner.newPartner(null, expectedCNPJ, expectedEmail);
        });

        // then
        Assertions.assertEquals("Name is required", actualException.getMessage());

    }

}
