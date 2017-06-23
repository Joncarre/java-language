//
//  Created by Jonathan Carrero.
//  Copyright (c) Jonathan Carrero. All rights reserved.
//

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.StringTokenizer;
import java.math.*;
import java.text.DecimalFormat;

public class Main {

	private static final int TAM = 100;
	private static final int NUMCLASES = 2;
	
	// Valores iniciales de los centros
	private static final double V11 = 4.6;
	private static final double V12 = 3.0;
	private static final double V13 = 4.0;
	private static final double V14 = 0.0;
	private static final double V21 = 6.8;
	private static final double V22 = 3.4;
	private static final double V23 = 4.6;
	private static final double V24 = 0.7;
	
	// Valor de Epsilon
	private static final double EPSILON = 0.01;
	

	/**
	 * Método main
	 * @param args
	 */
    public static void main(String[] args) {
        ListaMuestras lista = new ListaMuestras(TAM);
        double matrizPertenencia[][] = new double[NUMCLASES][TAM];
        double v1Inicial[] = {V11,V12,V13,V14};
		double v2Inicial[] = {V21,V22,V23,V24};
		double v1Nuevo[] = new double[4];
		double v2Nuevo[] = new double[4];
        boolean seguir;
        DecimalFormat decimales = new DecimalFormat("#0.000");
		
        leerMuestras(lista);
        
        System.out.println("----------[Iteración 1]----------");
        System.out.println();
        System.out.println("Los centros iniciales son:");
        System.out.println("V1(t): " + V11 +", "+ V12 +", "+ V13 +", "+ V14);
        System.out.println("V2(t): " + V21 +", "+ V22 +", "+ V23 +", "+ V24);
        System.out.println();
        calcularPertenencia(lista, matrizPertenencia,v1Inicial,v2Inicial);
        for (int i=0; i<NUMCLASES; i++){
        	for (int j=0; j<TAM; j++){
        		System.out.print(decimales.format(matrizPertenencia[i][j]) + " ");	
        	}
        	System.out.println();
        }
        calcularCentros(lista, matrizPertenencia, v1Nuevo, v2Nuevo);
        System.out.println();
        System.out.println("Los nuevos centros son:");
        System.out.println("V1(t+1): " + v1Nuevo[0] +", "+ v1Nuevo[1] +", "+ v1Nuevo[2] +", "+ v1Nuevo[3]);
        System.out.println("V2(t+1): " + v2Nuevo[0] +", "+ v2Nuevo[1] +", "+ v2Nuevo[2] +", "+ v2Nuevo[3]);
        System.out.println();
        seguir = comprobarEpsilon(v1Inicial,v2Inicial,v1Nuevo,v2Nuevo);
        // Poner los nuevos centros como iniciales y hacer un bucle hasta que seguir sea false
        for (int iter=2;iter<=4;iter++){
        	for (int i = 0; i <= 3; i++){
        		v1Inicial[i] = v1Nuevo[i];
        	}
        	System.out.println("----------[Iteración " + iter + "]----------");
            System.out.println();
        	calcularPertenencia(lista, matrizPertenencia,v1Inicial,v2Inicial);
            for (int i=0; i<NUMCLASES; i++){
            	for (int j=0; j<TAM; j++){
            		System.out.print(decimales.format(matrizPertenencia[i][j]) + " ");	
            	}
            	System.out.println();
            }
            System.out.println();
            calcularCentros(lista, matrizPertenencia, v1Nuevo, v2Nuevo);
            System.out.println("Los nuevos centros son:");
            System.out.println("V1(t+"+iter+"): " + v1Nuevo[0] +", "+ v1Nuevo[1] +", "+ v1Nuevo[2] +", "+ v1Nuevo[3]);
            System.out.println("V2(t+"+iter+"): " + v2Nuevo[0] +", "+ v2Nuevo[1] +", "+ v2Nuevo[2] +", "+ v2Nuevo[3]);
            System.out.println();
            seguir = comprobarEpsilon(v1Inicial,v2Inicial,v1Nuevo,v2Nuevo);
        }
    }

    /**
     * Se leen las muestras desde el fichero indicado en la ruta
     * @param lista
     */
	private static void leerMuestras(ListaMuestras lista) {
		File archivo = new File ("/home/bobby/Documentos/UCM/4º Año/IC/IC_P3_KMedias/IC_P3_KMedias/src/Iris2Clases.txt");
        String linea = "", delimitador = ",", atributo = "";
        FileReader fr = null;
        BufferedReader br = null; 
        Muestra muestra;
	    
	    try {
	    	fr = new FileReader(archivo);
	    	br = new BufferedReader(fr);
	        
	        linea = br.readLine();
	        StringTokenizer st = new StringTokenizer(linea,delimitador);
	        while (st.hasMoreTokens()){
	        	muestra = new Muestra();
                for(int i=0; i<=3; i++){
                	muestra.setValor(Double.parseDouble((String)st.nextToken()));
                }
                muestra.setTipo(st.nextToken().toString());
                //Añadir esta muestra a la listaMuestras
                lista.anadirValor(muestra);
                linea = br.readLine();
                if (linea != null){
                	st = new StringTokenizer(linea,delimitador);
                }
	        }
			
		} catch (FileNotFoundException e) {
			System.out.println("Fichero no encontrado");
            System.out.println(e.getMessage());
		}
	    catch (Exception e) {
            // Operaciones en caso de error general
            System.out.println("Error de lectura del fichero");
            System.out.println(e.getMessage());
        } 
    }
   	
	/**
	 * Se lleva a cabo el cálculo de la pertenencia
	 * @param lista
	 * @param matrizPertenencia
	 * @param v1Inicial
	 * @param v2Inicial
	 */
   	private static void calcularPertenencia(ListaMuestras lista, double matrizPertenencia[][], double[] v1Inicial, double[] v2Inicial) {
		Muestra muestra;
		double valor = 0, u1=0, u2=0;
		int i = 0, fila, columna;
		
		for (columna = 0; columna < TAM; columna++){
			muestra = lista.getMuestra(i);
			u1 = calcularD(muestra,v1Inicial,v2Inicial);
			matrizPertenencia[0][columna] = u1;
			i++;
		}
		for (columna = 0; columna < TAM; columna++){
		   	u2 = 1 - matrizPertenencia[0][columna];
		   	matrizPertenencia[1][columna] = u2;
		}
		System.out.println("Matriz de pertenencia calculada:");
	}
   	
   	/**
   	 * Cálculo de la distancia
   	 * @param muestra
   	 * @param v1Inicial
   	 * @param v2Inicial
   	 * @return
   	 */
	private static double calcularD(Muestra muestra, double[] v1Inicial, double[] v2Inicial) {
		double resultado = 0;
		double valor1 = muestra.getValor(0);
		double valor2 = muestra.getValor(1);
		double valor3 = muestra.getValor(2);
		double valor4 = muestra.getValor(3);
		double d11, d21;
		
		d11 = Math.pow((valor1 - v1Inicial[0]), 2) + Math.pow((valor2 - v1Inicial[1]), 2) + Math.pow((valor3 - v1Inicial[2]), 2) + Math.pow((valor4 - v1Inicial[3]), 2);
		d21 = Math.pow((valor1 - v2Inicial[0]), 2) + Math.pow((valor2 - v2Inicial[1]), 2) + Math.pow((valor3 - v2Inicial[2]), 2) + Math.pow((valor4 - v2Inicial[3]), 2);
		resultado = ( 1/(double)d11 / ((1/(double)d11)+(1/(double)d21)) ) ;
		
		return resultado;
	}
	
	/**
	 * Cálculo de los centros
	 * @param lista
	 * @param matrizPertenencia
	 * @param v1
	 * @param v2
	 */
   	private static void calcularCentros(ListaMuestras lista, double[][] matrizPertenencia, double v1[], double v2[]) {
		double aux = 0, denominador = 0;
		double[][] muestrasPorMatrizPertenencia = new double[4][TAM];
		double numerador[] = new double[4];
		Muestra muestra;
		
		// Cálculo los dos centros
		for (int numCentros = 0; numCentros < NUMCLASES; numCentros++){
			// Numerador de la fórmula de cálculo de un centro sin sumar
			for (int columna = 0; columna < TAM; columna++){
				muestra = lista.getMuestra(columna);
				for (int i = 0; i<=3; i++)
					muestrasPorMatrizPertenencia[i][columna] = Math.pow(matrizPertenencia[numCentros][columna],2) * muestra.getValor(i);
			}
			// Sumar valores del numerador
			for (int fila = 0; fila <= 3; fila++){
				for (int columna = 0; columna < TAM; columna++){
					aux += muestrasPorMatrizPertenencia[fila][columna];
				}
				numerador[fila] = aux;
				aux = 0;
			}
			// Calcular denominador
			for (int columna = 0; columna < TAM; columna++){
				denominador += Math.pow(matrizPertenencia[numCentros][columna],2);	
			}
			if (numCentros == 0){
				// Calcular V1
				for (int i = 0; i <= 3; i++)
					v1[i] = numerador[i]/denominador;
			}
			else {
				// Calcular V2
				for (int i = 0; i <= 3; i++)
					v2[i] = numerador[i]/denominador;
			}
		}		
	}
   	
   	/**
   	 * Comprobación para saber si el algoritmo debe detenerse
   	 * @param v1
   	 * @param v2
   	 * @param v1Nuevo
   	 * @param v2Nuevo
   	 * @return
   	 */
   	private static boolean comprobarEpsilon(double[] v1, double[] v2, double[] v1Nuevo, double[] v2Nuevo) {
		double valor1=0, valor2=0;
		boolean seguir;
		
		for (int i=0; i<=3; i++)
			valor1 += Math.pow((v1[i] - v1Nuevo[i]), 2);

		for (int i=0; i<=3; i++)
			valor2 += Math.pow((v2[i] - v2Nuevo[i]), 2);
        System.out.println("Cálculo Epsilon:");
		System.out.println("||V1(t) - V1(t+1)|| = " + valor1); 
		System.out.println("||V2(t) - V2(t+1)|| = " + valor2);
        System.out.println();
		
		if ((valor1 < EPSILON) && (valor2 < EPSILON))
			seguir = false;
		else 
			seguir = true;
		
		return seguir;
	}		
}

