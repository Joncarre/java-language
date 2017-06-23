<!--Creado por Jonathan Carrero -->

**Bayes**
==============
----------

**Descripción**

Antes de comenzar, puedes consultar la estimación paramétrica de Bayes en la página web del Instituto Nacional de Estadística www.ine.es o realizando una sencilla búsqueda en Internet. Para no entrar demasiado en términos estadísticos, nosotros vamos a partir desde una matriz en la que ya tenemos dos clases y un determinado número de muestras. Apartir de aquí, el objetivo consiste en calcular la función para estimar por máxima verosimilitud.


![enter image description here](https://github.com/Joncarre/Java-language/blob/master/Ingenier%C3%ADa%20del%20Conocimiento/images/IC3_2.png)

donde *w* = { *w*1, *w*2 } es el vector de parámetros a "aprender" (estimar).

Las ecuaciones que nos ayudan a sacar la media y las matrices de covarianza son las siguiente:

![enter image description here](https://github.com/Joncarre/Java-language/blob/master/Ingenier%C3%ADa%20del%20Conocimiento/images/IC3_3.png)

**Ejemplo de ejecución**

En el siguiente ejemplo observamos una matriz inicial con 2 clases y 4 muestras. Como hay 2 clases, se procede a calcular las 2 matrices de covarianza. Al finalizar y a modo de prueba, se introducen nuentras muestras y se determinan a qué clases pertenece cada una de ellas.

![enter image description here](https://github.com/Joncarre/Java-language/blob/master/Ingenier%C3%ADa%20del%20Conocimiento/images/IC3_1.png)
