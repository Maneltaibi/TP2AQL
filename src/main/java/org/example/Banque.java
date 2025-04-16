package org.example;

public class Banque implements BanqueInterface {
    private int fond;
    private final int fondMinimum;

    public Banque(int fondInitial, int fondMinimum) {
        this.fond = fondInitial;
        this.fondMinimum = fondMinimum;
    }

    @Override
    public void crediter(int somme) {
        this.fond += somme;
    }

    @Override
    public void debiter(int somme) {
        this.fond -= somme;
    }

    @Override
    public boolean est_solvable() {
        return this.fond >= this.fondMinimum;
    }
}
