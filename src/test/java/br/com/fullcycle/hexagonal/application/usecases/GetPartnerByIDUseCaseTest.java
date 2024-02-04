package br.com.fullcycle.hexagonal.application.usecases;

import br.com.fullcycle.hexagonal.infrastructure.models.Partner;
import br.com.fullcycle.hexagonal.infrastructure.services.PartnerService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.Optional;
import java.util.UUID;

import static org.mockito.Mockito.when;

class GetPartnerByIDUseCaseTest {

    @Test
    @DisplayName("Deve obter um parceiro  por id")
    public void testGetById() throws Exception {

        // given
        final var expectedID = UUID.randomUUID().getMostSignificantBits();
        final var expectedCNPJ = "41536538000100";
        final var expectedEmail = "johon.joe@gmail.com";
        final var expectedName = "John Doe";

        final var aPartner = new Partner();
        aPartner.setId(expectedID);
        aPartner.setCnpj(expectedCNPJ);
        aPartner.setEmail(expectedEmail);
        aPartner.setName(expectedName);

        final var input = new GetPartnerByIDUseCase.Input(expectedID);
        // when
        final var partnerService = Mockito.mock(PartnerService.class);
        when(partnerService.findById(expectedID)).thenReturn(Optional.of(aPartner));


        final var useCase = new GetPartnerByIDUseCase(partnerService);
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
        final var expectedID = UUID.randomUUID().getMostSignificantBits();

        final var input = new GetPartnerByIDUseCase.Input(expectedID);
        // when
        final var partnerService = Mockito.mock(PartnerService.class);
        when(partnerService.findById(expectedID)).thenReturn(Optional.empty());


        final var useCase = new GetPartnerByIDUseCase(partnerService);
        final var output = useCase.execute(input);

        // then
        Assertions.assertTrue(output.isEmpty());

    }

}