package com.mockitotutorial.happyhotel.booking;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.mockito.Mockito.*;
import static org.assertj.core.api.Assertions.*;

import java.time.LocalDate;

public class Test02DefaultReturnValues {

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
		
		//default returned valuesd by mockito
		System.out.println("List returned "+this.roomService.getAvailableRooms());
		System.out.println("Object returned "+this.roomService.findAvailableRoomId(null));
		System.out.println("Primitive returned "+this.roomService.getRoomCount());
	}
	
	@Test
	void should_should_countAvailablePlaces() {
		//given
		int expected = 0;
		
		//when
		int actual = this.bookingService.getAvailablePlaceCount();
		
		//then
		assertThat(actual).isEqualTo(expected);
	}
}
