package ru.s1pepega.gol.core.debug;

import ru.s1pepega.gol.core.GameObject;

import static org.lwjgl.opengl.GL11.*;

public class GO3DFloor extends GameObject{

    public void draw(){
        glPushMatrix();
        glBegin(GL_QUADS);
        for (int x = -19; x < 20; x++) {
            for (int z = -19; z < 20; z++) {
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
}
