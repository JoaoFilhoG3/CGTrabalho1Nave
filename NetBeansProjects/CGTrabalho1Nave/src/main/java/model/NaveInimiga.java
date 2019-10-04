package model;

import java.util.List;
import static org.lwjgl.opengl.GL11.GL_QUADS;
import static org.lwjgl.opengl.GL11.glBegin;
import static org.lwjgl.opengl.GL11.glColor3f;
import static org.lwjgl.opengl.GL11.glEnd;
import static org.lwjgl.opengl.GL11.glVertex2fv;
import utils.MyUtils;

public class NaveInimiga extends Desenhavel {

    private int atirando = 0;

    private float direcaoX = 0.5f;
    private float recarga = 0;
    private int numTiros = 3;

    public NaveInimiga(float posX, float posY, float colorR, float colorG, float colorB) {
        super(posX, posY, 5, colorR, colorG, colorB);
    }

    @Override
    public void desenhar() {
        /**
         * Criando os vértices
         */
        float vertices[][] = null;
        vertices = new float[][]{
            new float[]{getPosX() + 5, getPosY() + 15},
            new float[]{getPosX(), getPosY() + 10},
            new float[]{getPosX() - 5, getPosY() + 15},
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

    @Override
    public boolean update(List<Desenhavel> lFormas) {

        if (getPosY() < -120) {
            return false;
        }

        int pos = MyUtils.randInt(0, 20, 1);
        if (pos == 10) {
            direcaoX = -direcaoX;
        }
        setPosition(getPosX() + direcaoX, getPosY() - 1);
        atirar(lFormas, Tiro.DIRECAO_BAIXO);
        return true;
    }

    public void atirar(List<Desenhavel> lFormas, int direcao) {
        if (getRecarga() <= 0) {
            Tiro p = new Tiro(getPosX(), getPosY(), direcao, 0f, 0f, 1f, this);
            lFormas.add(p);
            if (numTiros > 1) {
                setRecarga(50);
                numTiros--;
            } else {
                setRecarga(70);
                numTiros = 3;
            }

        }
    }

    public int getAtirando() {
        return atirando;
    }

    public void setAtirando(int atirando) {
        this.atirando = atirando;
    }

    public float getDirecaoX() {
        return direcaoX;
    }

    public void setDirecaoX(float direcaoX) {
        this.direcaoX = direcaoX;
    }

    public float getRecarga() {
        return recarga;
    }

    public void setRecarga(float recarga) {
        this.recarga = recarga;
    }

    public int getNumTiros() {
        return numTiros;
    }

    public void setNumTiros(int numTiros) {
        this.numTiros = numTiros;
    }

}
