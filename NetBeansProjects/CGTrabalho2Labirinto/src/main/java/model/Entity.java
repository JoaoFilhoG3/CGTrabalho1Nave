package model;

public abstract class Entity {

    public static final int X = 0;
    public static final int Y = 1;

    protected int posX;
    protected int posY;
    protected int width;
    protected int height;
    protected float colorR;
    protected float colorG;
    protected float colorB;
    protected float colorA;
    protected int vertex[][];

    public Entity(int posX, int posY, int width, int height, float colorR, float colorG, float colorB) {
        this.posX = posX;
        this.posY = posY;
        this.width = width;
        this.height = height;
        this.colorR = colorR;
        this.colorG = colorG;
        this.colorB = colorB;
    }

    public int getPosX() {
        return posX;
    }

    public void setPosX(int posX) {
        this.posX = posX;
    }

    public int getPosY() {
        return posY;
    }

    public void setPosY(int posY) {
        this.posY = posY;
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

    public float getColorR() {
        return colorR;
    }

    public void setColorR(float colorR) {
        this.colorR = colorR;
    }

    public float getColorG() {
        return colorG;
    }

    public void setColorG(float colorG) {
        this.colorG = colorG;
    }

    public float getColorB() {
        return colorB;
    }

    public void setColorB(float colorB) {
        this.colorB = colorB;
    }

    public float getColorA() {
        return colorA;
    }

    public void setColorA(int colorA) {
        this.colorA = colorA;
    }

    public int[][] getVertex() {
        return vertex;
    }

    public void setVertex(int[][] vertex) {
        this.vertex = vertex;
    }

    public abstract void initVertex();

    public abstract void draw();

    public abstract boolean update();

    public abstract void move();

}
