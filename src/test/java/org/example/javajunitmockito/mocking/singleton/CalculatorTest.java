package org.example.javajunitmockito.mocking.singleton;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.doReturn;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

class CalculatorTest {

	@Mock
	Calculator mockCalculator;

	@BeforeEach
	void setUp() {
		MockitoAnnotations.initMocks(this);
	}

	@Test
	void testMock() {
		assertNotNull(mockCalculator, () -> "Unable to mock");
	}

	@Test
	void testAdd() {
//		Arrange
		int expected = 5;
		doReturn(expected)
		.when(mockCalculator)
		.add(eq(2), eq(3));

//		Act
		Integer actual = mockCalculator.add(2, 3);

//		Assert
		assertEquals(expected, actual);

	}
}
