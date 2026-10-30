// ElementOptionnel.java
package montre;

import java.awt.Graphics2D;
import java.time.LocalDateTime;

// Interface représentant un élément optionnel affichable sur la montre
public interface ElementOptionnel {

    // Indique si l'élément doit être affiché
    boolean getAfficher();

    // Permet d'activer ou désactiver l'affichage de l'élément
    void setAfficher(boolean afficher);

    // Met à jour les données de l'élément
    void actualiser();

    // Dessine l'élément sur la montre
    void dessiner(Graphics2D g2, Montre montre, int centreX, int centreY, int rayon, LocalDateTime temps);
}