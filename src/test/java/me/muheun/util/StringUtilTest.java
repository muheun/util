package me.muheun.util;

import java.util.Objects;
import org.junit.Test;

public class StringUtilTest {

	@Test
	public void testToString() {

		String defaultString = "default string";

		Debug.debug(Objects.toString(null));
		Debug.debug(Objects.toString("", defaultString));

		Debug.debug(StringUtil.toString(null));
		Debug.debug(StringUtil.toString("", defaultString));

		Debug.debug(StringUtil.clean("", 5));
		Debug.debug(StringUtil.clean("", 5L));
		Debug.debug(StringUtil.clean(null, 6));
		Debug.debug(StringUtil.clean(null, 6L));
	}

}
