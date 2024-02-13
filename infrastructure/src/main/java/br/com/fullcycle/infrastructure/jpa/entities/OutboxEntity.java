package br.com.fullcycle.infrastructure.jpa.entities;

import br.com.fullcycle.domain.DomainEvent;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

import java.util.Objects;
import java.util.UUID;
import java.util.function.Function;

@Entity(name = "OutBox")
@Table(name = "outbox")
public class OutboxEntity {

    @Id
    private UUID id;

    private String content;

    private boolean published;

    public OutboxEntity() {
    }

    public OutboxEntity(
            final UUID id,
            final String content,
            final boolean published
    ) {
        this.id = id;
        this.content = content;
        this.published = published;
    }

    public static OutboxEntity of(
            final DomainEvent domainEvent,
            final Function<DomainEvent, String> toJson
    ) {
        return new OutboxEntity(
                UUID.fromString(domainEvent.domainEventId()),
                toJson.apply(domainEvent),
                false
        );
    }

    public UUID id() {
        return id;
    }

    public OutboxEntity setId(UUID id) {
        this.id = id;
        return this;
    }

    public String content() {
        return content;
    }

    public OutboxEntity setContent(String content) {
        this.content = content;
        return this;
    }

    public boolean published() {
        return published;
    }

    public OutboxEntity setPublished(boolean published) {
        this.published = published;
        return this;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        OutboxEntity that = (OutboxEntity) o;
        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}