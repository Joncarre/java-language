<!--Creado por Jonathan Carrero -->

**Compilador para Máquina Virtual**
==============
----------

**Descripción**

En esta versión vamos a permitir que el usuario escriba programas en un lenguaje imperativo simple, de forma que estos programas sean traducidos a programas bytecode que más tarde ejecutará la Máquina Virtual.

Los programas fuentes escritos en un fichero deben cumplir además los siguientes requisitos
sintácticos:

- Cada asignación o instrucción write debe aparecer en una línea.
- No puede haber líneas en blanco en ningún sitio del fichero, ni tampoco al principio ni al final del mismo.

A continuación muestro un programa correcto que calcula en la variable r el factorial de 5 y lo muestra por pantalla.

    x = 5
    r = 1
    while 0 < x
	    r = r * x
	    x = x - 1
    endwhile
    write r
    end
    
El esta nueva versión el conjunto de comandos disponibles es el siguiente:

- HELP: muestra la ayuda correspondiente a los comandos que describiremos a continuación.
- QUIT: cierra la aplicación.
- LOAD FICH: carga el fichero de nombre FICH como programa fuente. No realiza ningún tipo de comprobación sintáctica.
- REPLACEBC N: solicita al usuario una nueva instrucción bytecode y reemplaza la línea N del programa bytecode por la nueva instrucción introducida por el usuario.
- COMPILE: realiza el análisis léxico del programa fuente, generando un nuevo programa parseado y, posteriormente a partir del programa parseado genera un programa bytecode.
- RUN: ejecuta el programa bytecode igual que en la Práctica 2.

**Ejemplo de ejecución**

Ya vimos en la versión anterior que podíamos insertar instrucciones para tener nuestro propio programa bytecode. Aquí, eso mismo se conseguira pero en una segunda fase. Como muestra la siguiente imagen, primero debemos cargar un programa escrito en lenguaje imperativo simple para saber si, apartir de ese programa, es posible transformarlo en un programa bytecode.

![enter image description here](https://github.com/Joncarre/Java-language/blob/master/Programaci%C3%B3n%20en%20Java/M%C3%A1quina%20Virtual/images/3_1.png)

En segundo lugar debemos compilar el programa. En esta fase se analiza que las instrucciones son correctas, es decir que, por ejemplo, no hay saltos a instrucciones que no existen.

![enter image description here](https://github.com/Joncarre/Java-language/blob/master/Programaci%C3%B3n%20en%20Java/M%C3%A1quina%20Virtual/images/3_2.png)

Por último, una vez compilado, ya podemos llevar a cabo la ejecución del programa. En este caso, volvemos a calcular el factorial de 5. El texto en verde representa lo que el usuario de la aplicación introduce por teclado.

![enter image description here](https://github.com/Joncarre/Java-language/blob/master/Programaci%C3%B3n%20en%20Java/M%C3%A1quina%20Virtual/images/3_3.png)

