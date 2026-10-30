// TemperatureCorporelle.java
package montre;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.io.Serializable;
import java.time.LocalDateTime;

// Classe représentant un élément optionnel : la température corporelle
public class TemperatureCorporelle implements ElementOptionnel, Serializable {

    private static final long serialVersionUID = 1L;

    private double temperature; // Valeur actuelle de la température
    private boolean afficher; // Indique si la température doit être affichée
    private int compteur; // Compteur pour ralentir la variation
    private boolean augmente; // Indique si la température augmente ou diminue

    // Constructeur : initialise une température normale
    public TemperatureCorporelle() {
        this.temperature = 36.8;
        this.afficher = false;
        this.compteur = 0;
        this.augmente = true;
    }

    // Indique si l'affichage est activé
    public boolean getAfficher() {
        return this.afficher;
    }

    // Active ou désactive l'affichage de la température
    public void setAfficher(boolean afficher) {
        this.afficher = afficher;
    }

    // Retourne la température actuelle
    public double getTemperature() {
        return this.temperature;
    }

    // Fait varier la température progressivement
    public void actualiser() {
        this.compteur = this.compteur + 1;

        // La température change seulement toutes les 10 secondes
        if (this.compteur >= 10) {
            this.compteur = 0;

            if (this.augmente) {
                this.temperature = this.temperature + 0.1;
            } else {
                this.temperature = this.temperature - 0.1;
            }

            // Limite supérieure
            if (this.temperature >= 38.0) {
                this.temperature = 38.0;
                this.augmente = false;
            }

            // Limite inférieure
            if (this.temperature <= 36.0) {
                this.temperature = 36.0;
                this.augmente = true;
            }
        }
    }

    // Dessine la température sur la montre
    public void dessiner(Graphics2D g2, Montre montre, int centreX, int centreY, int rayon, LocalDateTime temps) {
        if (!this.afficher) {
            return;
        }

        int largeur = rayon / 2;
        int hauteur = rayon / 5;
        int x = centreX + rayon / 4;
        int y = centreY - hauteur / 2;

        g2.setColor(new Color(0, 0, 0, 160));
        g2.fillRoundRect(x, y, largeur, hauteur, 15, 15);

        g2.setColor(Color.WHITE);
        g2.setFont(new Font("Arial", Font.BOLD, Math.max(12, rayon / 12)));
        g2.drawString(String.format("%.1f°C", this.temperature), x + 12, y + hauteur - 8);
    }
}