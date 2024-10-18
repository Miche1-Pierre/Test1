package cassebrique.models;

import cassebrique.CasseBrique;

import java.awt.*;

public class Balle extends Rond {

    protected int vitesseX;
    protected int vitesseY;

    public Balle() {
        super();
        this.x = this.nombreAleatoire(diametre, CasseBrique.LARGEUR - diametre);
        this.y = this.nombreAleatoire(400,500);
        this.vitesseX = 3;
        this.vitesseY = -3;
        this.couleur = new Color(ratioAleatoire(), ratioAleatoire(), ratioAleatoire(0.4f,0.7f));
    }

    public Balle(int x, int y, int vitesseX, int vitesseY) {
        this.x = x;
        this.y = y;
        this.vitesseX = vitesseX;
        this.vitesseY = vitesseY;
        this.couleur = new Color(ratioAleatoire(), ratioAleatoire(), ratioAleatoire(0.4f,0.7f));
    }

    public Balle(int x, int y, int vitesseX, int vitesseY, Color couleur) {
        this.x = x;
        this.y = y;
        this.vitesseX = vitesseX;
        this.vitesseY = vitesseY;
        this.couleur = couleur;
    }

    protected float ratioAleatoire(float min, float max) {
        return (float)Math.random() * (max - min) + min;
    }

    protected float ratioAleatoire() {
        return ratioAleatoire(0.2f, 0.8f);
    }

    protected int nombreAleatoire(int min, int max) {
        return (int)(Math.random() * (max - min) + min);
    }

    public void deplacer() {

        x += vitesseX;
        y += vitesseY;

        if(x >= CasseBrique.LARGEUR - diametre || x <= 0) {
            vitesseX = -vitesseX;
        }

        if(y >= CasseBrique.HAUTEUR - diametre || y <= 0) {
            vitesseY = -vitesseY;
        }
    }

    // Collision avec la barre
    public void gererCollisionAvecBarre(Barre barre) {
        if ((y + diametre >= barre.y) && (y <= barre.y + Barre.hauteurDefaut)) {
            if ((x + diametre >= barre.x) && (x <= barre.x + Barre.largeurDefaut)) {
                vitesseY = -vitesseY;

                int pointImpact = (x + diametre / 2) - (barre.x + Barre.largeurDefaut / 2);
                vitesseX = pointImpact / 10;
            }
        }
    }

    // Collision avec les briques
    public void gererCollisionAvecBrique(Brique brique) {
        if ((x + diametre >= brique.x) && (x <= brique.x + Brique.largeurDefaut) &&
                (y + diametre >= brique.y) && (y <= brique.y + Brique.hauteurDefaut)) {
            vitesseY = -vitesseY;

            brique.setResistance(brique.getResistance() - 1);

            if (brique.getResistance() <= 0) {
                brique.setResistance(0);
            }
        }
    }

    // GETTER & SETTER
    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public int getDiametre() {
        return diametre;
    }
}