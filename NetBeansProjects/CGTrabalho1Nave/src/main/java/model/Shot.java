package model;

import interfaces.Atirador;
import interfaces.Colidivel;
import java.awt.Rectangle;
import main.Jogo;
import static model.Entity.X;
import static model.Entity.Y;
import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL11.glBegin;
import static org.lwjgl.opengl.GL11.glColor3f;
import static org.lwjgl.opengl.GL11.glEnd;
import static org.lwjgl.opengl.GL11.glVertex2d;

public class Shot extends Entity implements Colidivel {

    public static final int DIRECAO_CIMA = 0;
    public static final int DIRECAO_BAIXO = 1;

    private static final int DEFAULT_WIDTH = 4;
    private static final int DEFAULT_HEIGHT = 10;

    private int moveCima;
    private int moveBaixo;

    private Atirador atirador;

    public Shot(int posX, int posY, int direcao, float colorR, float colorG, float colorB, Atirador atirador) {
        super(posX, posY, DEFAULT_WIDTH, DEFAULT_HEIGHT, colorR, colorG, colorB);
        this.atirador = atirador;

        if (direcao == DIRECAO_BAIXO) {
            moveBaixo = 1;
        } else {
            moveCima = 1;
        }

        this.setVertex(new int[4][2]);
        initVertex();
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

        if (posY >= 0 && posY < Jogo.getInstance().getWidth()) {
            move();
            return true;
        } else {
            return false;
        }

    }

    @Override
    public void initVertex() {
        this.getVertex()[0][X] = posX + (DEFAULT_WIDTH / 2);
        this.getVertex()[0][Y] = posY - (DEFAULT_HEIGHT / 2);

        this.getVertex()[1][X] = posX + (DEFAULT_WIDTH / 2);
        this.getVertex()[1][Y] = posY + (DEFAULT_HEIGHT / 2);

        this.getVertex()[2][X] = posX - (DEFAULT_WIDTH / 2);
        this.getVertex()[2][Y] = posY + (DEFAULT_HEIGHT / 2);

        this.getVertex()[3][X] = posX - (DEFAULT_WIDTH / 2);
        this.getVertex()[3][Y] = posY - (DEFAULT_HEIGHT / 2);
    }

    @Override
    public void move() {
        this.posY += (moveBaixo - moveCima) * 5;

        for (int i = 0; i < this.getVertex().length; i++) {
            getVertex()[i][Y] += (moveBaixo - moveCima) * 5;
        }
        draw();
    }

    public Atirador getAtirador() {
        return atirador;
    }

    public void setAtirador(Atirador atirador) {
        this.atirador = atirador;
    }

    @Override
    public boolean verificaColisao() {
         Rectangle rShot = new Rectangle(posX, posY, width, height);

        for (int i = 0; i < Jogo.getInstance().getManager().getlEntities().size(); i++) {
            Entity entity = Jogo.getInstance().getManager().getlEntities().get(i);

            //Verifica se não é o próprio tiro
            if (entity == this) {
                continue;
            }

            Rectangle rEntity = new Rectangle(entity.getPosX(), entity.getPosY(), entity.getWidth(), entity.getHeight());
            if (rShot.intersects(rEntity)) {
                return true;
            }
        }
        return false;
    }

}
