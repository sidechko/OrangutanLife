package ru.s1pepega.gol.render;

import ru.s1pepega.gol.input.KeyboardListener;
import ru.s1pepega.gol.input.MouseListener;

import static java.lang.Math.*;
import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.opengl.GL11.glRotated;
import static org.lwjgl.opengl.GL11.glTranslated;

public class CameraController {
    boolean exit = false;
    double xRot = 0, yRot = 0;
    double xPos = 0, yPos = 1.7, zPos = 0;
    double speed = 0.5;

    public void updateParams(long window){

        if(exit)
            return;


        xRot += (MouseListener.getPosY()-500)/5;
        yRot += (MouseListener.getPosX()-500)/5;
        glfwSetCursorPos(window,500,500);

        boolean keyW = KeyboardListener.isKeyPressed(GLFW_KEY_W);
        boolean keyA = KeyboardListener.isKeyPressed(GLFW_KEY_A);
        boolean keyS = KeyboardListener.isKeyPressed(GLFW_KEY_S);
        boolean keyD = KeyboardListener.isKeyPressed(GLFW_KEY_D);
        boolean keySh = KeyboardListener.isKeyPressed(GLFW_KEY_LEFT_SHIFT);
        boolean keySp = KeyboardListener.isKeyPressed(GLFW_KEY_SPACE);

        if(KeyboardListener.isKeyPressed(GLFW_KEY_R)){
            xPos = 0; yPos = 1.7; zPos = 0;
            return;
        }

        if((keyW==keyS)&&(keyA==keyD)&&(keySh==keySp))
            return;

        if(keyW!=keyS) zPos += (keyW ? -1 : 1) * speed;
        if(keyA!=keyD) xPos += (keyA ? -1 : 1) * speed;
        if(keySp!=keySh) yPos += (keySp ? -1 : 1) * speed;

    }

    public void applyTransfroms(){
        glRotated(xRot,1,0,0);
        glRotated(yRot,0,1,0);
        glTranslated(xPos, yPos, zPos);
    }
}
