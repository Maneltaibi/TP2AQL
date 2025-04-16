package org.example;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class JeuTest {
    @Mock
    private Banque banque;
    @Mock
    private Joueur joueur;
    @Mock
    private De de1;
    @Mock
    private De de2;

    @Test
    public void testJouerJeuFerme() {
        // Arrange: Close the game
        Jeu jeu = new Jeu(banque);
        jeu.fermer();

        // Act & Assert: Expect JeuFermeException
        assertThrows(JeuFermeException.class, () -> jeu.jouer(joueur, de1, de2));
    }

    @Test
    public void testJouerJoueurInsolvable() throws JeuFermeException, DebitImpossibleException {
        // Arrange: Mock player to throw DebitImpossibleException
        Jeu jeu = new Jeu(banque);
        when(joueur.mise()).thenReturn(100);
        doThrow(new DebitImpossibleException()).when(joueur).debiter(100);

        // Act: Play the game
        jeu.jouer(joueur, de1, de2);

        // Assert: Verify interactions and no dice/bank calls
        verify(joueur).mise();
        verify(joueur).debiter(100);
        verifyNoInteractions(de1, de2, banque);
    }

    @Test
    public void testJouerPerdant() throws JeuFermeException, DebitImpossibleException {
        // Arrange: Mock dice to sum != 7
        Jeu jeu = new Jeu(banque);
        when(joueur.mise()).thenReturn(100);
        when(de1.lancer()).thenReturn(2);
        when(de2.lancer()).thenReturn(3); // 2 + 3 = 5
        when(banque.est_solvable()).thenReturn(true);

        // Act: Play the game
        jeu.jouer(joueur, de1, de2);

        // Assert: Verify bet credited to bank, no further actions
        verify(joueur).debiter(100);
        verify(banque).crediter(100);
        verify(de1).lancer();
        verify(de2).lancer();
        verifyNoMoreInteractions(joueur, banque);
    }

    @Test
    public void testJouerGagnantBanqueSolvable() throws JeuFermeException, DebitImpossibleException {
        // Arrange: Mock dice to sum = 7, bank solvent
        Jeu jeu = new Jeu(banque);
        when(joueur.mise()).thenReturn(100);
        when(de1.lancer()).thenReturn(4);
        when(de2.lancer()).thenReturn(3); // 4 + 3 = 7
        when(banque.est_solvable()).thenReturn(true);

        // Act: Play the game
        jeu.jouer(joueur, de1, de2);

        // Assert: Verify credits and game remains open
        verify(joueur).debiter(100);
        verify(banque).crediter(100);
        verify(joueur).crediter(200);
        verify(banque).debiter(200);
        verify(banque).est_solvable();
        assertTrue(jeu.estOuvert());
    }

    @Test
    public void testJouerGagnantBanqueInsolvable() throws JeuFermeException, DebitImpossibleException {
        // Arrange: Mock dice to sum = 7, bank insolvent
        Jeu jeu = new Jeu(banque);
        when(joueur.mise()).thenReturn(100);
        when(de1.lancer()).thenReturn(4);
        when(de2.lancer()).thenReturn(3); // 4 + 3 = 7
        when(banque.est_solvable()).thenReturn(false);

        // Act: Play the game
        jeu.jouer(joueur, de1, de2);

        // Assert: Verify credits and game closes
        verify(joueur).debiter(100);
        verify(banque).crediter(100);
        verify(joueur).crediter(200);
        verify(banque).debiter(200);
        verify(banque).est_solvable();
        assertFalse(jeu.estOuvert());
    }
}
