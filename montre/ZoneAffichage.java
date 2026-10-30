// ZoneAffichage.java
package montre;

import javax.swing.JPanel;
import javax.swing.ImageIcon;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.RadialGradientPaint;
import java.awt.MultipleGradientPaint;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Polygon;
import java.awt.Shape;
import java.awt.geom.Ellipse2D;
import java.awt.geom.RoundRectangle2D;
import java.time.LocalDateTime;

// Classe qui gère tout le dessin graphique de la montre
public class ZoneAffichage extends JPanel {

    private static final long serialVersionUID = 1L;

    private Montre montre;
    private TempsMontre tempsMontre;

    // Initialise la zone d'affichage avec la montre et le temps utilisé
    public ZoneAffichage(Montre montre, TempsMontre tempsMontre) {
        this.montre = montre;
        this.tempsMontre = tempsMontre;
        setBackground(new Color(235, 235, 235));
    }

    // Crée la forme du boîtier selon le choix rond ou carré
    private Shape creerBoitier(int x, int y, int taille) {
        if (montre.getFormeBoitier().equals("carre")) {
            return new RoundRectangle2D.Double(x, y, taille, taille, taille / 6, taille / 6);
        }

        return new Ellipse2D.Double(x, y, taille, taille);
    }

    // Choisit une couleur lisible selon la couleur du fond
    private Color couleurLisible() {
        if (montre.getTypeFond().equals("image")) {
            return Color.WHITE;
        }

        Color fond = montre.getCouleurFond();
        int moyenne = (fond.getRed() + fond.getGreen() + fond.getBlue()) / 3;

        if (moyenne < 128) {
            return Color.WHITE;
        }

        return Color.BLACK;
    }

    // Retourne la couleur du texte en mode numérique
    private Color couleurTexteNumerique() {
        if (montre.getCouleurTexteNumerique().equals("vert")) {
            return new Color(0, 255, 127);
        }

        if (montre.getCouleurTexteNumerique().equals("or")) {
            return new Color(255, 215, 0);
        }

        return Color.WHITE;
    }

    // Dessine le fond de la montre : uniforme, dégradé ou image
    private void dessinerFond(Graphics2D g2, Shape boitier, int x, int y, int taille) {
        Shape ancienClip = g2.getClip();
        g2.setClip(boitier);

        if (montre.getTypeFond().equals("uniforme")) {
            g2.setColor(montre.getCouleurFond());
            g2.fill(boitier);
        } else if (montre.getTypeFond().equals("degrade")) {
            Color base = montre.getCouleurFond();

            float centreX = x + taille / 2.0f;
            float centreY = y + taille / 2.0f;
            float rayon = taille / 2.0f;

            float[] fractions = {0.0f, 0.65f, 1.0f};
            Color[] couleurs = {
                    base.brighter().brighter(),
                    base,
                    base.darker().darker()
            };

            RadialGradientPaint gp = new RadialGradientPaint(
                    centreX,
                    centreY,
                    rayon,
                    fractions,
                    couleurs,
                    MultipleGradientPaint.CycleMethod.NO_CYCLE
            );

            g2.setPaint(gp);
            g2.fill(boitier);
        } else if (montre.getTypeFond().equals("image") && !montre.getCheminImageFond().equals("")) {
            Image image = new ImageIcon(montre.getCheminImageFond()).getImage();
            g2.setColor(montre.getCouleurFond());
            g2.fill(boitier);
            g2.drawImage(image, x, y, taille, taille, this);
        } else {
            g2.setColor(montre.getCouleurFond());
            g2.fill(boitier);
        }

        g2.setClip(ancienClip);
    }

    // Dessine un texte centré horizontalement
    private void dessinerTexteCentre(Graphics2D g2, String texte, int centreX, int y) {
        FontMetrics fm = g2.getFontMetrics();
        int x = centreX - fm.stringWidth(texte) / 2;
        g2.drawString(texte, x, y);
    }

    // Dessine une petite étiquette avec un fond sombre
    private void dessinerEtiquette(Graphics2D g2, String texte, int x, int y, int largeur, int hauteur) {
        g2.setColor(new Color(0, 0, 0, 160));
        g2.fillRoundRect(x, y, largeur, hauteur, 15, 15);

        g2.setColor(Color.WHITE);
        g2.setFont(new Font("Arial", Font.BOLD, Math.max(11, hauteur / 2)));

        FontMetrics fm = g2.getFontMetrics();
        int texteX = x + (largeur - fm.stringWidth(texte)) / 2;
        int texteY = y + (hauteur + fm.getAscent()) / 2 - 3;

        g2.drawString(texte, texteX, texteY);
    }

    // Prépare le texte de la date selon le format choisi
    private String texteDate(LocalDateTime temps) {
        if (montre.getFormatDate() == 1) {
            return String.format("%02d/%02d/%04d",
                    temps.getDayOfMonth(),
                    temps.getMonthValue(),
                    temps.getYear());
        }

        if (montre.getFormatDate() == 2) {
            return String.format("%04d-%02d-%02d",
                    temps.getYear(),
                    temps.getMonthValue(),
                    temps.getDayOfMonth());
        }

        String[] jours = {"Lun.", "Mar.", "Mer.", "Jeu.", "Ven.", "Sam.", "Dim."};
        String[] mois = {"Jan.", "Fév.", "Mar.", "Avr.", "Mai", "Juin",
                "Juil.", "Août", "Sep.", "Oct.", "Nov.", "Déc."};

        return jours[temps.getDayOfWeek().getValue() - 1] + " " +
                temps.getDayOfMonth() + " " +
                mois[temps.getMonthValue() - 1];
    }

    // Dessine les graduations fixes du cadran analogique
    private void dessinerGraduations(Graphics2D g2, int centreX, int centreY, int rayon) {
        g2.setColor(couleurLisible());

        for (int i = 0; i < 60; i++) {
            double angle = i * Math.PI * 2 / 60.0;

            int exterieur = rayon - rayon / 15;
            int interieur;

            if (i % 5 == 0) {
                interieur = rayon - rayon / 5;
                g2.setStroke(new BasicStroke(Math.max(2, rayon / 45)));
            } else {
                interieur = rayon - rayon / 9;
                g2.setStroke(new BasicStroke(1));
            }

            int x1 = (int) (centreX + exterieur * Math.sin(angle));
            int y1 = (int) (centreY - exterieur * Math.cos(angle));

            int x2 = (int) (centreX + interieur * Math.sin(angle));
            int y2 = (int) (centreY - interieur * Math.cos(angle));

            g2.drawLine(x1, y1, x2, y2);
        }
    }

    // Définit la longueur de l'aiguille des minutes selon la taille choisie
    private double proportionMinute() {
        if (montre.getTailleAiguilles().equals("petite")) {
            return 0.50;
        }

        if (montre.getTailleAiguilles().equals("grande")) {
            return 0.85;
        }

        return 0.70;
    }

    // Définit l'épaisseur des aiguilles selon la taille choisie
    private int epaisseurAiguille() {
        if (montre.getTailleAiguilles().equals("petite")) {
            return 2;
        }

        if (montre.getTailleAiguilles().equals("grande")) {
            return 6;
        }

        return 4;
    }

    // Dessine une aiguille avec un contraste si nécessaire
    private void dessinerAiguille(Graphics2D g2, int centreX, int centreY,
                                  double angle, int longueur, int epaisseur, Color couleur) {

        if (couleur.equals(Color.WHITE) && montre.getCouleurFond().equals(Color.GRAY)) {
            dessinerAiguilleAvecCouleur(g2, centreX, centreY, angle, longueur, epaisseur + 2, Color.BLACK);
        }

        dessinerAiguilleAvecCouleur(g2, centreX, centreY, angle, longueur, epaisseur, couleur);
    }

    // Dessine réellement l'aiguille selon sa forme
    private void dessinerAiguilleAvecCouleur(Graphics2D g2, int centreX, int centreY,
                                             double angle, int longueur, int epaisseur, Color couleur) {

        g2.setColor(couleur);

        int boutX = (int) (centreX + longueur * Math.sin(angle));
        int boutY = (int) (centreY - longueur * Math.cos(angle));

        if (montre.getFormeAiguilles().equals("simple")) {
            g2.setStroke(new BasicStroke(epaisseur, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND));
            g2.drawLine(centreX, centreY, boutX, boutY);
        } else if (montre.getFormeAiguilles().equals("lancette")) {
            int base = epaisseur * 2;

            double px = Math.cos(angle);
            double py = Math.sin(angle);

            Polygon p = new Polygon();
            p.addPoint((int) (centreX + base * px), (int) (centreY + base * py));
            p.addPoint(boutX, boutY);
            p.addPoint((int) (centreX - base * px), (int) (centreY - base * py));
            p.addPoint((int) (centreX - longueur * 0.15 * Math.sin(angle)),
                    (int) (centreY + longueur * 0.15 * Math.cos(angle)));

            g2.fillPolygon(p);
        } else {
            int base = epaisseur;

            double px = Math.cos(angle);
            double py = Math.sin(angle);

            Polygon p = new Polygon();
            p.addPoint((int) (centreX + base * px), (int) (centreY + base * py));
            p.addPoint((int) (boutX + base * px), (int) (boutY + base * py));
            p.addPoint((int) (boutX - base * px), (int) (boutY - base * py));
            p.addPoint((int) (centreX - base * px), (int) (centreY - base * py));

            g2.fillPolygon(p);
        }
    }

    // Dessine le mini-cadran des secondes
    private void dessinerMiniCadran(Graphics2D g2, int centreX, int centreY, int diametre, int seconde) {
        int rayon = diametre / 2;
        int x = centreX - rayon;
        int y = centreY - rayon;

        g2.setColor(new Color(0, 0, 0, 160));
        g2.fillOval(x - 4, y - 4, diametre + 8, diametre + 8);

        g2.setColor(Color.WHITE);
        g2.setStroke(new BasicStroke(2));
        g2.drawOval(x, y, diametre, diametre);

        g2.setFont(new Font("Arial", Font.PLAIN, Math.max(9, diametre / 6)));
        dessinerTexteCentre(g2, "0", centreX, y + 11);
        dessinerTexteCentre(g2, "30", centreX, y + diametre - 4);
        g2.drawString("15", x + diametre - 18, centreY + 4);
        g2.drawString("45", x + 4, centreY + 4);

        double angle = seconde * Math.PI * 2 / 60.0;
        int xs = (int) (centreX + (rayon - 10) * Math.sin(angle));
        int ys = (int) (centreY - (rayon - 10) * Math.cos(angle));

        g2.setColor(new Color(255, 59, 48));
        g2.setStroke(new BasicStroke(1));
        g2.drawLine(centreX, centreY, xs, ys);

        g2.setColor(Color.WHITE);
        g2.fillOval(centreX - 3, centreY - 3, 6, 6);
    }

    // Dessine l'état de la batterie
    private void dessinerBatterie(Graphics2D g2, int centreX, int y, int rayon) {
        int niveau = montre.getBatterie().getNiveau();

        int largeur = rayon / 2;
        int hauteur = rayon / 8;
        int x = centreX - largeur / 2;

        g2.setColor(new Color(0, 0, 0, 160));
        g2.fillRoundRect(x - 8, y - 6, largeur + 16, hauteur + 26, 15, 15);

        g2.setColor(Color.WHITE);
        g2.setStroke(new BasicStroke(2));
        g2.drawRect(x, y, largeur, hauteur);
        g2.fillRect(x + largeur, y + hauteur / 3, 5, hauteur / 3);

        int remplissage = niveau * largeur / 100;
        g2.fillRect(x, y, remplissage, hauteur);

        g2.setFont(new Font("Arial", Font.BOLD, Math.max(11, rayon / 13)));
        dessinerTexteCentre(g2, niveau + "%", centreX, y + hauteur + 17);
    }

    // Dessine les éléments optionnels : batterie, rythme, température et date
    private void dessinerElementsOptionnels(Graphics2D g2, LocalDateTime temps,
                                            int centreX, int centreY, int rayon,
                                            boolean numerique, int yBoitier) {

        if (montre.getBatterie().getAfficherBatterie()) {
            dessinerBatterie(g2, centreX, yBoitier + rayon / 8, rayon);
        }

        if (montre.getRythme().getAfficher()) {
            dessinerEtiquette(g2, montre.getRythme().getBpm() + " bpm",
                    centreX - rayon + rayon / 8,
                    centreY - rayon / 2,
                    rayon / 2,
                    rayon / 6);
        }

        if (montre.getTemperature().getAfficher()) {
            int largeur = rayon / 2;
            int hauteur = rayon / 6;
            int x = centreX + rayon / 4;
            int y;

            if (numerique) {
                y = centreY + rayon / 7;
            } else {
                y = centreY - hauteur / 2;
            }

            dessinerEtiquette(g2,
                    String.format("%.1f°C", montre.getTemperature().getTemperature()),
                    x, y, largeur, hauteur);
        }

        if (montre.getAfficherDate()) {
            dessinerEtiquette(g2, texteDate(temps),
                    centreX - rayon / 2,
                    centreY + rayon - rayon / 4,
                    rayon,
                    rayon / 6);
        }
    }

    // Dessine l'affichage analogique avec les aiguilles et les secondes
    private void dessinerAnalogique(Graphics2D g2, LocalDateTime temps,
                                    int centreX, int centreY, int rayon, int yBoitier) {

        int heure = temps.getHour() % 12;
        int minute = temps.getMinute();
        int seconde = temps.getSecond();

        dessinerGraduations(g2, centreX, centreY, rayon);

        double angleHeure = (heure + minute / 60.0) * Math.PI * 2 / 12.0;
        double angleMinute = minute * Math.PI * 2 / 60.0;

        int longueurMinute = (int) (rayon * proportionMinute());
        int longueurHeure = (int) (longueurMinute * 0.70);
        int epaisseur = epaisseurAiguille();

        dessinerAiguille(g2, centreX, centreY, angleHeure, longueurHeure,
                epaisseur + 2, montre.getCouleurAiguilles());

        dessinerAiguille(g2, centreX, centreY, angleMinute, longueurMinute,
                epaisseur, montre.getCouleurAiguilles());

        if (montre.getAfficherSecondes() && montre.getModeSecondes().equals("trotteuse")) {
            double angleSeconde = seconde * Math.PI * 2 / 60.0;
            int longueurSeconde = (int) (rayon * 0.86);

            g2.setColor(new Color(255, 59, 48));
            g2.setStroke(new BasicStroke(2));
            g2.drawLine(centreX, centreY,
                    (int) (centreX + longueurSeconde * Math.sin(angleSeconde)),
                    (int) (centreY - longueurSeconde * Math.cos(angleSeconde)));
        }

        if (montre.getAfficherSecondes() && montre.getModeSecondes().equals("mini")) {
            dessinerMiniCadran(g2, centreX, centreY + rayon / 2, rayon / 3, seconde);
        }

        if (montre.getAfficherSecondes() && montre.getModeSecondes().equals("compteur")) {
            dessinerEtiquette(g2, seconde + "s",
                    centreX - rayon + rayon / 8,
                    centreY - rayon / 10,
                    rayon / 3,
                    rayon / 6);
        }

        g2.setColor(montre.getCouleurAiguilles());
        g2.fillOval(centreX - rayon / 25, centreY - rayon / 25,
                rayon / 12, rayon / 12);

        dessinerElementsOptionnels(g2, temps, centreX, centreY, rayon, false, yBoitier);
    }

    // Dessine l'affichage numérique de l'heure
    private void dessinerNumerique(Graphics2D g2, LocalDateTime temps,
                                   int centreX, int centreY, int rayon, int yBoitier) {

        g2.setColor(couleurTexteNumerique());

        int taillePolice = montre.getTaillePoliceNumerique();
        g2.setFont(new Font(montre.getPoliceNumerique(), Font.BOLD, taillePolice));

        String texteHeure;

        if (montre.getAfficherSecondes()) {
            texteHeure = String.format("%02d:%02d:%02d",
                    temps.getHour(), temps.getMinute(), temps.getSecond());
        } else {
            texteHeure = String.format("%02d:%02d",
                    temps.getHour(), temps.getMinute());
        }

        // Réduit la police si les secondes débordent du petit boîtier.
        int largeurDisponible = (int) (rayon * 1.65);
        while (g2.getFontMetrics().stringWidth(texteHeure) > largeurDisponible && taillePolice > 12) {
            taillePolice--;
            g2.setFont(new Font(montre.getPoliceNumerique(), Font.BOLD, taillePolice));
        }
        dessinerTexteCentre(g2, texteHeure, centreX, centreY - rayon / 12);

        dessinerElementsOptionnels(g2, temps, centreX, centreY, rayon, true, yBoitier);
    }

    // Méthode appelée automatiquement pour redessiner la montre
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        Graphics2D g2 = (Graphics2D) g;

        // Récupération du temps actuel
        LocalDateTime temps = tempsMontre.getTempsActuel();

        // Calcul de la position du boîtier au centre du panneau
        int taille = montre.getTaille();
        int rayon = taille / 2;

        int centreX = getWidth() / 2;
        int centreY = getHeight() / 2;

        int x = centreX - rayon;
        int y = centreY - rayon;

        Shape boitier = creerBoitier(x, y, taille);

        // Dessin du fond puis du contour
        dessinerFond(g2, boitier, x, y, taille);

        g2.setColor(new Color(58, 58, 60));
        g2.setStroke(new BasicStroke(Math.max(4, rayon / 22)));

        if (montre.getFormeBoitier().equals("carre")) {
            g2.draw(new RoundRectangle2D.Double(x, y, taille, taille, taille / 6, taille / 6));
        } else {
            g2.drawOval(x, y, taille, taille);
        }

        // Choix de l'affichage selon le mode sélectionné
        if (montre.getModeAnalogique()) {
            dessinerAnalogique(g2, temps, centreX, centreY, rayon, y);
        } else {
            dessinerNumerique(g2, temps, centreX, centreY, rayon, y);
        }
    }
}