package ru.s1pepega.gol.render;

import org.lwjgl.Version;
import org.lwjgl.glfw.GLFWErrorCallback;
import org.lwjgl.opengl.GL;
import ru.s1pepega.gol.input.KeyboardListener;
import ru.s1pepega.gol.input.MouseListener;

import static org.lwjgl.glfw.Callbacks.glfwFreeCallbacks;
import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.system.MemoryUtil.NULL;

public class Window {
    private int width, height;
    private final String title;
    private long glfwWindow;
    private final CameraSpaceRenderer camera = new CameraSpaceRenderer();
    private static Window window = null;

    private Window() {
        this.width = 500;
        this.height = 500;
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

        glfwSetCursorPosCallback(glfwWindow, MouseListener.mouse::mousePosCallback);
        glfwSetMouseButtonCallback(glfwWindow,MouseListener.mouse::mouseButtonCallback);
        glfwSetScrollCallback(glfwWindow,MouseListener.mouse::mouseScrollCallback);
        glfwSetKeyCallback(glfwWindow,KeyboardListener.keyboard::keyCallback);
        glfwSetWindowSizeCallback(glfwWindow,this::onWindowResize);
        glfwSetWindowFocusCallback(glfwWindow,this::onFocusChange);
        // Make gl context
        glfwMakeContextCurrent(glfwWindow);

        //v-sync
        glfwSwapInterval(1);

        //visible
        glfwShowWindow(glfwWindow);

        GL.createCapabilities();
    }

    private void onFocusChange(long window, boolean isFocus) {
        camera.setFreeze(!isFocus);
    }

    double fov = 1;
    protected void loop() {
        while (!glfwWindowShouldClose(glfwWindow)){
            glfwPollEvents();

            camera.render(glfwWindow);

            MouseListener.mouse.endFrame();
            KeyboardListener.keyboard.endFrame();
        }
    }

    private void onWindowResize(long window, int width, int height){
        this.width = width;
        this.height = height;
        camera.onWindowResize(width,height);
        glViewport(0,0,width,height);
    }
}

