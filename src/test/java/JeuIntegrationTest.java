package org.example;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class JeuIntegrationTest {
    @Mock
    private Joueur joueur;
    @Mock
    private De de1;
    @Mock
    private De de2;

    @Test
    public void testJouerGagnantBanqueDevientInsolvableIntegration() throws JeuFermeException, DebitImpossibleException {
        // Arrange: Create a bank with low funds
        Banque banque = new Banque(200, 300); // Will become insolvent after debit
        Jeu jeu = new Jeu(banque);
        when(joueur.mise()).thenReturn(100);
        when(de1.lancer()).thenReturn(4);
        when(de2.lancer()).thenReturn(3); // 4 + 3 = 7

        // Act: Play the game
        jeu.jouer(joueur, de1, de2);

        // Assert: Verify player credited and game closed
        verify(joueur).debiter(100);
        verify(joueur).crediter(200);
        assertFalse(jeu.estOuvert());
    }
}
