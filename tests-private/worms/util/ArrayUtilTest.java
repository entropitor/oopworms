package worms.util;

import static org.junit.Assert.*;

import org.junit.Test;

import static worms.util.ArrayUtil.*;

public class ArrayUtilTest {

	@Test
	public void testDeepClone_SingleCase() {
		boolean[][] src = {{true,true},{false,true},{true,true}};
		boolean[][] dest = deepClone(src);
		assertTrue(dest[2][1]);
		src[2] = new boolean[]{true,false};
		assertTrue("Check shallow clone",dest[2][1]);
		
		assertTrue(dest[2][1]);
		src[2][1] = false;
		assertTrue("Check deep clone",dest[2][1]);
		
		assertFalse(dest.equals(src));
		assertFalse(dest[1].equals(src[1]));
	}
	
	@Test
	public void testDeepEquals_TrueCase(){
		boolean[][] src = {{true,true},{false,true},{true,true,false}};
		boolean[][] dest = {{true,true},{false,true},{true,true,false}};
		assertTrue(deepEquals(src,dest));
		
		src = new boolean[][]{{true,false}};
		dest = new boolean[][]{{true,false}};
		assertTrue(deepEquals(src,dest));
	}
	
	@Test
	public void testDeepEquals_NullCase(){
		boolean[][] src = {{true,true},{false,true},{true,true}};
		assertTrue(deepEquals(null,null));
		assertFalse(deepEquals(src,null));
		assertFalse(deepEquals(null,src));
	}
	
	@Test
	public void testDeepEquals_FalseWrongLenghtsCase(){
		boolean[][] src = {{true,true},{false,true},{true,true,true}};
		boolean[][] dest = {{true,true},{false,true},{true,true,true},{true,true}};
		boolean[][] dest2 = {{true,true},{false},{true,true,true}};
		assertFalse(deepEquals(src,dest));
		assertFalse(deepEquals(src,dest2));
	}
	
	@Test
	public void testDeepEquals_FalseWrongValuesCase(){
		boolean[][] src = {{true,true},{false,true},{true,true,true}};
		boolean[][] dest = {{true,true},{false,true},{true,true,false}};
		assertFalse(deepEquals(src,dest));
	}

}
