<!--Creado por Jonathan Carrero -->

**Hilos y concurrencia**
==============
----------

**Descripción**

Los cambios principales, aunque están cómo se lleva a cabo el flujo de ejecución (y por lo tanto en el código), vamos a ver qué nuevos componentes tenemos en la ventana:

- Un icono para ilustrar que está pensando el jugador automático (smart). El fondo debe aparecer en amarillo cuando está pensando.

- Selector del número de hilos que puede utilizar el jugador automático. Ahora este jugador puede utilizar múltiples hilos en paralelo para buscar jugadas mucho más rápido que la versión no-concurrente.

- Icono para ilustrar el selector de tiempo máximo de búsqueda de jugada.

- Selector del tiempo máximo de búsqueda de jugada, en milisegundos. La búsqueda de jugada del jugador automático nunca deberá exceder de este valor.

- Botón para cancelar el proceso de búsqueda de jugada. Este botón debe estar activo (enabled) solo cuando el jugador automático está buscando una jugada. Cuando el usuario pulse este botón se debe parar la ejecución del hilo de ejecución de la búsqueda.

La nueva versión quedaría de la siguiente forma (puede observarse cómo el jugador smart está pensando para realizar su siguiente jugada):

![enter image description here](https://github.com/Joncarre/Java-language/blob/master/Programaci%C3%B3n%20en%20Java/Juegos%20usando%20MVC/images/6_1.png)

Cabe destacar que cuando un jugador no tiene el turno no puede realizar movimientos automáticos como random o smart. Es por eso que creo conveniente que dichos botones se deshabiliten mientras no es su turno. Este sería el resultado:

![enter image description here](https://github.com/Joncarre/Java-language/blob/master/Programaci%C3%B3n%20en%20Java/Juegos%20usando%20MVC/images/6_2.png)
