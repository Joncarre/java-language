<!--Creado por Jonathan Carrero -->

**K-Medias**
==============
----------

**Descripción**

El objetivo básicamente en la partición de un conjunto de n observaciones en k grupos en el que cada observación pertenece al grupo cuyo valor medio es más cercano. Los pasos que realiza el algoritmo son los siguientes:

- **1.** Se leen de fichero los datos para extraer el número de muestras *n* a utilizar y el número de clases *c*.
- **2.** Se inicializan los centro de las clases *v*i y las probabilidades i = 1 hasta *c* y j = 1 hasta *n*.
- **3.** Se normalizan las probabilidades.
- **4.** Obtener los *v*i de acuerdo con la ecuación superior de la siguiente imagen.
- **5.** Recalcular por medio de la ecuación inferior de la siguiente imagen.

![enter image description here](https://github.com/Joncarre/Java-language/blob/master/Ingenier%C3%ADa%20del%20Conocimiento/images/IC4_2.png)

- **6.** Repetir los pasos **3** y **5** hasta que *v*i no cambie o el cambio sea pequeño. El criterio de finalización corresponde a un cierto valor epsilon tal y como se muestra a continuación. Cuando el valor de la iteración i-ésima + 1 menos el valor de la iteración i-ésima sean (para todos los *v*i) menor que epsilon, entonces el algoritmo se detiene.

![enter image description here](https://github.com/Joncarre/Java-language/blob/master/Ingenier%C3%ADa%20del%20Conocimiento/images/IC4_3.png)


**Ejemplo de ejecución**

En la siguiente imagen observamos las dos primeras iteraciones del algoritmo. En ella se puede observar los valores de los centros iniciales, la matriz de pertenencia calculada (que aparece incompleta ya que tiene mxn dimensión donde m es el número de clases y n es el número de muestras), el recálculo de los centros y la comprobación del valor epsilon. En este ejemplo concreto, aunque no aparece en la imagen, el algoritmo detiene su ejecución en la iteración 4.

![enter image description here](https://github.com/Joncarre/Java-language/blob/master/Ingenier%C3%ADa%20del%20Conocimiento/images/IC4_1.png)
