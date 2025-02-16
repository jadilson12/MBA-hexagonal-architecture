package br.com.fullcycle.application.event;

import br.com.fullcycle.application.repository.InMemoryCustomerRepository;
import br.com.fullcycle.application.repository.InMemoryEventRepository;
import br.com.fullcycle.domain.customer.Customer;
import br.com.fullcycle.domain.customer.CustomerId;
import br.com.fullcycle.domain.event.Event;
import br.com.fullcycle.domain.event.EventId;
import br.com.fullcycle.domain.exceptions.ValidationException;
import br.com.fullcycle.domain.partner.Partner;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class SubscribeCustomerToEventUseCaseTest {

    @Test
    @DisplayName("Deve comprar um ticket de um evento")
    public void testReserveTicket() {
        //given
        final var expectedTicketSize = 1;


        final var eventRepository = new InMemoryEventRepository();
        final var aPartner = Partner.newPartner("John Doe", "41.536.538/0001-00", "john.doe@gmail.com");
        final var anEvent = eventRepository.create(Event.newEvent("Disney on Ice", "2021-01-01", 10, aPartner));
        final var eventId = anEvent.eventId().value();

        final var customerRepository = new InMemoryCustomerRepository();
        final var aCustomer = customerRepository.create(Customer.newCustomer("Jadilson Doe", "123.456.789-01", "jadilson.doe@gmail.com"));
        final var customerId = aCustomer.customerId().value();



        final var createInput = new SubscribeCustomerToEventUseCase.Input(customerId, eventId);

        //when
        final var useCase = new SubscribeCustomerToEventUseCase(eventRepository, customerRepository);
        final var actualResponse = useCase.execute(createInput);

        //then
        assertEquals(eventId, actualResponse.eventId());
        assertNotNull(actualResponse.reservationDate());


        final var actualEvent = eventRepository.eventOfId(anEvent.eventId()).get();
        assertEquals(expectedTicketSize, actualEvent.allTickets().size());
    }

    @Test
    @DisplayName("Não deve comprar um ticket de um evento que não existe")
    public void testReserveTicketWhenEventDoesNotExistsShouldThrowError() {
        //given
        final var expectedError = "Event not found";

        final var customerRepository = new InMemoryCustomerRepository();
        final var aCustomer = customerRepository.create(customerRepository.create(Customer.newCustomer("Jadilson Doe", "123.456.789-01", "jadilson.doe@gmail.com")));
        final var customerId = aCustomer.customerId().value();

        final var eventId = EventId.unique().value();

        final var eventRepository = new InMemoryEventRepository();

        final var createInput = new SubscribeCustomerToEventUseCase.Input(customerId, eventId);

        //when
        final var useCase = new SubscribeCustomerToEventUseCase(eventRepository, customerRepository);
        final var actualException = assertThrows(ValidationException.class, () -> useCase.execute(createInput));

        //then
        assertEquals(expectedError, actualException.getMessage());
    }

    @Test
    @DisplayName("Não deve comprar um ticket de um cliente que não existe")
    public void testReserveTicketWhenCustomerDoesNotExistsShouldThrowError() {
        //given
        final var expectedError = "Customer not found";

        final var eventRepository = new InMemoryEventRepository();
        final var aPartner = Partner.newPartner("John Doe", "41.536.538/0001-00", "john.doe@gmail.com");
        final var anEvent = eventRepository.create(eventRepository.create(Event.newEvent("Disney on Ice", "2021-01-01", 10, aPartner)));
        final var eventId = anEvent.eventId().value();

        final var customerId = CustomerId.unique().value();

        final var customerRepository = new InMemoryCustomerRepository();

        final var createInput = new SubscribeCustomerToEventUseCase.Input(customerId, eventId);

        //when
        final var useCase = new SubscribeCustomerToEventUseCase(eventRepository, customerRepository);
        final var actualException = assertThrows(ValidationException.class, () -> useCase.execute(createInput));

        //then
        assertEquals(expectedError, actualException.getMessage());
    }

    @Test
    @DisplayName("Não deve comprar mais de um ticket de um cliente para o mesmo evento")
    public void testReserveTicketMoreThanOnceShouldThrowError() {
        //given
        final var expectedError = "Email already registered";

        final var customerRepository = new InMemoryCustomerRepository();
        final var aCustomer = customerRepository.create(Customer.newCustomer("Jadilson Doe", "123.456.789-01", "jadilson.doe@gmail.com"));
        final var customerId = aCustomer.customerId().value();

        final var eventRepository = new InMemoryEventRepository();
        final var aPartner = Partner.newPartner("John Doe", "41.536.538/0001-00", "john.doe@gmail.com");
        final var anEvent = eventRepository.create(Event.newEvent("Disney on Ice", "2021-01-01", 10, aPartner));
        final var eventId = anEvent.eventId().value();

        final var createInput = new SubscribeCustomerToEventUseCase.Input(customerId, eventId);

        //when
        final var useCase = new SubscribeCustomerToEventUseCase(eventRepository, customerRepository);
        final var actualException = assertThrows(ValidationException.class, () -> useCase.execute(createInput));

        //then
        assertEquals(expectedError, actualException.getMessage());
    }

    @Test
    @DisplayName("Não deve comprar um ticket de um evento esgotado")
    public void testReserveTicketWhenEventIsSoldOutShouldThrowError() {
        //given
        final var expectedError = "Event sold out";

        final var customerRepository = new InMemoryCustomerRepository();
        final var aCustomer = customerRepository.create(Customer.newCustomer("Jadilson Doe", "123.456.789-01", "jadilson.doe@gmail.com"));
        final var anotherCustomer = customerRepository.create(Customer.newCustomer("Pedro Doe", "123.456.789-10", "pedro.doe@gmail.com"));
        final var customerId = aCustomer.customerId().value();

        final var eventRepository = new InMemoryEventRepository();
        final var aPartner = Partner.newPartner("John Doe", "41.536.538/0001-00", "john.doe@gmail.com");
        final var anEvent = eventRepository.create(Event.newEvent("Disney on Ice", "2021-01-01", 1, aPartner));
        final var eventId = anEvent.eventId().value();

        final var createInput = new SubscribeCustomerToEventUseCase.Input(customerId, eventId);

        //when
        final var useCase = new SubscribeCustomerToEventUseCase(eventRepository, customerRepository);
        final var actualException = assertThrows(ValidationException.class, () -> useCase.execute(createInput));

        //then
        assertEquals(expectedError, actualException.getMessage());
    }
}