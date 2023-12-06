package ru.s1pepega.gol;

import org.lwjgl.Version;
import org.lwjgl.glfw.GLFWErrorCallback;
import org.lwjgl.opengl.GL;
import ru.s1pepega.gol.input.KeyboardListener;
import ru.s1pepega.gol.input.MouseListener;
import ru.s1pepega.gol.render.CameraController;

import static org.lwjgl.glfw.Callbacks.glfwFreeCallbacks;
import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.system.MemoryUtil.NULL;

public class Window {
    private int width, height;
    private String title;
    private long glfwWindow;
    private CameraController camera = new CameraController();

    private static Window window = null;

    private Window() {
        this.width = 1000;
        this.height = 1000;
        this.title = "OrgLife";
    }

    public static Window instance() {
        if (window == null)
            window = new Window();
        return Window.window;
    }

    private long getWindow(){return glfwWindow;}
    public int getHeight() {
        return height;
    }
    public int getWidth() {
        return width;
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
        glfwSetKeyCallback(glfwWindow,KeyboardListener::keyCallback);

        // Make gl context
        glfwMakeContextCurrent(glfwWindow);

        //v-sync
        glfwSwapInterval(1);

        //visible
        glfwShowWindow(glfwWindow);

        GL.createCapabilities();
    }

    static final double i = 0.1;
    protected void loop() {
        while (!glfwWindowShouldClose(glfwWindow)){
            glfwPollEvents();
            camera.updateParams(glfwWindow);

            glClearColor(0.1f,0.1f,0.1f,1);
            glClear(GL_COLOR_BUFFER_BIT);

            glLoadIdentity();
            glFrustum(-2,2,-2,2,1,100);
            camera.applyTransfroms();
            glPushMatrix();
                glBegin(GL_QUADS);
                    for (int x = -19; x < 20; x++) {
                        for (int z = -19; z < 20; z++) {
//                            glColor3d(((double)(x<<30>>>30))/3,((double)(z<<30>>>30))/3,1);
                            if(x<<31==z<<31)
                                glColor3d(1,1,1);
                            else
                                glColor3d(0,0,0);
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

            glfwSwapBuffers(glfwWindow);

            MouseListener.endFrame();
            KeyboardListener.endFrame();
        }
    }
}

