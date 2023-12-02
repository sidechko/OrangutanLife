package ru.s1pepega.gol;

import org.lwjgl.Version;
import org.lwjgl.glfw.GLFWErrorCallback;
import org.lwjgl.opengl.GL;
import ru.s1pepega.gol.input.MouseListener;

import static org.lwjgl.glfw.Callbacks.glfwFreeCallbacks;
import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.system.MemoryUtil.NULL;

public class Window {
    private int width, height;
    private String title;
    private long glfwWindow;

    private static Window window = null;

    private Window() {
        this.width = 1920;
        this.height = 1080;
        this.title = "OrgLife";
    }

    public static Window instance() {
        if (window == null)
            window = new Window();
        return Window.window;
    }

    public void run() {
        System.out.println("Hello LWJGL " + Version.getVersion() + "!");
        init();
        loop();

        //free memory
        glfwFreeCallbacks(glfwWindow);
        glfwDestroyWindow(glfwWindow);

        // terminate GLFW and the free the error callback
        glfwTerminate();
        glfwSetErrorCallback(null).free();
    }

    protected void init() {
        GLFWErrorCallback.createPrint(System.err).set();

        if (!glfwInit())
            throw new IllegalStateException("Unable to initialize GLFW.");

        //window params
        glfwDefaultWindowHints();
        glfwWindowHint(GLFW_VISIBLE, GLFW_FALSE);
        glfwWindowHint(GLFW_RESIZABLE, GLFW_TRUE);
        glfwWindowHint(GLFW_MAXIMIZED, GLFW_FALSE);

        //create window
        glfwWindow = glfwCreateWindow(width,height,title,NULL,NULL);
        if(glfwWindow == NULL)
            throw new IllegalStateException("Failed to create the GLFW window.");

        glfwSetCursorPosCallback(glfwWindow, MouseListener::mousePosCallback);
        glfwSetMouseButtonCallback(glfwWindow,MouseListener::mouseButtonCallback);
        glfwSetScrollCallback(glfwWindow,MouseListener::mouseScrollCallback);

        // Make gl context
        glfwMakeContextCurrent(glfwWindow);

        //v-sync
        glfwSwapInterval(1);

        //visible
        glfwShowWindow(glfwWindow);

        GL.createCapabilities();
    }

    protected void loop() {
        double ang = 60;
        float move = 0;
        float color = 0.1f;
        while (!glfwWindowShouldClose(glfwWindow)){
            //poll inputs
            glfwPollEvents();
            //draw

            glClearColor(color,color,color,1);
            glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);

            glEnable(GL_DEPTH_TEST);
            glEnable(GL_CULL_FACE);
            glFrontFace(GL_CCW);
            glCullFace(GL_BACK);

            //normalize window

            //camera
            glLoadIdentity();
            //hints resize
            glScaled(0.5208,0.9259,1);
            ang+=0.1f;
            glRotated(-30,1,0,0);
            glRotated(ang,0,1,0);
            glScaled(0.1,0.1,0.1);

            //Axis
            glPointSize(5);
            glBegin(GL_POINTS);
            glColor3f(1,1,1);
            glVertex3d(0,0,0);
            glColor3f(1,0,0);
            glVertex3d(1,0,0);
            glColor3f(0,1,0);
            glVertex3d(0,1,0);
            glColor3f(0,0,1);
            glVertex3d(0,0,1);
            glEnd();


            glLineWidth(1);
            glBegin(GL_LINES);
            glColor3f(1,0,0); glVertex3d(-100,0,0);
            glColor3f(1,0,0); glVertex3d(100,0,0);

            glColor3f(0,1,0); glVertex3d(0,-100,0);
            glColor3f(0,1,0); glVertex3d(0,100,0);

            glColor3f(0,0,1); glVertex3d(0,0,-100);
            glColor3f(0,0,1); glVertex3d(0,0,100);
            glEnd();

            move += 0.05f;
            glPushMatrix();
                glBegin(GL_LINE_STRIP);
                    for (double i = -10; i < 10; i+=0.001) {
                        glColor3d(i/10,-i/10,1);
                        glVertex3d(i,Math.sin(i+move)*2, Math.cos(i+move)*2);
                        glVertex3d(i,0, 0);
                    }
                glEnd();
//                glRotated(ang+=2,1,49,0);
//                glTranslated(1, Math.sin(ang/20), 1);
//                glScaled(2,4,2);
//
//                glBegin(GL_TRIANGLES);
//                    glColor3f(0,0,1); glVertex3d(0,0,.577d);//blue
//                    glColor3f(1,1,1); glVertex3d(0,.8,0);//white
//                    glColor3f(0,1,0); glVertex3d(.5d,0,-.289d);//green
//                    glColor3f(0,0,1); glVertex3d(0,0,.577d);//blue
//                    glColor3f(0,1,0); glVertex3d(.5d,0,-.289d);//green
//                    glColor3f(0,0,0); glVertex3d(0,-.8,0);//black
//
//                    glColor3f(1,0,0); glVertex3d(-.5d,0,-.289d);//red
//                    glColor3f(1,1,1); glVertex3d(0,.8,0);//white
//                    glColor3f(0,0,1); glVertex3d(0,0,.577d);//blue
//                    glColor3f(1,0,0); glVertex3d(-.5d,0,-.289d);//red
//                    glColor3f(0,0,1); glVertex3d(0,0,.577d);//blue
//                    glColor3f(0,0,0); glVertex3d(0,-.8,0);//black
//
//                    glColor3f(0,1,0); glVertex3d(.5d,0,-.289d);//green
//                    glColor3f(1,1,1); glVertex3d(0,.8,0);//white
//                    glColor3f(1,0,0); glVertex3d(-.5d,0,-.289d);//red
//                    glColor3f(0,1,0); glVertex3d(.5d,0,-.289d);//green
//                    glColor3f(1,0,0); glVertex3d(-.5d,0,-.289d);//red
//                    glColor3f(0,0,0); glVertex3d(0,-.8,0);//black
//                glEnd();
            glPopMatrix();


            glfwSwapBuffers(glfwWindow);
        }
    }
}

