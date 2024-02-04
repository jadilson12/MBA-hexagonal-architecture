package br.com.fullcycle.hexagonal.application.usecases;

import br.com.fullcycle.hexagonal.application.exceptions.ValidationException;
import br.com.fullcycle.hexagonal.infrastructure.models.Customer;
import br.com.fullcycle.hexagonal.infrastructure.models.Event;
import br.com.fullcycle.hexagonal.infrastructure.models.Ticket;
import br.com.fullcycle.hexagonal.infrastructure.models.TicketStatus;
import br.com.fullcycle.hexagonal.infrastructure.services.CustomerService;
import br.com.fullcycle.hexagonal.infrastructure.services.EventService;
import io.hypersistence.tsid.TSID;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.Optional;

import static org.mockito.Mockito.when;

class SubscribeCustomerToEventUseCaseTest {
    @Test
    @DisplayName("Deve comprar um ticket de um evento")
    public void testReserveTicket() throws Exception {
        // given
        final var expectedTicketSize = 1;
        final var customerID = TSID.fast().toLong();
        final var eventID = TSID.fast().toLong();

        final var aCustomer = new Customer();
        aCustomer.setId(customerID);
        aCustomer.setCpf("12345678901");
        aCustomer.setEmail("johon.joe@gmail.com");
        aCustomer.setName("John Doe");

        final var aEvent = new Event();
        aEvent.setId(eventID);
        aEvent.setName("Disney");
        aEvent.setTotalSpots(10);

        final var subscriberInput = new SubscribeCustomerToEventUseCase.Input(aCustomer.getId(), aEvent.getId());


        // when
        final var customerService = Mockito.mock(CustomerService.class);
        final var eventService = Mockito.mock(EventService.class);

        when(customerService.findById(customerID)).thenReturn(Optional.of(aCustomer));

        when(eventService.findById(eventID)).thenReturn(Optional.of(aEvent));
        when(eventService.findTicketByEventIdAndCustomerId(eventID, customerID)).thenReturn(Optional.empty());
        when(eventService.save(Mockito.any())).thenAnswer(i -> {
            final var e = i.getArgument(0, Event.class);
            e.setId(eventID);
            Assertions.assertEquals(expectedTicketSize, e.getTickets().size() );
            return e;
        });

        final var useCase = new SubscribeCustomerToEventUseCase(customerService, eventService);
        final var output = useCase.execute(subscriberInput);
        // then
        Assertions.assertEquals(eventID, output.eventId());
        Assertions.assertNotNull(output.reservationDate());
        Assertions.assertEquals(TicketStatus.PENDING.name(), output.ticketStatus());

    }

    @Test
    @DisplayName("Não deve comprar um ticket de um evento que não existe")
    public void testReserveTicketWindowntEvent() throws Exception {
        // given
        final var expectedError = "Event not found";
        final var customerID = TSID.fast().toLong();
        final var eventID = TSID.fast().toLong();

        final var aCustomer = new Customer();
        aCustomer.setId(customerID);
        aCustomer.setCpf("12345678901");
        aCustomer.setEmail("johon.joe@gmail.com");
        aCustomer.setName("John Doe");

        final var subscriberInput = new SubscribeCustomerToEventUseCase.Input(customerID, eventID);

        // when
        final var customerService = Mockito.mock(CustomerService.class);
        final var eventService = Mockito.mock(EventService.class);

        when(customerService.findById(customerID)).thenReturn(Optional.of(aCustomer));

        when(eventService.findById(eventID)).thenReturn(Optional.empty());

        final var useCase = new SubscribeCustomerToEventUseCase(customerService, eventService);
        final var actualException = Assertions.assertThrows(ValidationException.class, () -> useCase.execute(subscriberInput));
        // then

        Assertions.assertEquals(expectedError, actualException.getMessage());
    }

    @Test
    @DisplayName("Não deve comprar um ticket de um evento com um cliente existente")
    public void testReserveTicketWithoutCustomer() throws Exception {
        // given
        final var expectedError = "Customer not found";
        final var customerID = TSID.fast().toLong();
        final var eventID = TSID.fast().toLong();

        final var aCustomer = new Customer();
        aCustomer.setId(customerID);
        aCustomer.setCpf("12345678901");
        aCustomer.setEmail("johon.joe@gmail.com");
        aCustomer.setName("John Doe");

        final var subscriberInput = new SubscribeCustomerToEventUseCase.Input(customerID, eventID);

        // when
        final var customerService = Mockito.mock(CustomerService.class);
        final var eventService = Mockito.mock(EventService.class);

        when(customerService.findById(customerID)).thenReturn(Optional.empty());

        final var useCase = new SubscribeCustomerToEventUseCase(customerService, eventService);
        final var actualException = Assertions.assertThrows(ValidationException.class, () -> useCase.execute(subscriberInput));
        // then

        Assertions.assertEquals(expectedError, actualException.getMessage());
    }

    @Test
    @DisplayName("Um mesmo cliente não deve comprar um ticket de um evento mais de uma vez")
    public void testReserveTicketMoreThanOnce() throws Exception {
        // given
        final var expectedError = "Email already registered";
        final var customerID = TSID.fast().toLong();
        final var eventID = TSID.fast().toLong();

        final var aCustomer = new Customer();
        aCustomer.setId(customerID);
        aCustomer.setCpf("12345678901");
        aCustomer.setEmail("johon.joe@gmail.com");
        aCustomer.setName("John Doe");

        final var aEvent = new Event();
        aEvent.setId(eventID);
        aEvent.setName("Disney");
        aEvent.setTotalSpots(10);

        final var subscriberInput = new SubscribeCustomerToEventUseCase.Input(aCustomer.getId(), aEvent.getId());


        // when
        final var customerService = Mockito.mock(CustomerService.class);
        final var eventService = Mockito.mock(EventService.class);

        when(customerService.findById(customerID)).thenReturn(Optional.of(aCustomer));

        when(eventService.findById(eventID)).thenReturn(Optional.of(aEvent));
        when(eventService.findTicketByEventIdAndCustomerId(eventID, customerID)).thenReturn(Optional.of(new Ticket()));


        final var useCase = new SubscribeCustomerToEventUseCase(customerService, eventService);
        final var actualException = Assertions.assertThrows(ValidationException.class, () -> useCase.execute(subscriberInput));
        // then

        Assertions.assertEquals(expectedError, actualException.getMessage());
    }

    @Test
    @DisplayName("Um mesmo cliente não deve comprar um ticket de um evento que não tem mais vagas")
    public void testReserveTicketWithoutSlots() throws Exception {
        // given
        final var expectedError = "Event sold out";
        final var customerID = TSID.fast().toLong();
        final var eventID = TSID.fast().toLong();

        final var aCustomer = new Customer();
        aCustomer.setId(customerID);
        aCustomer.setCpf("12345678901");
        aCustomer.setEmail("johon.joe@gmail.com");
        aCustomer.setName("John Doe");

        final var aEvent = new Event();
        aEvent.setId(eventID);
        aEvent.setName("Disney");
        aEvent.setTotalSpots(0);

        final var subscriberInput = new SubscribeCustomerToEventUseCase.Input(aCustomer.getId(), aEvent.getId());


        // when
        final var customerService = Mockito.mock(CustomerService.class);
        final var eventService = Mockito.mock(EventService.class);

        when(customerService.findById(customerID)).thenReturn(Optional.of(aCustomer));

        when(eventService.findById(eventID)).thenReturn(Optional.of(aEvent));
        when(eventService.findTicketByEventIdAndCustomerId(eventID, customerID)).thenReturn(Optional.empty());


        final var useCase = new SubscribeCustomerToEventUseCase(customerService, eventService);
        final var actualException = Assertions.assertThrows(ValidationException.class, () -> useCase.execute(subscriberInput));
        // then

        Assertions.assertEquals(expectedError, actualException.getMessage());
    }
}