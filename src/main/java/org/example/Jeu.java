package org.example;

public class Jeu {
    private final Banque banque;
    private boolean estOuvert;

    public Jeu(Banque banque) {
        this.banque = banque;
        this.estOuvert = true;
    }

    public void jouer(Joueur joueur, De de1, De de2) throws JeuFermeException {
        // Check if game is open
        if (!estOuvert) {
            throw new JeuFermeException();
        }

        // Get player's bet
        int mise = joueur.mise();
        try {
            // Debit the bet from player
            joueur.debiter(mise);
        } catch (DebitImpossibleException e) {
            return; // Stop if player is insolvent
        }

        // Credit bet to bank
        banque.crediter(mise);

        // Roll dice
        int resultat = de1.lancer() + de2.lancer();
        if (resultat == 7) {
            // Player wins: credit double the bet
            int gain = mise * 2;
            joueur.crediter(gain);
            banque.debiter(gain);
            // Check bank solvency
            if (!banque.est_solvable()) {
                fermer();
            }
        }
    }

    public void fermer() {
        this.estOuvert = false;
    }

    public boolean estOuvert() {
        return this.estOuvert;
    }
}
