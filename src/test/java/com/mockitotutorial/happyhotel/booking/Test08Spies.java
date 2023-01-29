package com.mockitotutorial.happyhotel.booking;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.mockito.Mockito.*;
import static org.assertj.core.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;

import java.time.LocalDate;

public class Test08Spies {

	private BookingService bookingService;
	private PaymentService paymentService;
	private RoomService roomService;
	private BookingDAO bookingDAO;
	private MailSender mailSender;

	@BeforeEach
	void setup() {
		this.paymentService = mock(PaymentService.class);
		this.roomService = mock(RoomService.class);
		//this.bookingDAO = mock(BookingDAO.class);
		this.bookingDAO = spy(BookingDAO.class);
		this.mailSender = mock(MailSender.class);
		this.bookingService = new BookingService(paymentService, roomService, bookingDAO, mailSender);
	}
	
	@Test
	void should_makeBooking_when_inputOk() {
		//given
		BookingRequest bookingRequest = 
				new BookingRequest("1", LocalDate.of(2020, 01, 01), LocalDate.of(2020, 01, 05), 2, true);
		
		//when
		String bookingId = this.bookingService.makeBooking(bookingRequest);
		
		//then
		verify(this.bookingDAO).save(bookingRequest);
		//bookingDAO.save(bookingRequest); will return the bookingId
		//if we use this.bookingDAO = mock(BookingDAO.class); , the bookingId will be null, because it is mocked and for strings, the mock uses null as default value
		//but we can use spy instead of mock.
		//But attention! This will use the real bookingDAO.save(bookingRequest) method, not the mock
		//so we use spy to only partially mock a test
		System.out.println("booking id: "+bookingId);
	}
	
	@Test
	void should_cancelBooking_when_inputOk() {
		//given
		BookingRequest bookingRequest = 
				new BookingRequest("1", LocalDate.of(2020, 01, 01), LocalDate.of(2020, 01, 05), 2, true);
		
		bookingRequest.setRoomId("1.3");
		String bookingId = "1";
		
		//tell the spy to return that bookingRequest object when his get method is called 
		doReturn(bookingRequest).when(this.bookingDAO).get(bookingId);
		
		//when
		this.bookingService.cancelBooking(bookingId);
	}
}
