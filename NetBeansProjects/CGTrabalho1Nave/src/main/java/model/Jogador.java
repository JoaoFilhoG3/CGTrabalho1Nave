package model;

import com.mycompany.cgtrabalho1nave.GerenciadorDeEntidades;
import interfaces.OnKeyPressed;
import java.util.List;
import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.opengl.GL11.glBegin;
import static org.lwjgl.opengl.GL11.glColor3f;
import static org.lwjgl.opengl.GL11.glEnd;
import static org.lwjgl.opengl.GL11.glVertex2fv;
import static org.lwjgl.opengles.GLES32.GL_QUADS;

public class Jogador extends Desenhavel implements OnKeyPressed {

    public static final int MOVER_ESQUERDA = -1;
    public static final int MOVER_DIREITA = 1;

    private int esquerdaPressed = 0;
    private int direitaPressed = 0;
    private int atirando = 0;

    private float direcaoX = 0.5f;
    private float recarga = 0;
    private int numTiros = 3;

    private Tiro lTiros[] = new Tiro[numTiros];

    /**
     * Construtor da classe
     *
     * @param x
     * @param y
     * @param direcao
     * @param colorR
     * @param colorG
     * @param colorB
     */
    public Jogador(float x, float y, float colorR, float colorG, float colorB) {
        super(x, y, 5, colorR, colorG, colorB);
    }

    /**
     * * Método responsável por desenhar a nave na tela **
     */
    @Override
    public void desenhar() {
        /**
         * Criando os vértices
         */
        float vertices[][] = null;
        vertices = new float[][]{
            new float[]{getPosX() - 5, getPosY() - 15},
            new float[]{getPosX(), getPosY() - 10},
            new float[]{getPosX() + 5, getPosY() - 15},
            new float[]{getPosX(), getPosY()}
        };

        /**
         * Definindo a cor
         */
        glColor3f(this.getColorR(), this.getColorG(), this.getColorB());

        /**
         * Desenhando vértices
         */
        glBegin(GL_QUADS);
        for (int i = 0; i < vertices.length; i++) {
            glVertex2fv(vertices[i]);
        }
        glEnd();

    }

    /**
     * * Método responsável por mover a nave **
     */
    public void move(int direcao) {
        if (direcao == MOVER_ESQUERDA) {
            if (!(getPosX() <= -90)) {
                setPosition(getPosX() - 1, getPosY());
            }
        } else if (direcao == MOVER_DIREITA) {
            if (!(getPosX() >= 90)) {
                setPosition(getPosX() + 1, getPosY());
            }
        }
    }

    public void atirar(List<Desenhavel> lFormas, int direcao) {
        for (int i = 0; i < lTiros.length; i++) {
            if (!lFormas.contains(lTiros[i])) {
                lTiros[i] = null;
            }
        }
        for (int i = 0; i < lTiros.length; i++) {

            if (lTiros[i] == null) {
                Tiro p = new Tiro(getPosX(), getPosY(), direcao, 0f, 0f, 1f, this);
                lFormas.add(p);
                lTiros[i] = p;
                return;
            }
        }

//        if (getRecarga() <= 0) {
//            
//            
//            
//
//            if (numTiros > 1) {
//                setRecarga(5);
//                numTiros--;
//            } else {
//                setRecarga(10);
//                numTiros = 3;
//            }
//
//        }
    }

    @Override
    public boolean update(List<Desenhavel> lFormas) {
        if (atirando > 0) {
            atirar(lFormas, Tiro.DIRECAO_CIMA);
        }

        if (recarga > 0) {
            recarga--;
        }

        move(direitaPressed - esquerdaPressed);
        return true;
    }

    public float getRecarga() {
        return recarga;
    }

    public void setRecarga(float recarga) {
        this.recarga = recarga;
    }

    @Override
    public void onKeyPressed(int key, int action) {
        switch (key) {
            case GLFW_KEY_A:
                esquerdaPressed = action;
                break;
            case GLFW_KEY_D:
                direitaPressed = action;
                break;
            case GLFW_KEY_SPACE:
                atirando = action;
                this.atirar(GerenciadorDeEntidades.getInstance().getlFormas(), Tiro.DIRECAO_CIMA);
                break;
        }
    }

}
