package br.com.fullcycle.hexagonal.application.usecases;

import br.com.fullcycle.hexagonal.application.exceptions.ValidationException;
import br.com.fullcycle.hexagonal.infrastructure.models.Partner;
import br.com.fullcycle.hexagonal.infrastructure.services.PartnerService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.Optional;
import java.util.UUID;

import static org.mockito.Mockito.when;


public class CreatePartnerUseCaseTest {
    @DisplayName("Deve criar uma parceiro com sucesso")
    @Test
    public void testCreatePartner() {
        // given
        final var expectedCNPJ = "41536538000100";
        final var expectedEmail = "john.doe@gmail.com";
        final var expectedName = "John Doe";

        final var createPartnerInput = new CreatePartnerUseCase.Input(expectedCNPJ, expectedEmail, expectedName);
        // when
        final var partnerService = Mockito.mock(PartnerService.class);
        when(partnerService.findByCnpj(expectedCNPJ)).thenReturn(Optional.empty());
        when(partnerService.findByEmail(expectedEmail)).thenReturn(Optional.empty());
        when(partnerService.save(Mockito.any())).thenAnswer(invocationOnMock -> {
            final var customer = invocationOnMock.getArgument(0, Partner.class);
            customer.setId(UUID.randomUUID().getMostSignificantBits());
            return customer;
        });
        final var useCase = new CreatePartnerUseCase(partnerService);
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
        final var expectedCNPJ = "41536538000100";
        final var expectedEmail = "johon.joe@gmail.com";
        final var expectedName = "John Doe";
        final var expectedError = "Partner already exists";

        final var createPartnerInput = new CreatePartnerUseCase.Input(expectedCNPJ, expectedEmail, expectedName);

        final var aPartner = new Partner();
        aPartner.setId(UUID.randomUUID().getMostSignificantBits());
        aPartner.setCnpj(expectedCNPJ);
        aPartner.setEmail(expectedEmail);
        aPartner.setName(expectedName);
        // when
        final var partnerService = Mockito.mock(PartnerService.class);
        when(partnerService.findByCnpj(expectedCNPJ)).thenReturn(Optional.of(aPartner));


        final var useCase = new CreatePartnerUseCase(partnerService);
        final var actualException = Assertions.assertThrows(ValidationException.class, () -> useCase.execute(createPartnerInput));

        // then
        Assertions.assertEquals(expectedError, actualException.getMessage());

    }

    @Test
    @DisplayName("Não deve cadastrar um parceiro com e-mail duplicado")
    public void testCreateWithDuplicatedEmailShouldFail() throws Exception {
        // given
        final var expectedCNPJ = "41536538000100";
        final var expectedEmail = "johon.joe@gmail.com";
        final var expectedName = "John Doe";
        final var expectedError = "Partner already exists";

        final var createPartnerInput = new CreatePartnerUseCase.Input(expectedCNPJ, expectedEmail, expectedName);

        final var aPartner = new Partner();
        aPartner.setId(UUID.randomUUID().getMostSignificantBits());
        aPartner.setCnpj(expectedCNPJ);
        aPartner.setEmail(expectedEmail);
        aPartner.setName(expectedName);
        // when
        final var partnerService = Mockito.mock(PartnerService.class);
        when(partnerService.findByEmail(expectedEmail)).thenReturn(Optional.of(aPartner));


        final var useCase = new CreatePartnerUseCase(partnerService);
        final var actualException = Assertions.assertThrows(ValidationException.class, () -> useCase.execute(createPartnerInput));

        // then
        Assertions.assertEquals(expectedError, actualException.getMessage());

    }



}
