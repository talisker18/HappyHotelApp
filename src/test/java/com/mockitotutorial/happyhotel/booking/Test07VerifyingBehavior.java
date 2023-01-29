package com.mockitotutorial.happyhotel.booking;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.mockito.Mockito.*;
import static org.assertj.core.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;

import java.time.LocalDate;

public class Test07VerifyingBehavior {

	private BookingService bookingService;
	private PaymentService paymentService;
	private RoomService roomService;
	private BookingDAO bookingDAO;
	private MailSender mailSender;

	@BeforeEach
	void setup() {
		this.paymentService = mock(PaymentService.class);
		this.roomService = mock(RoomService.class);
		this.bookingDAO = mock(BookingDAO.class);
		this.mailSender = mock(MailSender.class);
		this.bookingService = new BookingService(paymentService, roomService, bookingDAO, mailSender);
	}
	
	@Test
	void should_invokePayment_when_prepaid() {
		//given
		BookingRequest bookingRequest = 
				new BookingRequest("1", LocalDate.of(2020, 01, 01), LocalDate.of(2020, 01, 05), 2, true);
		
		//when
		this.bookingService.makeBooking(bookingRequest);
		
		//now we want to check if the paymentService.pay(bookingRequest, price); was called one time with the paymentService mock
		//then
		verify(this.paymentService, times(1)).pay(bookingRequest, 400.0); //4 nights, 2 person, 50 per night = 4*2*50 = 400
		verifyNoMoreInteractions(this.paymentService); //checks if no more methods of paymentService are called after this.bookingService.makeBooking(bookingRequest);
	}
	
	@Test
	void should_notInvokePayment_when_notPrepaid() {
		//given
		BookingRequest bookingRequest = 
				new BookingRequest("1", LocalDate.of(2020, 01, 01), LocalDate.of(2020, 01, 05), 2, false); //false = paymentService mock should not be called
		
		//when
		this.bookingService.makeBooking(bookingRequest);
		
		//then
		verify(this.paymentService, never()).pay(any(), anyDouble()); //verify that paymentService method 'pay' was never called with any BookingRequest and any double
	}
}
