package ru.s1pepega.gol.render;

import ru.s1pepega.gol.input.KeyboardListener;
import ru.s1pepega.gol.input.MouseListener;

import java.util.ArrayList;
import java.util.List;

import static java.lang.Math.*;
import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.opengl.GL11.*;

public class CameraSpaceRenderer {
    double xRot = 0, yRot = 0;
    double xPos = 0.5, yPos = 1.7, zPos = 0.5;
    double fov = 1, widthHeightCoef = 1, heightWidthCoef = 1, renderDistance = 100;
    int widthMid = 250, heightMid = 250;
    boolean freeze = false;
    double speed = 0.5;

    public void onWindowResize(int width, int height){
        widthMid = width /2;
        heightMid = height /2;
        widthHeightCoef = (double) width /height;
        heightWidthCoef = (double) height /width;
    }
    public void updateParams(long window){
        //swap
        if(freeze) return;

        xRot += (MouseListener.mouse.getPosY()- heightMid)/5;
        yRot += (MouseListener.mouse.getPosX()- widthMid)/5;
        if(xRot < 0) xRot+=360; else if(xRot >= 360) xRot -= 360;
        if(yRot < 0) yRot+=360; else if(yRot >= 360) yRot -= 360;
        glfwSetCursorPos(window, widthMid, heightMid);

        boolean keyW = KeyboardListener.keyboard.isKeyPressed(GLFW_KEY_W);
        boolean keyA = KeyboardListener.keyboard.isKeyPressed(GLFW_KEY_A);
        boolean keyS = KeyboardListener.keyboard.isKeyPressed(GLFW_KEY_S);
        boolean keyD = KeyboardListener.keyboard.isKeyPressed(GLFW_KEY_D);
        boolean keySh = KeyboardListener.keyboard.isKeyPressed(GLFW_KEY_LEFT_SHIFT);
        boolean keySp = KeyboardListener.keyboard.isKeyPressed(GLFW_KEY_SPACE);

        if(keySp!=keySh) yPos += (keySp ? 1 : -1) * speed;

        if((keyW==keyS)&&(keyA==keyD)) return;

        int keyAngle = 0;
        if(keyS) keyAngle += 180;
        if(keyA) keyAngle += -90 / (keyS?-2:keyW?2:1);
        if(keyD) keyAngle +=  90 / (keyS?-2:keyW?2:1);

        zPos += -speed * cos(toRadians(keyAngle+yRot));
        xPos += speed * sin(toRadians(keyAngle+yRot));

    }

    public void changeFOV(double fov){
        this.fov = Math.max(0.8,Math.min(fov,1.8));
    }

    public void applyFrustum(){
        glFrustum(-1*fov* widthHeightCoef,1*fov* widthHeightCoef,-1*fov* heightWidthCoef,1*fov* heightWidthCoef, 1, renderDistance);
    }

    public void setFreeze(boolean freeze){
        this.freeze = freeze;
    }

    public void applyTransform(){
        glRotated(xRot,1,0,0);
        glRotated(yRot,0,1,0);
        glTranslated(-xPos, -yPos, -zPos);
    }

    public void render(long window){
        updateParams(window);

        glClearColor(0.1f,0.1f,0.1f,1);
        glClear(GL_COLOR_BUFFER_BIT);
        glLoadIdentity();
        changeFOV(fov);
        applyFrustum();
        applyTransform();
        render();
        glfwSwapBuffers(window);
    }

    private void render(){
        glPushMatrix();
        debugRenderFig1();
        debugRenderAxis();
        debugRenderFigs();
        glPopMatrix();
    }

    List<IRendered> figs = new ArrayList<>();
    {
        figs.add(new Debug2DTile());
    }
    private void debugRenderFigs(){
        figs.forEach(f->f.render(yRot,xRot));
    }

    private void debugRenderFig1(){
        glPushMatrix();
        glBegin(GL_QUADS);
        for (int x = -19; x < 20; x++) {
            for (int z = -19; z < 20; z++) {
//                            glColor3d(((double)(x<<30>>>30))/3,((double)(z<<30>>>30))/3,1);
                int a = x<<30>>>30;
                int b = z<<30>>>30;
                switch (a^b){
                    case 0: glColor3d(1,1,1);break;
                    case 1: glColor3d(1,0,0);break;
                    case 2: glColor3d(0,1,0);break;
                    case 3: glColor3d(0,0,1);break;
                }
                glVertex3d(x+1,0,z+1);
                glVertex3d(x,0,z+1);
                glVertex3d(x,0,z);
                glVertex3d(x+1,0,z);
            }
        }
        glEnd();
        glPopMatrix();
    }

    private void debugRenderAxis(){
        glPushMatrix();
            glLineWidth(3);
            glBegin(GL_LINES);
                glColor3d(1,0,0);
                glVertex3d(0,0,0);
                glVertex3d(5,0,0);
                glColor3d(0,1,0);
                glVertex3d(0,0,0);
                glVertex3d(0,5,0);
                glColor3d(0,0,1);
                glVertex3d(0,0,0);
                glVertex3d(0,0,5);
            glEnd();
        glPopMatrix();
    }
}
