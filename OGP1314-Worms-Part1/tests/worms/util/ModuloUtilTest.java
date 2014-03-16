package worms.util;

import static org.junit.Assert.*;
import static worms.util.AssertUtil.*;
import static worms.util.ModuloUtil.posMod;

import org.junit.Test;

public class ModuloUtilTest {

	@Test
	public void testPosMod() {
		assertFuzzyEquals(posMod( -1,     4), 3);
		assertFuzzyEquals(posMod(  3,     4), 3);
		assertFuzzyEquals(posMod( 19,     4), 3);
		assertFuzzyEquals(posMod(-19,     4), 1);
		assertFuzzyEquals(posMod( -1E-20, 4), 0);
	}
}
