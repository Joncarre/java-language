<!-- Created by Jonathan Carrero -->

# Wolf and Sheep (Console Version) 🐺🐑🎮

---

## Description

This initial version of the project includes two console-based games:

*   **Tic-Tac-Toe**: The classic game. Played on an NxN board (typically 3x3), players take turns placing their marks. A player wins by getting three of their marks in a row, horizontally, vertically, or diagonally.

*   **Wolf and Sheep (Was)**: Played on an 8x8 board (like a chessboard), using only the dark squares. Four sheep start on the dark squares at one end, and one wolf starts on a dark square at the opposite end.
    *   **Turns**: Players alternate turns, with the wolf (Player 0) moving first.
    *   **Movement**:
        *   The wolf moves one diagonal square in *any* direction (forward or backward).
        *   Sheep move one diagonal square *only forward* (towards the wolf's starting side).
    *   **Winning Conditions**:
        *   The **wolf wins** if it reaches the sheep's starting row.
        *   The **sheep win** if they trap the wolf so it cannot make a valid move.

### Player Types

Both Tic-Tac-Toe and Wolf and Sheep can be played with the following player combinations:

*   `console`: The user inputs moves via the console.
*   `random`: A player making completely random valid moves.
*   `smart`: An intelligent player using a **Minimax** algorithm (implemented in a dedicated class) to determine the best move.

### Running the Game

Configure the game execution via `Run > Run Configurations...` in Eclipse. The command-line syntax is:

```bash
game player1 player2
```

Where:
*   `[game]` can be `ttt` (Tic-Tac-Toe) or `was` (Wolf and Sheep).
*   `[player]` can be `console`, `random`, or `smart`.

Below is an example configuration for Wolf and Sheep, with a human (`console`) playing as the wolf (Player 1) against the smart AI (`smart`) as the sheep (Player 2).

![Eclipse Run Configuration Example for Was (console vs smart)](https://github.com/Joncarre/Java-language/blob/master/Tecnología%20de%20la%20Programación/Juegos%20usando%20MVC/images/4_3.png)

### Console Gameplay Example

Since this version is console-only, the game displays the current board state and lists the available moves for the current player. The user (if playing as `console`) selects a move from the list.

Here's an example showing the wolf's first move (as `console`) and the `smart` sheep player's response:

![Console Gameplay: Wolf's first move and Smart Sheep response](https://github.com/Joncarre/Java-language/blob/master/Tecnología%20de%20la%20Programación/Juegos%20usando%20MVC/images/4_1.png)

We can also see an example of a game ending. In this case, the sheep have successfully cornered the wolf, resulting in a win for the sheep.

![Console Gameplay: Sheep win by trapping the wolf](https://github.com/Joncarre/Java-language/blob/master/Tecnología%20de%20la%20Programación/Juegos%20usando%20MVC/images/4_2.png)

## JUnit Tests ✅

Unit tests are used to verify that individual methods within classes function as expected. They test the smallest logical units of code in isolation (as opposed to integration tests, which check how components work together). We use the **JUnit** library (supported by Maven and Eclipse) for writing and running these tests.

More information on installing and using JUnit can be found here: [http://junit.org/junit4/](http://junit.org/junit4/)

The following tests were implemented for the Wolf and Sheep game logic:

*   A surrounded wolf results in a sheep victory.
*   A wolf reaching the opposite side (row `y=0`) results in a wolf victory.
*   A wolf in its initial position has only 1 valid move; after making that move, it should have 4 valid moves on its next turn (assuming open space).
*   A sheep in its initial row has 2 valid moves (if space allows); a sheep on a side column also has 2 valid moves (if space allows).
*   Providing fewer than 3 command-line arguments results in an error.
*   Providing too many arguments (more players than the game supports) results in an error.
*   Providing an invalid game name as the first argument results in an error.

Here's an example snippet of the JUnit test code verifying the wolf's winning condition (reaching row `y=0`):

![JUnit Test Example: Wolf Wins Condition](https://github.com/Joncarre/Java-language/blob/master/Tecnología%20de%20la%20Programación/Juegos%20usando%20MVC/images/4_4.png)


