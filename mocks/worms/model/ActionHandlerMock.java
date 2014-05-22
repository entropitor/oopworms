package worms.model;

import worms.gui.game.IActionHandler;

public class ActionHandlerMock implements IActionHandler{

	@Override
	public boolean turn(Worm worm, double angle) {
		hasCalledTurn = true;
		return true;
	}
	
	public boolean hasCalledTurn = false;

	@Override
	public boolean move(Worm worm) {
		hasCalledMove = true;
		return true;
	}
	public boolean hasCalledMove = false;

	@Override
	public boolean jump(Worm worm) {
		hasCalledJump = true;
		return true;
	}
	public boolean hasCalledJump = false;

	@Override
	public boolean fire(Worm worm, int propulsion) {
		hasCalledFire = true;
		return true;
	}
	public boolean hasCalledFire = false;

	@Override
	public boolean toggleWeapon(Worm worm) {
		hasCalledToggleWeapon = true;
		return true;
	}
	public boolean hasCalledToggleWeapon = false;

	@Override
	public void print(String message) {
		hasCalledPrint = true;
	}
	public boolean hasCalledPrint = false;

}
