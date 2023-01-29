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
public class Test13StrictStubbing {

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
	void should_invokePayment_when_prepaid() {
		//given
		BookingRequest bookingRequest = 
				new BookingRequest("1", LocalDate.of(2020, 01, 01), LocalDate.of(2020, 01, 05), 2, false);
		
		//when we call an unnecessary stubbing, the test will fail. Because here, the paymentService.pay is never called
		//because prepaid = false
		//we can overcome this by using lenient(). before the when()
		//but do not overuse this feature because the strict stubbing feature of mockito is a good thing
		//in this case we would check in verify part if paymentService was never called,
		//see: Test07VerifyingBehavior
		when(this.paymentService.pay(any(), anyDouble())).thenReturn("1");
		
		//when
		this.bookingService.makeBooking(bookingRequest);
		
		
		
		//then
		//no exception is thrown
	}
}
