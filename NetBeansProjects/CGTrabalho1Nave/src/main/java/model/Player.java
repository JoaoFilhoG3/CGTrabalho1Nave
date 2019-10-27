package model;

import interfaces.Atirador;
import interfaces.Colidivel;
import interfaces.KeyListener;
import java.awt.Rectangle;
import java.util.ArrayList;
import java.util.List;
import main.Jogo;
import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.opengl.GL11.*;

public class Player extends Entity implements KeyListener, Atirador, Colidivel {

    private static final int DEFAULT_WIDTH = 20;
    private static final int DEFAULT_HEIGHT = 20;

    private int moveEsquerda;
    private int moveDireita;
    private int atira;

    private int numTiros = 3;
    private float delayTiro = 0;

    public Player(int posX, int posY, float colorR, float colorG, float colorB) {
        super(posX, posY, DEFAULT_WIDTH, DEFAULT_HEIGHT, colorR, colorG, colorB);

        this.setVertex(new int[4][2]);
        initVertex();
    }

    @Override
    public void atira() {
        if (delayTiro > 0) {
            delayTiro -= 2;
        } else if (atira > 0 && delayTiro <= 0f && numTiros > 0) {
            numTiros--;
            delayTiro = 30;
            Jogo.getInstance().getManager().add(new Shot(posX, posY, Shot.DIRECAO_CIMA, colorR, colorG, colorB, this));
        }

    }

    @Override
    public void move() {
        int movimento = (moveDireita - moveEsquerda) * 2;

        if (!(this.posX + movimento < 10) && !(this.posX + movimento > Jogo.getInstance().getWidth() - 10)) {
            this.posX += movimento;

            for (int i = 0; i < this.getVertex().length; i++) {
                getVertex()[i][X] += movimento;
            }
            draw();
        }

    }

    @Override
    public void initVertex() {
        this.getVertex()[0][X] = posX;
        this.getVertex()[0][Y] = posY - (DEFAULT_HEIGHT / 2);

        this.getVertex()[1][X] = posX + (DEFAULT_WIDTH / 3);
        this.getVertex()[1][Y] = posY + (DEFAULT_HEIGHT / 2);

        this.getVertex()[2][X] = posX;
        this.getVertex()[2][Y] = posY + (DEFAULT_HEIGHT / 4);

        this.getVertex()[3][X] = posX - (DEFAULT_WIDTH / 3);
        this.getVertex()[3][Y] = posY + (DEFAULT_HEIGHT / 2);
    }

    @Override
    public void draw() {
        glColor3f(colorR, colorG, colorB);
        glBegin(GL_TRIANGLES);
        {
            glVertex2d(vertex[0][X], vertex[0][Y]);
            glVertex2d(vertex[1][X], vertex[1][Y]);
            glVertex2d(vertex[2][X], vertex[2][Y]);

            glVertex2d(vertex[2][X], vertex[2][Y]);
            glVertex2d(vertex[3][X], vertex[3][Y]);
            glVertex2d(vertex[0][X], vertex[0][Y]);

        }
        glEnd();
    }

    @Override
    public boolean update() {
        if (verificaColisao()) {
            Jogo.getInstance().getManager().add(new Explosion(posX, posY, colorR, colorG, colorB));
            return false;
        }
        
        move();
        if (atira != 0) {
            atira();
        }

        verificaTiros();

        return true;
    }

    @Override
    public void onKeyEvent(int key, int action) {
        if (key == GLFW_KEY_A) {
            moveEsquerda = action;
        } else if (key == GLFW_KEY_D) {
            moveDireita = action;
        } else if (key == GLFW_KEY_SPACE) {
            atira = action;
        }
    }

    public void verificaTiros() {
        List<Entity> lEntities = Jogo.getInstance().getManager().getlEntities();
        List<Shot> lTiros = new ArrayList<Shot>();
        for (int i = 0; i < lEntities.size(); i++) {
            if (lEntities.get(i) instanceof Shot) {
                Shot t = (Shot) lEntities.get(i);
                if (t.getAtirador().equals(this)) {
                    lTiros.add((Shot) lEntities.get(i));
                }
            }
        }
        if (lTiros.size() < 3 && numTiros == 0) {
            numTiros++;
        }
    }

    @Override
    public boolean verificaColisao() {
        Rectangle rPlayer = new Rectangle(posX, posY, width, height);

        for (int i = 0; i < Jogo.getInstance().getManager().getlEntities().size(); i++) {
            Entity entity = Jogo.getInstance().getManager().getlEntities().get(i);

            //Verifica se não é o próprio player
            if (entity == this) {
                continue;
            }

            //Verifica se não é um tiro do player
            if (entity instanceof Shot) {
                if (((Shot) entity).getAtirador() == this) {
                    continue;
                }
            }

            Rectangle rEntity = new Rectangle(entity.getPosX(), entity.getPosY(), entity.getWidth(), entity.getHeight());
            if (rPlayer.intersects(rEntity)) {
                return true;
            }
        }
        return false;
    }

}
