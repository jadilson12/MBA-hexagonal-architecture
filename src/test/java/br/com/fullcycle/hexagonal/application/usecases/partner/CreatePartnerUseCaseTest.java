package br.com.fullcycle.hexagonal.application.usecases.partner;

import br.com.fullcycle.hexagonal.application.repository.InMemoryPartnerRepository;
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

}
