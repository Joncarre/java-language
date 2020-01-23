<!--Creado por Jonathan Carrero -->

**Modelo de plegado de proteínas Hydrophobic-Polar implementado con algoritmo genético**
==============
--------------

**Descripción**

El modelo de plegamiento de proteínas Hydrophobic-Polar es un modelo altamente simplificado para examinar los pliegues de proteínas en el espacio. Propuesto por primera vez por Ken Dill en 1985, es el tipo más conocido de proteína de red: se deriva de la observación de que las interacciones hidrófobas entre los residuos de aminoácidos son la fuerza impulsora para que las proteínas se plieguen en su estado nativo.

Todos los tipos de aminoácidos se clasifican como hidrofóbicos (H) o polares (P), y el plegamiento de una secuencia de proteínas se define como una caminata auto evitada en una red 2D o 3D. El modelo HP imita el efecto hidrofóbico asignando un peso negativo (favorable) a las interacciones entre los residuos H adyacentes, no unidos covalentemente. Se supone que las proteínas que tienen energía mínima están en su estado nativo.

El modelo HP se puede expresar en dos y tres dimensiones, generalmente con redes cuadradas , aunque también se han utilizado redes triangulares.

A pesar de que el modelo HP abstrae muchos de los detalles del plegamiento de proteínas, sigue siendo un problema NP-difícil en las redes 2D y 3D.
