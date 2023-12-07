package ru.s1pepega.gol.render;

import ru.s1pepega.gol.input.KeyboardListener;
import ru.s1pepega.gol.input.MouseListener;

import static java.lang.Math.*;
import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.opengl.GL11.glRotated;
import static org.lwjgl.opengl.GL11.glTranslated;

public class CameraController {
    double xRot = 0, yRot = 0;
    double xPos = 0.5, yPos = 1.7, zPos = 0.5;
    double speed = 0.5;
    boolean swap = false;
    int wwc = 250;
    int whc = 250;

    public void onWindowResize(int width, int height){
        wwc = width /2;
        whc = height /2;
    }
    public void updateParams(long window){
        //swap
        if(KeyboardListener.keyboard.isKeyPressed(GLFW_KEY_C))
            swap = !swap;
        if(swap) return;

        xRot += (MouseListener.mouse.getPosY()-whc)/5;
        yRot += (MouseListener.mouse.getPosX()-wwc)/5;
        if(xRot < 0) xRot+=360; else if(xRot >= 360) xRot -= 360;
        if(yRot < 0) yRot+=360; else if(yRot >= 360) yRot -= 360;
        glfwSetCursorPos(window,wwc,whc);

        boolean keyW = KeyboardListener.keyboard.isKeyPressed(GLFW_KEY_W);
        boolean keyA = KeyboardListener.keyboard.isKeyPressed(GLFW_KEY_A);
        boolean keyS = KeyboardListener.keyboard.isKeyPressed(GLFW_KEY_S);
        boolean keyD = KeyboardListener.keyboard.isKeyPressed(GLFW_KEY_D);
        boolean keySh = KeyboardListener.keyboard.isKeyPressed(GLFW_KEY_LEFT_SHIFT);
        boolean keySp = KeyboardListener.keyboard.isKeyPressed(GLFW_KEY_SPACE);

        if(keySp!=keySh) yPos += (keySp ? 1 : -1) * speed;

        if((keyW==keyS)&&(keyA==keyD)) return;
        double ang = yRot;
        if(keyS) ang += 180;
        if(keyA) ang += -90;
        if(keyD) ang += 90;

        zPos += -speed * cos(toRadians(ang));
        xPos += speed * sin(toRadians(ang));

//        if(keySp!=keySh) yPos += (keySp ? 1 : -1) * speed;

    }

    public void applyTransform(){
        glRotated(xRot,1,0,0);
        glRotated(yRot,0,1,0);
        glTranslated(-xPos, -yPos, -zPos);
    }
}
