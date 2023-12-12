package ru.s1pepega.gol.core.debug;

import ru.s1pepega.gol.core.GameObject;

import static org.lwjgl.opengl.GL11.*;

public class GO2DT extends GameObject {
    int color = 0xff00ff;

    public GO2DT(int x, int y, int z){
        posX = x; posY = y; posZ = z;
    }

    @Override
    protected boolean is2D() {
        return true;
    }

    public GO2DT setColor(int color){this.color =color; return this;}
    @Override
    public void draw() {
        glBegin(GL_QUADS);
            glColor3d(((double)(color>>16&255))/256,((double)(color>>8&255))/256,((double)(color&255))/256);
            glVertex3d(1,1,0);
            glVertex3d(-1,1,0);
            glVertex3d(-1,-1,0);
            glVertex3d(1,-1,0);
        glEnd();
    }
}
