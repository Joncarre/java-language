<!-- Created by Jonathan Carrero -->

# ID3 Decision Tree Algorithm (Simplified) 🌳📊

---

## Description

This project implements a **reduced and preliminary version** of the **ID3 algorithm** for building decision trees. It's well-known that the full ID3 algorithm is *recursive*, building the tree level by level. However, this implementation presents only a **single iteration**, determining the *first decision node* (the root) of the tree.

The core idea of ID3 is to iteratively select the attribute that best splits the data, typically based on maximizing **Information Gain** (or minimizing Entropy).

### Resources

*   The **WEKA** machine learning toolkit can be helpful for understanding and implementing decision trees: [http://www.cs.waikato.ac.nz/ml/weka/](http://www.cs.waikato.ac.nz/ml/weka/)
*   An illustrative manual (PhD Course, Orallo and Ramírez, Polytechnic University of Valencia) describing decision trees (specifically C4.5/J48, a successor to ID3) in WEKA is available here: [http://users.dsic.upv.es/~cferri/weka/CursDoctorat-weka.pdf](http://users.dsic.upv.es/~cferri/weka/CursDoctorat-weka.pdf) (See Section 2).

## Execution Example

The program takes two `.txt` files as input:

1.  `AtributosJuego.txt`: Contains the names of the attributes (features) to be evaluated (e.g., Outlook, Temperature, Humidity, Wind).
2.  `Juego.txt`: Contains the dataset with values for each attribute for different instances, along with the target class (e.g., Play/Don't Play).

As shown in the output below, the algorithm calculates a "merit" score (likely Information Gain) for each attribute. The attribute with the highest merit is selected as the root node for the decision tree.

In this example:
*   The attribute `TiempoExterior` (Outlook) has the highest merit.
*   Therefore, the root node will split based on `TiempoExterior`.
*   This creates three branches corresponding to its possible values: `soleado` (sunny), `nublado` (overcast), and `lluvioso` (rainy).
*   Each branch would then conceptually contain a subset of the original data corresponding to that attribute value, ready for the next (recursive) step in a full ID3 implementation.

![ID3 First Iteration Output - Root Node Selection](https://github.com/Joncarre/Java-language/blob/master/Ingenier%C3%ADa%20del%20Conocimiento/images/IC2_1.png)
