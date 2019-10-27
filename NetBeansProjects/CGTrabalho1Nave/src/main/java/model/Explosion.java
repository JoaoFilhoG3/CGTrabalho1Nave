package model;

import java.util.Random;
import static model.Entity.X;
import static model.Entity.Y;
import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL11.glBegin;
import static org.lwjgl.opengl.GL11.glColor3f;
import static org.lwjgl.opengl.GL11.glEnd;
import static org.lwjgl.opengl.GL11.glVertex2d;

public class Explosion extends Entity {

    private static final int DEFAULT_WIDTH = 40;
    private static final int DEFAULT_HEIGHT = 40;

    private int sumir = 50;

    public Explosion(int posX, int posY, float colorR, float colorG, float colorB) {
        super(posX, posY, DEFAULT_WIDTH, DEFAULT_HEIGHT, colorR, colorG, colorB);

        this.setVertex(new int[8][2]);
        initVertex();
    }

    @Override
    public void initVertex() {

        Random r = new Random();

        this.getVertex()[0][X] = posX;
        this.getVertex()[0][Y] = posY - (DEFAULT_HEIGHT / 2);

        this.getVertex()[1][X] = posX - r.nextInt(DEFAULT_WIDTH / 2);
        this.getVertex()[1][Y] = posY - r.nextInt(DEFAULT_HEIGHT / 2);

        this.getVertex()[2][X] = posX - (DEFAULT_WIDTH / 2);
        this.getVertex()[2][Y] = posY;

        this.getVertex()[3][X] = posX - r.nextInt(DEFAULT_WIDTH / 2);
        this.getVertex()[3][Y] = posY + r.nextInt(DEFAULT_HEIGHT / 2);

        this.getVertex()[4][X] = posX;
        this.getVertex()[4][Y] = posY + (DEFAULT_HEIGHT / 2);

        this.getVertex()[5][X] = posX + r.nextInt(DEFAULT_WIDTH / 2);
        this.getVertex()[5][Y] = posY + r.nextInt(DEFAULT_HEIGHT / 2);

        this.getVertex()[6][X] = posX + (DEFAULT_WIDTH / 3);
        this.getVertex()[6][Y] = posY;

        this.getVertex()[7][X] = posX + r.nextInt(DEFAULT_WIDTH / 2);
        this.getVertex()[7][Y] = posY - r.nextInt(DEFAULT_HEIGHT / 2);
    }

    @Override
    public void draw() {
        glColor3f(1, 1, 0);
        glBegin(GL_POLYGON);
        {
            for (int i = 0; i < getVertex().length - 1; i++) {
                glVertex2d(vertex[i][X], vertex[i][Y]);
            }
            glVertex2d(vertex[0][X], vertex[0][Y]);

        }
        glEnd();

        rotate();

        glColor3f(1, 1, 0);
        glBegin(GL_POLYGON);
        {
            for (int i = 0; i < getVertex().length - 1; i++) {
                glVertex2d(vertex[i][X], vertex[i][Y]);
            }
            glVertex2d(vertex[0][X], vertex[0][Y]);

        }
        glEnd();

        initVertex();
    }

    @Override
    public boolean update() {
        if (sumir <= 0) {
            return false;
        } else {
            sumir--;
        }
        return true;
    }

    @Override
    public void move() {

    }

    public void rotate() {
        for (int i = 0; i < getVertex().length; i++) {
            int oldX = this.getVertex()[i][X];
            int oldY = this.getVertex()[i][X];

            this.getVertex()[i][X] = (int) ((oldX * Math.cos(90)) - (oldY * Math.sin(90)));
            this.getVertex()[i][Y] = (int) ((oldX * Math.sin(90)) + (oldY * Math.cos(90)));
        }
    }

}
