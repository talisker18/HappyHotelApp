package com.mockitotutorial.happyhotel.booking;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.mockito.Mockito.*;
import static org.assertj.core.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;

import java.time.LocalDate;

public class Test06Matchers {

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
	void should_notCompleteBooking_when_PriceTooHigh() {
		//given
		BookingRequest bookingRequest = 
				new BookingRequest("2", LocalDate.of(2020, 01, 01), LocalDate.of(2020, 01, 05), 2, true);
		
		when(this.paymentService.pay(any(BookingRequest.class), anyDouble())).thenThrow(BusinessException.class);
		
		//if we want a mock that throws exception when any booking object but an exact double value:
		//when(this.paymentService.pay(any(BookingRequest.class), eq(400))).thenThrow(BusinessException.class);
		
		//there is also a matcher for anyString, but this does not work with a null string
		
		//when + then
		assertThatExceptionOfType(BusinessException.class).isThrownBy(()->{
			this.bookingService.makeBooking(bookingRequest);
		});
		
	}
}
