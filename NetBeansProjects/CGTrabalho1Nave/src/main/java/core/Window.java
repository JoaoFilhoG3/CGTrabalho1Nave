package core;

import static org.lwjgl.glfw.GLFW.*;

import org.lwjgl.glfw.GLFWVidMode;
import org.lwjgl.opengl.GL;
import org.lwjgl.opengl.GL11;

public class Window {

    private static boolean hasInit = false;
    private static long ptrWindow;
    private static int width;
    private static int height;

    public static void init() {
        if (!hasInit) {
            hasInit = true;
            if (!glfwInit()) {
                System.err.println("Não foi possível iniciar o GLFW!");
                System.exit(-1);
            }
        }
    }

    public static void createWindow(int width, int height, String title) {
        init();
        Window.width = width;
        Window.height = height;
        ptrWindow = glfwCreateWindow(width, height, title, 0, 0);
        GLFWVidMode vm = glfwGetVideoMode(glfwGetPrimaryMonitor());
        glfwSetWindowPos(ptrWindow, (vm.width() - width) / 2, (vm.height() - height) / 2);
        glfwMakeContextCurrent(ptrWindow);
        GL.createCapabilities();
    }

    public static void updateEvents() {
        glfwPollEvents();
    }

    public static void clearColor() {
        GL11.glClear(GL11.GL_COLOR_BUFFER_BIT);
    }

    public static void swapBuffers() {
        glfwSwapBuffers(ptrWindow);
    }

    public static boolean shouldClose() {
        return glfwWindowShouldClose(ptrWindow);
    }

    public static boolean getKey(int key) {
        return glfwGetKey(ptrWindow, key) > GLFW_RELEASE;
    }

    public static void terminate() {
        if (hasInit) {
            hasInit = false;
            glfwMakeContextCurrent(0);
            glfwDestroyWindow(ptrWindow);
        }
    }

    public static long getPtrWindow() {
        return ptrWindow;
    }

    public static int getWidth() {
        return width;
    }

    public static int getHeight() {
        return height;
    }

}
