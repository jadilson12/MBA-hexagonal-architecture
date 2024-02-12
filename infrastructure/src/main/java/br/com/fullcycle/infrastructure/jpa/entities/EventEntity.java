package br.com.fullcycle.infrastructure.jpa.entities;

import br.com.fullcycle.domain.event.Event;
import br.com.fullcycle.domain.event.EventTicket;
import jakarta.persistence.*;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

@Entity(name = "Event")
@Table(name = "events")
public class EventEntity {

    @Id
    private UUID id;

    private String name;

    private LocalDate date;

    private int totalSpots;

    private UUID partnerId;

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER, mappedBy = "event")
    private Set<EventTicketEntity> tickets;

    public EventEntity() {
        this.tickets = new HashSet<>();
    }

    public EventEntity(UUID id, String name, LocalDate date, int totalSpots, UUID partnerId) {
        this();
        this.id = id;
        this.name = name;
        this.date = date;
        this.totalSpots = totalSpots;
        this.partnerId = partnerId;
    }

    public static EventEntity of(final Event event) {
        final var entity = new EventEntity(
                UUID.fromString(event.eventId().value()),
                event.name().value(),
                event.date(),
                event.totalSpots(),
                UUID.fromString(event.partnerId().value())
        );

        event.allTickets().forEach(entity::addTicket);
     return entity;
    }

    public Event toEvent() {
        return Event.restore(
                this.id.toString(),
                this.name(),
                this.date().format(DateTimeFormatter.ISO_LOCAL_DATE),
                this.totalSpots(),
                this.partnerId().toString(),
                this.tickets().stream().map(EventTicketEntity::toEventTicket).collect(Collectors.toSet()
        ));
    }

    private void addTicket(final EventTicket ticket) {
        this.tickets.add(EventTicketEntity.of(this, ticket));

    }

    public UUID id() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String name() {
        return name;
    }

    public EventEntity setName(String name) {
        this.name = name;
        return this;
    }

    public LocalDate date() {
        return date;
    }

    public EventEntity setDate(LocalDate date) {
        this.date = date;
        return this;
    }

    public int totalSpots() {
        return totalSpots;
    }

    public EventEntity setTotalSpots(int totalSpots) {
        this.totalSpots = totalSpots;
        return this;
    }

    public UUID partnerId() {
        return partnerId;
    }

    public EventEntity setPartnerId(UUID partnerId) {
        this.partnerId = partnerId;
        return this;
    }

    public Set<EventTicketEntity> tickets() {
        return tickets;
    }

    public void setTickets(Set<EventTicketEntity> tickets) {
        this.tickets = tickets;
    }
}
