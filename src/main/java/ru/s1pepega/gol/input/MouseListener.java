package ru.s1pepega.gol.input;

import static org.lwjgl.glfw.GLFW.GLFW_PRESS;
import static org.lwjgl.glfw.GLFW.GLFW_RELEASE;

public class MouseListener {
    private static MouseListener listener;
    private double scrollX, scrollY;
    private double posX, posY, lastX, lastY;
    private boolean mouseButtonPressed[] = new boolean[3];
    private boolean isDragging;

    private MouseListener(){
        scrollX = 0d;
        scrollY = 0d;
        posX = 0d;
        posY = 0d;
        lastX = 0d;
        lastY = 0d;
    }

    public static MouseListener instance(){
        if(listener==null)
            listener = new MouseListener();
        return listener;
    }

    public static void mousePosCallback(long window, double xpos, double ypos){
        instance().lastX = instance().posX;
        instance().lastY = instance().posY;
        instance().posX = xpos;
        instance().posY = ypos;
        instance().isDragging =
                instance().mouseButtonPressed[0]
                || instance().mouseButtonPressed[1]
                || instance().mouseButtonPressed[2];
    }

    public static void mouseButtonCallback(long window, int button, int action, int mode){
        if(button < instance().mouseButtonPressed.length)
            if(action == GLFW_PRESS)
                instance().mouseButtonPressed[button] = true;
            else if(action == GLFW_RELEASE){
                instance().mouseButtonPressed[button] = false;
                instance().isDragging = false;
            }
    }

    public static void mouseScrollCallback(long window, double xOff, double yOff){
        instance().scrollX = xOff;
        instance().scrollY = yOff;
    }

    public static void endFrame(){
        instance().scrollX = 0;
        instance().scrollY = 0;
        instance().lastX = instance().posX;
        instance().lastY = instance().posY;
    }
}
