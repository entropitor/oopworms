package worms.util;

public class ModuloUtil {
	/**
	 * Provides a Python- and discrete algebra-like modulo operation
	 * that only returns positive remainders.
	 * Java:  -1 % 4  == -1
	 * posMod(-1,  4) ==  3
	 * 
	 * @return	'a' modulo 'divisor'
	 * 			| result == a + z*divisor				// With 'z' an integer number.
	 * 			| && 0 <= result && result < divisor
	 */
	public static double posMod(double a, double divisor){
		return ((a % divisor) + divisor) % divisor;
	}
}