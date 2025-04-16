package org.example;

public class JeuFermeException extends Exception {
    public JeuFermeException() {
        super("Le jeu est fermé");
    }
}