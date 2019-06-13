package com.jacobniebloom.tetris;

import java.util.TimerTask;

public class PeriodicTask extends TimerTask {

    //Overriding the pre existing method in TimerTask class
    @Override
    public void run() {
        //The difficulty level increases as the user progresses through the game
        int text = Integer.parseInt(Board.score.getText().split("/")[0]);
        int maxScoreTillImposible = 160;
        int delay = maxScoreTillImposible - text;
        if (delay < 10) delay = 10;
        try {
            Thread.sleep(delay);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        //Periodically calling the moveDown method so that the piece
        //keeps moving down automatically
        Test.myBoard.movePiece(Board.piece, MoveType.Down, true);
        Game.timer.schedule(new PeriodicTask(), delay);
    }
}