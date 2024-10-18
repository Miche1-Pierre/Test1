package cassebrique.models;

import java.awt.*;

public abstract class Sprite {

    protected int x;
    protected int y;
    protected Color couleur;

    public Sprite() {
    }

    public Sprite(int x, int y, Color couleur) {
        this.x = x;
        this.y = y;
        this.couleur = couleur;
    }

    abstract void dessiner(Graphics2D dessin);

    // GETTER & SETTER
    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }
}