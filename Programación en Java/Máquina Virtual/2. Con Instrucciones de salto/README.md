<!--Creado por Jonathan Carrero -->

**Versión básica**
==============
----------

**Nuevas instrucciones**

En esta nueva versión el comando BYTECODE permite al usuario introducir en orden las distintas instrucciones que componen un programa bytecode. Cuando el usuario haya terminado de escribir todas las instrucciones, tecleará "END" (que no es una instrucción bytecode).

Por otro lado, las nuevas instrucciones bytecode que se incorporan son las siguientes:

- *Instrucciones de salto condicional*: habrá cuatro instrucciones de este tipo, que son IFEQ N, IFLE N, IFLEQ N y IFNEQ N. Estas instrucciones cogen la subcima -sc- y la cima -c- de la pila y comparan sus valores enteros. En función de qué tipo de condición sea, si se hace verdadera, entonces continua su flujo, pero si la condición es falsa, entonces salta a la instrucción N.

- *Instrucción de salto incondicional*: esta instrucción, cuya sintaxis es GOTO N, provoca un cambio en el contador de programa de la cpu, que pasa a ser N.

**Ejemplo de ejecución**

Veamos un ejemplo en el que un programa bytecode calcula el factorial de 5. El texto en verde representa lo que el usuario introduce por teclado.

![enter image description here](https://github.com/Joncarre/Java-language/blob/master/Programaci%C3%B3n%20en%20Java/M%C3%A1quina%20Virtual/images/2_1.png)
