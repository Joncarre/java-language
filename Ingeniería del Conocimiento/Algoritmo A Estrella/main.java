//
//  Created by Jonathan Carrero.
//  Copyright (c) Jonathan Carrero. All rights reserved.
//

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Scanner;

public class main {
	
	/**
	 * Función main que controla el flujo del programa
	 * @param args
	 */
	public static void main(String[] args) {
		Nodo nodo;
		Nodo resultado[];
		boolean posibleExpansion, llegadoAMeta = false;
		int op, columnas, filas;
		Scanner s = new Scanner(System.in);
		
		System.out.println(bienvenida());		
		
		do{
			System.out.println("  1 - Configurar trayecto de la Endurance");
			System.out.println("  0 - Abortar misión");
			op = s.nextInt();
			if(op == 1){
				System.out.println("-----[Límites del trayecto espacial]-----");
				System.out.print("Límite Y: ");
				filas = s.nextInt();
				System.out.print("Límite X: ");
				columnas = s.nextInt();
				if(filas < 2 || columnas < 2){
		            System.err.println("Las dimensiones del espacio son incorrectas");
		            System.exit(1);
				}
				Tablero tablero = new Tablero(filas,columnas);
				
				inicializacion(tablero);
				nodo = tablero.getNodoInicial();
				posibleExpansion = tablero.expansion(nodo.getFila(),nodo.getColumna(),tablero);
				while (posibleExpansion && !llegadoAMeta){
					tablero.sacarDeAbierta();
					llegadoAMeta = tablero.metaEnCerrada();
					nodo = tablero.siguienteNodoAExpandir();
					posibleExpansion = tablero.expansion(nodo.getFila(),nodo.getColumna(),tablero);
				}
				try {
					Thread.sleep(1000);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			
				resultado = tablero.resultado();
				if (!posibleExpansion || !llegadoAMeta){
					System.out.println("No es posible realizar al acoplamiento");
				}
				else{
					for (int i = tablero.getContadorResultado()-1; i >= 0; i--){
						if (resultado[i].getFichaNodo() != Ficha.ACOPLAMIENTO)
							resultado[i].setFichaNodo(Ficha.FLECHA);
						System.out.println();
						System.out.println(tablero.toString());
						try {
							Thread.sleep(1000);
						} catch (InterruptedException e) {
							e.printStackTrace();
						}
					}
					System.out.print("\n Resultado: Despegue > ");
					for (int i = tablero.getContadorResultado()-1; i >= 0; i--){
						System.out.print("(" + (resultado[i].getFila()+1) + ", " + 
									(resultado[i].getColumna()+1) + ") > ");
					}
					System.out.println("Acoplamiento");
					System.out.println();
				}
				llegadoAMeta = false;
				op = s.nextInt();	
			}else if(op == 0){
				System.out.println("Abortando misión...");
			}else
				System.out.println("Por favor, introduce una opción correcta");	
		}while(op != 0);
	}
	
	/**
	 * Inicializa el tablero
	 * @param tablero
	 */
	public static void inicializacion(Tablero tablero){
		coordenadasDespegue(tablero);
		coordenadasAcoplamiento(tablero);
		obstaculos(tablero);
	}
	
	/**
	 * Establece todos los obstáculos que se quieran configurar
	 * @param tablero
	 * @return
	 */
	public static Tablero obstaculos(Tablero tablero){
		Scanner s = new Scanner(System.in);
		int fila, columna, op;

		do{
			System.out.println("¿Quieres poner elementos en el trayecto?");
			System.out.println("  1 - Asteroides");
			System.out.println("  0 - No");
			op = s.nextInt();

			if(op == 1){
				System.out.println("-----[Coordenadas para el obstáculo]-----");
				System.out.print("Eje Y: ");
				fila = s.nextInt();
				System.out.print("Eje X: ");
				columna = s.nextInt();
				if(op == 1){
					tablero.cambiarFichaNodo(fila-1, columna-1, Ficha.ASTEROIDE);
					tablero.getNodo(fila - 1, columna - 1).setTipoLista(TipoLista.CERRRADA);
				}
			}else if(op == 0){
				System.out.println("¡Suerte en tu trayecto!");
			}else{
				System.out.println("Por favor, introduce una opción correcta");
			}
		}while(op != 0);

		return tablero;
	}
	
	/**
	 * Establece las coordenadas de despegue
	 * @param tablero
	 * @return
	 */
	public static Tablero coordenadasDespegue(Tablero tablero){
		Scanner s = new Scanner(System.in);
		int fila, columna;
		System.out.println("-----[Coordenadas para el despegue]-----");
		System.out.print("Eje Y: ");
		fila = s.nextInt();
		System.out.print("Eje X: ");
		columna = s.nextInt();
		if(fila < 1 || fila > tablero.getAlto() || columna < 1 || columna > tablero.getAncho()){
            System.err.println("Las coordenadas para el despegue no son correctas");
            System.exit(1);
		}
		tablero.fijarNodoInicial(fila - 1, columna - 1);
		return tablero;
	}
	
	/**
	 * Establece las coordenadas de acoplamiento
	 * @param tablero
	 * @return
	 */
	public static Tablero coordenadasAcoplamiento(Tablero tablero){
		Scanner s = new Scanner(System.in);
		int fila, columna;
		System.out.println("-----[Coordenadas para el acoplamiento]-----");
		System.out.print("Eje Y: ");
		fila = s.nextInt();
		System.out.print("Eje X: ");
		columna = s.nextInt();
		if(fila < 1 || fila > tablero.getAlto() || columna < 1 || columna > tablero.getAncho()){
            System.err.println("Las coordenadas para el acoplamiento no son correctas");
            System.exit(1);
		}else if(tablero.getNodo(fila - 1, columna - 1).getFichaNodo() == Ficha.DESPEGUE){
            System.err.println("Las coordenadas de despegue y acoplamiento deben ser distintas");
            System.exit(1);
		}
		tablero.fijarNodoMeta(fila - 1, columna - 1);
		return tablero;
	}
	
	/**
	 * Muestra un mensaje de bienvenida y la leyenda
	 * @return
	 */
	public static String bienvenida(){
		String message = "Welcome to NASA \n";
		return message;
	}
}
