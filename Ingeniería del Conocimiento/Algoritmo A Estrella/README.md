<!-- Created by Jonathan Carrero -->

# A* (A-Star) Algorithm Implementation ⭐🗺️

---

## Description

This project implements a version of the **A\* search algorithm**. The goal is to simulate the navigation of a vehicle (ground, air, or sea 🚗) within a defined space towards a target location.

How does it achieve this? Let's look at an implementation example and the decision-making process:

Consider the figure below, where the starting position is (1,1). The only successor node initially is (1,2); there's no other choice. This continues until the vehicle reaches position (2,5).

![A* Path Example Step 1](https://github.com/Joncarre/Java-language/blob/master/Ingenier%C3%ADa%20del%20Conocimiento/images/IC1_3.png)

Upon reaching position (2,5), there are two possible successors: (3,4) and (3,5). Let's evaluate the cost function `f(n)` for each, assuming the grid squares have dimensions 1x1. The A* cost function is defined as:

`f(n) = g(n) + h(n)`

Where:
*   `g(n)`: The actual cost of the path from the start node to node `n`.
*   `h(n)`: The heuristic estimate of the cost from node `n` to the target node.

**a) Node (3,4):**
*   `g(n)` = dist((1,1),(1,2)) + dist((1,2),(1,3)) + dist((1,3),(1,4)) + dist((1,4),(2,5)) + dist((2,5),(3,4)) = 1 + 1 + 1 + √2 + √2 ≈ 1 + 1 + 1 + 1.41 + 1.41 = **5.82**
*   `h(n)` = Euclidean distance from (3,4) to target (5,2) = √((5-3)² + (2-4)²) = √(2² + (-2)²) = √8 ≈ **2.82**
*   `f(n)` = 5.82 + 2.82 = **8.64**

**b) Node (3,5):**
*   `g(n)` = dist((1,1),(1,2)) + dist((1,2),(1,3)) + dist((1,3),(1,4)) + dist((1,4),(2,5)) + dist((2,5),(3,5)) = 1 + 1 + 1 + √2 + 1 ≈ 1 + 1 + 1 + 1.41 + 1 = **5.41**
*   `h(n)` = Euclidean distance from (3,5) to target (5,2) = √((5-3)² + (2-5)²) = √(2² + (-3)²) = √13 ≈ **3.61**
*   `f(n)` = 5.41 + 3.61 = **9.02**

In this case, node **(3,4)** would be chosen as the successor to (2,5) because it has the lower `f(n)` value (8.64 < 9.02).

## Execution Example

When starting the program, you need to specify:
1.  The dimensions of the grid (matrix).
2.  The start and end points.
3.  Whether to include obstacles along the path.

![A* Program Input Configuration](https://github.com/Joncarre/Java-language/blob/master/Ingenier%C3%ADa%20del%20Conocimiento/images/IC1_1.png)

After configuration, you can observe how the algorithm expands nodes to find the shortest path to the target. For each expansion step, the program prints the current state of the grid and its elements. Finally, it displays the completed path.

![A* Program Execution Output](https://github.com/Joncarre/Java-language/blob/master/Ingenier%C3%ADa%20del%20Conocimiento/images/IC1_2.png)
