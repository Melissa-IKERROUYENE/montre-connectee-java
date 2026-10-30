// RythmeCardiaque.java
package montre;

import java.io.Serializable;

// Classe qui représente le rythme cardiaque de la montre
// Elle gère les battements par minute (bpm) et leur évolution
public class RythmeCardiaque implements Serializable {

    private static final long serialVersionUID = 1L;

    // Nombre de battements par minute
    private int bpm;

    // Indique si le rythme doit être affiché
    private boolean afficher;

    // Mode d'activité : repos, normal ou sport
    private String modeActivite;

    // Constructeur : valeurs par défaut
    public RythmeCardiaque() {
        this.bpm = 72;
        this.afficher = false;
        this.modeActivite = "normal";
    }

    // Retourne le nombre de bpm actuel
    public int getBpm() {
        return this.bpm;
    }

    // Définit le bpm avec limites pour rester réaliste
    public void setBpm(int bpm) {
        if (bpm < 50) {
            this.bpm = 50;
        } else if (bpm > 160) {
            this.bpm = 160;
        } else {
            this.bpm = bpm;
        }
    }

    // Indique si l'affichage est activé
    public boolean getAfficher() {
        return this.afficher;
    }

    // Active ou désactive l'affichage du rythme
    public void setAfficher(boolean afficher) {
        this.afficher = afficher;
    }

    // Retourne le mode d'activité actuel
    public String getModeActivite() {
        return this.modeActivite;
    }

    // Définit le mode d'activité (avec vérification)
    public void setModeActivite(String modeActivite) {
        if (modeActivite.equals("repos") || modeActivite.equals("normal") || modeActivite.equals("sport")) {
            this.modeActivite = modeActivite;
        }
    }

    // Fait évoluer progressivement le rythme cardiaque
    public void faireVariation() {

        int cible;

        // Détermine une valeur cible selon l'activité
        if (this.modeActivite.equals("repos")) {
            cible = 60;
        } else if (this.modeActivite.equals("sport")) {
            cible = 120;
        } else {
            cible = 75;
        }

        // Le bpm se rapproche progressivement de la cible
        if (this.bpm < cible) {
            this.bpm = this.bpm + 1;
        } else if (this.bpm > cible) {
            this.bpm = this.bpm - 1;
        }

        // Ajoute une petite variation aléatoire pour plus de réalisme
        int bruit = (int) (Math.random() * 3) - 1;
        setBpm(this.bpm + bruit);
    }
}