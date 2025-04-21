<!-- Created by Jonathan Carrero -->

# K-Means Clustering Algorithm 📊🧠

---

## Description

The primary goal of the **K-Means algorithm** is to partition a set of *n* observations into *k* distinct clusters. Each observation belongs to the cluster with the nearest mean (cluster centroid).

The algorithm follows these general steps:

1.  **Initialization**:
    *   Read data from a file to determine the number of samples (*n*) and the desired number of clusters (*k* - referred to as *c* in the original text).
    *   Initialize the *k* cluster centroids (*v<sub>i</sub>*). This can be done randomly or using specific initialization techniques.
    *   *(Note: The original text mentions initializing probabilities, which might refer to Fuzzy C-Means, a related algorithm. Standard K-Means assigns points definitively, but initialization might involve preliminary assignments.)*

2.  **Assignment Step**: Assign each data point (*x<sub>j</sub>*) to the nearest cluster centroid (*v<sub>i</sub>*) based on a distance metric (commonly Euclidean distance).

3.  **Update Step**: Recalculate the position of the *k* centroids. The new centroid is the mean of all data points assigned to that cluster in the previous step.

    *The formulas shown likely represent:*
    *   *Top Equation:* Calculating the new centroid (*v<sub>i</sub>*) as the weighted average (or simple average in standard K-Means) of the points (*x<sub>k</sub>*) belonging to cluster *i*. The *u<sub>ik</sub>* term often represents the degree of membership (1 or 0 in standard K-Means, a probability in Fuzzy C-Means).
    *   *Bottom Equation:* Updating the membership (*u<sub>ik</sub>*) of point *k* to cluster *i*, typically based on the inverse distance to the centroid *v<sub>i</sub>*.

    ![K-Means/Fuzzy C-Means Update Formulas](https://github.com/Joncarre/Java-language/blob/master/Ingenier%C3%ADa%20del%20Conocimiento/images/IC4_2.png)

4.  **Iteration**: Repeat the **Assignment** (Step 2) and **Update** (Step 3) steps until the cluster centroids no longer change significantly or a maximum number of iterations is reached.

5.  **Stopping Criterion**: The algorithm stops when the change in the centroids between iterations is very small, below a predefined threshold *epsilon* (ε). This indicates that the cluster assignments have stabilized.

    *The formula represents the condition:* Stop if the maximum change in any centroid position between iteration *t* and *t+1* is less than ε.

    ![K-Means Stopping Criterion (Epsilon)](https://github.com/Joncarre/Java-language/blob/master/Ingenier%C3%ADa%20del%20Conocimiento/images/IC4_3.png)

## Execution Example

The image below shows the output of the first two iterations of the algorithm. You can observe:
*   The initial centroid values.
*   The calculated membership matrix (partially shown, dimensions are *k* clusters × *n* samples).
*   The recalculated centroids after the first iteration.
*   The check against the *epsilon* value to determine convergence.

In this specific example (though not fully visible in the screenshot), the algorithm reportedly converged and stopped at iteration 4.

![K-Means Execution Example Output](https://github.com/Joncarre/Java-language/blob/master/Ingenier%C3%ADa%20del%20Conocimiento/images/IC4_1.png)
