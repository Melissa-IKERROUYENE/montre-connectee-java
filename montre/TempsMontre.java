// TempsMontre.java
package montre;

import java.time.LocalDateTime;

// Cette classe gère le temps affiché par la montre
// Elle permet soit d'utiliser l'heure réelle du PC,
// soit une heure simulée choisie par l'utilisateur
public class TempsMontre {

    // Indique si on utilise l'heure du système (true) ou une simulation (false)
    private boolean utiliserHeureSysteme;

    // Heure de départ choisie pour la simulation
    private LocalDateTime heureDepart;

    // Moment où la simulation a commencé (temps réel)
    private LocalDateTime debutSimulation;

    // Constructeur : initialise par défaut avec l'heure réelle
    public TempsMontre() {
        this.utiliserHeureSysteme = true;
        this.heureDepart = LocalDateTime.now();
        this.debutSimulation = LocalDateTime.now();
    }

    // Retourne si on utilise l'heure système
    public boolean getUtiliserHeureSysteme() {
        return this.utiliserHeureSysteme;
    }

    // Permet de choisir entre heure réelle et heure simulée
    public void setUtiliserHeureSysteme(boolean utiliserHeureSysteme) {
        this.utiliserHeureSysteme = utiliserHeureSysteme;
    }

    // Retourne l'heure de départ de la simulation
    public LocalDateTime getHeureDepart() {
        return this.heureDepart;
    }

    // Définit une nouvelle heure de départ pour la simulation
    // et réinitialise le moment de début de simulation
    public void setHeureDepart(LocalDateTime heureDepart) {
        this.heureDepart = heureDepart;
        this.debutSimulation = LocalDateTime.now();
    }

    // Calcule le temps actuel à afficher sur la montre
    public LocalDateTime getTempsActuel() {

        // Si on utilise l'heure du PC → on retourne l'heure actuelle directement
        if (this.utiliserHeureSysteme) {
            return LocalDateTime.now();
        } else {
            // Sinon on calcule combien de secondes se sont écoulées
            // depuis le début de la simulation
            long secondesEcoulees =
                    this.debutSimulation.until(
                            LocalDateTime.now(),
                            java.time.temporal.ChronoUnit.SECONDS
                    );

            // On ajoute ces secondes à l'heure de départ
            // pour simuler un temps qui avance normalement
            return this.heureDepart.plusSeconds(secondesEcoulees);
        }
    }
}