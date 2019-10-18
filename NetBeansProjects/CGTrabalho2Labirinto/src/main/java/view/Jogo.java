package view;

import controller.Window;
import interfaces.KeyListener;
import controller.EntityManager;
import controller.Texture;
import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.opengl.GL11.*;

public class Jogo implements KeyListener {

    private int width;
    private int height;
    private EntityManager manager = EntityManager.getInstance();

    private static Jogo jogo = new Jogo(500, 500);

    private Jogo(int width, int height) {
        this.width = width;
        this.height = height;
    }

    public static Jogo getInstance() {
        return jogo;
    }

    /**
     * Método principal
     *
     * @param args
     */
    public static void main(String[] args) {

        //Criando a janela
        Window.createWindow(jogo.getWidth(), jogo.getHeight(), "É isso aí");
        glMatrixMode(GL_PROJECTION);
        glLoadIdentity();
        glOrtho(0, Window.getWidth(), Window.getHeight(), 0, -1, 1);
        glMatrixMode(GL_MODELVIEW);
        glEnable(GL_TEXTURE_2D);
        glBlendFunc(GL_SRC_ALPHA, GL_ONE_MINUS_SRC_ALPHA);

        /**
         * * Callback de tecla pressionada **
         */
        glfwSetKeyCallback(Window.getPtrWindow(), (window, key, scancode, action, mods) -> {
            jogo.onKeyEvent(key, action);
        });

        /**
         * * Callback de posição do mouse **
         */
        glfwSetCursorPosCallback(Window.getPtrWindow(), (long window, double xPos, double yPos) -> {

        });

        /**
         * * Callback de clique do mouse **
         */
        glfwSetMouseButtonCallback(Window.getPtrWindow(), (long window, int button, int action, int mods) -> {

        });

        Texture texture = new Texture("environment/grassland/grassland1.png");

        //Motor
        while (!Window.shouldClose()) {
            Window.updateEvents();
            glClear(GL_COLOR_BUFFER_BIT);
            {
                jogo.getManager().render();

                texture.bind();

                glBegin(GL_QUADS);
                {
                    glTexCoord2f(0, 0);
                    glVertex2f(0, 0);

                    glTexCoord2f(0, 1);
                    glVertex2f(0, 500);

                    glTexCoord2f(1, 1);
                    glVertex2f(500, 500);

                    glTexCoord2f(1, 0);
                    glVertex2f(500, 0);
                }
                glEnd();

                jogo.getManager().update();
            }
            Window.swapBuffers();
        }

        Window.terminate();
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public EntityManager getManager() {
        return manager;
    }

    public void setManager(EntityManager manager) {
        this.manager = manager;
    }

    @Override
    public void onKeyEvent(int key, int action) {
    }

}
