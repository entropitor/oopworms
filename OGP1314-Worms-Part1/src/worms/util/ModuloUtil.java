package worms.util;

public class ModuloUtil {
	/**
	 * Provides a Python- and discrete algebra-like modulo operation
	 * that only returns positive remainders.
	 * Java: -1 % 4  = -1
	 * posMod(-1, 4) = 3
	 * 
	 * @return a mod b where a negative 'a' yields a positive result.
	 */
	public static double posMod(double a, double b){
		double r = a % b;
		if (r < 0)
			r += b;
			if (r >= b) // -1E-20 mod 4 would produce 4. Here we check those cases,
				r = 0;	// and make the result zero.
		return r;
	}
}
