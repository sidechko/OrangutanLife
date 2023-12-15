package ru.s1pepega.gol.core;

import static org.lwjgl.opengl.GL11.*;

public abstract class GameObject {
    public int posX = 0, posY = 0, posZ = 0;
    protected boolean needRemove = false;

    //Draw entity from center
    public abstract void draw();

    public void render(double yAngle, double xAngle) {
        glPushMatrix();
        glTranslated(posX, posY, posZ);
        if (is2D()) {
            glRotated(-yAngle, 0, 1, 0);
            glRotated(-xAngle, 1, 0, 0);
        }
        glPushMatrix();
        this.draw();
        glPopMatrix();
        glPopMatrix();
    }



    public GameObject update(){

        return this;
    }

    protected boolean is2D(){return false;}
}
