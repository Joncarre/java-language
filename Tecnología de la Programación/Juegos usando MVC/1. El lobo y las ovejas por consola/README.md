<!--Creado por Jonathan Carrero -->

**El lobo y las ovejas**
==============
----------

**Descripción**

En esta primera versión del proyecto nos encontramos con dos juegos:

- *Tic-Tac-Toe*: este juego es el famoso tres en raya. Hay un tablero de nxn (donde en principio será un 3x3) en el que los jugadores van poniendo fichas y altarnándose los turnos. Un jugador gana si consigue tener una línea de tres de sus símbolos: la línea puede ser horizontal, vertical o diagonal

- *El lobo y las ovejas*: para este juego jugaremos, en principio, en un tablero 8x8 similar al usado para el ajedrez, donde sólo se usarán las casillas negras (ver ilustración más abajo). Las ovejas se sitúan en las casillas negras de un extremo, mientras que el lobo ocupa la esquina opuesta. El juego transcurre por turnos, empezando a mover el jugador que controla al lobo. El lobo puede moverse, en diagonal, 1 casilla en cualquier dirección. Las ovejas pueden moverse, también en diagonal, 1 casilla; pero sólo avanzando hacia el lado opuesto a su posición inicial. El lobo gana si consigue alcanzar el extremo del tablero en el que empezaban las ovejas. Las ovejas ganan si consiguen que el lobo quede inmovilizado. Se supone que el jugador 0 es el que controla el lobo, y el jugador 1 es el que controla la ovejas.

Tanto al Tic-Tac-Toe como a El lobo y las ovejas se podrá jugar con la siguiente combinación de jugadores:

 - console: en este modo es el propio usuario quien realiza las jugadas.
 - random: un jugador totalmente aleatorio realizará las jugadas.
 - smart: un jugador inteligente (gracias sobre todo a una clase que implementa un árbol Minimax) realizará las jugadas.
 
 Esta configuración se debe realizar dentro de Run > Run Configurations...(Eclipse). Y su sintaxis es la siguiente:
 
 *juego jugador1 jugador2*
 
donde [juego] puede ser *ttt* (para Tic-Tac-Toe) o *was* (WolfAndSheep) y [jugador] puede ser *console*, *random* o *smart*. A continuación se muestra un ejemplo donde se juega a WolfAndSheep, el primer jugador es un usuario y el segundo es un jugador inteligente.

![enter image description here](https://github.com/Joncarre/Java-language/blob/master/Programaci%C3%B3n%20en%20Java/Juegos%20usando%20MVC/images/4_3.png)

Ya que en esta primera versión el juego tan sólo es por consola, notemos que los movimientos posible ya nos los dan y nosotros sólo debemos decidir dónde mover. A modo de ejemplo veamos el primer movimiento de un lobo en modo console y cómo un jugador smart responde a dicha jugada.

![enter image description here](https://github.com/Joncarre/Java-language/blob/master/Programaci%C3%B3n%20en%20Java/Juegos%20usando%20MVC/images/4_1.png)

También podemos observar un final de partida. En este caso, las ovejas han logrado acorralar al lobo y, por lo tanto, han ganado la partida.

![enter image description here](https://github.com/Joncarre/Java-language/blob/master/Programaci%C3%B3n%20en%20Java/Juegos%20usando%20MVC/images/4_2.png)

**JUnit**

Las pruebas unitarias son una forma de verificar que los métodos de las clases funcionan como se espera. Se llaman unitarias porque prueban las unidades más pequeñas que tiene sentido probar en aislamiento unas de otras (en oposición a las pruebas de integración, que verifican que todo funciona una vez juntado). Usaremos la librería JUnit, que cuenta con soporte tanto Maven como Eclipse, para escribir y ejecutar pruebas unitarias en nuestras prácticas.

Puedes encontrar más información para instalar y realizar pruebas JUnit en el siguiente enlace: http://junit.org/junit4/

Las pruebas llevadas a cabo son las siguientes:

- Un lobo rodeado resulta en victoria de las ovejas.
- Un lobo en una casilla con y = 0 resulta en victoria del lobo.
- Un lobo en su posición inicial sólo tiene 1 acción válida; y tras llevarla a cabo, en su siguiente turno, tiene 4 acciones válidas.
- Una oveja en su posición inicial tiene 2 acciones válidas; y si está en un lateral, tiene 2 acciones válidas.
- Proporcionar menos de 3 argumentos ó demasiados argumentos (más jugadores de los que acepta el juego) resulta en un error.
- Proporcionar un juego inválido como primer argumento resulta en un error.

A modo de ejemplo, veamos el código correspondiente a la segunda prueba (lobo en casilla y = 0 resulta victorioso):

![enter image description here](https://github.com/Joncarre/Java-language/blob/master/Programaci%C3%B3n%20en%20Java/Juegos%20usando%20MVC/images/4_4.png)


