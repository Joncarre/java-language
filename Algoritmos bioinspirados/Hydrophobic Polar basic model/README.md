<!-- Created by Jonathan Carrero -->

# Simplified Hydrophobic-Polar (HP) Protein Folding Model 🧬

---

## Description

The **Hydrophobic-Polar (HP) protein folding model** is a *highly simplified* model used to study protein folds in space. First proposed by Ken Dill in 1985, it's the most well-known type of lattice protein model.

💡 The core idea stems from the observation that *hydrophobic interactions* between amino acid residues are the primary driving force for proteins folding into their native state.

### How it Works

1.  **Classification**: All amino acids are categorized as either **Hydrophobic (H)** or **Polar (P)**.
2.  **Folding**: The folding of a protein sequence is represented as a *self-avoiding walk* on a 2D or 3D lattice (square lattices are common, though triangular ones have also been used).
3.  **Energy**: The HP model mimics the hydrophobic effect by assigning a negative (favorable) energy value to interactions between adjacent, non-covalently bonded H residues.
4.  **Native State**: Proteins are assumed to be in their native state when they achieve minimum energy.

### Complexity

Despite abstracting many details of protein folding, finding the minimum energy conformation in the HP model remains an **NP-hard problem** in both 2D and 3D lattices.

