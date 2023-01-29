package com.mockitotutorial.happyhotel.booking;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.mockito.Mockito.*;
import static org.assertj.core.api.Assertions.*;

import java.time.LocalDate;

public class Test09MockingVoidMethods {

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
	void should_throwException_when_mailServiceNotReady() {
		//given
		BookingRequest bookingRequest = 
				new BookingRequest("1", LocalDate.of(2020, 01, 01), LocalDate.of(2020, 01, 05), 2, false);
		
		//does not work for void methods that throw exceptions
		//when(this.mailSender.sendBookingConfirmation(null)).thenThrow(BusinessException.class);
		//so use...
		doThrow(new BusinessException()).when(this.mailSender).sendBookingConfirmation(any());
		
		//when + then
		assertThatExceptionOfType(BusinessException.class).isThrownBy(()->{
			this.bookingService.makeBooking(bookingRequest);
		});
		
	}
	
	@Test
	void should_doNothing_when_mailServiceNotReady() {
		//given
		BookingRequest bookingRequest = 
				new BookingRequest("1", LocalDate.of(2020, 01, 01), LocalDate.of(2020, 01, 05), 2, false);
		
		//instead of throwing an exception like in test before, we expect that nothing happens
		//on the void method
		
		//doNothing is the default behavior of void methods, so we also could comment out the next line
		doNothing().when(this.mailSender).sendBookingConfirmation(any());
		
		//when
		this.bookingService.makeBooking(bookingRequest);
		
		//then
		//no exception thrown
	}
}
