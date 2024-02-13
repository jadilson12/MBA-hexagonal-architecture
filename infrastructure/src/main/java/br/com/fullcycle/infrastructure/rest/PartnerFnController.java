package br.com.fullcycle.infrastructure.rest;

import br.com.fullcycle.application.partner.CreatePartnerUseCase;
import br.com.fullcycle.application.partner.GetPartnerByIDUseCase;
import br.com.fullcycle.domain.exceptions.ValidationException;
import br.com.fullcycle.infrastructure.dtos.NewPartnerDTO;
import br.com.fullcycle.infrastructure.http.HttpRouter;
import br.com.fullcycle.infrastructure.http.HttpRouter.HttpRequest;
import br.com.fullcycle.infrastructure.http.HttpRouter.HttpResponse;

import java.net.URI;
import java.util.Objects;

public class PartnerFnController {
  private final CreatePartnerUseCase createPartnerUseCase;
  private final GetPartnerByIDUseCase getPartnerByIDUseCase;

    public PartnerFnController(
            final CreatePartnerUseCase createPartnerUseCase,
            final GetPartnerByIDUseCase getPartnerByIDUseCase) {
        this.createPartnerUseCase = Objects.requireNonNull(createPartnerUseCase);
        this.getPartnerByIDUseCase = Objects.requireNonNull(getPartnerByIDUseCase);
    }

    public HttpRouter  bind(final HttpRouter router) {
        router.POST("/partners", this::create);
        router.GET("/partners/{id}", this::get);

        return router;
    }

    public HttpResponse<?> create(final HttpRequest request) {
        try {
            final var dto = request.body(NewPartnerDTO.class);
            final var  output =
                    createPartnerUseCase.execute(
                            new CreatePartnerUseCase.Input(dto.cnpj(), dto.email(), dto.name()));
            return HttpResponse.created(URI.create("/partners/" + output.id())).body(output);
        } catch (ValidationException ex) {
            return HttpResponse.unprocessableEntity().body(ex.getMessage());
        }
    }

    public HttpResponse<?> get(final HttpRequest request) {
        final String id = request.pathParans("id");
        return getPartnerByIDUseCase.execute(new GetPartnerByIDUseCase.Input(id))
                .map(HttpResponse::ok)
                .orElseGet(HttpResponse.notFound()::build);
    }

}
