package br.com.fullcycle.application.partner;

import br.com.fullcycle.application.repository.InMemoryPartnerRepository;
import br.com.fullcycle.domain.partner.Partner;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.UUID;

class GetPartnerByIDUseCaseTest {

    @Test
    @DisplayName("Deve obter um parceiro  por id")
    public void testGetById() throws Exception {

        // given
        final var expectedCNPJ = "41.536.538/0001-00";
        final var expectedEmail = "johon.joe@gmail.com";
        final var expectedName = "John Doe";

        final var aPartner = Partner.newPartner(expectedName, expectedCNPJ, expectedEmail);
        final var partnerRepository = new InMemoryPartnerRepository();
        partnerRepository.create(aPartner);

        final var expectedID = aPartner.partnerId().value();

        final var input = new GetPartnerByIDUseCase.Input(expectedID);

        // when
        final var useCase = new GetPartnerByIDUseCase(partnerRepository);
        final var output = useCase.execute(input).get();

        // then
        Assertions.assertEquals(expectedID, output.id());
        Assertions.assertEquals(expectedCNPJ, output.cnpj());
        Assertions.assertEquals(expectedEmail, output.email());
        Assertions.assertEquals(expectedName, output.name());

    }

    @Test
    @DisplayName("Deve obter vazio quando tentar recuperar um parceiro  que não existe por id")
    public void testGetByIdWithInvalidID() throws Exception {

        // given
        final var expectedID = UUID.randomUUID().toString();

        final var input = new GetPartnerByIDUseCase.Input(expectedID);
        // when
        final var partnerRepository = new InMemoryPartnerRepository();
        final var useCase = new GetPartnerByIDUseCase(partnerRepository);
        final var output = useCase.execute(input);

        // then
        Assertions.assertTrue(output.isEmpty());

    }

}