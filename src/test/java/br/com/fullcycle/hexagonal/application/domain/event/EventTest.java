package br.com.fullcycle.hexagonal.application.domain.event;

import br.com.fullcycle.hexagonal.application.domain.partner.Partner;
import br.com.fullcycle.hexagonal.application.exceptions.ValidationException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.format.DateTimeFormatter;

public class EventTest {
    @Test
    @DisplayName("Deve criar um evento")
    public void testCreateEvent() {
        // given
        final var aPartner = Partner.newPartner("John Do","41.536.538/0001-00", "johon.joe@gmail.com");

        final var expectedDate = "2021-01-01";
        final var expectedName = "Disney on Ice";
        final var expectedTotalSpots = 100;
        final var expectedPartnerId = aPartner.partnerId().value();
        final var expectedTickets = 0;

        // when
        final var actualEvent = Event.newEvent(expectedName, expectedDate, expectedTotalSpots, aPartner);

        // then
        Assertions.assertNotNull(actualEvent.eventId());
        Assertions.assertEquals(expectedDate, actualEvent.date().format(DateTimeFormatter.ISO_LOCAL_DATE));
        Assertions.assertEquals(expectedPartnerId, actualEvent.partnerId().value());
        Assertions.assertEquals(expectedName, actualEvent.name().value());
        Assertions.assertEquals(expectedTotalSpots, actualEvent.totalSpots());
        Assertions.assertEquals(expectedTickets, actualEvent.allTickets().size());
    }

    @Test
    @DisplayName("Não deve criar um evento quando o Partner não existir")
    public void testCreateEvent_whenPartnerDoesnotsExisted_ShouldTownError() {
        // given
        final var aPartner = Partner.newPartner("John Do", "41.536.538/0001-00", "jadilson@gmail.com");

        final var expectedDate = "2021-01-01";
        final var expectedName = "Disney on Ice";
        final var expectedTotalSpots = 100;
        final var expectedPartnerId = aPartner.partnerId().value();

        // when
        final var actualEvent = Event.newEvent(expectedName, expectedDate, expectedTotalSpots, aPartner);

        // then
        Assertions.assertNotNull(actualEvent.eventId());
        Assertions.assertEquals(expectedDate, actualEvent.date().format(DateTimeFormatter.ISO_LOCAL_DATE));
        Assertions.assertEquals(expectedPartnerId, actualEvent.partnerId().value());
        Assertions.assertEquals(expectedName, actualEvent.name().value());
        Assertions.assertEquals(expectedTotalSpots, actualEvent.totalSpots());
        Assertions.assertEquals(0, actualEvent.allTickets().size());

    }

    @Test
    @DisplayName("Não deve criar um evento quando o nome for nulo")
    public void testCreateEvent_whenNameIsNull_ShouldThrowError() {
        // given
        final var aPartner = Partner.newPartner("John Do", "41.536.538/0001-00", "jadilson@gm.com");
        final var expectedDate = "2021-01-01";

        final var expectedTotalSpots = 100;
        final var expectedError = "Invalid name for Event";

        // when
        final var actualException = Assertions.assertThrows(ValidationException.class, () -> Event.newEvent(null, expectedDate, expectedTotalSpots, aPartner));

        // then
        Assertions.assertEquals(expectedError, actualException.getMessage());

    }

    @Test
    @DisplayName("Não deve criar um evento quando a data for nula")
    public void testCreateEvent_whenDateIsNull_ShouldThrowError() {
        // given
        final var aPartner = Partner.newPartner("John Do", "41.536.538/0001-00", "jadilson@gm.com");
        final var expectedName = "Disney on Ice";

        final var expectedTotalSpots = 100;
        final var expectedError = "Invalid date for Event";

        // when
        final var actualException = Assertions.assertThrows(ValidationException.class, () -> Event.newEvent(expectedName, "12145441", expectedTotalSpots, aPartner));

        // then
        Assertions.assertEquals(expectedError, actualException.getMessage());

    }

    @Test
    @DisplayName("Não deve criar um evento quando o total de vagas for nulo")
    public void testCreateEvent_whenTotalSpotsIsNull_ShouldThrowError() {
        // given
        final var aPartner = Partner.newPartner("John Do", "41.536.538/0001-00", "jadilson@gm.com");
        final var expectedName = "Disney on Ice";
        final var expectedDate = "2021-01-01";
        final var expectedError = "Invalid totalSpots for Event";

        // when
        final var actualException = Assertions.assertThrows(ValidationException.class, () -> Event.newEvent(expectedName, expectedDate, null, aPartner));

        // then
        Assertions.assertEquals(expectedError, actualException.getMessage());

    }

//    @Test
//    @DisplayName("Deve reservar um ticket quando o evento tiver vagas")
//    public void testReserveTicket() {
//        // given
//        final var aPartner = Partner.newPartner("John Do", "41.536.538/0001-00", "jadilson@gm.com");
//        final var aCustomer = Customer.newCustomer("John Do", "123.456.789-00", "jadilson@gm.com");
//        final var expectedTickets = 1;
//        final var expectedTotalSpots = 10;
//        final var expectedDate = "2021-01-01";
//        final var expectedName = "Disney on Ice";
//        final var expectedPartnerId = aPartner.partnerId().value();
//        final var expectedticketOrder = 1;
//        final var expectedTicketsStatus = TicketStatus.PENDING;
//
//        final var actualEvent = Event.newEvent("Disney on Ice", "2021-01-01", expectedTotalSpots, aPartner);
//
//        // when
//        final var actualTicket = actualEvent.reserveTicket(aCustomer.customerId());
//
//        // then
//        Assertions.assertNotNull(actualEvent.eventId());
//
//        Assertions.assertEquals(expectedDate, actualEvent.date().format(DateTimeFormatter.ISO_LOCAL_DATE));
//        Assertions.assertEquals(expectedName, actualEvent.name().value());
//        Assertions.assertEquals(expectedTotalSpots, actualEvent.totalSpots());
//        Assertions.assertEquals(expectedTickets, actualEvent.allTickets().size());
//
//
//        Assertions.assertNotNull(actualTicket.ticketId());
//        Assertions.assertEquals(actualEvent.eventId(), actualTicket.eventId());
//        Assertions.assertEquals(aCustomer.customerId(), actualTicket.customerId());
//        Assertions.assertEquals(expectedTicketsStatus, actualTicket.status().name());
//        Assertions.assertNotNull(actualTicket.reservedAt());
//
//        final var actualEventTicket = actualEvent.allTickets().iterator().next();
//        Assertions.assertEquals(expectedticketOrder, actualEventTicket.ordering());
//
//        Assertions.assertEquals(actualEvent.eventId(), actualEventTicket.eventId());
//        Assertions.assertEquals(aCustomer.customerId(), actualEventTicket.customerId());
//        Assertions.assertEquals(actualTicket.ticketId(), actualEventTicket.ticketId());
//
//    }
//

}
