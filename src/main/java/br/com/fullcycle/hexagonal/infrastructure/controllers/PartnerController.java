package br.com.fullcycle.hexagonal.infrastructure.controllers;

import br.com.fullcycle.hexagonal.application.exceptions.ValidationException;
import br.com.fullcycle.hexagonal.application.usecases.CreatePartnerUseCase;
import br.com.fullcycle.hexagonal.application.usecases.GetPartnerByIDUseCase;
import br.com.fullcycle.hexagonal.infrastructure.dtos.PartnerDTO;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.Objects;

@RestController
@RequestMapping(value = "partners")
public class PartnerController {
  private final CreatePartnerUseCase createPartnerUseCase;
  private final GetPartnerByIDUseCase getPartnerByIDUseCase;

    public PartnerController(
            final CreatePartnerUseCase createPartnerUseCase,
            final GetPartnerByIDUseCase getPartnerByIDUseCase) {
        this.createPartnerUseCase = Objects.requireNonNull(createPartnerUseCase);
        this.getPartnerByIDUseCase = Objects.requireNonNull(getPartnerByIDUseCase);
    }

    @PostMapping
    public ResponseEntity<?> create(@RequestBody PartnerDTO dto) {
        try {
            final var  output =
                    createPartnerUseCase.execute(
                            new CreatePartnerUseCase.Input(dto.getCnpj(), dto.getEmail(), dto.getName()));
            return ResponseEntity.created(URI.create("/partners/" + output.id())).body(output);
        } catch (ValidationException ex) {
            return ResponseEntity.unprocessableEntity().body(ex.getMessage());
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> get(@PathVariable Long id) {
        return getPartnerByIDUseCase.execute(new GetPartnerByIDUseCase.Input(id))
                .map(ResponseEntity::ok)
                .orElseGet(ResponseEntity.notFound()::build);
    }

}
