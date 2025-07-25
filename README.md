# TP2AQL
# TP2 Unit Tests - Exercise 4 README

## Question 1: Which objects that `Jeu` depends on must be mocked?

The `Jeu` class depends on `Banque`, `Joueur`, `De1`, and `De2`. All must be mocked in unit tests to:
- **Isolate** `Jeu` from external dependencies, ensuring tests focus on `Jeu`'s logic.
- **Control** behavior, e.g., dice rolls (fixed values), player solvency (throwing exceptions), bank solvency (true/false).
- **Automate** testing by defining predictable outputs, avoiding real random dice rolls or bank state.

## Question 2: Scenarios (Equivalence Classes) for Testing `jouer`

1. **Game is closed**: `estOuvert()` is false, throws `JeuFermeException`.
2. **Player is insolvent**: `Joueur.debiter()` throws `DebitImpossibleException`, no dice rolls or bank interactions.
3. **Player loses**: Dice sum ≠ 7, bet credited to bank, no further actions.
4. **Player wins, bank solvent**: Dice sum = 7, player credited double bet, bank debited, game remains open.
5. **Player wins, bank insolvent**: Dice sum = 7, player credited, bank debited, game closes due to insolvency.

## Question 4: Test for Game Closed - State or Interaction?

The test `testJouerJeuFerme` is a **state test**. It checks the game's state by verifying that `JeuFermeException` is thrown when `estOuvert` is false. No interactions with mocks are verified, as the exception occurs before any dependency calls.

## Question 5: Test for Player Insolvent - State or Interaction?

The test `testJouerJoueurInsolvable` is an **interaction test**. It verifies:
- `Joueur.mise()` and `Joueur.debiter()` are called.
- No interactions occur with `De1`, `De2`, or `Banque` (using `verifyNoInteractions()`), ensuring the game stops after insolvency.
- The lack of dice rolls is confirmed by no calls to `lancer()`.

## Question 7: Difference Between Mocked and Integrated Bank Tests

- **Mocked Test (`testJouerGagnantBanqueInsolvable`)**:
    - Uses a mocked `Banque` with `est_solvable()` stubbed to return `false`.
    - Isolates `Jeu`, focusing only on its logic.
    - Faster, as it avoids real bank state calculations.
    - Limited to verifying `Jeu`'s reaction to the mock's behavior.

- **Integrated Test (`testJouerGagnantBanqueDevientInsolvableIntegration`)**:
    - Uses a real `Banque` with initial funds (200) and minimum (300).
    - Tests the interaction between `Jeu` and `Banque`, verifying the bank’s state (funds drop below minimum, triggering insolvency).
    - Slower, as it involves real bank logic.
    - More realistic, ensuring `Jeu` and `Banque` work together correctly.