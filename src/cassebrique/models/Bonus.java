package cassebrique.models;

import java.awt.*;

public class Bonus extends Rond {

    protected int vitesseY;
    protected int type;
    protected boolean malus;
    public static int TYPE_VITESSE = 1;
    public static int TYPE_TAILLE = 2;

    public Bonus(int vitesseY, int type, boolean malus) {
        this.vitesseY = vitesseY;
        this.type = type;
        this.malus = malus;
    }

    public Bonus(int diametre, int vitesseY, int type, boolean malus) {
        super(diametre);
        this.vitesseY = vitesseY;
        this.type = type;
        this.malus = malus;
    }

    public Bonus(int x, int y, int diametre, int vitesseY, int type, boolean malus) {
        super(x, y, (malus ? Color.RED : Color.GREEN), diametre);
        this.vitesseY = vitesseY;
        this.type = type;
        this.malus = malus;
    }

    public void deplacer() {
        this.y += this.vitesseY;
    }

    @Override
    public void dessiner(Graphics2D dessin) {
        super.dessiner(dessin);
        dessin.setColor(this.couleur);
    }

    // GETTER & SETTER
    public int getType() {
        return type;
    }

    public boolean isMalus() {
        return malus;
    }
}