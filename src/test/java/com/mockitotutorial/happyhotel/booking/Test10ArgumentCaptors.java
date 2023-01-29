package com.mockitotutorial.happyhotel.booking;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.mockito.Mockito.*;
import static org.assertj.core.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

public class Test10ArgumentCaptors {
	
	private BookingService bookingService;
	private PaymentService paymentService;
	private RoomService roomService;
	private BookingDAO bookingDAO;
	private MailSender mailSender;
	private ArgumentCaptor<Double> doubleCaptor;
	
	@BeforeEach
	void setup() {
		this.paymentService = mock(PaymentService.class);
		this.roomService = mock(RoomService.class);
		this.bookingDAO = mock(BookingDAO.class);
		this.mailSender = mock(MailSender.class);
		this.bookingService = new BookingService(paymentService, roomService, bookingDAO, mailSender);

		this.doubleCaptor = ArgumentCaptor.forClass(Double.class);
	}
	
	@Test
	void should_payCorrectPrice_when_inputOk() {
		//given
		BookingRequest bookingRequest = 
				new BookingRequest("1", LocalDate.of(2020, 01, 01), LocalDate.of(2020, 01, 05), 2, true);
		
		//when
		this.bookingService.makeBooking(bookingRequest);
		
		//then
		verify(this.paymentService, times(1)).pay(eq(bookingRequest), this.doubleCaptor.capture()); //since ArgumentCaptor works as an matcher, we have to provide also a matcher for bookingRequest, in this case eq
		double capturedArg = this.doubleCaptor.getValue();
		
		assertThat(400.0).isEqualTo(capturedArg);
	}
	
	@Test
	void should_payCorrectPrices_when_multipleCalls() {
		//given
		BookingRequest bookingRequest = 
				new BookingRequest("1", LocalDate.of(2020, 01, 01), LocalDate.of(2020, 01, 05), 2, true);
		
		BookingRequest bookingRequest2 = 
				new BookingRequest("1", LocalDate.of(2020, 01, 01), LocalDate.of(2020, 01, 02), 2, true);
		
		List<Double> expectedPrices = Arrays.asList(400.0,100.0);
		
		//when
		this.bookingService.makeBooking(bookingRequest);
		this.bookingService.makeBooking(bookingRequest2);
		
		//then
		verify(this.paymentService, times(2)).pay(any(), this.doubleCaptor.capture()); //since ArgumentCaptor works as an matcher, we have to provide also a matcher for bookingRequest, in this case eq
		List<Double> capturedArgs = this.doubleCaptor.getAllValues();
		
		assertThat(expectedPrices).isEqualTo(capturedArgs);
	}
}
