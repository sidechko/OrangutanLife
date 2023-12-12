package ru.s1pepega.gol.render;

import static org.lwjgl.opengl.GL11.*;

public class Debug2DTile implements IRendered {
    @Override
    public void render(double yAngle, double xAngle) {
        glPushMatrix();
            glTranslated(5,5,5);
            glRotated(-yAngle,0,1,0);
            glRotated(-xAngle,1,0,0);
            glBegin(GL_QUADS);
                glColor3d(1,1,1);
                glVertex3d(-1,-1,0);
                glVertex3d(1,-1,0);
                glVertex3d(1,1,0);
                glVertex3d(-1,1,0);
            glEnd();
        glPopMatrix();
    }
}
