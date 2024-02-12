package br.com.fullcycle.domain.event.ticket;

import br.com.fullcycle.domain.customer.Customer;
import br.com.fullcycle.domain.event.Event;
import br.com.fullcycle.domain.partner.Partner;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class TicketTest {
    @Test
    @DisplayName("Deve reservar um ticket quando o evento tiver vagas")
    public void testReserveTicket() {
        // given
        final var aPartner = Partner.newPartner("John Do", "41.536.538/0001-00", "jadilson@gm.com");

        final var aCustomer = Customer.newCustomer("John Do", "123.456.789-00", "jadilson@gm.com");
        final var aEvent = Event.newEvent("Disney on Ice", "2021-01-01", 10, aPartner);

        final var expectedEventId = aEvent.eventId().value();
        final var expectedCustomerId = aCustomer.customerId().value();

        // when
        final var actualTicket = Ticket.newTicket(aCustomer.customerId(), aEvent.eventId());

        // then
        Assertions.assertNotNull(actualTicket.eventId());
        Assertions.assertNotNull(actualTicket.reservedAt());
        Assertions.assertNotNull(actualTicket.ticketId());
        Assertions.assertEquals(expectedCustomerId, actualTicket.customerId().value());
        Assertions.assertEquals(expectedEventId, actualTicket.eventId().value());
        Assertions.assertEquals(TicketStatus.PENDING, actualTicket.status());
    }


}
