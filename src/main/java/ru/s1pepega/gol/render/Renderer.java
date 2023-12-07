package ru.s1pepega.gol.render;

import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL11.glPopMatrix;

public class Renderer {
    double fov = 1;
    double wCh = 1;
    double hCw = 1;
    double renderDistance = 100;
    public void onWindowResize(int width, int height){
        wCh = (double) width /height;
        hCw = (double) height /width;
    }

    public void changeFOV(double fov){
        this.fov = Math.max(0.8,Math.min(fov,1.8));
    }

    public void applyFrustum(){
        glFrustum(-1*fov*wCh,1*fov*wCh,-1*fov*hCw,1*fov*hCw, 1, renderDistance);
    }

    public void render(){
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
        glColor3d(0,1,0);
        glVertex3d(1,1,1);
        glVertex3d(0,1,1);
        glVertex3d(0,1,0);
        glVertex3d(1,1,0);
        glEnd();
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
