package worms.model;

import static org.junit.Assert.*;

import java.util.Random;

import org.junit.Before;
import org.junit.Test;

public class TeamTest_Name {
	private Team cool;
	
	@Before
	public void setup(){
		World world = new World(20,30,new boolean[][]{{true,true},{false,true},{true,true}},new Random());
		cool = new Team(world, "LeWyllieWonka");
	}

	@Test
	public void testIsValidCharacterForName_TrueCases(){;
		assertTrue(Team.isValidCharacterForName('X'));
		assertTrue(Team.isValidCharacterForName('d'));
	}
	
	@Test
	public void testIsValidCharacterForName_Symbols(){
		assertFalse(Team.isValidCharacterForName('$'));
		assertFalse(Team.isValidCharacterForName('~'));
	}
	
	@Test
	public void testIsValidCharacterForName_Tab(){
		assertFalse(Team.isValidCharacterForName('\t'));
	}
	
	@Test
	public void testIsValidCharacterForName_WeirdLetters(){
		assertFalse(Team.isValidCharacterForName('Ö'));
		assertFalse(Team.isValidCharacterForName('å'));
	}

	@Test
	public void testIsValidCharacterForName_Numbers(){
		assertFalse(Team.isValidCharacterForName('3'));
		assertFalse(Team.isValidCharacterForName('9'));
	}

	@Test
	public void testIsValidCharacterForName_Quotes(){
		assertFalse(Team.isValidCharacterForName('\''));
		assertFalse(Team.isValidCharacterForName('\"'));
	}

	@Test
	public void testIsValidCharacterForName_Space(){
		assertFalse(Team.isValidCharacterForName(' '));
	}
	
	@Test
	public void testIsValidName_TrueCase(){
		assertTrue(Team.isValidName("TheATeam"));
	}
	
	@Test
	public void testIsValidName_TooShort(){
		assertFalse(Team.isValidName("Q"));
	}
	
	@Test
	public void testIsValidName_NonUpperCaseStart(){
		assertFalse(Team.isValidName("jamesoHara"));
	}
	
	@Test
	public void testIsValidName_WeirdSymbols(){
		assertFalse(Team.isValidName("James *ö'Hara*"));
	}

	@Test
	public void testIsValidName_Spaces(){
		assertFalse(Team.isValidName("Apollo Enterprises"));
	}
	
	@Test
	public void testSetName_LegalCase(){
		cool.setName("ApolloEnterprises");
		assertEquals(cool.getName(), "ApolloEnterprises");
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void testSetName_TooShort(){
		cool.setName("D");
	}

	@Test(expected=IllegalArgumentException.class)
	public void testSetName_Numbers(){
		cool.setName("M6");
	}

	@Test(expected=IllegalArgumentException.class)
	public void testSetName_Spaces(){
		cool.setName("Apollo Enterprises");
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void testSetName_Quotes(){
		cool.setName("\"DieAnotherDay\"");
	}	

	@Test(expected=IllegalArgumentException.class)
	public void testSetName_IllegalSymbols(){
		cool.setName("D*n*ldD*ck");
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void testSetName_LowerCaseStart(){
		cool.setName("apolloEnterprises");
	}
}
