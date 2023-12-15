package ru.s1pepega.gol;

import ru.s1pepega.gol.core.GameLogic;
import ru.s1pepega.gol.render.Window;

public class Main {
    public static Window window;
    public static GameLogic logic;
    public static void main(String[] args) {
        Thread gameLogicThread = new Thread(()->{
            logic = new GameLogic();
            logic.start();
        });
        gameLogicThread.start();
        Thread winodwThread = new Thread(() -> {
            window = Window.instance();
            window.run();
        });
        winodwThread.start();

    }
}