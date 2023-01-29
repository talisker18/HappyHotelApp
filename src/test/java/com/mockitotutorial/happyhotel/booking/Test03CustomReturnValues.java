package com.mockitotutorial.happyhotel.booking;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.mockito.Mockito.*;
import static org.assertj.core.api.Assertions.*;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class Test03CustomReturnValues {

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

		// default returned values by mockito
		System.out.println("List returned " + this.roomService.getAvailableRooms());
		System.out.println("Object returned " + this.roomService.findAvailableRoomId(null));
		System.out.println("Primitive returned " + this.roomService.getRoomCount());
	}

	@Test
	void should_countAvailablePlaces_when_oneRoomAvailable() {
		// given
		when(this.roomService.getAvailableRooms()).thenReturn(Collections.singletonList(new Room("Room1", 2))); // change
																												// default
																												// mockito
																												// value
		// this.roomService.getAvailableRooms() is called in
		// bookingService.getAvailablePlaceCount()

		int expected = 2;

		// when
		int actual = this.bookingService.getAvailablePlaceCount();

		// then
		assertThat(actual).isEqualTo(expected);
	}

	@Test
	void should_countAvailablePlaces_when_multipleRoomsAvailable() {
		// given
		List<Room> rooms = Arrays.asList(new Room("Room1", 2), new Room("Room 2", 5));
		when(this.roomService.getAvailableRooms()).thenReturn(rooms); 

		int expected = 7;

		// when
		int actual = this.bookingService.getAvailablePlaceCount();

		// then
		assertThat(actual).isEqualTo(expected);
	}
}
