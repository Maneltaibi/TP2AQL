package org.example;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class UserServiceScenariosTest {
    @Mock
    private UtilisateurApi utilisateurApiMock;

    @Test
    public void testCreerUtilisateurThrowsServiceException() throws ServiceException {
        // Arrange: Create a user and configure mock to throw exception
        Utilisateur utilisateur = new Utilisateur("Jean", "Dupont", "jeandupont@email.com");
        doThrow(new ServiceException("Echec de la création de l'utilisateur"))
                .when(utilisateurApiMock).creerUtilisateur(utilisateur);
        UserService userService = new UserService(utilisateurApiMock);

        // Act & Assert: Expect ServiceException
        assertThrows(ServiceException.class, () -> userService.creerUtilisateur(utilisateur));
    }

    @Test
    public void testCreerUtilisateurValidationError() throws ServiceException {
        // Arrange: Create an invalid user
        Utilisateur utilisateur = new Utilisateur("", "", "invalid@email.com");
        UserService userService = new UserService(utilisateurApiMock);

        // Act: Attempt to create user, expecting no API call
        try {
            userService.creerUtilisateur(utilisateur);
        } catch (ServiceException e) {
            // Expected, but we focus on no API interaction
        }

        // Assert: Verify API was never called
        verify(utilisateurApiMock, never()).creerUtilisateur(any(Utilisateur.class));
    }

    @Test
    public void testCreerUtilisateurReturnsId() throws ServiceException {
        // Arrange: Create a user and configure mock to set ID
        Utilisateur utilisateur = new Utilisateur("Jean", "Dupont", "jeandupont@email.com");
        int idUtilisateur = 123;
        doAnswer(invocation -> {
            Utilisateur u = invocation.getArgument(0);
            u.setId(idUtilisateur); // Assumes setId exists
            return null; // Void method
        }).when(utilisateurApiMock).creerUtilisateur(utilisateur);
        UserService userService = new UserService(utilisateurApiMock);

        // Act: Call the method
        userService.creerUtilisateur(utilisateur);

        // Assert: Verify the user's ID
        assertEquals(idUtilisateur, utilisateur.getId());
    }

    @Test
    public void testCreerUtilisateurArgumentCapture() throws ServiceException {
        // Arrange: Create a user and set up ArgumentCaptor
        Utilisateur utilisateur = new Utilisateur("Jean", "Dupont", "jeandupont@email.com");
        ArgumentCaptor<Utilisateur> argumentCaptor = ArgumentCaptor.forClass(Utilisateur.class);
        doNothing().when(utilisateurApiMock).creerUtilisateur(any(Utilisateur.class));
        UserService userService = new UserService(utilisateurApiMock);

        // Act: Call the method
        userService.creerUtilisateur(utilisateur);

        // Assert: Capture and verify arguments
        verify(utilisateurApiMock).creerUtilisateur(argumentCaptor.capture());
        Utilisateur utilisateurCapture = argumentCaptor.getValue();
        assertEquals("Jean", utilisateurCapture.getPrenom());
        assertEquals("Dupont", utilisateurCapture.getNom());
        assertEquals("jeandupont@email.com", utilisateurCapture.getEmail());
    }
}
