<!--Creado por Jonathan Carrero -->

**Algoritmo A Estrella**
==============
----------

**Descripción**

Se trata de implementar una versión reducida del algoritmo A estrella. El objetivo consiste en simular la navegación de un vehículo (terrestre, aéreo o marítimo) en un espacio determinado hacia la consecución de un objetivo (target). ¿Cómo se logra esto? Veamos un ejemplo de implementación y qué habría que hacer en el siguiente paso:

Consideremos la figura mostrada, en la que la posición de inicio es (1,1). El único nodo sucesor en este caso es (1,2), no existe otra posibilidad de elección. Esto ocurre hasta que el vehículo alcanza la posición (2,5).

IMAGEN


Llegados a la posición (2,5), existen dos posibles sucesores de (2,5), que son (3,4) y (3,5). Evaluemos las funciones f(n) para cada uno de ellos suponiendo que las dimensiones de los cuadrados son 1x1.

**a) Nodo (3,4):**
- g(n) = dist((1,1),(1,2)) + dist((1,2),(1,3)) + dist((1,3),(1,4)) + dist((1,4),(2,5)) + dist((2,5),(3,4)) = 1 + 1 + 1 + 1.41 + 1.41= 5.82
- h(n) = dist((3,4),(5,2)) = sqrt((5-3)^2+(2-4) ^2) = sqrt(8) = 2.82
- f(n) = 5.82 + 2.82 = 8.64

**b) Nodo (3,5):**
- g(n) = dist((1,1),(1,2)) + dist((1,2),(1,3)) + dist((1,3),(1,4)) + dist((1,4),(2,5)) + dist((2,5),(3,5)) = 1 + 1 + 1 + 1.41 + 1= 5.41
- h(n) = dist((3,5),(5,2)) = sqrt((5-3)^2+(2-5) ^2) = sqrt(13) = 3.61
- f(n) = 5.41 + 3.61 = 9.02

En el caso anterior, el nodo elegido sería el (3,4) como el sucesor del (2,4).

**Ejemplo de ejecución**

Al comenzar debemos especificar las dimensiones que tendrá nuestra matriz, puntos de inicio y fin y si queremos que haya obstáculos a lo largo del camino.

IMAGEN

Después, tan sólo queda ver cómo el algoritmo realiza las expansiones para encontrar el camino mínimo hasta el punto de aterrizaje. Por cada expansión, el programa imprime una vez el estado actual de la matriz y sus elementos, finalizando y mostrando el recorrido hecho.

IMAGEN
