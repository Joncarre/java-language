//
//  Created by Jonathan Carrero.
//  Copyright (c) Jonathan Carrero. All rights reserved.
//

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.text.DecimalFormat;
import java.util.StringTokenizer;
import java.util.Vector;

public class main {

	private static final int TAM = 100;
	private static final int NUMCLASES = 2;
	private static final int DIMENSION = 4;
	
	static double m1[] = new double[DIMENSION];
	static double m2[] = new double[DIMENSION];
	static double c1[] = new double[DIMENSION*DIMENSION];
	static double c2[] = new double[DIMENSION*DIMENSION];
	static double inversaC1[][] = new double[DIMENSION][DIMENSION];
	static double inversaC2[][] = new double[DIMENSION][DIMENSION];
	static double ejemplo[] = new double[DIMENSION];
	static double dC1 = 0;
	static double dC2 = 0;
	
	/**
	 * Método main
	 * @param args
	 */
	public static void main(String[] args) {
		ListaMuestras lista = new ListaMuestras(TAM);
		DecimalFormat decimales = new DecimalFormat("#0.00000");
		
		// Leer fichero
        leerMuestras(lista);
        
        // Valores medios
        calcularVectoresMedia(lista);
        System.out.println("Los vectores media son:");
        System.out.print("m1:(");
        for (int i=0; i<DIMENSION; i++){
        	if (i==DIMENSION-1)
        		System.out.println(decimales.format(m1[i]) + ")");
        	else 
        		System.out.print(decimales.format(m1[i]) +" ");

        }
        System.out.print("m2:(");
        for (int i=0; i<DIMENSION; i++){
        	if (i==DIMENSION-1)
        		System.out.println(decimales.format(m2[i]) + ")");
        	else 
        		System.out.print(decimales.format(m2[i]) +" ");	
        }
        System.out.println();
        
        // Covarianzas
        calcularMatricesCovarianza(lista);
        System.out.println("Las matrices de covarianza son:");
        System.out.println("Matriz c1: ");
        for (int i=0; i<DIMENSION*DIMENSION; i++){
        	if ((i == 4) || (i == 8) || (i == 12))
        		System.out.println();
        	if (i==DIMENSION*DIMENSION-1)
        		System.out.print(decimales.format(c1[i]));
        	else
       	 		System.out.print(decimales.format(c1[i]) +" ");	
        }
        System.out.println();
        System.out.println("Matriz c2: ");
        for (int i=0; i<DIMENSION*DIMENSION; i++){
        	if ((i == 4) || (i == 8) || (i == 12))
        		System.out.println();
        	if (i==DIMENSION*DIMENSION-1)
        		System.out.print(decimales.format(c2[i]));
        	else
       	 		System.out.print(decimales.format(c2[i]) +" ");	
        }
        System.out.println();
        System.out.println();
        
        // Calcular inversa de las covarianzas
        inversaC1 = matrizInversa(transformarCovarianza(c1));
        inversaC2 = matrizInversa(transformarCovarianza(c2));
         
        // Calcular distancias leyendo antes los ficheros de ejemplos
        for (int i=1; i<=3; i++){
        	leerEjemplo(i);
        	double[] ejMenosVectorMedio1 = new double[DIMENSION];
        	double[] ejMenosVectorMedio2 = new double[DIMENSION];
        	for (int j=0;j<DIMENSION;j++){
        		ejMenosVectorMedio1[j] = ejemplo[j] - m1[j];	
        	}
        	for (int j=0;j<DIMENSION;j++){
        		ejMenosVectorMedio2[j] = ejemplo[j] - m2[j];	
        	}
        	dC1 = distancia(inversaC1,ejMenosVectorMedio1);
            dC2 = distancia(inversaC2,ejMenosVectorMedio2);
            System.out.println("La distancia a la Clase 1 es: " + decimales.format(dC1));
            System.out.println("La distancia a la Clase 2 es: " + decimales.format(dC2));
            System.out.println();
            System.out.print("La muestra( ");
            if (dC1 < dC2){
            	for (int j=0;j<DIMENSION;j++){
            		System.out.print(ejemplo[j] + " ");
            	}
            	System.out.print(") pertenece a la Clase 1");
                System.out.println();
            }
            else {
            	for (int j=0;j<DIMENSION;j++){
            		System.out.print(ejemplo[j] + " ");
            	}
            	System.out.print(") pertenece a la Clase 2");
                System.out.println();
            }
            System.out.println();
        }
    }
	
	/**
	 * Lee las muestras del fichero indicado
	 * @param lista
	 */
	private static void leerMuestras(ListaMuestras lista) {
		File archivo = new File ("/home/bobby/Documentos/UCM/4º Año/IC/IC_P3_Bayes/IC_P3_Bayes/src/Iris2Clases.txt");
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
	 * Calcula la media de los vectores
	 * @param lista
	 */
	private static void calcularVectoresMedia(ListaMuestras lista) {
		
		double acumulador1 = 0, acumulador2 = 0;
		
		for(int i=0; i<=3; i++){
			for(int j=0; j<(TAM/2); j++){
				acumulador1 += lista.getMuestra(j).getValor(i);
			}
			for(int j=(TAM/2); j<TAM; j++){
				acumulador2 += lista.getMuestra(j).getValor(i);
			}
			m1[i] = acumulador1/50;
			m2[i] = acumulador2/50;
			acumulador1 = 0;
			acumulador2 = 0;
		}
	}
	
	/**
	 * Lleva a cabo el cálculo de las matrices de covarianza
	 * @param lista
	 */
	private static void calcularMatricesCovarianza(ListaMuestras lista) {
		int contador, indice = 0;
		double auxiliar[] = new double[DIMENSION];
		double muestraXTraspuesta[] = new double[DIMENSION*DIMENSION];
		
		for(int j=0; j<DIMENSION*DIMENSION; j++)
			muestraXTraspuesta[j] = 0;
		
		// C1
		for(int i=0; i<(TAM/2); i++){
			for(int j=0; j<DIMENSION; j++){
				auxiliar[j] = lista.getMuestra(i).getValor(j)- m1[j];
			}
			for(int j=0; j<DIMENSION; j++){
				contador = j;
				for(int k=0; k<DIMENSION; k++){
					muestraXTraspuesta[indice] += auxiliar[contador]*auxiliar[k];
					indice++;
				}
			}
			indice = 0;
		}
		
		for(int i=0; i<(DIMENSION*DIMENSION); i++) 
			c1[i] = 1/(double)(TAM-1) * muestraXTraspuesta[i];
			
		// C2
		for(int i=(TAM/2); i<TAM; i++){
			for(int j=0; j<DIMENSION; j++){
				auxiliar[j] = lista.getMuestra(i).getValor(j)- m1[j];
			}
			for(int j=0; j<DIMENSION; j++){
				contador = j;
				for(int k=0; k<DIMENSION; k++){
					muestraXTraspuesta[indice] += auxiliar[contador]*auxiliar[k];
					indice++;
				}
			}
			indice = 0;
		}
		
		for(int i=0; i<(DIMENSION*DIMENSION); i++){ 
			c2[i] = 1/(double)(TAM-1) * muestraXTraspuesta[i];
		}
	}
	
	/**
	 * Convierte el array de covarianza en una matriz para luego poder hacer la inversa
	 * @param covarianza
	 * @return
	 */
	public static double[][] transformarCovarianza(double[] covarianza){
		double[][] inversa = new double[DIMENSION][DIMENSION];
		int k = 0;
		for (int i = 0; i < inversa.length; i++){
			for (int j = 0; j < inversa.length; j++){
				inversa[i][j] = covarianza[k];
				k++;	
			}
		}		
		return inversa;	
	}
	
	/* -------------------------------- Funciones para sacar la matriz inversa -------------------------------- */ 
	/**
	 * 
	 * @param matriz
	 * @return
	 */
	public static double[][] matrizInversa(double[][] matriz) {
	    double det=1/determinante(matriz);
	    double[][] nmatriz=matrizAdjunta(matriz);
	    multiplicarMatriz(det,nmatriz);
	    return nmatriz;
	}
	
	/**
	 * 
	 * @param n
	 * @param matriz
	 */
	public static void multiplicarMatriz(double n, double[][] matriz) {
	    for(int i=0;i<matriz.length;i++)
	        for(int j=0;j<matriz.length;j++)
	            matriz[i][j]*=n;
	}
	
	/**
	 * 
	 * @param matriz
	 * @return
	 */
	public static double[][] matrizAdjunta(double [][] matriz){
	    return matrizTranspuesta(matrizCofactores(matriz));
	}
	
	/**
	 * 
	 * @param matriz
	 * @return
	 */
	public static double[][] matrizCofactores(double[][] matriz){
	    double[][] nm=new double[matriz.length][matriz.length];
	    for(int i=0;i<matriz.length;i++) {
	        for(int j=0;j<matriz.length;j++) {
	            double[][] det=new double[matriz.length-1][matriz.length-1];
	            double detValor;
	            for(int k=0;k<matriz.length;k++) {
	                if(k!=i) {
	                    for(int l=0;l<matriz.length;l++) {
	                        if(l!=j) {
	                        int indice1=k<i ? k : k-1 ;
	                        int indice2=l<j ? l : l-1 ;
	                        det[indice1][indice2]=matriz[k][l];
	                        }
	                    }
	                }
	            }
	            detValor=determinante(det);
	            nm[i][j]=detValor * (double)Math.pow(-1, i+j+2);
	        }
	    }
	    return nm;
	}
	
	/**
	 * 
	 * @param matriz
	 * @return
	 */
	public static double[][] matrizTranspuesta(double [][] matriz){
	    double[][]nuevam=new double[matriz[0].length][matriz.length];
	    for(int i=0; i<matriz.length; i++)
	    {
	        for(int j=0; j<matriz.length; j++)
	            nuevam[i][j]=matriz[j][i];
	    }
	    return nuevam;
	}
	
	/**
	 * 
	 * @param matriz
	 * @return
	 */
	public static double determinante(double[][] matriz){
	    double det;
	    if(matriz.length==2)
	    {
	        det=(matriz[0][0]*matriz[1][1])-(matriz[1][0]*matriz[0][1]);
	        return det;
	    }
	    double suma=0;
	    for(int i=0; i<matriz.length; i++){
	    double[][] nm=new double[matriz.length-1][matriz.length-1];
	        for(int j=0; j<matriz.length; j++){
	            if(j!=i){
	                for(int k=1; k<matriz.length; k++){
	                int indice=-1;
	                if(j<i)
	                indice=j;
	                else if(j>i)
	                indice=j-1;
	                nm[indice][k-1]=matriz[j][k];
	                }
	            }
	        }
	        if(i%2==0)
	        suma+=matriz[i][0] * determinante(nm);
	        else
	        suma-=matriz[i][0] * determinante(nm);
	    }
	    return suma;
	}
	
	/* -------------- Funciones para sacar la distancia, es decir, multiplicar los vectores media por la inversa de la covarianza: m1 * inversaConvarianza * m1Traspuesto -------------- */ 
	/**
	 * 
	 * @param indice
	 */
	private static void leerEjemplo(int indice) {
		File archivo = null;
		switch(indice) {
		 case 1: 
			 archivo = new File ("/home/bobby/Documentos/UCM/4º Año/IC/IC_P3_Bayes/IC_P3_Bayes/src/TestIris01.txt");
		     break;
		 case 2: 
			 archivo = new File ("/home/bobby/Documentos/UCM/4º Año/IC/IC_P3_Bayes/IC_P3_Bayes/src/TestIris02.txt");
		     break;
		 case 3:  
			 archivo = new File ("/home/bobby/Documentos/UCM/4º Año/IC/IC_P3_Bayes/IC_P3_Bayes/src/TestIris03.txt");
		     break;    
		}
        String linea = "", delimitador = ",", atributo = "";
        FileReader fr = null;
        BufferedReader br = null; 
        Muestra muestra;
	    
	    try {
	    	fr = new FileReader(archivo);
	    	br = new BufferedReader(fr);
	        
	        linea = br.readLine();
	        StringTokenizer st = new StringTokenizer(linea,delimitador);
            // Cogemos el primer número y la tenemos que asociar a la primera componente de muestra
	        while (st.hasMoreTokens()){
	        	muestra = new Muestra();
                for(int i=0; i<=3; i++){
                	muestra.setValor(Double.parseDouble((String)st.nextToken()));
                }
                muestra.setTipo(st.nextToken().toString());
                for (int i=0;i<DIMENSION;i++){
                	ejemplo[i] = muestra.getValor(i);
                }
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
	 * Producto de m1 * inversa * m1Traspuesto
	 * @param inversa
	 * @param m1
	 * @return
	 */
    static double distancia(double[][] inversa, double[] m1){
        double distancia = 0;
    	double[] resultado =new double[DIMENSION];
    	// inversa * m1Traspuesto
        for(int i=0; i<DIMENSION; i++){
            for(int j=0; j<DIMENSION; j++){
                resultado[i]+=inversa[i][j]*m1[j];
            }
        }
        // m1 * resultado anterior
        for (int i=0; i<DIMENSION; i++){
        	distancia += m1[i]*resultado[i];
        }
        return distancia;
    }	
}

