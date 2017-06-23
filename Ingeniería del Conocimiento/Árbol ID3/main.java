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

public class main {
	
	private static final int TAMANIO = 4;
	private static ListaAtributos listaAtributos = new ListaAtributos();
	
	/**
	 * Función main que realiza las llamadas al resto de métodos
	 * @param args
	 */
	public static void main(String[] args) {
		String atributo;
		
		leerAtributos(); // Lee AtributosJuego.txt
		leerValores(); // Lee Juego.txt
		calcularValores(); // Calcula los valores necesarios para el mérito
		calcularMeritos(); // Procede a realizar el cálculo del mérito
		atributo = elegirMerito(); // Elige el menor y muestra el resultado por pantalla
	}

	/**
	 * Lee los atributos del finchero indicado como 'File' (hay que poner la ruta completa)
	 */
	private static void leerAtributos() {
		File archivo = new File ("/home/bobby/Documentos/UCM/4º Año/IC/IC_P2/IC_P2/src/AtributosJuego.txt");
		String linea = "", delimitador = ",", atributo = "";
		FileReader fr = null;
	    BufferedReader br = null; 
	    
	    try {
			fr = new FileReader(archivo);
			br = new BufferedReader(fr);
	        
	        linea = br.readLine();
	        StringTokenizer st = new StringTokenizer(linea,delimitador);
	        while (st.hasMoreTokens()){
	            atributo = st.nextToken();
	            if (!atributo.equals("Jugar"))
	            	listaAtributos.insertar(atributo);
	        }
			
		} catch (FileNotFoundException e) {
			System.out.println("Fichero no encontrado");
            System.out.println(e.getMessage());
		}
	    catch (Exception e) {
            System.out.println("Error de lectura del fichero");
            System.out.println(e.getMessage());
        }
       
	}
	
	/**
	 * Lee los valores del finchero indicado como 'File' (hay que poner la ruta completa)
	 */
	private static void leerValores() {
		File archivo = new File ("/home/bobby/Documentos/UCM/4º Año/IC/IC_P2/IC_P2/src/Juego.txt");
		String linea = "", delimitador = ",", valor = "";
		int contador = 0, contadorAux = 0;
		Attribute atributo;
		Value value;
		ListaValues valores;
		Valor val;
		Value aux[] = new Value[TAMANIO];
		boolean actualizado = false;
		FileReader fr = null;
	    BufferedReader br = null; 
	    
	    try {
			fr = new FileReader(archivo);
			br = new BufferedReader(fr);
	        
	        while((linea = br.readLine())!= null){ 
	        	StringTokenizer st = new StringTokenizer(linea,delimitador);
	        	while (st.hasMoreTokens()){
	        		valor = st.nextToken();
	        		if ((!valor.equals("si")) && !valor.equals("no")){
	        			atributo = listaAtributos.getLista(contador);
			            valores = atributo.getLista();
			            value = valores.getValue(valores.StringAValor(valor));
			            if (valorEnLista(valor,valores)){
			            	// Actualiza a sumando 1
			            	value.setA(1);
			            	// Actualiza N sumando 1 y modifica total con el nuevo valor de N
			            	valores.setN(1);
			            	value.setTotal(valores.getN());			       	
			            }   
			            else{
			            	valores.insertar(valor);
			            	value = valores.getValue(valores.StringAValor(valor));
			            	// Actualiza a
			            	value.setA(1);
			            	// Actualiza N y modifica total
			            	valores.setN(1);
			            	value.setTotal(valores.getN());		
			            	
			            }
			            aux[contadorAux] = value;
			            contadorAux++;
	        		}
	        		
	        		else{// Si lee la decisión...
	        			// Recorre la lista de atributos actualizando los valores de value
	        			for (int i = 0; i < listaAtributos.getContador(); i++){
	        				atributo = listaAtributos.getLista(i);
				            valores = atributo.getLista();
				            // value = valores.getValue(i);
				            value = aux[i];
				          // valor "si" actualiza p 
		        			if (valor.equals("si")){
					            value.setPositivos(1);
		        			}
		        			// Valor "no" actualiza n
		        			else{
		        				value.setNegativos(1);
		        			}
		        			val = value.getValor();
		        			valores.setValue(value, val);
	        			}
	        			actualizado = true;
	        		}
		            contador++;
		            if (actualizado){
		            	contador = 0;
		            	contadorAux = 0;
		            	actualizado = false;
		            }
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
	 * Busca un valor determinado en la lista de valores
	 * @param valor
	 * @param lista
	 * @return
	 */
	private static boolean valorEnLista(String valor, ListaValues lista){
		boolean enLista = false;
		int i = 0;
		Valor val = lista.StringAValor(valor); // Valor buscado
		while (!enLista && i < lista.getContador()){
			if (lista.getValue(i).getValor() == val)
				enLista = true;
			else i++;	
		}
		return enLista;
	}
	
	/**
	 * Calcula los valores Pi, Ni, Ri
	 */
	private static void calcularValores(){
		Attribute lista;
		ListaValues listaValores;
		for (int i = 0; i < listaAtributos.getContador(); i++){
			lista = listaAtributos.getLista(i);
			listaValores = lista.getLista();
			for (int j = 0; j < listaValores.getContador(); j++){
				listaValores.getValue(j).setP(listaValores.getValue(j).getPositivos());
				listaValores.getValue(j).setN(listaValores.getValue(j).getNegativos());
				listaValores.getValue(j).setR();
			}
		}	
	}
	
	/**
	 * Método que calcula los méritos (lleva a cabo las operaciones matemáticas descritas en los apuntes)
	 */
	private static void calcularMeritos(){
		Attribute lista;
		ListaValues listaValores;
		double merito = 0, p = 0, n = 0, r = 0, infor = 0, a = 0, b = 0;
		for (int i = 0; i < listaAtributos.getContador(); i++){
			lista = listaAtributos.getLista(i);
			listaValores = lista.getLista();
			for (int j = 0; j < listaValores.getContador(); j++){
				p = listaValores.getValue(j).getP();
				if (p == 0)
					a = 0;
				else
					a = -p*(Math.log10(p)/Math.log10(2));
				n = listaValores.getValue(j).getN();
				if (n == 0)
					b = 0;
				else
					b = -n*(Math.log10(n)/Math.log10(2));
				
				infor = a + b ;
				r = listaValores.getValue(j).getR();
				
				merito += r * infor;
			}
			lista.setMerito(merito);
			merito = 0;
		}
	}
	
	/**
	 * Se elige el atributo cuyo mérito es menor
	 * @return
	 */
	private static String elegirMerito() {
		Attribute lista;
		Attribute aux;
		ListaValues valores;
		Value valor;
		String atributo, cadenaValor;
		double merito = 0, otro = 0;
		lista = listaAtributos.getLista(0);
		aux = lista;
		merito = lista.getMerito();
		for (int i = 1; i < listaAtributos.getContador(); i++){
			lista = listaAtributos.getLista(i);
			otro = lista.getMerito();
			if (otro < merito){
				merito = otro;
				aux = listaAtributos.getLista(i);
			}
		}
		lista = aux;
		atributo = AtributoAString(lista.getAtributo());
		System.out.println("El mejor atributo es: " + atributo + ".\n");
		System.out.println("Merito = " + merito + " y los siguientes valores:");
		System.out.println();
		valores = lista.getLista();
		for (int i = 0; i < valores.getContador(); i++){
			valor = valores.getValue(i);
			System.out.println("Valor" + (i+1) + " -> " + ValorAString(valor.getValor()) + ": p = " + valor.getP()+ " | n = " + valor.getN()+ 
								" | a = " + valor.getA() +	" | N = " + valor.getTotal() + " | r = " + valor.getR());
		}
		return atributo;
	}
	
	/**
	 * Según el 'atributo', se devuelve un String que representa dicho atributo
	 * @param atributo
	 * @return
	 */
	private static String AtributoAString(Atributo atributo){
		String atrib = null;
		switch(atributo) {
		 case tiempoExterior: 
			 atrib = "TiempoExterior";
		     break;
		 case temperatura: 
			 atrib = "Temperatura";
		     break;
		 case humedad:  
			 atrib = "Humedad";
		     break;
		 case viento:  
			 atrib = "Viento";
		     break;
		 case jugar :
			 atrib = "Jugar";
		     break;		     
		}
		
		return atrib;
	}
	
	/**
	 * Según el 'valor', se devuelve un String que representa dicho valor
	 * @param valor
	 * @return
	 */
	public static String ValorAString(Valor valor){
		String val = "";
		switch(valor) {
		 case soleado: 
			 val = "soleado";
		     break;
		 case nublado: 
			 val = "nublado";
		     break;
		 case lluvioso:  
			 val = "lluvioso";
		     break;
		 case caluroso:  
			 val = "caluroso";
		     break;
		 case templado:  
			 val = "templado";
		     break;		
		 case frio: 
			 val = "frio";
		     break;
		 case alta:  
			 val = "alta";
		     break;
		 case normal:  
			 val = "normal";
		     break;    
		 case verdad:  
			 val = "verdad";
		     break;    
		 case falso:  
			 val = "falso";
		     break;
		 case si:  
			 val = "si";
		     break;
		 case no:  
			 val = "no";
		     break;
		}
		return val;
	}
}
