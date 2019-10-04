package model;


import interfaces.Colidivel;
import java.util.List;
import utils.MyUtils;

public abstract class Desenhavel implements Colidivel {

    private float posX;
    private float posY;

    private float raio;

    private float colorR;
    private float colorG;
    private float colorB;

    public Desenhavel(float posX, float posY, float raio, float colorR, float colorG, float colorB) {
        this.posX = posX;
        this.posY = posY;
        this.raio = raio;
        this.colorR = colorR;
        this.colorG = colorG;
        this.colorB = colorB;
    }

    public float getPosX() {
        return posX;
    }

    public float getPosY() {
        return posY;
    }

    public float getRaio() {
        return raio;
    }

    public void setRaio(float raio) {
        this.raio = raio;
    }

    public float getColorR() {
        return colorR;
    }

    public float getColorG() {
        return colorG;
    }

    public float getColorB() {
        return colorB;
    }

    public void setPosition(float posX, float posY) {
        this.posX = posX;
        this.posY = posY;
    }

    public void setColor(float colorR, float colorG, float colorB) {
        this.colorR = colorR;
        this.colorG = colorG;
        this.colorB = colorB;
    }

    /**
     * * Método responsável por desenhar componentes na tela **
     */
    public abstract void desenhar();

    /**
     * * Método responsável por atualizar a posição dos componentes na tela **
     */
    public abstract boolean update(List<Desenhavel> lFormas);

    @Override
    public void verificaColisao(List<Desenhavel> lDesenhavel) {
        for (int i = 0; i < lDesenhavel.size(); i++) {
            Desenhavel des = lDesenhavel.get(i);
            if (this == des || this instanceof Tiro) {
                continue;
            }
            if (des instanceof Tiro) {
                if (((Tiro) des).getDono() == this) {
                    continue;
                }
            }
            
            if (MyUtils.calculaColisao(this, des)) {
                lDesenhavel.remove(this);
                lDesenhavel.remove(des);
            }
        }
    }

}
