
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import static org.mockito.Mockito.*;
import org.example.*;
@ExtendWith(MockitoExtension.class)
public class UserServiceTest {
    @Mock
    private UtilisateurApi utilisateurApiMock;

    @Test
    public void testCreerUtilisateur() throws ServiceException {
        // Arrange: Create a new user
        Utilisateur utilisateur = new Utilisateur("Jean", "Dupont", "jeandupont@email.com");

        // Arrange: Configure the mock to do nothing when creerUtilisateur is called
        doNothing().when(utilisateurApiMock).creerUtilisateur(utilisateur);

        // Arrange: Create the service with the mock
        UserService userService = new UserService(utilisateurApiMock);

        // Act: Call the method to test
        userService.creerUtilisateur(utilisateur);

        // Assert: Verify the API call was made with the correct user
        verify(utilisateurApiMock).creerUtilisateur(utilisateur);
    }
}
