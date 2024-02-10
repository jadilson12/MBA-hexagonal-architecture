package br.com.fullcycle.hexagonal.application.usecases.event;

import br.com.fullcycle.hexagonal.application.repository.InMemoryCustomerRepository;
import br.com.fullcycle.hexagonal.application.repository.InMemoryEventRepository;
import br.com.fullcycle.hexagonal.application.repository.InMemoryTicketRepository;
import br.com.fullcycle.hexagonal.application.domain.customer.Customer;
import br.com.fullcycle.hexagonal.application.domain.customer.CustomerId;
import br.com.fullcycle.hexagonal.application.domain.event.Event;
import br.com.fullcycle.hexagonal.application.domain.event.EventId;
import br.com.fullcycle.hexagonal.application.domain.partner.Partner;
import br.com.fullcycle.hexagonal.application.exceptions.ValidationException;
import br.com.fullcycle.hexagonal.application.domain.event.ticket.TicketStatus;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class SubscribeCustomerToEventUseCaseTest {
    @Test
    @DisplayName("Deve comprar um ticket de um evento")
    public void testReserveTicket() throws Exception {
        // given
        final var expectedTicketSize = 1;

        final var expectedCNPJ = "41.536.538/0001-00";
        final var aCustomer = Customer.newCustomer("Jadilson Doe","123.456.789-01", "jadilson.joe@gmail.com");
        final var aPartner = Partner.newPartner("John Doe",expectedCNPJ, "johon.joe@gmail.com");
        final var aEvent = Event.newEvent("Disney", "2021-01-01", 10, aPartner);

        final var customerID = aCustomer.customerId().value();
        final var eventID = aEvent.eventId().value();

        final var subscriberInput = new SubscribeCustomerToEventUseCase.Input(customerID, eventID);

        final var customerRepository = new InMemoryCustomerRepository();
        final var eventRepository = new InMemoryEventRepository();
        final var tickerRepository = new InMemoryTicketRepository();

        customerRepository.create(aCustomer);
        eventRepository.create(aEvent);

        // when
        final var useCase = new SubscribeCustomerToEventUseCase(tickerRepository, customerRepository, eventRepository);
        final var output = useCase.execute(subscriberInput);
        // then
        Assertions.assertEquals(eventID, output.eventId());
        Assertions.assertNotNull(output.tickerId());
        Assertions.assertNotNull(output.reservationDate());
        Assertions.assertEquals(TicketStatus.PENDING.name(), output.ticketStatus());

        final var actualTicketSize = eventRepository.eventOfId(aEvent.eventId());
        Assertions.assertEquals(expectedTicketSize, actualTicketSize.get().allTickets().size());

    }

    @Test
    @DisplayName("Não deve comprar um ticket de um evento que não existe")
    public void testReserveTicketWindowntEvent() throws Exception {
        // given
        final var expectedError = "Event not found";

        final var aPartner = Partner.newPartner("John Doe","41.536.538/0001-00", "johon.joe@gmail.com");
        final var aEvent = Event.newEvent("Disney", "2021-01-01", 10, aPartner);

        final var customerID = CustomerId.unique().value();
        final var eventID = aEvent.eventId().value();

        final var subscriberInput = new SubscribeCustomerToEventUseCase.Input(customerID, eventID);

        final var customerRepository = new InMemoryCustomerRepository();
        final var eventRepository = new InMemoryEventRepository();
        final var tickerRepository = new InMemoryTicketRepository();

        eventRepository.create(aEvent);

        // when
        final var useCase = new SubscribeCustomerToEventUseCase(tickerRepository, customerRepository, eventRepository);
        final var actualException = Assertions.assertThrows(ValidationException.class, () -> useCase.execute(subscriberInput));
        // then

        Assertions.assertEquals(expectedError, actualException.getMessage());
    }

    @Test
    @DisplayName("Não deve comprar um ticket de um evento com um cliente existente")
    public void testReserveTicketWithoutCustomer() throws Exception {
        // given
        final var expectedError = "Customer not found";
        final var aCustomer = Customer.newCustomer("Jadilson Doe","123.456.789-01", "jadilson.joe@gmail.com");
        final var customerID = aCustomer.customerId().value();
        final var eventID = EventId.unique().value();

        final var subscriberInput = new SubscribeCustomerToEventUseCase.Input(customerID, eventID);

        final var customerRepository = new InMemoryCustomerRepository();
        final var eventRepository = new InMemoryEventRepository();
        final var tickerRepository = new InMemoryTicketRepository();

        customerRepository.create(aCustomer);

        // when
        final var useCase = new SubscribeCustomerToEventUseCase(tickerRepository, customerRepository, eventRepository);
        final var actualException = Assertions.assertThrows(ValidationException.class, () -> useCase.execute(subscriberInput));
        // then

        Assertions.assertEquals(expectedError, actualException.getMessage());
    }

    @Test
    @DisplayName("Um mesmo cliente não deve comprar um ticket de um evento mais de uma vez")
    public void testReserveTicketMoreThanOnce() throws Exception {
        // given
        final var expectedError = "Email already registered";

        final var aPartner = Partner.newPartner("John Doe","41.536.538/0001-00", "johon.joe@gmail.com");
        final var aEvent = Event.newEvent("Disney", "2021-01-01", 10, aPartner);
        final var aCustomer = Customer.newCustomer("Jadilson Doe","123.456.789-01", "jadilson.joe@gmail.com");
        final var customerID = aCustomer.customerId().value();
        final var eventID = aEvent.eventId().value();

        final var subscriberInput = new SubscribeCustomerToEventUseCase.Input(customerID, eventID);

        final var customerRepository = new InMemoryCustomerRepository();
        final var eventRepository = new InMemoryEventRepository();
        final var tickerRepository = new InMemoryTicketRepository();

       final var aTicket = aEvent.reserveTicket(aCustomer.customerId());
        customerRepository.create(aCustomer);
        eventRepository.create(aEvent);
        tickerRepository.create(aTicket);

        // when
        final var useCase = new SubscribeCustomerToEventUseCase(tickerRepository, customerRepository, eventRepository);
        final var actualException = Assertions.assertThrows(ValidationException.class, () -> useCase.execute(subscriberInput));
        // then

        Assertions.assertEquals(expectedError, actualException.getMessage());
    }

    @Test
    @DisplayName("Um mesmo cliente não deve comprar um ticket de um evento que não tem mais vagas")
    public void testReserveTicketWithoutSlots() throws Exception {
        // given
        final var expectedError = "Event sold out";

        final var aPartner = Partner.newPartner("John Doe","41.536.538/0001-00", "johon.joe@gmail.com");
        final var aEvent = Event.newEvent("Disney", "2021-01-01", 1, aPartner);
        final var aCustomer = Customer.newCustomer("Jadilson Doe","123.456.789-01", "jadilson.joe@gmail.com");
        final var aCustomer2 = Customer.newCustomer("Jadilson Doe","123.456.789-01", "maria.joe@gmail.com");
        final var customerID = aCustomer.customerId().value();
        final var eventID = aEvent.eventId().value();

        final var subscriberInput = new SubscribeCustomerToEventUseCase.Input(customerID, eventID);

        final var customerRepository = new InMemoryCustomerRepository();
        final var eventRepository = new InMemoryEventRepository();
        final var tickerRepository = new InMemoryTicketRepository();
        final var aTicket = aEvent.reserveTicket(aCustomer.customerId());
        customerRepository.create(aCustomer);
        customerRepository.create(aCustomer2);
        eventRepository.create(aEvent);
        tickerRepository.create(aTicket);

        // when
        final var useCase = new SubscribeCustomerToEventUseCase(tickerRepository, customerRepository, eventRepository);
        final var actualException = Assertions.assertThrows(ValidationException.class, () -> useCase.execute(subscriberInput));
        // then

        Assertions.assertEquals(expectedError, actualException.getMessage());
    }
}