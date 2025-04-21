<!-- Created by Jonathan Carrero -->

# Graphical User Interface (GUI) Version ✨🖱️

---

## Description

It's important to note that the **console mode** from the previous version is still available, but this version adds a new **GUI mode** (`gui`) using Java Swing. Consequently, the command-line arguments for configuration have been slightly modified.

The new syntax is:

```bash
game mode player1 player2
```

Where:

*   `game`: `ttt` (for Tic-Tac-Toe) or `was` (for Wolf and Sheep).
*   `mode`: `gui` (to use the Swing GUI) or `console` (to use the console interface).
*   `player`: `manual` (for a human player), `random` (for a random AI), or `smart` (for the intelligent AI using Minimax).

**⚠️ NOTICE:** The meaning of `console` has changed from the previous version. Previously, `console` represented a human player interacting via the console. Now, `manual` is used for a human player in *both* GUI and console modes. The `console` argument now *only* refers to the **mode** of interaction (the console view), not the player type.

Here's an example configuration for playing Tic-Tac-Toe in GUI mode, with the first player being human (`manual`) and the second being a random AI (`random`):

![Eclipse Run Configuration Example for TTT (gui, manual vs random)](https://github.com/Joncarre/Java-language/blob/master/Tecnología%20de%20la%20Programación/Juegos%20usando%20MVC/images/5_3.png)

## Implementation (MVC Structure)

Without going into excessive detail about every class and its exact role in the Model-View-Controller (MVC) pattern, here's a brief overview:

*   **Model**: Primarily handled by a few classes within the `mvc` package, with `GameTable` being the most significant. It manages the game state and logic.
*   **Controller**: Defined by the `GameController` interface and implemented by two concrete classes:
    *   `GUIController`: Handles user input and interactions from the Swing GUI.
    *   `ConsoleController`: Handles input and interactions for the console mode.
*   **View**:
    *   The console view is simpler (handled within `ConsoleController` or a dedicated console view class).
    *   The GUI view is managed mainly by `GUIView`, an abstract class. Five key components inherit from it and compose the visual interface:
        *   `RectBoardView`: Displays the game board.
        *   `MessageViewer`: Shows game messages (e.g., whose turn, game over).
        *   `PlayersInfoViewer`: Allows selecting player colors/types (potentially).
        *   `ControlPanel`: Contains buttons for actions like Restart, Exit, Smart Move hint, etc.
        *   `GameContainer`: The main window/frame holding all other GUI components.

    A picture is worth a thousand words:

    ![Diagram showing GUI Components Layout](https://github.com/Joncarre/Java-language/blob/master/Tecnología%20de%20la%20Programación/Juegos%20usando%20MVC/images/5_4.png)

    This simple diagram illustrates the inheritance structure for the View components:

    ![Simplified Class Diagram for View Inheritance](https://github.com/Joncarre/Java-language/blob/master/Tecnología%20de%20la%20Programación/Juegos%20usando%20MVC/images/5_5.png)

## Execution Examples

Here's how an initial game of Wolf and Sheep looks in the GUI mode:

![GUI Gameplay: Initial state of Wolf and Sheep](https://github.com/Joncarre/Java-language/blob/master/Tecnología%20de%20la%20Programación/Juegos%20usando%20MVC/images/5_1.png)

And this is the result at the end of a Tic-Tac-Toe game in the GUI:

![GUI Gameplay: End state of Tic-Tac-Toe](https://github.com/Joncarre/Java-language/blob/master/Tecnología%20de%20la%20Programación/Juegos%20usando%20MVC/images/5_2.png)

