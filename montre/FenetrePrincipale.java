// FenetrePrincipale.java
package montre;

import javax.swing.JFrame;
import javax.swing.Timer;
import javax.swing.JPanel;
import javax.swing.JMenuBar;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.BorderFactory;
import javax.swing.JRadioButton;
import javax.swing.ButtonGroup;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JButton;
import javax.swing.filechooser.FileNameExtensionFilter;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

// Fenêtre principale de l'application : elle crée l'interface et gère les actions
public class FenetrePrincipale extends JFrame implements ActionListener {

    private static final long serialVersionUID = 1L;

    private static final Color GRIS = new Color(128, 128, 128);
    private static final Color NOIR = new Color(28, 28, 30);
    private static final Color BLEU_NUIT = new Color(13, 27, 42);
    private static final Color ROUGE = new Color(255, 59, 48);
    private static final Color BLEU_ELECTRIQUE = new Color(26, 110, 255);

    private Montre montre;
    private TempsMontre tempsMontre;
    private ZoneAffichage zone;
    private Timer timer;

    private JMenuItem itemSauvegarder;
    private JMenuItem itemOuvrir;

    private JPanel panneauAnalogique;
    private JPanel panneauNumerique;

    private JRadioButton radioHeurePC;
    private JRadioButton radioHeureSimulee;

    private JRadioButton radioRond;
    private JRadioButton radioCarre;
    private JRadioButton radioPetite;
    private JRadioButton radioMoyenne;
    private JRadioButton radioGrande;

    private JRadioButton radioFondGris;
    private JRadioButton radioFondNoir;
    private JRadioButton radioFondBleu;
    private JRadioButton radioFondUniforme;
    private JRadioButton radioFondDegrade;
    private JRadioButton radioFondImage;
    private JButton boutonChoisirImage;

    private JRadioButton radioAnalogique;
    private JRadioButton radioNumerique;

    private JRadioButton radioAigBlanc;
    private JRadioButton radioAigRouge;
    private JRadioButton radioAigBleu;

    private JRadioButton radioAigSimple;
    private JRadioButton radioAigLancette;
    private JRadioButton radioAigBaton;

    private JRadioButton radioAigPetite;
    private JRadioButton radioAigMoyenne;
    private JRadioButton radioAigGrande;

    private JRadioButton radioSecondesTrotteuse;
    private JRadioButton radioSecondesMini;
    private JRadioButton radioSecondesCompteur;

    private JRadioButton radioPoliceArial;
    private JRadioButton radioPoliceMono;
    private JRadioButton radioPoliceDialog;

    private JRadioButton radioPolicePetite;
    private JRadioButton radioPoliceMoyenne;
    private JRadioButton radioPoliceGrande;

    private JRadioButton radioTexteBlanc;
    private JRadioButton radioTexteVert;
    private JRadioButton radioTexteOr;

    private JCheckBox checkSecondesAnalogique;
    private JCheckBox checkSecondes;
    private JCheckBox checkDate;
    private JCheckBox checkBatterie;
    private JCheckBox checkRythme;
    private JCheckBox checkTemperature;

    private JComboBox<String> comboFormatDate;
    private JComboBox<String> comboRythme;

    public FenetrePrincipale() {
        setTitle("Montre connectée personnalisable");
        setSize(1300, 820);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());
        setLocationRelativeTo(null);

        // Création du modèle, du gestionnaire de temps et de la zone de dessin
        this.montre = new Montre();
        this.tempsMontre = new TempsMontre();
        this.zone = new ZoneAffichage(this.montre, this.tempsMontre);

        creerMenu();
        creerInterface();

        // Timer utilisé pour actualiser l'affichage chaque seconde
        this.timer = new Timer(1000, this);
        this.timer.start();

        setVisible(true);
    }

    // Crée le menu Fichier avec les actions de sauvegarde et de chargement
    private void creerMenu() {
        JMenuBar barreMenu = new JMenuBar();
        JMenu menuFichier = new JMenu("Fichier");

        this.itemSauvegarder = new JMenuItem("Sauvegarder");
        this.itemOuvrir = new JMenuItem("Ouvrir");

        this.itemSauvegarder.addActionListener(this);
        this.itemOuvrir.addActionListener(this);

        menuFichier.add(this.itemSauvegarder);
        menuFichier.add(this.itemOuvrir);

        barreMenu.add(menuFichier);
        setJMenuBar(barreMenu);
    }

    // Crée une ligne pour placer plusieurs boutons sur la même rangée
    private JPanel creerLigne() {
        return new JPanel(new FlowLayout(FlowLayout.LEFT, 6, 0));
    }

    // Organise les panneaux d'options et la zone d'affichage
    private void creerInterface() {
        JPanel panneauOptions = new JPanel();
        panneauOptions.setLayout(new javax.swing.BoxLayout(panneauOptions, javax.swing.BoxLayout.Y_AXIS));
        panneauOptions.setBorder(BorderFactory.createEmptyBorder(8, 8, 8, 8));
        panneauOptions.setPreferredSize(new Dimension(530, 760));

        panneauOptions.add(creerSectionSimulation());
        panneauOptions.add(creerSectionBoitier());
        panneauOptions.add(creerSectionFond());
        panneauOptions.add(creerSectionTypeAffichage());

        this.panneauAnalogique = creerSectionAnalogique();
        this.panneauNumerique = creerSectionNumerique();

        panneauOptions.add(this.panneauAnalogique);
        panneauOptions.add(this.panneauNumerique);
        panneauOptions.add(creerSectionElements());

        javax.swing.JScrollPane defilement = new javax.swing.JScrollPane(panneauOptions);
        defilement.setPreferredSize(new Dimension(550, 760));
        add(defilement, BorderLayout.WEST);
        add(this.zone, BorderLayout.CENTER);

        mettreAJourVisibiliteMode();
    }

    // Crée la section permettant de choisir l'heure de l'ordinateur ou une heure simulée
    private JPanel creerSectionSimulation() {
        JPanel p = new JPanel(new GridLayout(1, 1));
        p.setBorder(BorderFactory.createTitledBorder("Simulation"));

        this.radioHeurePC = new JRadioButton("Heure PC", true);
        this.radioHeureSimulee = new JRadioButton("Heure simulée");

        ButtonGroup groupe = new ButtonGroup();
        groupe.add(this.radioHeurePC);
        groupe.add(this.radioHeureSimulee);

        this.radioHeurePC.addActionListener(this);
        this.radioHeureSimulee.addActionListener(this);

        JPanel ligne = creerLigne();
        ligne.add(this.radioHeurePC);
        ligne.add(this.radioHeureSimulee);

        p.add(ligne);
        return p;
    }

    // Crée la section de personnalisation du boîtier
    private JPanel creerSectionBoitier() {
        JPanel p = new JPanel(new GridLayout(2, 1));
        p.setBorder(BorderFactory.createTitledBorder("Boîtier"));

        this.radioRond = new JRadioButton("Rond", true);
        this.radioCarre = new JRadioButton("Carré");

        ButtonGroup groupeForme = new ButtonGroup();
        groupeForme.add(this.radioRond);
        groupeForme.add(this.radioCarre);

        this.radioPetite = new JRadioButton("Petite");
        this.radioMoyenne = new JRadioButton("Moyenne", true);
        this.radioGrande = new JRadioButton("Grande");

        ButtonGroup groupeTaille = new ButtonGroup();
        groupeTaille.add(this.radioPetite);
        groupeTaille.add(this.radioMoyenne);
        groupeTaille.add(this.radioGrande);

        this.radioRond.addActionListener(this);
        this.radioCarre.addActionListener(this);
        this.radioPetite.addActionListener(this);
        this.radioMoyenne.addActionListener(this);
        this.radioGrande.addActionListener(this);

        JPanel ligne1 = creerLigne();
        ligne1.add(this.radioRond);
        ligne1.add(this.radioCarre);

        JPanel ligne2 = creerLigne();
        ligne2.add(this.radioPetite);
        ligne2.add(this.radioMoyenne);
        ligne2.add(this.radioGrande);

        p.add(ligne1);
        p.add(ligne2);
        return p;
    }

    // Crée la section de personnalisation du fond
    private JPanel creerSectionFond() {
        JPanel p = new JPanel(new GridLayout(3, 1));
        p.setBorder(BorderFactory.createTitledBorder("Fond"));

        this.radioFondGris = new JRadioButton("Gris", true);
        this.radioFondNoir = new JRadioButton("Noir");
        this.radioFondBleu = new JRadioButton("Bleu nuit");

        ButtonGroup groupeCouleurs = new ButtonGroup();
        groupeCouleurs.add(this.radioFondGris);
        groupeCouleurs.add(this.radioFondNoir);
        groupeCouleurs.add(this.radioFondBleu);

        this.radioFondUniforme = new JRadioButton("Uniforme", true);
        this.radioFondDegrade = new JRadioButton("Dégradé");
        this.radioFondImage = new JRadioButton("Image");

        ButtonGroup groupeTypes = new ButtonGroup();
        groupeTypes.add(this.radioFondUniforme);
        groupeTypes.add(this.radioFondDegrade);
        groupeTypes.add(this.radioFondImage);

        this.boutonChoisirImage = new JButton("Choisir image...");

        JRadioButton[] boutons = {
                radioFondGris, radioFondNoir, radioFondBleu,
                radioFondUniforme, radioFondDegrade, radioFondImage
        };

        for (JRadioButton bouton : boutons) {
            bouton.addActionListener(this);
        }

        this.boutonChoisirImage.addActionListener(this);

        JPanel ligne1 = creerLigne();
        ligne1.add(radioFondGris);
        ligne1.add(radioFondNoir);
        ligne1.add(radioFondBleu);

        JPanel ligne2 = creerLigne();
        ligne2.add(radioFondUniforme);
        ligne2.add(radioFondDegrade);
        ligne2.add(radioFondImage);

        JPanel ligne3 = creerLigne();
        ligne3.add(boutonChoisirImage);

        p.add(ligne1);
        p.add(ligne2);
        p.add(ligne3);

        return p;
    }

    // Crée la section permettant de choisir entre affichage analogique et numérique
    private JPanel creerSectionTypeAffichage() {
        JPanel p = new JPanel(new GridLayout(1, 1));
        p.setBorder(BorderFactory.createTitledBorder("Type d'affichage"));

        this.radioAnalogique = new JRadioButton("Analogique", true);
        this.radioNumerique = new JRadioButton("Numérique");

        ButtonGroup groupeMode = new ButtonGroup();
        groupeMode.add(this.radioAnalogique);
        groupeMode.add(this.radioNumerique);

        this.radioAnalogique.addActionListener(this);
        this.radioNumerique.addActionListener(this);

        JPanel ligne = creerLigne();
        ligne.add(this.radioAnalogique);
        ligne.add(this.radioNumerique);

        p.add(ligne);
        return p;
    }

    // Crée les options propres au mode analogique
    private JPanel creerSectionAnalogique() {
        JPanel p = new JPanel(new GridLayout(5, 1));
        p.setBorder(BorderFactory.createTitledBorder("Options analogiques"));

        this.radioAigBlanc = new JRadioButton("Blanc", true);
        this.radioAigRouge = new JRadioButton("Rouge");
        this.radioAigBleu = new JRadioButton("Bleu");

        ButtonGroup groupeCouleur = new ButtonGroup();
        groupeCouleur.add(this.radioAigBlanc);
        groupeCouleur.add(this.radioAigRouge);
        groupeCouleur.add(this.radioAigBleu);

        this.radioAigSimple = new JRadioButton("Simple", true);
        this.radioAigLancette = new JRadioButton("Lancette");
        this.radioAigBaton = new JRadioButton("Bâton");

        ButtonGroup groupeForme = new ButtonGroup();
        groupeForme.add(this.radioAigSimple);
        groupeForme.add(this.radioAigLancette);
        groupeForme.add(this.radioAigBaton);

        this.radioAigPetite = new JRadioButton("Petite");
        this.radioAigMoyenne = new JRadioButton("Moyenne", true);
        this.radioAigGrande = new JRadioButton("Grande");

        ButtonGroup groupeTaille = new ButtonGroup();
        groupeTaille.add(this.radioAigPetite);
        groupeTaille.add(this.radioAigMoyenne);
        groupeTaille.add(this.radioAigGrande);

        this.radioSecondesTrotteuse = new JRadioButton("Trotteuse", true);
        this.radioSecondesMini = new JRadioButton("Mini-cadran");
        this.radioSecondesCompteur = new JRadioButton("Compteur");

        ButtonGroup groupeSecondes = new ButtonGroup();
        groupeSecondes.add(this.radioSecondesTrotteuse);
        groupeSecondes.add(this.radioSecondesMini);
        groupeSecondes.add(this.radioSecondesCompteur);

        this.checkSecondesAnalogique = new JCheckBox("Afficher les secondes");

        JRadioButton[] boutons = {
                radioAigBlanc, radioAigRouge, radioAigBleu,
                radioAigSimple, radioAigLancette, radioAigBaton,
                radioAigPetite, radioAigMoyenne, radioAigGrande,
                radioSecondesTrotteuse, radioSecondesMini, radioSecondesCompteur
        };

        for (JRadioButton bouton : boutons) {
            bouton.addActionListener(this);
        }

        this.checkSecondesAnalogique.addActionListener(this);

        JPanel ligne1 = creerLigne();
        ligne1.add(radioAigBlanc);
        ligne1.add(radioAigRouge);
        ligne1.add(radioAigBleu);

        JPanel ligne2 = creerLigne();
        ligne2.add(radioAigSimple);
        ligne2.add(radioAigLancette);
        ligne2.add(radioAigBaton);

        JPanel ligne3 = creerLigne();
        ligne3.add(radioAigPetite);
        ligne3.add(radioAigMoyenne);
        ligne3.add(radioAigGrande);

        JPanel ligne4 = creerLigne();
        ligne4.add(radioSecondesTrotteuse);
        ligne4.add(radioSecondesMini);
        ligne4.add(radioSecondesCompteur);

        JPanel ligne5 = creerLigne();
        ligne5.add(checkSecondesAnalogique);

        p.add(ligne1);
        p.add(ligne2);
        p.add(ligne3);
        p.add(ligne4);
        p.add(ligne5);

        return p;
    }

    // Crée les options propres au mode numérique
    private JPanel creerSectionNumerique() {
        JPanel p = new JPanel(new GridLayout(4, 1));
        p.setBorder(BorderFactory.createTitledBorder("Options numériques"));

        this.radioPoliceArial = new JRadioButton("Arial", true);
        this.radioPoliceMono = new JRadioButton("Monospaced");
        this.radioPoliceDialog = new JRadioButton("Dialog");

        ButtonGroup groupePolice = new ButtonGroup();
        groupePolice.add(this.radioPoliceArial);
        groupePolice.add(this.radioPoliceMono);
        groupePolice.add(this.radioPoliceDialog);

        this.radioPolicePetite = new JRadioButton("Petit");
        this.radioPoliceMoyenne = new JRadioButton("Moyen", true);
        this.radioPoliceGrande = new JRadioButton("Grand");

        ButtonGroup groupeTaille = new ButtonGroup();
        groupeTaille.add(this.radioPolicePetite);
        groupeTaille.add(this.radioPoliceMoyenne);
        groupeTaille.add(this.radioPoliceGrande);

        this.radioTexteBlanc = new JRadioButton("Blanc", true);
        this.radioTexteVert = new JRadioButton("Vert");
        this.radioTexteOr = new JRadioButton("Or");

        ButtonGroup groupeCouleur = new ButtonGroup();
        groupeCouleur.add(this.radioTexteBlanc);
        groupeCouleur.add(this.radioTexteVert);
        groupeCouleur.add(this.radioTexteOr);

        this.checkSecondes = new JCheckBox("Afficher les secondes");

        JRadioButton[] boutons = {
                radioPoliceArial, radioPoliceMono, radioPoliceDialog,
                radioPolicePetite, radioPoliceMoyenne, radioPoliceGrande,
                radioTexteBlanc, radioTexteVert, radioTexteOr
        };

        for (JRadioButton bouton : boutons) {
            bouton.addActionListener(this);
        }

        this.checkSecondes.addActionListener(this);

        JPanel ligne1 = creerLigne();
        ligne1.add(radioPoliceArial);
        ligne1.add(radioPoliceMono);
        ligne1.add(radioPoliceDialog);

        JPanel ligne2 = creerLigne();
        ligne2.add(radioPolicePetite);
        ligne2.add(radioPoliceMoyenne);
        ligne2.add(radioPoliceGrande);

        JPanel ligne3 = creerLigne();
        ligne3.add(radioTexteBlanc);
        ligne3.add(radioTexteVert);
        ligne3.add(radioTexteOr);

        JPanel ligne4 = creerLigne();
        ligne4.add(checkSecondes);

        p.add(ligne1);
        p.add(ligne2);
        p.add(ligne3);
        p.add(ligne4);

        return p;
    }

    // Crée les éléments optionnels affichables sur la montre
    private JPanel creerSectionElements() {
        JPanel p = new JPanel(new GridLayout(3, 1));
        p.setBorder(BorderFactory.createTitledBorder("Éléments optionnels"));

        this.checkDate = new JCheckBox("Date", true);
        this.checkBatterie = new JCheckBox("Batterie");
        this.checkRythme = new JCheckBox("Rythme");
        this.checkTemperature = new JCheckBox("Température");

        this.comboFormatDate = new JComboBox<String>();
        this.comboFormatDate.addItem("JJ/MM/AAAA");
        this.comboFormatDate.addItem("AAAA-MM-JJ");
        this.comboFormatDate.addItem("Ven. 24 Avr.");

        this.comboRythme = new JComboBox<String>();
        this.comboRythme.addItem("Repos");
        this.comboRythme.addItem("Normal");
        this.comboRythme.addItem("Sport");
        this.comboRythme.setSelectedItem("Normal");

        this.checkDate.addActionListener(this);
        this.checkBatterie.addActionListener(this);
        this.checkRythme.addActionListener(this);
        this.checkTemperature.addActionListener(this);
        this.comboFormatDate.addActionListener(this);
        this.comboRythme.addActionListener(this);

        JPanel ligne1 = creerLigne();
        ligne1.add(checkDate);
        ligne1.add(comboFormatDate);

        JPanel ligne2 = creerLigne();
        ligne2.add(checkBatterie);
        ligne2.add(checkRythme);
        ligne2.add(comboRythme);

        JPanel ligne3 = creerLigne();
        ligne3.add(checkTemperature);

        p.add(ligne1);
        p.add(ligne2);
        p.add(ligne3);

        return p;
    }

    // Affiche seulement le panneau correspondant au mode choisi
    private void mettreAJourVisibiliteMode() {
        boolean analogique = this.montre.getModeAnalogique();

        this.panneauAnalogique.setVisible(analogique);
        this.panneauNumerique.setVisible(!analogique);

        // Synchronise les deux cases avec l'état réel du modèle
        this.checkSecondesAnalogique.setSelected(this.montre.getAfficherSecondes());
        this.checkSecondes.setSelected(this.montre.getAfficherSecondes());

        revalidate();
        repaint();
    }

    // Permet de choisir une image comme fond de montre
    private void choisirImage() {
        JFileChooser chooser = new JFileChooser();
        chooser.setFileFilter(new FileNameExtensionFilter("Images", "jpg", "jpeg", "png"));

        int choix = chooser.showOpenDialog(this);

        if (choix == JFileChooser.APPROVE_OPTION) {
            javax.swing.ImageIcon image = new javax.swing.ImageIcon(chooser.getSelectedFile().getAbsolutePath());
            if (image.getIconWidth() <= 0 || image.getIconHeight() <= 0) {
                JOptionPane.showMessageDialog(this, "Image illisible", "Erreur", JOptionPane.ERROR_MESSAGE);
                synchroniserTypeFond();
                return;
            }
            this.montre.setCheminImageFond(chooser.getSelectedFile().getAbsolutePath());
            this.montre.setTypeFond("image");
            this.radioFondImage.setSelected(true);
        } else {
            synchroniserTypeFond();
        }
    }

    private void synchroniserTypeFond() {
        this.radioFondUniforme.setSelected(this.montre.getTypeFond().equals("uniforme"));
        this.radioFondDegrade.setSelected(this.montre.getTypeFond().equals("degrade"));
        this.radioFondImage.setSelected(this.montre.getTypeFond().equals("image"));
    }

    // Demande une heure de départ pour la simulation
    private void choisirHeureSimulee() {
        String saisie = JOptionPane.showInputDialog(this, "Entrer l'heure de départ : HH:MM:SS", "12:00:00");

        if (saisie != null) {
            try {
                DateTimeFormatter format = DateTimeFormatter.ofPattern("HH:mm:ss").withResolverStyle(java.time.format.ResolverStyle.STRICT);
                LocalTime heure = LocalTime.parse(saisie, format);

                LocalDateTime maintenant = LocalDateTime.now();
                LocalDateTime depart = LocalDateTime.of(
                        maintenant.getYear(),
                        maintenant.getMonth(),
                        maintenant.getDayOfMonth(),
                        heure.getHour(),
                        heure.getMinute(),
                        heure.getSecond()
                );

                this.tempsMontre.setHeureDepart(depart);
                this.tempsMontre.setUtiliserHeureSysteme(false);
            } catch (DateTimeParseException ex) {
                JOptionPane.showMessageDialog(this, "Format invalide. Utilise HH:MM:SS", "Erreur", JOptionPane.ERROR_MESSAGE);
                this.radioHeurePC.setSelected(true);
                this.tempsMontre.setUtiliserHeureSysteme(true);
            }
        }
        this.radioHeurePC.setSelected(this.tempsMontre.getUtiliserHeureSysteme());
        this.radioHeureSimulee.setSelected(!this.tempsMontre.getUtiliserHeureSysteme());
    }

    // Met à jour les boutons après le chargement d'une montre sauvegardée
    private void mettreAJourBoutonsDepuisMontre() {
        this.radioRond.setSelected(this.montre.getFormeBoitier().equals("rond"));
        this.radioCarre.setSelected(this.montre.getFormeBoitier().equals("carre"));

        if (this.montre.getTaille() <= 220) this.radioPetite.setSelected(true);
        else if (this.montre.getTaille() >= 320) this.radioGrande.setSelected(true);
        else this.radioMoyenne.setSelected(true);

        if (this.montre.getCouleurFond().equals(GRIS)) this.radioFondGris.setSelected(true);
        else if (this.montre.getCouleurFond().equals(NOIR)) this.radioFondNoir.setSelected(true);
        else this.radioFondBleu.setSelected(true);

        if (this.montre.getTypeFond().equals("uniforme")) this.radioFondUniforme.setSelected(true);
        else if (this.montre.getTypeFond().equals("degrade")) this.radioFondDegrade.setSelected(true);
        else this.radioFondImage.setSelected(true);

        this.radioAnalogique.setSelected(this.montre.getModeAnalogique());
        this.radioNumerique.setSelected(!this.montre.getModeAnalogique());

        if (this.montre.getCouleurAiguilles().equals(Color.WHITE)) this.radioAigBlanc.setSelected(true);
        else if (this.montre.getCouleurAiguilles().equals(ROUGE)) this.radioAigRouge.setSelected(true);
        else this.radioAigBleu.setSelected(true);

        if (this.montre.getFormeAiguilles().equals("simple")) this.radioAigSimple.setSelected(true);
        else if (this.montre.getFormeAiguilles().equals("lancette")) this.radioAigLancette.setSelected(true);
        else this.radioAigBaton.setSelected(true);

        if (this.montre.getTailleAiguilles().equals("petite")) this.radioAigPetite.setSelected(true);
        else if (this.montre.getTailleAiguilles().equals("grande")) this.radioAigGrande.setSelected(true);
        else this.radioAigMoyenne.setSelected(true);

        if (this.montre.getModeSecondes().equals("mini")) this.radioSecondesMini.setSelected(true);
        else if (this.montre.getModeSecondes().equals("compteur")) this.radioSecondesCompteur.setSelected(true);
        else this.radioSecondesTrotteuse.setSelected(true);

        if (this.montre.getPoliceNumerique().equals("Monospaced")) this.radioPoliceMono.setSelected(true);
        else if (this.montre.getPoliceNumerique().equals("Dialog")) this.radioPoliceDialog.setSelected(true);
        else this.radioPoliceArial.setSelected(true);

        if (this.montre.getTaillePoliceNumerique() <= 28) this.radioPolicePetite.setSelected(true);
        else if (this.montre.getTaillePoliceNumerique() >= 48) this.radioPoliceGrande.setSelected(true);
        else this.radioPoliceMoyenne.setSelected(true);

        if (this.montre.getCouleurTexteNumerique().equals("vert")) this.radioTexteVert.setSelected(true);
        else if (this.montre.getCouleurTexteNumerique().equals("or")) this.radioTexteOr.setSelected(true);
        else this.radioTexteBlanc.setSelected(true);

        this.checkSecondesAnalogique.setSelected(this.montre.getAfficherSecondes());
        this.checkSecondes.setSelected(this.montre.getAfficherSecondes());
        this.checkDate.setSelected(this.montre.getAfficherDate());
        this.checkBatterie.setSelected(this.montre.getBatterie().getAfficherBatterie());
        this.checkRythme.setSelected(this.montre.getRythme().getAfficher());
        this.checkTemperature.setSelected(this.montre.getTemperature().getAfficher());

        this.comboFormatDate.setSelectedIndex(this.montre.getFormatDate() - 1);

        if (this.montre.getRythme().getModeActivite().equals("repos")) this.comboRythme.setSelectedItem("Repos");
        else if (this.montre.getRythme().getModeActivite().equals("sport")) this.comboRythme.setSelectedItem("Sport");
        else this.comboRythme.setSelectedItem("Normal");

        mettreAJourVisibiliteMode();
    }

    // Recrée la zone d'affichage après un chargement
    private void rechargerZone() {
        getContentPane().remove(this.zone);
        this.zone = new ZoneAffichage(this.montre, this.tempsMontre);
        add(this.zone, BorderLayout.CENTER);
        revalidate();
        repaint();
    }

    // Gère les actions des boutons, du menu et du timer
    @Override
    public void actionPerformed(ActionEvent e) {
        Object source = e.getSource();

        if (source == this.timer) {
            this.montre.getRythme().faireVariation();
            this.montre.getTemperature().actualiser();
            this.montre.getBatterie().diminuer();
            this.zone.repaint();
            return;
        }

        if (source == this.radioHeurePC) this.tempsMontre.setUtiliserHeureSysteme(true);
        if (source == this.radioHeureSimulee) choisirHeureSimulee();

        if (source == this.radioRond) this.montre.setFormeBoitier("rond");
        if (source == this.radioCarre) this.montre.setFormeBoitier("carre");

        if (source == this.radioPetite) this.montre.setTaille(220);
        if (source == this.radioMoyenne) this.montre.setTaille(260);
        if (source == this.radioGrande) this.montre.setTaille(320);

        if (source == this.radioFondGris) this.montre.setCouleurFond(GRIS);
        if (source == this.radioFondNoir) this.montre.setCouleurFond(NOIR);
        if (source == this.radioFondBleu) this.montre.setCouleurFond(BLEU_NUIT);

        if (source == this.radioFondUniforme) this.montre.setTypeFond("uniforme");
        if (source == this.radioFondDegrade) this.montre.setTypeFond("degrade");
        if (source == this.radioFondImage || source == this.boutonChoisirImage) choisirImage();

        if (source == this.radioAnalogique) {
            this.montre.setModeAnalogique(true);
            mettreAJourVisibiliteMode();
        }

        if (source == this.radioNumerique) {
            this.montre.setModeAnalogique(false);
            mettreAJourVisibiliteMode();
        }

        if (source == this.radioAigBlanc) this.montre.setCouleurAiguilles(Color.WHITE);
        if (source == this.radioAigRouge) this.montre.setCouleurAiguilles(ROUGE);
        if (source == this.radioAigBleu) this.montre.setCouleurAiguilles(BLEU_ELECTRIQUE);

        if (source == this.radioAigSimple) this.montre.setFormeAiguilles("simple");
        if (source == this.radioAigLancette) this.montre.setFormeAiguilles("lancette");
        if (source == this.radioAigBaton) this.montre.setFormeAiguilles("baton");

        if (source == this.radioAigPetite) this.montre.setTailleAiguilles("petite");
        if (source == this.radioAigMoyenne) this.montre.setTailleAiguilles("moyenne");
        if (source == this.radioAigGrande) this.montre.setTailleAiguilles("grande");

        if (source == this.radioSecondesTrotteuse) this.montre.setModeSecondes("trotteuse");
        if (source == this.radioSecondesMini) this.montre.setModeSecondes("mini");
        if (source == this.radioSecondesCompteur) this.montre.setModeSecondes("compteur");

        if (source == this.checkSecondesAnalogique) {
            this.montre.setAfficherSecondes(this.checkSecondesAnalogique.isSelected());
        }

        if (source == this.radioPoliceArial) this.montre.setPoliceNumerique("Arial");
        if (source == this.radioPoliceMono) this.montre.setPoliceNumerique("Monospaced");
        if (source == this.radioPoliceDialog) this.montre.setPoliceNumerique("Dialog");

        if (source == this.radioPolicePetite) this.montre.setTaillePoliceNumerique(28);
        if (source == this.radioPoliceMoyenne) this.montre.setTaillePoliceNumerique(38);
        if (source == this.radioPoliceGrande) this.montre.setTaillePoliceNumerique(48);

        if (source == this.radioTexteBlanc) this.montre.setCouleurTexteNumerique("blanc");
        if (source == this.radioTexteVert) this.montre.setCouleurTexteNumerique("vert");
        if (source == this.radioTexteOr) this.montre.setCouleurTexteNumerique("or");

        if (source == this.checkSecondes) {
            this.montre.setAfficherSecondes(this.checkSecondes.isSelected());
        }

        if (source == this.checkDate) this.montre.setAfficherDate(this.checkDate.isSelected());
        if (source == this.checkBatterie) this.montre.getBatterie().setAfficherBatterie(this.checkBatterie.isSelected());
        if (source == this.checkRythme) this.montre.getRythme().setAfficher(this.checkRythme.isSelected());
        if (source == this.checkTemperature) this.montre.setAfficherTemperature(this.checkTemperature.isSelected());

        if (source == this.comboFormatDate) this.montre.setFormatDate(this.comboFormatDate.getSelectedIndex() + 1);

        if (source == this.comboRythme) {
            String choix = (String) this.comboRythme.getSelectedItem();

            if (choix.equals("Repos")) this.montre.getRythme().setModeActivite("repos");
            if (choix.equals("Normal")) this.montre.getRythme().setModeActivite("normal");
            if (choix.equals("Sport")) this.montre.getRythme().setModeActivite("sport");
        }

        if (source == this.itemSauvegarder) {
            JFileChooser fileChooser = new JFileChooser();
            fileChooser.setFileFilter(new FileNameExtensionFilter("Fichiers SER (*.ser)", "ser"));

            if (fileChooser.showSaveDialog(this) == JFileChooser.APPROVE_OPTION) {
                try {
                    this.montre.sauvegarder(fileChooser.getSelectedFile().getAbsolutePath());
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(this, "Erreur lors de la sauvegarde", "Erreur", JOptionPane.ERROR_MESSAGE);
                }
            }
        }

        if (source == this.itemOuvrir) {
            JFileChooser fileChooser = new JFileChooser();
            fileChooser.setFileFilter(new FileNameExtensionFilter("Fichiers SER (*.ser)", "ser"));

            if (fileChooser.showOpenDialog(this) == JFileChooser.APPROVE_OPTION) {
                try {
                    this.montre = Montre.charger(fileChooser.getSelectedFile().getAbsolutePath());
                    mettreAJourBoutonsDepuisMontre();
                    rechargerZone();
                    return;
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(this, "Erreur lors du chargement", "Erreur", JOptionPane.ERROR_MESSAGE);
                }
            }
        }

        this.zone.repaint();
    }
}