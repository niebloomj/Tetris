import javax.swing.*;
import java.awt.*;
import java.util.Random;
import java.io.*;
import java.net.URL;
import javax.sound.sampled.*;
import javax.swing.*;
import java.net.*;
import java.io.*;

public class Test extends JFrame {
	//Declaring instance variables
	static Board myBoard;
	static JFrame frame;
	static Game game;
	static Clip clip;

	public static void main(String [] args) {
		//main method begins
		//Creates the pop up message whenever the game is run
		JOptionPane.showMessageDialog(null,
									  "CONTROLS.\nHorizontal movement - Left/Right keys.\nRotation - Up key.\nKeys Z and X for holding and releasing a piece respectively\n"
									  +
									  "Moving the piece all the way down - Space key.\nPlease keep the volume up for music.\nEnjoy the game.",
									  "WELCOME TO TETRIS!!",
									  JOptionPane.INFORMATION_MESSAGE);
		//Creates the frame
		frame = new JFrame();
		frame.setTitle("TETRIS");
		frame.setVisible(true);
		frame.setSize(650, 850);
		frame.setLocation(400, 0);
		frame.setBackground(Color.black);
		frame.setResizable(false);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		//adds key listener
		frame.addKeyListener(new KeyListener());
		//Creates the  Board;
		myBoard = new Board();
		frame.add(myBoard);
		//Game Instance;
		game = new Game();
		game.startTask();
		//plays music in the background
		try {
			// Open an audio input stream.
			URL url = game.getClass().getClassLoader().getResource("tetris.wav");
			AudioInputStream audioIn = AudioSystem.getAudioInputStream(url);
			// Get a sound clip resource.
			clip = AudioSystem.getClip();
			// Open audio clip and load samples from the audio input stream.
			clip.open(audioIn);
			clip.start();
		} catch (UnsupportedAudioFileException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (LineUnavailableException e) {
			e.printStackTrace();
		}
	}
}
