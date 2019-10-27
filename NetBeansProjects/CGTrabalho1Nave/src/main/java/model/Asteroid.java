package model;

import interfaces.Colidivel;
import java.awt.Rectangle;
import java.util.Random;
import main.Jogo;
import static model.Entity.X;
import static model.Entity.Y;
import static org.lwjgl.opengl.GL11.GL_POLYGON;
import static org.lwjgl.opengl.GL11.glBegin;
import static org.lwjgl.opengl.GL11.glColor3f;
import static org.lwjgl.opengl.GL11.glEnd;
import static org.lwjgl.opengl.GL11.glVertex2d;

public class Asteroid extends Entity implements Colidivel {

    private static final int DEFAULT_WIDTH = 40;
    private static final int DEFAULT_HEIGHT = 40;

    public Asteroid() {
        super(new Random().nextInt(Jogo.getInstance().getWidth()), 0, DEFAULT_WIDTH, DEFAULT_HEIGHT, 0.55f, 0.34f, 0.26f);

        this.setVertex(new int[4][2]);
        initVertex();
    }

    @Override
    public void initVertex() {

        Random r = new Random();

        this.getVertex()[0][X] = posX;
        this.getVertex()[0][Y] = posY - (DEFAULT_HEIGHT / 2);

        this.getVertex()[1][X] = posX - (DEFAULT_WIDTH / 2);
        this.getVertex()[1][Y] = posY;

        this.getVertex()[2][X] = posX;
        this.getVertex()[2][Y] = posY + (DEFAULT_HEIGHT / 2);

        this.getVertex()[3][X] = posX + (DEFAULT_WIDTH / 2);
        this.getVertex()[3][Y] = posY;
    }

    @Override
    public void draw() {
        glColor3f(colorR, colorG, colorB);
        glBegin(GL_POLYGON);
        {
            
            glVertex2d(vertex[0][X], vertex[0][Y]);
            glVertex2d(vertex[1][X], vertex[1][Y]);
            glVertex2d(vertex[2][X], vertex[2][Y]);
            glVertex2d(vertex[3][X], vertex[3][Y]);
            glVertex2d(vertex[0][X], vertex[0][Y]);

        }
        glEnd();

        initVertex();
    }

    @Override
    public boolean update() {
        if (verificaColisao()) {
            Jogo.getInstance().getManager().add(new Explosion(posX, posY, colorR, colorG, colorB));
            return false;
        }

        if (getPosY() <= Jogo.getInstance().getHeight()) {
            move();
            return true;
        }

        return false;
    }

    @Override
    public void move() {
        for (int i = 0; i < vertex.length; i++) {
            vertex[i][Y]+=3;
        }
        posY+=3;
        draw();
    }

    @Override
    public boolean verificaColisao() {
        Rectangle rAsteroid = new Rectangle(posX, posY, width, height);

        for (int i = 0; i < Jogo.getInstance().getManager().getlEntities().size(); i++) {
            Entity entity = Jogo.getInstance().getManager().getlEntities().get(i);

            //Verifica se não é o próprio asteroide
            if (entity == this) {
                continue;
            }

            Rectangle rEntity = new Rectangle(entity.getPosX(), entity.getPosY(), entity.getWidth(), entity.getHeight());
            if (rAsteroid.intersects(rEntity)) {
                return true;
            }
        }
        return false;
    }

}
