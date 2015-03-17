import java.util.Random;

public class utilities {
	//The random function generates a random integer
	public int randInt(int min, int max) {
		Random rand = new Random();
		int random = rand.nextInt((max - min) + 1) + min;
		return random;
	}
}