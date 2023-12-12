package ru.s1pepega.gol;

import ru.s1pepega.gol.core.GameThreadRunnable;

public class Main {
    public static void main(String[] args) {
        GameThreadRunnable.initializeThread();
        Window window = Window.instance();
        window.run();
    }
}