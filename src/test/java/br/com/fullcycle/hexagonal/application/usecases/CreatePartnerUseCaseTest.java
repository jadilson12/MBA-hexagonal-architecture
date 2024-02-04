package br.com.fullcycle.hexagonal.application.usecases;

import br.com.fullcycle.hexagonal.application.InMemoryPartnerRepository;
import br.com.fullcycle.hexagonal.application.entities.Partner;
import br.com.fullcycle.hexagonal.application.exceptions.ValidationException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;


public class CreatePartnerUseCaseTest {
    @DisplayName("Deve criar uma parceiro com sucesso")
    @Test
    public void testCreatePartner() {
        // given
        final var expectedCNPJ = "41.536.538/0001-00";
        final var expectedEmail = "john.doe@gmail.com";
        final var expectedName = "John Doe";

        final var partnerRepository = new InMemoryPartnerRepository();
        final var createPartnerInput = new CreatePartnerUseCase.Input(expectedCNPJ, expectedEmail, expectedName);
        // when

        final var useCase = new CreatePartnerUseCase(partnerRepository);
        final var output = useCase.execute(createPartnerInput);

        // then
        Assertions.assertNotNull(output.id());
        Assertions.assertEquals(expectedCNPJ, output.cnpj());
        Assertions.assertEquals(expectedEmail, output.email());
        Assertions.assertEquals(expectedName, output.name());

    }

    @Test
    @DisplayName("Não deve cadastrar um parceiro com CNPJ duplicado")
    public void testCreateWithDuplicatedCNPJShouldFail() throws Exception {
        // given
        final var expectedCNPJ = "41.536.538/0001-00";
        final var expectedEmail = "johon.joe@gmail.com";
        final var expectedName = "John Doe";
        final var expectedError = "Partner already exists";

        final var aPartner = Partner.newPartner("41.536.538/0002-00", expectedEmail, expectedName);
        final var partnerRepository = new InMemoryPartnerRepository();
        partnerRepository.create(aPartner);
        final var createPartnerInput = new CreatePartnerUseCase.Input(expectedCNPJ, expectedEmail, expectedName);

        // when
        final var useCase = new CreatePartnerUseCase(partnerRepository);
        final var actualException = Assertions.assertThrows(ValidationException.class, () -> useCase.execute(createPartnerInput));

        // then
        Assertions.assertEquals(expectedError, actualException.getMessage());

    }

    @Test
    @DisplayName("Não deve cadastrar um parceiro com e-mail duplicado")
    public void testCreateWithDuplicatedEmailShouldFail() throws Exception {
        // given
        final var expectedCNPJ = "41.536.538/0001-00";
        final var expectedEmail = "johon.joe@gmail.com";
        final var expectedName = "John Doe";
        final var expectedError = "Partner already exists";

        final var aPartner = Partner.newPartner("41.536.538/0002-00", expectedEmail, expectedName);
        final var partnerRepository = new InMemoryPartnerRepository();
        partnerRepository.create(aPartner);

        final var createPartnerInput = new CreatePartnerUseCase.Input(expectedCNPJ, expectedEmail, expectedName);

        // when
        final var useCase = new CreatePartnerUseCase(partnerRepository);
        final var actualException = Assertions.assertThrows(ValidationException.class, () -> useCase.execute(createPartnerInput));

        // then
        Assertions.assertEquals(expectedError, actualException.getMessage());

    }



}
