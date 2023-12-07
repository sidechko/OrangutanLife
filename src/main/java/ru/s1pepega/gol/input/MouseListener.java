package ru.s1pepega.gol.input;

import static org.lwjgl.glfw.GLFW.GLFW_PRESS;
import static org.lwjgl.glfw.GLFW.GLFW_RELEASE;

public class MouseListener {
    private double scrollX, scrollY;
    private double posX, posY, lastX, lastY;
    private boolean mouseButtonPressed[] = new boolean[3];
    private boolean isDragging;
    public static final MouseListener mouse = new MouseListener();

    private MouseListener(){
        scrollX = 0d;
        scrollY = 0d;
        posX = 0d;
        posY = 0d;
        lastX = 0d;
        lastY = 0d;
    }

    public void mousePosCallback(long window, double xpos, double ypos){
        lastX = posX;
        lastY = posY;
        posX = xpos;
        posY = ypos;
        isDragging =
                mouseButtonPressed[0]
                || mouseButtonPressed[1]
                || mouseButtonPressed[2];
    }

    public void mouseButtonCallback(long window, int button, int action, int mode){
        if(button < mouseButtonPressed.length)
            if(action == GLFW_PRESS)
                mouseButtonPressed[button] = true;
            else if(action == GLFW_RELEASE){
                mouseButtonPressed[button] = false;
                isDragging = false;
            }
    }

    public double getMoveX(){
        return posX - lastX;
    }

    public double getPosX(){
        return posX;
    }
    public double getLastX(){
        return lastX;
    }

    public double getMoveY(){
        return posY - lastY;
    }

    public double getPosY(){
        return posY;
    }
    public double getLastY(){
        return lastY;
    }

    public double getScrollY(){
        return scrollY;
    }

    public void mouseScrollCallback(long window, double xOff, double yOff){
        scrollX = xOff;
        scrollY = yOff;
    }

    public void endFrame(){
        scrollX = 0;
        scrollY = 0;
        lastX = posX;
        lastY = posY;
    }
}
