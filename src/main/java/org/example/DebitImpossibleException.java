package org.example;

public class DebitImpossibleException extends Exception {
    public DebitImpossibleException() {
        super("Débit impossible");
    }
}