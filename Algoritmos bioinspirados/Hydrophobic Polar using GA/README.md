<!-- Created by Jonathan Carrero -->

# Hydrophobic-Polar Protein Folding Model using Genetic Algorithm (GA) 🧬 + PSO Concepts

---

## Description

This project extends the basic [Hydrophobic-Polar model](../Hydrophobic%20Polar%20basic%20model) implementation (which recursively sought the most efficient fold) by incorporating ideas from **Genetic Algorithms (GA)** and **Particle Swarm Optimization (PSO)** 🐦. The goal is to find optimal protein folding configurations using these bio-inspired techniques.

The project consists of five main Java classes. Here's a brief overview of each:

*   `Main`: The entry point of the program. It creates an `Engine` object responsible for executing the algorithm. Following good practice, this class is kept simple and delegates the main work.

*   `Engine`: Manages the overall process. It initializes the population and handles the evolutionary loop, printing population status as needed (e.g., after each generation/evolution step).

*   `Population`: This class performs the core evolutionary operations. It manages the collection of individuals and implements the main phase: *movement* or *evolution*. This involves updating individuals' positions (potential folds) based on PSO-like concepts, moving them towards promising areas of the search space (guided by the 'fittest' individual found so far). Movement logic considers factors like individual fitness, distance to the best-known solution, and potentially other parameters (analogous to velocity/inertia in PSO).

*   `Individual`: Represents a single potential solution (a specific protein fold). It holds necessary attributes like the sequence conformation (coordinates of amino acids) and its calculated fitness score. It also includes standard getters and setters for accessing/modifying these attributes.

*   `Location`: A helper class likely used to define and manage the coordinates (position) of amino acids within the lattice structure for an `Individual`.

