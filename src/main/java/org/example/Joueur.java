package org.example;

public interface Joueur {
    int mise(); // Assumes positive bet
    void debiter(int somme) throws DebitImpossibleException;
    void crediter(int somme);
}
