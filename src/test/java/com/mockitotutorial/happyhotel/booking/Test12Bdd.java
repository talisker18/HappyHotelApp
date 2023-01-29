package com.mockitotutorial.happyhotel.booking;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.mockito.Mockito.*;
import static org.assertj.core.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.BDDMockito.*;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

@ExtendWith(MockitoExtension.class)
public class Test12Bdd {

	// instead of using setup method

	@InjectMocks // injects also Spies
	private BookingService bookingService;
	@Mock
	private PaymentService paymentService;
	@Mock
	private RoomService roomService;
	@Spy
	private BookingDAO bookingDAO;
	@Mock
	private MailSender mailSender;
	@Captor
	private ArgumentCaptor<Double> doubleCaptor;

	@Test
	void should_countAvailablePlaces_when_oneRoomAvailable() {
		// given
		given(this.roomService.getAvailableRooms()).willReturn(Collections.singletonList(new Room("Room1", 2))); 

		int expected = 2;

		// when
		int actual = this.bookingService.getAvailablePlaceCount();

		// then
		assertThat(actual).isEqualTo(expected);
	}
	
	@Test
	void should_invokePayment_when_prepaid() {
		//given
		BookingRequest bookingRequest = 
				new BookingRequest("1", LocalDate.of(2020, 01, 01), LocalDate.of(2020, 01, 05), 2, true);
		
		//when
		this.bookingService.makeBooking(bookingRequest);
		
		//then
		then(this.paymentService).should(times(1)).pay(bookingRequest, 400.0); //4 nights, 2 person, 50 per night = 4*2*50 = 400
		verifyNoMoreInteractions(this.paymentService); //checks if no more methods of paymentService are called after this.bookingService.makeBooking(bookingRequest);
	}
}
