package hu.unideb.progtech.headswitcher.game.utility;

import java.util.Random;

public class DiceRoll {

	private Random r;

	public DiceRoll() {
		r = new Random();
	}

	public int roll(int from, int until) {
		int l = r.nextInt(until) + from;
		return l;
	}

	public int rollDice() {
		return r.nextInt(6) + 1;
	}

	public boolean rollBoolean() {
		return r.nextBoolean();
	}

}
