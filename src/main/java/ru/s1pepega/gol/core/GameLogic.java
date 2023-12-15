package ru.s1pepega.gol.core;

import ru.s1pepega.gol.core.debug.GO2DT;

import java.util.ArrayList;
import java.util.concurrent.CopyOnWriteArrayList;

public class GameLogic {
    private long lastTime = System.currentTimeMillis();
    private CopyOnWriteArrayList<GameObject> gameObjects = new CopyOnWriteArrayList<>();
    {
        gameObjects.add(new GO2DT(1, 2, 3).setColor(0xffff00));
        gameObjects.add(new GO2DT(7, 7, 3).setColor(0x00ff00));
        gameObjects.add(new GO2DT(12, 3, 2).setColor(0xee2589));
        gameObjects.add(new GO2DT(6, 1, 5).setColor(0x781294));
    }

    public void start() {
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
        CopyOnWriteArrayList<GameObject> updatedState = new CopyOnWriteArrayList<>();
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

    public CopyOnWriteArrayList<GameObject> getGameObjects() {
        return gameObjects;
    }
}
