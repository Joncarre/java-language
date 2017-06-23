<!--Creado por Jonathan Carrero -->

**Interfaz gráfica**
==============
----------

**Descripción**

Antes de comenzar cabe destacar que el modo por consola se sigue manteniendo en esta versión, pero se añade un nuevo modo: el modo gui. Es por esto que se han realizado pequeñas modificaciones en cuanto a la entrada de argumentos para la configuración. La nueva sintaxis es:

*game mode jugador1 jugador2*

donde:

- game: ttt (para *Tres-en-Raya*) ó was (para *Wolf and Sheep*).
- mode: gui (para usar Swing) ó console (para usar la interfaz de consola).
- jugador: manual (para un jugador manual player), random (para uno aleatorio) ó smart (para uno inteligente).

AVISO: el significado de console varía con respecto al usado en la versión anterior. Anteriormente, representaba un jugador por consola; y ahora para indicar lo mismo usaremos *manual* (en modo consola). Ahora, console sólo se refiere al “modo consola”.

A modo de ejemplo veamos cómo sería una configuración donde se juega al Tic-Tac-Toe en el modo gráfico cuyo primer jugador es un usuario y el segundo jugador es un jugador aleatorio.

![enter image description here](https://github.com/Joncarre/Java-language/blob/master/Programaci%C3%B3n%20en%20Java/Juegos%20usando%20MVC/images/5_3.png)

**Implementación**

No creo que tenga demasiado sentido meterme a explicar en profundidad cada una de las clases y qué papel juegan en el Modelo-Vista-Controlador. Tan sólo mencionar que el Modelo se encuentra repartido entre unas pocas clases, siendo (de lejos) la más importante la clase *GameTable*, dentro del paquete *mvc*. Por otro lado, el controllador podemos encontrarlo en la interfaz *GameController* y las dos clases que implementan dicha interfaz: *GUIController* y *ConsoleController*. Por último, la mayor parte de la vista es manejada por *GUIView*, una clase abstracta de la que heredan cinco principales componentes que son los que juntos forman toda la vista: *RectBoardView* (el tablero), *MessageViewer* (la zona de mensajes a la derecha), *PlayersInfoViewer* (la zona para elegir el color de cada jugador), *ControlPanel* (la zona superior donde reiniciar, acabar una partida, hacer un movimiento smart, etc) y *GameContainer* (que como su propio nombre indica es el contenedor de todo lo anterior). Una imagen vale más que mil palabras:

![enter image description here](https://github.com/Joncarre/Java-language/blob/master/Programaci%C3%B3n%20en%20Java/Juegos%20usando%20MVC/images/5_4.png)

Quizá con un pequeño dibujito imitando un sencillo Diagrama de Clases se ve mejor cómo funciona la herencia en esta nueva versión:

![enter image description here](https://github.com/Joncarre/Java-language/blob/master/Programaci%C3%B3n%20en%20Java/Juegos%20usando%20MVC/images/5_5.png)

**Ejemplo de ejecución**

Así es como se vería una partida inicial de Wolf and Sheep:

![enter image description here](https://github.com/Joncarre/Java-language/blob/master/Programaci%C3%B3n%20en%20Java/Juegos%20usando%20MVC/images/5_1.png)

Y este sería el resultado al finalizar una partida de Tic-Tac-Toe:

![enter image description here](https://github.com/Joncarre/Java-language/blob/master/Programaci%C3%B3n%20en%20Java/Juegos%20usando%20MVC/images/5_2.png)

