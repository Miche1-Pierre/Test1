package cassebrique.models;

import java.awt.*;

public class Rond extends Sprite {

    protected int diametre = 20;

    public Rond() {
    }

    public Rond(int diametre) {
        this.diametre = diametre;
    }

    public Rond(int x, int y, Color couleur, int diametre) {
        super(x, y, couleur);
        this.diametre = diametre;
    }

    @Override
    public void dessiner(Graphics2D dessin) {
        dessin.setColor(couleur);
        dessin.fillOval(x, y, diametre, diametre);
    }

    // GETTER & SETTER
    public int getDiametre() {
        return diametre;
    }
}