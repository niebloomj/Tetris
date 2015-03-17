import java.util.Timer;
import java.util.TimerTask;

public class Game {
	//Creating an instance of the class Timer
	static Timer timer = new Timer();
	// Scheduales the periodic task of moving the piece down
	public void startTask() {
		timer.schedule(new PeriodicTask(), 0);
	}
}
