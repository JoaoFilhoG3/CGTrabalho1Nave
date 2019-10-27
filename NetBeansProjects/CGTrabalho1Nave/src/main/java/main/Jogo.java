package main;

import core.Window;
import interfaces.KeyListener;
import java.util.Random;
import model.Asteroid;
import model.EntityManager;
import model.Enemy;
import model.Player;
import static org.lwjgl.glfw.GLFW.glfwSetCursorPosCallback;
import static org.lwjgl.glfw.GLFW.glfwSetKeyCallback;
import static org.lwjgl.glfw.GLFW.glfwSetMouseButtonCallback;
import static org.lwjgl.opengl.GL11.GL_COLOR_BUFFER_BIT;
import static org.lwjgl.opengl.GL11.GL_MODELVIEW;
import static org.lwjgl.opengl.GL11.GL_PROJECTION;
import static org.lwjgl.opengl.GL11.glClear;
import static org.lwjgl.opengl.GL11.glLoadIdentity;
import static org.lwjgl.opengl.GL11.glMatrixMode;
import static org.lwjgl.opengl.GL11.glOrtho;

public class Jogo implements KeyListener {

    private int width;
    private int height;
    private EntityManager manager = EntityManager.getInstance();
    private Player player;

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

        jogo.setPlayer(new Player(jogo.getWidth() / 2, jogo.getHeight() - 50, 1, 0, 0));
        jogo.getManager().add(jogo.getPlayer());

        //Criando a janela
        Window.createWindow(jogo.getWidth(), jogo.getHeight(), "É isso aí");
        glMatrixMode(GL_PROJECTION);
        glLoadIdentity();
        glOrtho(0, Window.getWidth(), Window.getHeight(), 0, -1, 1);
        glMatrixMode(GL_MODELVIEW);

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

        //Motor
        while (!Window.shouldClose()) {
            Window.updateEvents();
            glClear(GL_COLOR_BUFFER_BIT);
            {
                jogo.getManager().render();

                if (new Random().nextInt(300) == 99) {
                    Enemy ni = new Enemy(0, 0, 1);
                    jogo.getManager().add(ni);
                }

                if (new Random().nextInt(300) == 100) {
                    Asteroid a = new Asteroid();
                    jogo.getManager().add(a);
                }

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

    public Player getPlayer() {
        return player;
    }

    public void setPlayer(Player player) {
        this.player = player;
    }

    @Override
    public void onKeyEvent(int key, int action) {
        if (action > 1) {
            action = 1;
        }
        jogo.getPlayer().onKeyEvent(key, action);
    }

}
