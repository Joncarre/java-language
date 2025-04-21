<!-- Created by Jonathan Carrero -->

# Bayesian Parametric Estimation / Classifier 📈🤖

---

## Description

This project deals with **parametric estimation**, often used in the context of Bayesian classifiers. For background, you can consult resources on Bayesian parametric estimation (e.g., from statistical institutes or online searches).

To simplify, we start with a dataset (matrix) where samples are already assigned to predefined classes (e.g., two classes). From this labeled data, the objective is typically to estimate the parameters of the probability distribution assumed for each class. A common approach is **Maximum Likelihood Estimation (MLE)**.

The goal is often to build a classifier. If we assume, for example, that the data for each class follows a multivariate Gaussian distribution, we need to estimate the parameters of this distribution for each class. The function often maximized in MLE for Gaussian distributions looks similar to this (representing the likelihood or log-likelihood):

![Likelihood Function Component (Gaussian)](https://github.com/Joncarre/Java-language/blob/master/Ingenier%C3%ADa%20del%20Conocimiento/images/IC3_2.png)

Here, *w* = { *w*<sub>1</sub>, *w*<sub>2</sub> } might represent the set of parameters (like mean vector and covariance matrix) for each class *w<sub>i</sub>* that we need to "learn" (estimate) from the data.

The key parameters estimated from the sample data (*x*) for each class are typically the **mean vector (μ)** and the **covariance matrix (Σ)**. The standard MLE formulas for these are:

![Mean Vector and Covariance Matrix Estimation Formulas](https://github.com/Joncarre/Java-language/blob/master/Ingenier%C3%ADa%20del%20Conocimiento/images/IC3_3.png)

*   *Top Formula:* Sample Mean (μ̂) for a class.
*   *Bottom Formula:* Sample Covariance Matrix (Σ̂) for a class. (*N* is the number of samples in that class).

Once these parameters are estimated for each class, a new, unseen data point can be classified by calculating the probability (or likelihood) that it belongs to each class given the estimated parameters, often using Bayes' theorem, and assigning it to the class with the highest probability.

## Execution Example

In the following example, we start with an initial matrix containing data for **2 classes** and **4 samples** per class (total 8 samples shown, though the description says 4).

Since there are 2 classes, the process involves calculating the mean vector and covariance matrix for *each* class based on its respective samples.

After estimating the parameters, the example likely demonstrates classifying *new* samples (as a test) by determining which class each new sample most probably belongs to, based on the learned distributions.

![Bayesian Classifier Execution Example Output](https://github.com/Joncarre/Java-language/blob/master/Ingenier%C3%ADa%20del%20Conocimiento/images/IC3_1.png)
