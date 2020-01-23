<!--Creado por Jonathan Carrero -->

**Modelo de plegado de proteínas Hydrophobic-Polar implementado con algoritmo genético**
==============
--------------

**Descripción**

Como extensión al proyecto básico que de manera recursiva trataba de encontrar el plegado más eficiente bajo el modelo Hydrophobic-Polar, esta nueva implementación pretende mezclar las principales ideas que están detrás del funcionamiento de los algoritmos genéticos con el desplazamiento de individuos (algoritmos PSO). El proyecto consta de cinco clases. A continuación podemos ver un pequeño resumen de qué es lo que hace cada una de ellas.

*Main*: es la clase que crea un objetivo de tipo Engine, el cual se encargará de llevar a cabo la ejecución del algoritmo. Es una buena práctica que todo programa tenga como entrada su correspondiente clase Main pero que esta no esté sobrecargada de métodos.

*Engine*: se encarga de crear una población e imprimirla cuando sea necesario (e.d. al finalizar cada evolución de la población).

*Population*: esta clase es la que realiza el mayor trabajo durante la población, llevando a cabo la fase principal: el movimiento. El movimiento consiste en desplazar a los individuos a través de la matriz hacia el supuesto Fittest de la población. Este movimiento depende de parámetros como: temperatura de los individuos (aminoácidos), distancia hasta el Fittest, valor actual del propio fitness, etc.

*Individual*: contiene los atributos necesarios de todo individuo (como por ejemplo las coordenadas en las que se encuentra). También posee sus respectivos setters y getters para modificar/acceder a dichos atributos.

*Location*: objetivo en el que se define las coordenadas que tienen los individuos para identificar su localización. 

