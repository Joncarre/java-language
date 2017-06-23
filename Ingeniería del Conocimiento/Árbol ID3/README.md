<!--Creado por Jonathan Carrero -->

**Árbol ID3**
==============
----------

**Descripción**

Se trata de implementar una versión reducida y preliminar del algoritmo ID3. Resulta conocido que este algoritmo presenta recursividad a medida que se avanza en su ejecución, pero aquí tan sólo presento una versión en la que se realiza una sola iteración (se toma la primera decisión respecto al árbol).

Existe la herramienta WEKA, que puede servir de ayuda para su implementación http://www.cs.waikato.ac.nz/ml/weka/, con un manual ilustrativo que se acompaña (Curso de Doctorado, Orallo y Ramírez, Universidad Politécnica de Valencia) y puede obtenerse en http://users.dsic.upv.es/~cferri/weka/CursDoctorat-weka.pdf, donde puede comprobarse la elaboración del árbol de decisión. En la sección 2 describe los árboles de decisión (C4.5), que en WEKA se identifican como J48.

**Ejemplo de ejecución**

Como entrada tenemos dos ficheros txt: *AtributosJuego.txt* que contiene los 4 atributos sobre los que se hará la evaluación 
y *Juego.txt* con los valores de los atributos.

Como vemos a continuación, el atributo con mejor mérito es TiempoExterior, luego saldrán 3 ramas del árbol con los valores: soleado, nublado y lluvioso, y cada una de estas ramas tendrá una subtabla de la tabla que se tuvo en la primera iteración.

![enter image description here](https://github.com/Joncarre/Java-language/blob/master/Ingenier%C3%ADa%20del%20Conocimiento/images/IC2_1.png)
