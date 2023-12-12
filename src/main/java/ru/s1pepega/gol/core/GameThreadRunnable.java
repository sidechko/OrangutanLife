package ru.s1pepega.gol.core;

import ru.s1pepega.gol.core.debug.GO2DT;

import java.util.ArrayList;
import java.util.Iterator;

public class GameThreadRunnable implements Runnable {
    public static GameThreadRunnable game;
    private static Thread MAIN_THREAD = null;
    private volatile long lastTime = System.currentTimeMillis();

    private ArrayList<GameObject> gameObjects = new ArrayList<>();

    {
        gameObjects.add(new GO2DT(1, 2, 3).setColor(0xffff00));
        gameObjects.add(new GO2DT(7, 7, 3).setColor(0x00ff00));
        gameObjects.add(new GO2DT(12, 3, 2).setColor(0xee2589));
        gameObjects.add(new GO2DT(6, 1, 5).setColor(0x781294));
    }

    @Override
    public void run() {
        while (true) {
            long current = System.currentTimeMillis();
            if (current - this.lastTime >= 40) {
                this.lastTime = current;
                update();
            }
        }
    }
    int c = 0;
    public void update() {
        ArrayList<GameObject> updatedState = new ArrayList<>(gameObjects.size());
        for (GameObject gameObject : gameObjects) {
            gameObject.update();
            if (gameObject.needRemove)
                continue;
            updatedState.add(gameObject);
            if(c++%400==0){
                updatedState.add(new GO2DT(c/400,0,0).setColor(0));
            }
        }
        gameObjects = updatedState;
    }

    public ArrayList<GameObject> getGameObjects() {
        return gameObjects;
    }

    public static void initializeThread() {
        if (MAIN_THREAD != null)
            throw new IllegalStateException("Game logic thread initialized");
        System.out.println("Initialize game thread!");
        game = new GameThreadRunnable();
        MAIN_THREAD = new Thread(game, "OranLfThread");
        MAIN_THREAD.start();
    }
}
