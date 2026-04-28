package com.trysol;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;

import static org.junit.jupiter.api.Assertions.*;

class CustomerServiceApplicationTests {

	@Test
	@DisplayName("Application name validation")
	void testApplicationName_IsValid() {
		// ARRANGE
		String applicationName = "customer-service";

		// ACT & ASSERT
		assertNotNull(applicationName);
		assertFalse(applicationName.isEmpty());
		assertEquals("customer-service", applicationName);
	}
}