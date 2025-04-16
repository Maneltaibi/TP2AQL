package org.example;

public interface BanqueInterface {
    void crediter(int somme);
    boolean est_solvable();
    void debiter(int somme);
}
