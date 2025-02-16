package br.com.fullcycle.infrastructure.jpa.repositories;

import br.com.fullcycle.infrastructure.jpa.entities.TicketEntity;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;
import java.util.UUID;

public interface TicketJpaRepository extends CrudRepository<TicketEntity, UUID> {

    Optional<TicketEntity> findByEventIdAndCustomerId(UUID id, UUID customerId);
}
