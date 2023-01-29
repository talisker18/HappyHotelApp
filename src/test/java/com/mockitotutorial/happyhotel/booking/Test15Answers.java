package com.mockitotutorial.happyhotel.booking;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockedStatic;
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
public class Test15Answers {

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
	void should_calculateCorrectPrice() {
		//to mock a static method, wrap the method body into try catch with resources
		try(MockedStatic<CurrencyConverter> mockedConverter = mockStatic(CurrencyConverter.class)){
			//given
			BookingRequest bookingRequest = 
					new BookingRequest("1", LocalDate.of(2020, 01, 01), LocalDate.of(2020, 01, 05), 2, false);
			
			double expectedPrice = 400.0 * 0.8;
			
			mockedConverter.when(
					() -> CurrencyConverter.toEuro(anyDouble()))
			//instead of using thenReturn(400), use thenAnswer so we can return dynamic values (based on the input) by getting the args (in this case the price arg of CurrencyConverter.toEuro(double price) )
			.thenAnswer(invokation -> (double)invokation.getArgument(0) * 0.8); //arg 0 = the price
			
			//when
			double actualPrice = this.bookingService.calculatePriceEuro(bookingRequest);
			
			//then
			assertThat(actualPrice).isEqualTo(expectedPrice);
		}
		
		
	}
}
