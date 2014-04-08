package worms.util;

import java.math.BigDecimal;
import java.math.RoundingMode;

public abstract class MathUtil {
	
	/**
	 * Round the given number to the given precision.
	 * 
	 * @param original		The number to round
	 * @param precision		The number of digits after the point.
	 * @return				The given number rounded to the given precision
	 * 						| new BigDecimal(original).setScale(precision, RoundingMode.HALF_EVEN).doubleValue()
	 */
	public static double round(double original, int precision){
		return new BigDecimal(original).setScale(precision, RoundingMode.HALF_EVEN).doubleValue();
	}
}
