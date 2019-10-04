package model;

import java.util.List;
import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL11.glBegin;
import static org.lwjgl.opengl.GL11.glColor3f;
import static org.lwjgl.opengl.GL11.glEnd;
import static org.lwjgl.opengl.GL11.glVertex2fv;

public class Tiro extends Desenhavel {

    public static int DIRECAO_CIMA = 1;
    public static int DIRECAO_BAIXO = 2;
    public static int DIRECAO_ESQUERDA = 3;
    public static int DIRECAO_DIREITA = 4;

    private int direcao;

    private Desenhavel dono;

    public Tiro(float x, float y, int direcao, float colorR, float colorG, float colorB, Desenhavel dono) {
        super(x, y, 1, colorR, colorG, colorB);
        this.direcao = direcao;
        this.dono = dono;
    }

    /**
     * * Método responsável por desenhar a nave na tela **
     */
    @Override
    public void desenhar() {
        float vertices[][] = new float[][]{
            new float[]{getPosX() - 1, getPosY()},
            new float[]{getPosX() - 1, getPosY() + 5},
            new float[]{getPosX() + 1, getPosY() + 5},
            new float[]{getPosX() + 1, getPosY()}
        };

        glColor3f(this.getColorR(), this.getColorG(), this.getColorB());
        glBegin(GL_QUADS);
        glLineWidth(5);
        for (int i = 0; i < vertices.length; i++) {
            glVertex2fv(vertices[i]);
        }

        glEnd();
    }

    @Override
    public boolean update(List<Desenhavel> lFormas) {
        if (getDirecao() == DIRECAO_CIMA) {
            if (getPosY() < 100) {
                setPosition(getPosX(), getPosY() + 3);
                return true;
            } else {
                return false;
            }
        } else if (getDirecao() == DIRECAO_BAIXO) {
            if (getPosY() < 100) {
                setPosition(getPosX(), getPosY() - 3);
                return true;
            } else {
                return false;
            }
        } else if (getDirecao() == DIRECAO_ESQUERDA) {
            if (getPosX() < 100) {
                setPosition(getPosX() + 3, getPosY());
                return true;
            } else {
                return false;
            }
        } else if (getDirecao() == DIRECAO_DIREITA) {
            if (getPosX() < 100) {
                setPosition(getPosX() - 3, getPosY());
                return true;
            } else {
                return false;
            }
        } else {
            return false;
        }

    }

    public int getDirecao() {
        return direcao;
    }

    public void setDirecao(int direcao) {
        this.direcao = direcao;
    }

    public Desenhavel getDono() {
        return dono;
    }

    public void setDono(Desenhavel dono) {
        this.dono = dono;
    }

}
