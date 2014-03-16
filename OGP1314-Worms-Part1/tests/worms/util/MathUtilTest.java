package worms.util;
import static worms.util.AssertUtil.*;

import org.junit.Test;


public class MathUtilTest {

	@Test
	public void testRoundToPrecision_SimpleCase() {
		assertFuzzyEquals(MathUtil.round(123.4567890123, 1),123.5,1e-10);
	}
	
	@Test
	public void testRoundToPrecision_NonPowerOf2Case() {
		assertFuzzyEquals(MathUtil.round(123.4567890123, 3),123.457,1e-10);
	}

}
