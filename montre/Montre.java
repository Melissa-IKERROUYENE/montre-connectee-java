// Montre.java
package montre;

import java.awt.Color;
import java.io.Serializable;
import java.io.FileOutputStream;
import java.io.FileInputStream;
import java.io.ObjectOutputStream;
import java.io.ObjectInputStream;
import java.io.IOException;

// Classe représentant l'état de la montre (modèle principal)
public class Montre implements Serializable {

    private static final long serialVersionUID = 1L;

    private int taille; // Taille du boîtier
    private boolean afficherSecondes; // Affichage des secondes
    private boolean modeAnalogique; // Mode analogique ou numérique
    private Color couleurFond; // Couleur du fond
    private Color couleurAiguilles; // Couleur des aiguilles
    private String formeAiguilles; // Forme des aiguilles

    private Batterie batterie; // Gestion de la batterie
    private RythmeCardiaque rythme; // Rythme cardiaque
    private TemperatureCorporelle temperature; // Température corporelle

    private boolean afficherDate; // Affichage de la date
    private int formatDate; // Format de la date

    private String formeBoitier; // Forme du boîtier
    private String typeFond; // Type de fond (uniforme, dégradé, image)
    private String cheminImageFond; // Chemin de l'image de fond
    private String modeSecondes; // Mode d'affichage des secondes
    private String policeNumerique; // Police du mode numérique
    private int taillePoliceNumerique; // Taille de la police

    private String tailleAiguilles; // Taille des aiguilles
    private String couleurTexteNumerique; // Couleur du texte numérique

    // Constructeur : initialise la montre avec des valeurs par défaut
    public Montre() {
        this.taille = 260;
        this.afficherSecondes = false;
        this.modeAnalogique = true;
        this.couleurFond = Color.GRAY;
        this.couleurAiguilles = Color.WHITE;
        this.formeAiguilles = "simple";

        this.batterie = new Batterie();
        this.rythme = new RythmeCardiaque();
        this.temperature = new TemperatureCorporelle();

        this.afficherDate = true;
        this.formatDate = 1;

        this.formeBoitier = "rond";
        this.typeFond = "uniforme";
        this.cheminImageFond = "";
        this.modeSecondes = "trotteuse";
        this.policeNumerique = "Arial";
        this.taillePoliceNumerique = 38;

        this.tailleAiguilles = "moyenne";
        this.couleurTexteNumerique = "blanc";
    }

    public int getTaille() {
        return this.taille;
    }

    // Définit la taille avec limites pour éviter les valeurs incohérentes
    public void setTaille(int taille) {
        if (taille < 180) this.taille = 180;
        else if (taille > 360) this.taille = 360;
        else this.taille = taille;
    }

    public boolean getAfficherSecondes() {
        return this.afficherSecondes;
    }

    public void setAfficherSecondes(boolean afficherSecondes) {
        this.afficherSecondes = afficherSecondes;
    }

    public boolean getModeAnalogique() {
        return this.modeAnalogique;
    }

    public void setModeAnalogique(boolean modeAnalogique) {
        this.modeAnalogique = modeAnalogique;
    }

    public Color getCouleurFond() {
        return this.couleurFond;
    }

    public void setCouleurFond(Color couleurFond) {
        this.couleurFond = couleurFond;
    }

    public Color getCouleurAiguilles() {
        return this.couleurAiguilles;
    }

    public void setCouleurAiguilles(Color couleurAiguilles) {
        this.couleurAiguilles = couleurAiguilles;
    }

    public String getFormeAiguilles() {
        return this.formeAiguilles;
    }

    // Vérifie que la forme est valide avant modification
    public void setFormeAiguilles(String formeAiguilles) {
        if (formeAiguilles.equals("simple") || formeAiguilles.equals("lancette") || formeAiguilles.equals("baton")) {
            this.formeAiguilles = formeAiguilles;
        }
    }

    public Batterie getBatterie() {
        return this.batterie;
    }

    public RythmeCardiaque getRythme() {
        return this.rythme;
    }

    public TemperatureCorporelle getTemperature() {
        return this.temperature;
    }

    public boolean getAfficherDate() {
        return this.afficherDate;
    }

    public void setAfficherDate(boolean afficherDate) {
        this.afficherDate = afficherDate;
    }

    public int getFormatDate() {
        return this.formatDate;
    }

    public void setFormatDate(int formatDate) {
        if (formatDate >= 1 && formatDate <= 3) {
            this.formatDate = formatDate;
        }
    }

    public String getFormeBoitier() {
        return this.formeBoitier;
    }

    public void setFormeBoitier(String formeBoitier) {
        if (formeBoitier.equals("rond") || formeBoitier.equals("carre")) {
            this.formeBoitier = formeBoitier;
        }
    }

    public String getTypeFond() {
        return this.typeFond;
    }

    public void setTypeFond(String typeFond) {
        if (typeFond.equals("uniforme") || typeFond.equals("degrade") || typeFond.equals("image")) {
            this.typeFond = typeFond;
        }
    }

    public String getCheminImageFond() {
        return this.cheminImageFond;
    }

    public void setCheminImageFond(String cheminImageFond) {
        this.cheminImageFond = cheminImageFond;
    }

    public String getModeSecondes() {
        return this.modeSecondes;
    }

    public void setModeSecondes(String modeSecondes) {
        if (modeSecondes.equals("trotteuse") || modeSecondes.equals("mini") || modeSecondes.equals("compteur")) {
            this.modeSecondes = modeSecondes;
        }
    }

    public String getPoliceNumerique() {
        return this.policeNumerique;
    }

    public void setPoliceNumerique(String policeNumerique) {
        if (policeNumerique.equals("Arial") || policeNumerique.equals("Monospaced") || policeNumerique.equals("Dialog")) {
            this.policeNumerique = policeNumerique;
        }
    }

    public int getTaillePoliceNumerique() {
        return this.taillePoliceNumerique;
    }

    public void setTaillePoliceNumerique(int taillePoliceNumerique) {
        if (taillePoliceNumerique < 20) this.taillePoliceNumerique = 20;
        else if (taillePoliceNumerique > 60) this.taillePoliceNumerique = 60;
        else this.taillePoliceNumerique = taillePoliceNumerique;
    }

    public String getTailleAiguilles() {
        return this.tailleAiguilles;
    }

    public void setTailleAiguilles(String tailleAiguilles) {
        if (tailleAiguilles.equals("petite") || tailleAiguilles.equals("moyenne") || tailleAiguilles.equals("grande")) {
            this.tailleAiguilles = tailleAiguilles;
        }
    }

    public String getCouleurTexteNumerique() {
        return this.couleurTexteNumerique;
    }

    public void setCouleurTexteNumerique(String couleurTexteNumerique) {
        if (couleurTexteNumerique.equals("blanc") || couleurTexteNumerique.equals("vert") || couleurTexteNumerique.equals("or")) {
            this.couleurTexteNumerique = couleurTexteNumerique;
        }
    }

    // Active ou désactive l'affichage de la température
    public void setAfficherTemperature(boolean afficherTemperature) {
        this.temperature.setAfficher(afficherTemperature);
    }

    // Sauvegarde l'objet dans un fichier .ser
    public void sauvegarder(String chemin) throws IOException {
        if (!chemin.toLowerCase().endsWith(".ser")) {
            chemin += ".ser";
        }

        ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(chemin));
        oos.writeObject(this);
        oos.close();
    }

    // Charge un objet Montre depuis un fichier .ser
    public static Montre charger(String chemin) throws IOException, ClassNotFoundException {
        if (!chemin.toLowerCase().endsWith(".ser")) {
            chemin += ".ser";
        }

        ObjectInputStream ois = new ObjectInputStream(new FileInputStream(chemin));
        Montre m = (Montre) ois.readObject();
        ois.close();

        return m;
    }
}