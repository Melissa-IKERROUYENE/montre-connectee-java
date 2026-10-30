// Batterie.java
package montre;

import java.io.Serializable;

// Classe qui représente la batterie de la montre
// Elle gère le niveau (%) et son évolution dans le temps
public class Batterie implements Serializable {

    private static final long serialVersionUID = 1L;
    // Niveau de batterie (entre 0 et 100)
    private int niveau;

    // Indique si la batterie doit être affichée ou non
    private boolean afficherBatterie;

    // Compteur utilisé pour ralentir la diminution
    private int compteur;

    // Constructeur : batterie pleine au départ
    public Batterie() {
        this.niveau = 100;
        this.afficherBatterie = false;
        this.compteur = 0;
    }

    // Retourne le niveau actuel de la batterie
    public int getNiveau() {
        return this.niveau;
    }

    // Définit le niveau avec vérification (0 à 100)
    public void setNiveau(int niveau) {
        if (niveau < 0) {
            this.niveau = 0;
        } else if (niveau > 100) {
            this.niveau = 100;
        } else {
            this.niveau = niveau;
        }
    }

    // Indique si l'affichage de la batterie est activé
    public boolean getAfficherBatterie() {
        return this.afficherBatterie;
    }

    // Active ou désactive l'affichage de la batterie
    public void setAfficherBatterie(boolean afficherBatterie) {
        this.afficherBatterie = afficherBatterie;
    }

    // Fait diminuer la batterie progressivement
    // (pas à chaque seconde pour éviter qu'elle descende trop vite)
    public void diminuer() {
        this.compteur++;

        // La batterie diminue seulement toutes les 30 "ticks"
        if (this.compteur >= 30) {
            if (this.niveau > 0) {
                this.niveau--;
            }
            this.compteur = 0;
        }
    }
}