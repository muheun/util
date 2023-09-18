package me.muheun.util;

import org.junit.Test;

public class ExceptionUtilTest {

	@Test
	public void firstTest() {

//		ExceptionUtil.printConsole(new Exception("test"));

//		for (String str : ExceptionUtil.getRootCauseStackTrace(new Exception("test"))) {
//			Debug.debug(str);
//		}

		ExceptionUtil.printStackTrace(new Exception("test"), 5);


	}

}
