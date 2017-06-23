<!--Creado por Jonathan Carrero -->

**El lobo y las ovejas**
==============
----------

**Descripción**

En esta primera versión del proyecto nos encontramos con dos juegos:

- *Tic-Tac-Toe*: este juego es el famoso tres en raya. Hay un tablero de nxn (donde en principio será un 3x3) en el que los jugadores van poniendo fichas y altarnándose los turnos. Un jugador gana si consigue tener una línea de tres de sus símbolos: la línea puede ser horizontal, vertical o diagonal

- *El lobo y las ovejas*: para este juego jugaremos, en principio, en un tablero 8x8 similar al usado para el ajedrez, donde sólo se usarán las casillas negras (ver ilustración más abajo). Las ovejas se sitúan en las casillas negras de un extremo, mientras que el lobo ocupa la esquina opuesta. El juego transcurre por turnos, empezando a mover el jugador que controla al lobo. El lobo puede moverse, en diagonal, 1 casilla en cualquier dirección. Las ovejas pueden moverse, también en diagonal, 1 casilla; pero sólo avanzando hacia el lado opuesto a su posición inicial. El lobo gana si consigue alcanzar el extremo del tablero en el que empezaban las ovejas. Las ovejas ganan si consiguen que el lobo quede inmovilizado. Se supone que el jugador 0 es el que controla el lobo, y el jugador 1 es el que controla la ovejas.

Tanto al Tic-Tac-Toe como a El lobo y las ovejas se podrá jugar con la siguiente convinación de jugadores:

 - console: en este modo es el propio usuario quien realiza las jugadas.
 - random: un jugador totalmente aleatorio realizará las jugadas.
 - smart: un jugador inteligente (gracias sobre todo a una clase que implementa un árbol Minimax) realizará las jugadas.
 
 Esta configuración se debe realizar dentro de Run > Run Configurations...(Eclipse). Y su sintaxis es la siguiente:
 
 *juego jugador1 jugador2*
 
donde [juego] puede ser *ttt* (para Tic-Tac-Toe) o *was* (WolfAndSheep) y [jugador] puede ser *console*, *random* o *smart*. A continuación se muestra un ejemplo donde se juega a WolfAndSheep, el primer jugador es un usuario y el segundo es un jugador inteligente.

IMAGEN

Ya que en esta primera versión el juego tan sólo es por consola, notemos que los movimientos posible ya nos los dan y nosotros sólo debemos decidir dónde mover. A modo de ejemplo veamos el primer movimiento de un lobo en modo console y cómo un jugador smart responde a dicha jugada.

![enter image description here](https://github.com/Joncarre/Java-language/blob/master/Programaci%C3%B3n%20en%20Java/Juegos%20usando%20MVC/images/4_1.png)

También podemos observar un final de partida. En este caso, las ovejas han logrado acorralar al lobo y, por lo tanto, han ganado la partida.

![enter image description here](https://github.com/Joncarre/Java-language/blob/master/Programaci%C3%B3n%20en%20Java/Juegos%20usando%20MVC/images/4_2.png)


