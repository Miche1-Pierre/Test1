/*
* To Do:
* Mise à jour de la zone de collision entre la balle et la barre
* Fixe le bug des collisions sur le côté
* Ajout brique resistance progressive
* Ajout autre bonus ou malus
* Amelioration du code global
*/

package cassebrique;

import cassebrique.models.Balle;
import cassebrique.models.Barre;
import cassebrique.models.Bonus;
import cassebrique.models.Brique;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;

public class CasseBrique extends Canvas implements KeyListener {

    public JFrame fenetre = new JFrame();
    public ArrayList<Balle> listeBalle = new ArrayList<>();
    public ArrayList<Brique> listeBrique = new ArrayList<>();
    public ArrayList<Bonus> listeBonus = new ArrayList<>();
    public Barre barre;

    public static final int LARGEUR = 500;
    public static final int HAUTEUR = 700;

    public boolean toucheDroite = false;
    public boolean toucheGauche = false;

    public CasseBrique() throws InterruptedException {

        this.fenetre.setSize(LARGEUR, HAUTEUR);
        this.setSize(LARGEUR, HAUTEUR);
        this.setBounds(0,0,LARGEUR, HAUTEUR);

        this.fenetre.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        JPanel panneau = new JPanel();
        panneau.add(this);
        this.fenetre.setContentPane(panneau);

        this.setIgnoreRepaint(true);
        this.setFocusable(false);
        this.fenetre.pack();
        this.fenetre.setResizable(false);
        this.fenetre.requestFocus();
        this.fenetre.addKeyListener(this);

        this.fenetre.setVisible(true);
        this.createBufferStrategy(2);

        lancerUnePartie();
    }

    public void lancerUnePartie() throws InterruptedException {
        // Balle
        listeBalle = new ArrayList<>();
        listeBalle.add(new Balle(250,500,3,4, Color.RED));

        // Barre
        barre = new Barre(
                CasseBrique.LARGEUR / 2 - Barre.largeurDefaut / 2,
                CasseBrique.HAUTEUR - 100);

        // Briques
        listeBrique = new ArrayList<>();
        for (int indexLigne = 0; indexLigne < 5; indexLigne ++) {
            for (int indexColonne = 0; indexColonne < 7; indexColonne ++) {
                Brique brique = new Brique(
                        indexColonne * (Brique.largeurDefaut + 5),
                        indexLigne * (Brique.hauteurDefaut + 5),
                        Color.RED);
                listeBrique.add(brique);
            }
        }

        // Bonus
        listeBonus = new ArrayList<>();

        while(true) {

            Graphics2D dessin = (Graphics2D)this.getBufferStrategy().getDrawGraphics();

            dessin.setColor(Color.WHITE);
            dessin.fillRect(0, 0, LARGEUR, HAUTEUR);

            for(Balle balle : listeBalle) {
                balle.deplacer();
                balle.gererCollisionAvecBarre(barre);
                balle.dessiner(dessin);

                for (int j = listeBrique.size() - 1; j >= 0; j--) {
                    Brique brique = listeBrique.get(j);
                    balle.gererCollisionAvecBrique(brique);
                    if (brique.getResistance() <= 0) {
                        listeBrique.remove(j);

                        // Génération du bonus
                        if (Math.random() < 0.1) {
                            Bonus bonus = creerBonus(brique);
                            listeBonus.add(bonus);
                        }
                    }
                }
            }

            // Gestion des bonus
            for (int i = listeBonus.size() - 1; i >= 0; i--) {
                Bonus bonus = listeBonus.get(i);
                bonus.deplacer();
                bonus.dessiner(dessin);

                if (bonus.getY() >= HAUTEUR) {
                    listeBonus.remove(i);
                } else if (collisionBarre(bonus, barre)) {
                    appliquerBonus(bonus);
                    listeBonus.remove(i);
                }
            }

            barre.dessiner(dessin);

            if(toucheDroite) {
                barre.deplacementDroite();
            }
            if (toucheGauche) {
                barre.deplacementGauche();
            }

            for(Brique brique : listeBrique) {
                brique.dessiner(dessin);
            }

            dessin.dispose();
            this.getBufferStrategy().show();

            Thread.sleep(1000 / 60);
        }
    }

    // Gestion bonus
    public Bonus creerBonus(Brique brique) {
        int typeBonus = (Math.random() < 0.5) ? Bonus.TYPE_VITESSE : Bonus.TYPE_TAILLE;
        boolean malus = Math.random() < 0.5;

        return new Bonus(brique.getX() + Brique.largeurDefaut / 2, brique.getY(), 20, 2, typeBonus, malus);
    }

    // Gestion collisions bonus avec barre
    public boolean collisionBarre(Bonus bonus, Barre barre) {
        return (bonus.getX() + bonus.getDiametre() >= barre.getX()) &&
                (bonus.getX() <= barre.getX() + barre.largeur) &&
                (bonus.getY() + bonus.getDiametre() >= barre.getY()) &&
                (bonus.getY() <= barre.getY() + Barre.hauteurDefaut);
    }

    // Effet bonus
    public void appliquerBonus(Bonus bonus) {
        if (bonus.getType() == Bonus.TYPE_VITESSE) {
            if (bonus.isMalus()) {
                barre.setVitesse(barre.getVitesse() - 2);
            } else {
                barre.setVitesse(barre.getVitesse() + 2);
            }
        } else if (bonus.getType() == Bonus.TYPE_TAILLE) {
            if (bonus.isMalus()) {
                barre.largeur = Math.max(barre.largeur - 20, 50);
            } else {
                barre.largeur = Math.min(barre.largeur + 20, CasseBrique.LARGEUR);
            }
        }
    }

    //main : raccourci
    public static void main(String[] args) throws InterruptedException {
        new CasseBrique();
    }

    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) {
        if(e.getKeyCode() == KeyEvent.VK_RIGHT) {
            toucheDroite = true;
        }
        if(e.getKeyCode() == KeyEvent.VK_LEFT) {
            toucheGauche = true;
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
        if(e.getKeyCode() == KeyEvent.VK_RIGHT) {
            toucheDroite = false;
        }
        if(e.getKeyCode() == KeyEvent.VK_LEFT) {
            toucheGauche = false;
        }
    }
}