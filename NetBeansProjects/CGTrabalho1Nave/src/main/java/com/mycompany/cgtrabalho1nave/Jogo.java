package com.mycompany.cgtrabalho1nave;

import core.Window;
import java.util.Random;
import javax.swing.JOptionPane;
import model.Jogador;
import model.NaveInimiga;
import static org.lwjgl.glfw.GLFW.GLFW_RELEASE;
import static org.lwjgl.glfw.GLFW.glfwSetCursorPosCallback;
import static org.lwjgl.glfw.GLFW.glfwSetKeyCallback;
import static org.lwjgl.glfw.GLFW.glfwSetMouseButtonCallback;
import static org.lwjgl.opengl.GL11.GL_COLOR_BUFFER_BIT;
import static org.lwjgl.opengl.GL11.glClear;

public class Jogo {

    private int width, height;
    private Jogador jogador;
    private GerenciadorDeEntidades manager = GerenciadorDeEntidades.getInstance();

    public Jogo(int width, int height) {
        this.width = width;
        this.height = height;
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

    public void init() {
        Window.createWindow(getWidth(), getHeight(), "É isso aí");

        jogador = new Jogador(0f, -80f, 0f, 0.8f, 0f);
        manager.addForma(jogador);

        /**
         * * Callback de tecla pressionada **
         */
        glfwSetKeyCallback(Window.getPtrWindow(), (window, key, scancode, action, mods) -> {
            keyPressed(key, action);
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
    }

    /**
     * Motor do jogo
     */
    public void engine() {
        while (!Window.shouldClose()) {
            Window.updateEvents();
            {

                glClear(GL_COLOR_BUFFER_BIT);
                //Desenhado todos os componentes
                manager.render();

                if (!manager.getlFormas().contains(jogador)) {
                    JOptionPane.showMessageDialog(null, "Você perdeu");
                    JOptionPane.showMessageDialog(null, "SU... PERA!");
                    manager.clear();
                    jogador = new Jogador(0f, -80f, 0f, 0.8f, 0f);
                    manager.addForma(jogador);
                }

                /**
                 * * Gerando inimigos **
                 */
                Random random = new Random();
                int geraInimigo = random.nextInt(1000);
                if (geraInimigo > 995) {
                    Random position = new Random(); 
                    position.ints(-100, 100);
                    int x = -100 + position.nextInt(200);
                    NaveInimiga naveInimiga = new NaveInimiga(x, 80f, 1f, 0f, 0f);
                    manager.addForma(naveInimiga);
                }

                //Verificando se os componentes ainda precisam ser desenhados
                manager.stillDrawing();
            }
            Window.swapBuffers();
        }
        Window.terminate();
    }

    public void keyPressed(int key, int action) {
        if (action > GLFW_RELEASE) {
            action = 1;
        }
        this.jogador.onKeyPressed(key, action);
    }
}
