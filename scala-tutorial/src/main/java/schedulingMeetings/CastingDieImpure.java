package castingdieimpure;

import java.util.Random;

public class CastingDieImpure {
	static int getIntUnsafely() {
		Random rand = new Random();
		if (rand.nextBoolean())
			throw new RuntimeException();
		return rand.nextInt(6) + 1;
	}

  public static int castTheDieImpureNofailures() {
    System.out.println("The die is cast");
    Random rand = new Random();
    return rand.nextInt(6) + 1;
  }

	public static int castTheDieImpureWithFailures() {
    Random rand = new Random();
    if (rand.nextBoolean())
      throw new RuntimeException("Die fell off");
    return rand.nextInt(6) + 1;
	}

	public static int drawAPointCard() {
		Random rand = new Random();
		if (rand.nextBoolean())
			throw new RuntimeException("No cards");
		return rand.nextInt(14) + 1;
	}
}