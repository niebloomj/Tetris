package com.jacobniebloom.tetris;

import java.util.Timer;

class Game {
    //Creating an instance of the class Timer
    static Timer timer = new Timer();

    // Schedules the periodic task of moving the piece down
    void startTask() {
        timer.schedule(new PeriodicTask(), 0);
    }
}
