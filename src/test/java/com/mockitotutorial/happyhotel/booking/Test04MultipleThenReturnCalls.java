package com.mockitotutorial.happyhotel.booking;

import org.assertj.core.api.SoftAssertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.mockito.Mockito.*;
import static org.assertj.core.api.Assertions.*;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class Test04MultipleThenReturnCalls {

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
	void should_countAvailablePlaces_when_calledMultipleTimes() {
		// given
		when(this.roomService.getAvailableRooms())
			.thenReturn(Collections.singletonList(new Room("Room1", 2))) //first time we call, we get List of 1
			.thenReturn(Collections.emptyList()); //second time we call, we get empty list

		int expectedFirstCall = 2;
		int expectedSecondCall = 0;

		// when
		int actualFirst = this.bookingService.getAvailablePlaceCount();
		int actualSecond = this.bookingService.getAvailablePlaceCount();

		// then
		SoftAssertions softAssertions = new SoftAssertions();
		
		softAssertions.assertThat(actualFirst).as("first call").isEqualTo(expectedFirstCall);
		softAssertions.assertThat(actualSecond).as("second call").isEqualTo(expectedSecondCall);
		softAssertions.assertAll();
	}
}
