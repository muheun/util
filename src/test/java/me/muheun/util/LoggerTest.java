package me.muheun.util;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class LoggerTest {

	private static Logger LOGGER = LoggerFactory.getLogger(LoggerTest.class);


	@Test
	public void test() {
		LOGGER.debug("test");
	}

}
