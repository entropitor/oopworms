package worms.model;

import static org.junit.Assert.*;
import static worms.util.AssertUtil.*;

import java.util.Random;

import org.junit.Before;
import org.junit.Test;

public class WormTest_Weapon {
	
	Worm jeffrey;
	World world;

	@Before
	public void setUp() throws Exception {
		world = new World(20,30,new boolean[][]{{true,true},{false,true},{true,true}},new Random());
		jeffrey = new Worm(world, 15, 15, Math.PI/2, 1, "Jeffrey");
	}

	@Test
	public void testFire_LegalCase() {
		assertTrue(jeffrey.getSelectedWeapon() instanceof Bazooka);
		
		jeffrey.fire(34);
		
		assertTrue(world.hasProjectile());
		Projectile projectile = world.getProjectile();
		
		assertEquals(34, projectile.getPropulsionYield());
		assertFuzzyEquals(Math.pow(1+0.0209401,2), projectile.getPosition().squaredDistance(jeffrey.getPosition()));
		assertFalse(projectile.collidesWith(jeffrey));
		assertFuzzyEquals(projectile.getDirection(), Math.PI/2);
		assertFuzzyEquals(15+Math.cos(Math.PI/2)*1.0209401, projectile.getPosition().getX());
		assertFuzzyEquals(15+Math.sin(Math.PI/2)*1.0209401, projectile.getPosition().getY());
		assertEquals(world, projectile.getWorld());
	}
	
	@Test(expected = IllegalStateException.class)
	public void testFire_WorldHasProjectileCase() throws Exception {
		world.setProjectile(new RifleProjectile(world, 100));
		jeffrey.fire(34);
	}
	
	@Test(expected = IllegalStateException.class)
	public void testFire_NoWeaponsCase() throws Exception {
		jeffrey.removeWeapon(jeffrey.getSelectedWeapon());
		jeffrey.removeWeapon(jeffrey.getSelectedWeapon());
		assertEquals(0, jeffrey.getNbWeapons());
		jeffrey.fire(34);
	}
	
	@Test(expected = IllegalStateException.class)
	public void testFire_NoActionPointsCase() throws Exception {
		jeffrey = new Worm(world, 15, 15, Math.PI/2, 0.25, "Jeffrey");
		jeffrey.turn(2*Math.PI);
		assertEquals(10, jeffrey.getActionPoints());
		assertTrue(jeffrey.getSelectedWeapon() instanceof Bazooka);
		jeffrey.fire(30);
	}
	
	@Test(expected = IllegalStateException.class)
	public void testFire_ImpassableTerrainCase() throws Exception {
		jeffrey = new Worm(world, 5, 15, Math.PI/2, 1, "Jeffrey");
		assertFalse(jeffrey.getWorld().isPassablePosition(jeffrey.getPosition(), jeffrey.getRadius()));
		jeffrey.fire(30);
	}

	@Test
	public void testHasProperWeapons() {
		assertTrue(jeffrey.hasProperWeapons());
	}

	@Test
	public void testGetSelectedWeapon_NoWeaponsCase() {
		jeffrey.removeWeapon(jeffrey.getSelectedWeapon());
		jeffrey.removeWeapon(jeffrey.getSelectedWeapon());
		assertNull(jeffrey.getSelectedWeapon());
	}

	@Test
	public void testGetIndexOfWeapon_HasNot() {
		Weapon weapon = new Bazooka();
		assertEquals(-1,jeffrey.getIndexOfWeapon(weapon));
	}
	
	@Test
	public void testHasWeapon_TrueCase() {
		Weapon weapon = new Bazooka();
		jeffrey.addWeapon(weapon);
		assertTrue(jeffrey.hasWeapon(weapon));
	}
	
	@Test
	public void testHasWeapon_FalseCase() {
		jeffrey.hasWeapon(new Bazooka());
	}

	@Test
	public void testSelectNextWeapon() {
		assertEquals(jeffrey.getSelectedWeapon(), jeffrey.getWeaponAt(0));
		jeffrey.selectNextWeapon();
		assertEquals(jeffrey.getSelectedWeapon(), jeffrey.getWeaponAt(1));
		jeffrey.selectNextWeapon();
		assertEquals(jeffrey.getSelectedWeapon(), jeffrey.getWeaponAt(0));
	}

	@Test
	public void testGetWeaponAt_LegalCase() {
		Weapon weapon = new Bazooka();
		assertEquals(2, jeffrey.getNbWeapons());
		jeffrey.addWeapon(weapon);
		assertEquals(weapon, jeffrey.getWeaponAt(2));
	}
	
	@Test(expected = IndexOutOfBoundsException.class)
	public void testGetWeaponAt_IllegalCase() throws Exception {
		jeffrey.getWeaponAt(10);
	}

	@Test
	public void testGetNbWeapons() {
		Weapon weapon = new Bazooka();
		assertEquals(jeffrey.getNbWeapons(),2);
		jeffrey.addWeapon(weapon);
		assertEquals(jeffrey.getNbWeapons(), 3);
		jeffrey.removeWeapon(weapon);
		assertEquals(jeffrey.getNbWeapons(),2);
	}

	@Test
	public void testCanHaveAsNbWeapons_TrueCase() {
		assertTrue(jeffrey.canHaveAsNbWeapons(34));
	}
	
	@Test
	public void testCanHaveAsNbWeapons_FalseCase() {
		assertFalse(jeffrey.canHaveAsNbWeapons(-34));
	}

	@Test
	public void testAddWeapon_NewWeaponCase() {
		Weapon weapon = new Bazooka();
		assertEquals(jeffrey.getNbWeapons(),2);
		assertFalse(jeffrey.hasWeapon(weapon));
		jeffrey.addWeapon(weapon);
		assertTrue(jeffrey.hasWeapon(weapon));
		assertEquals(3,jeffrey.getNbWeapons());
	}
	
	@Test
	public void testAddWeapon_AlreadyAddedCase() {
		Weapon weapon = new Bazooka();
		jeffrey.addWeapon(weapon);
		assertEquals(3, jeffrey.getNbWeapons());
		
		assertTrue(jeffrey.hasWeapon(weapon));
		jeffrey.addWeapon(weapon);
		assertTrue(jeffrey.hasWeapon(weapon));
		assertEquals(3,jeffrey.getNbWeapons());
	}

	@Test
	public void testCanHaveAsWeapon_TrueCase() {
		assertTrue(jeffrey.canHaveAsWeapon(new Rifle()));
	}
	
	@Test
	public void testCanHaveAsWeapon_FalseCase() {
		assertFalse(jeffrey.canHaveAsWeapon(null));
	}

	@Test
	public void testRemoveWeapon_NormalCase() {
		Weapon bazooka = new Bazooka();
		jeffrey.addWeapon(bazooka);
		assertEquals(2, jeffrey.getIndexOfWeapon(bazooka));
		
		Weapon weapon = jeffrey.getWeaponAt(1);
		assertTrue(jeffrey.hasWeapon(weapon));
		jeffrey.removeWeapon(weapon);
		assertFalse(jeffrey.hasWeapon(weapon));
		assertEquals(1, jeffrey.getIndexOfWeapon(bazooka));
	}
	
	@Test
	public void testRemoveWeapon_SelectedLastCase() {
		Weapon weapon = new Bazooka();
		jeffrey.addWeapon(weapon);
		assertTrue(jeffrey.hasWeapon(weapon));
		
		Weapon first = jeffrey.getSelectedWeapon();
		jeffrey.selectNextWeapon();
		jeffrey.selectNextWeapon();
		assertEquals(jeffrey.getSelectedWeapon(), weapon);
		
		jeffrey.removeWeapon(weapon);
		assertFalse(jeffrey.hasWeapon(weapon));
		assertEquals(jeffrey.getSelectedWeapon(), first);
	}
	
	@Test
	public void testRemoveWeapon_SelectedMiddleCase() {
		Weapon weapon = new Bazooka();
		jeffrey.addWeapon(weapon);
		assertTrue(jeffrey.hasWeapon(weapon));
		
		Weapon last = new Bazooka();
		jeffrey.addWeapon(last);
		assertTrue(jeffrey.hasWeapon(last));
		
		jeffrey.selectNextWeapon();
		jeffrey.selectNextWeapon();
		assertEquals(jeffrey.getSelectedWeapon(), weapon);
		
		jeffrey.removeWeapon(weapon);
		assertFalse(jeffrey.hasWeapon(weapon));
		assertEquals(jeffrey.getSelectedWeapon(), last);
	}
	
	@Test
	public void testRemoveWeapon_AllCase() {
		assertEquals(2, jeffrey.getNbWeapons());
		jeffrey.removeWeapon(jeffrey.getSelectedWeapon());
		assertEquals(1, jeffrey.getNbWeapons());
		jeffrey.removeWeapon(jeffrey.getSelectedWeapon());
		assertEquals(0, jeffrey.getNbWeapons());
	}
	
	@Test
	public void testRemoveWeapon_BeforeSelectedCase() {
		Weapon first = jeffrey.getWeaponAt(0);
		Weapon second = jeffrey.getWeaponAt(1);
		
		jeffrey.selectNextWeapon();
		
		jeffrey.removeWeapon(first);
		assertEquals(jeffrey.getSelectedWeapon(),second);
	}

}
