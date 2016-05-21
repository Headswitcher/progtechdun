package hu.unideb.progtech.headswitcher.game.utility;

import java.util.Random;

public class DiceRoll {

	private Random r;

	public DiceRoll() {
		r = new Random();
	}

	public int roll(int from, int until) throws Exception {
		if (from > until) {
			Exception e = new Exception();
			throw e;
		} else {
			int l = r.nextInt(until) + from;
			return l;
		}
	}

	public boolean rollBoolean() {
		return r.nextBoolean();
	}

}
