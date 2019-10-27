package model;

import interfaces.Atirador;
import interfaces.Colidivel;
import java.awt.Rectangle;
import java.util.Random;
import main.Jogo;
import static model.Entity.X;
import static model.Entity.Y;
import static org.lwjgl.opengl.GL11.GL_TRIANGLES;
import static org.lwjgl.opengl.GL11.glBegin;
import static org.lwjgl.opengl.GL11.glColor3f;
import static org.lwjgl.opengl.GL11.glEnd;
import static org.lwjgl.opengl.GL11.glVertex2d;

public class Enemy extends Entity implements Atirador, Colidivel {

    private static final int DEFAULT_WIDTH = 20;
    private static final int DEFAULT_HEIGHT = 20;

    private int moveEsquerda;
    private int moveDireita;

//    private int numTiros = 1;
    private float delayTiro = 0;

    public Enemy(float colorR, float colorG, float colorB) {
        super(new Random().nextInt(Jogo.getInstance().getWidth()), 0, DEFAULT_WIDTH, DEFAULT_HEIGHT, colorR, colorG, colorB);

        Random r = new Random();
        if (r.nextInt(2) == 0) {
            moveDireita = 1;
            moveEsquerda = 0;
        }else{
            moveDireita = 0;
            moveEsquerda = 1;
        }
        
        this.setVertex(new int[4][2]);
        initVertex();
    }

    @Override
    public void initVertex() {
        this.getVertex()[0][X] = posX;
        this.getVertex()[0][Y] = posY + (DEFAULT_HEIGHT / 2);

        this.getVertex()[1][X] = posX + (DEFAULT_WIDTH / 3);
        this.getVertex()[1][Y] = posY - (DEFAULT_HEIGHT / 2);

        this.getVertex()[2][X] = posX;
        this.getVertex()[2][Y] = posY - (DEFAULT_HEIGHT / 4);

        this.getVertex()[3][X] = posX - (DEFAULT_WIDTH / 3);
        this.getVertex()[3][Y] = posY - (DEFAULT_HEIGHT / 2);
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

        atira();
        if (getPosY() <= Jogo.getInstance().getHeight()) {
            move();
            return true;
        }

        return false;
    }

    @Override
    public void move() {
        Random r = new Random();
        if (r.nextInt(1000) > 980) {
            moveDireita = moveDireita == 0 ? 1 : 0;
            moveEsquerda = moveEsquerda == 0 ? 1 : 0;
        }
        for (int i = 0; i < vertex.length; i++) {
            vertex[i][Y]++;
            vertex[i][X] += (moveDireita - moveEsquerda);
        }
        posX+=(moveDireita - moveEsquerda);
        posY++;
        draw();
    }

    @Override
    public void atira() {
        if (delayTiro > 0) {
            delayTiro -= 2;
        } else if (delayTiro <= 0f) {
            delayTiro = 150;
            Jogo.getInstance().getManager().add(new Shot(posX, posY, Shot.DIRECAO_BAIXO, colorR, colorG, colorB, this));
        }
    }

    @Override
    public boolean verificaColisao() {
        Rectangle rEnemy = new Rectangle(posX, posY, width, height);

        for (int i = 0; i < Jogo.getInstance().getManager().getlEntities().size(); i++) {
            Entity entity = Jogo.getInstance().getManager().getlEntities().get(i);

            //Verifica se não é o próprio inimigo
            if (entity == this) {
                continue;
            }

            //Verifica se não é um tiro do inimigo
            if (entity instanceof Shot) {
                if (((Shot) entity).getAtirador() == this) {
                    continue;
                }
            }

            Rectangle rEntity = new Rectangle(entity.getPosX(), entity.getPosY(), entity.getWidth(), entity.getHeight());
            if (rEnemy.intersects(rEntity)) {
                return true;
            }
        }
        return false;
    }

}
