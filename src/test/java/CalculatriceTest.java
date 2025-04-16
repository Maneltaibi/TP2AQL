import org.example.Calculatrice;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class CalculatriceTest {
    @Mock
    private Calculatrice calculatrice;

    @Test
    public void testAdditionner() {
        // Arrange: Define the behavior of the mocked additionner method
        when(calculatrice.additionner(2, 3)).thenReturn(5);

        // Act: Call the method to test
        int resultat = calculatrice.additionner(2, 3);

        // Assert: Verify the result
        assertEquals(5, resultat);

        // Assert: Verify that additionner was called with arguments 2 and 3
        verify(calculatrice).additionner(2, 3);

        // Assert: Verify that no other methods were called on the mock
        verifyNoMoreInteractions(calculatrice);

        // Note: The TP2 document mentions verifying state with getState(),
        // but Calculatrice has no getState() method, and result is private.
        // This step is skipped as it's not feasible with the given class.
    }
}