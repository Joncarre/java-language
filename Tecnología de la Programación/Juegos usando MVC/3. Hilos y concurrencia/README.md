<!-- Created by Jonathan Carrero -->

# Threads and Concurrency Enhancements ⚙️⏳

---

## Description

While the main changes involve the execution flow (and thus the underlying code) to incorporate concurrency, let's focus on the new components added to the GUI window in this version:

*   **Thinking Indicator**: An icon (e.g., a gear or hourglass) indicates when the `smart` player is calculating its move. The background of this area turns yellow during the thinking process. 🤔
*   **Thread Count Selector**: Allows the user to specify the number of threads the `smart` player can use for its search. This enables parallel processing to potentially find moves much faster than the non-concurrent version.
*   **Max Search Time Icon**: An icon associated with the time limit setting.
*   **Max Search Time Selector**: A control (e.g., a spinner or text field) to set the maximum time (in milliseconds) the `smart` player is allowed to search for a move. The search must not exceed this limit.
*   **Cancel Button**: A button (e.g., labeled "Cancel" or with a stop icon 🛑) to interrupt the `smart` player's move calculation. This button is *enabled only* while the AI is actively searching. Clicking it stops the search thread(s).

The updated GUI looks like this (note the `smart` player is currently thinking, indicated by the yellow background and potentially the icon):

![GUI with Concurrency Controls - Smart Player Thinking](https://github.com/Joncarre/Java-language/blob/master/Tecnología%20de%20la%20Programación/Juegos%20usando%20MVC/images/6_1.png)

Additionally, it's worth noting that when it's not a player's turn, they shouldn't be able to trigger automatic moves (`random` or `smart`). Therefore, the corresponding buttons in the control panel are now **disabled** when it is not that player type's turn. This prevents accidental clicks and provides clearer visual feedback:

![GUI with Disabled AI Move Buttons when not their turn](https://github.com/Joncarre/Java-language/blob/master/Tecnología%20de%20la%20Programación/Juegos%20usando%20MVC/images/6_2.png)
