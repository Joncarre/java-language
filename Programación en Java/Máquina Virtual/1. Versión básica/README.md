<!--Creado por Jonathan Carrero -->

**Versión básica**
==============
----------

**Introducción**

Una máquina virtual es un software que simula a un ordenador y que puede ejecutar programas como si fuese un ordenador real. Puedes encontrar más información en http://es.wikipedia.org/wiki/Maquina_virtual. El ordenador simulado puede ejecutar programas como si se tratase de un ordenador real. Este tipo de aplicaciones tiene diferentes usos aunque el más extendido es la “prueba” de sistemas operativos sin tener que cambiar el que utilizan habitualmente. La máquina virtual que se presenta es una máquina de pila, cuyos principales componentes son una *pila de operandos* y una *memoria*. La pila de operandos almacena los datos que se generan durante la ejecución mientras que en la memoria podemos almacenar algunos de esos datos. Es decir, la memoria sería similar a una tabla de variables de un programa.

**Descripción**

La Máquina Virtual está compuesta de dos partes muy simples:

- Una *memoria* capaz de almacenar datos. La unidad mínima de memoria es el entero, es decir en cada celda almacena un entero completo (y no un byte como suele ocurrir en las máquinas reales). La capacidad de la memoria es *ilimitada*, es decir se podrá  escribir en cualquier dirección (≥ 0), hasta que la memoria de la máquina física subyacente “aguante”.

- Una *pila de operandos* en la que se realizan las operaciones. Gran parte de las distin- tas instrucciones bytecode de la máquina virtual trabajan sobre la pila de operandos, cogiendo de ella valores y/o dejando en ella resultados.

El conjunto de instrucciones bytecode que la Máquina Virtual en su primera versión admite son:

- PUSH n: apila en la pila de operandos el entero n.
- LOAD pos: lee de la memoria el valor almacenado en pos y lo apila en la pila de operandos.
- STORE pos: escribe en la posición pos de la memoria el contenido de la cima de la pila de operandos, y lo elimina de ella. 
- ADD, SUB, MUL, DIV: operaciones aritméticas de suma, resta, multiplicación y división. Todas ellas utilizan como operandos la subcima y la cima de la pila. Tanto la cima como la subcima son sustituidas por el resultado de la operación. El primer operando es la subcima (esto es importante para las operaciones no conmutativas).
- OUT: escribe el entero almacenado en la cima de la pila.
- HALT: para la máquina.

**Ejemplo de ejecución**

A continuación mostramos un ejemplo de ejecución de nuestra simulación. Observa que los comandos e instrucciones bytecode no son sensibles a mayúsculas ni minúsculas (puedes escribirlas indistintamente). Eso sí, cuando la aplicación tiene que mostrar una instrucción o un bytecode, lo hará utilizando mayúsculas.

En el momento de mostrar el estado de la máquina, la aplicación muestra la pila y la memoria tras la ejecución de cada bytecode. Si no tienen elementos, se indica <vacia>. En el caso de la memoria eso viene a significar que alguna celda de memoria ha sido escrita en algún momento. Sólo se mostrará el contenido de las posiciones de memoria que están ocupadas.

A continuación aparece un ejemplo de ejecución. El texto en verde representa lo que el usuario de la aplicación introduce por teclado.

![enter image description here](https://github.com/Joncarre/Java-language/blob/master/Programaci%C3%B3n%20en%20Java/M%C3%A1quina%20Virtual/images/1_1.png)

