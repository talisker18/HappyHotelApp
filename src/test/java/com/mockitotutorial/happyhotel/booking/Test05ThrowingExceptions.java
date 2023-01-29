package com.mockitotutorial.happyhotel.booking;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.mockito.Mockito.*;
import static org.assertj.core.api.Assertions.*;

import java.time.LocalDate;

public class Test05ThrowingExceptions {

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
	void should_throwException_when_noRoomAvailable() {
		//given
		BookingRequest bookingRequest = 
				new BookingRequest("1", LocalDate.of(2020, 01, 01), LocalDate.of(2020, 01, 05), 2, false);
		
		when(this.roomService.findAvailableRoomId(bookingRequest)).thenThrow(BusinessException.class);
		
		//when + then
		assertThatExceptionOfType(BusinessException.class).isThrownBy(()->{
			this.bookingService.makeBooking(bookingRequest);
		});
		
	}
}
