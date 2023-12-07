package ru.s1pepega.gol.input;

import java.util.Arrays;

import static org.lwjgl.glfw.GLFW.GLFW_PRESS;
import static org.lwjgl.glfw.GLFW.GLFW_RELEASE;

public class KeyboardListener {
    public static final KeyboardListener keyboard = new KeyboardListener();
    private boolean keyPressed[] = new boolean[350];
    private boolean keyBeginPress[] = new boolean[350];

    private KeyboardListener() {

    }

    public void keyCallback(long window, int key, int scancode, int action, int mods) {
        if (action == GLFW_PRESS) {
            keyPressed[key] = true;
            keyBeginPress[key] = true;
        } else if (action == GLFW_RELEASE) {
            keyPressed[key] = false;
            keyBeginPress[key] = false;
        }
    }

    public void endFrame() {
        Arrays.fill(keyBeginPress, false);
    }

    public boolean isKeyPressed(int keyCode) {
        return keyPressed[keyCode];
    }

    public boolean keyBeginPress(int keyCode) {
        return keyBeginPress[keyCode];
    }
}
