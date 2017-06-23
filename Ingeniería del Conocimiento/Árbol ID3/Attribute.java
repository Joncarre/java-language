
public class Attribute {
		
		private static final int TIEMPOEXTERIOR = 3;
		private static final int TEMPERATURA = 3;
		private static final int HUMEDAD = 2;
		private static final int VIENTO = 2;
		private static final int JUGAR = 2;
		private Atributo atributo;
		private ListaValues listaValores;
		private double merito;
		
		/**
		 * Constructora
		 * @param atributo
		 */
		public Attribute(String atributo){
			int tamano = tamanoLista(atributo);
			this.atributo = StringAAtributo(atributo);
			this.listaValores = new ListaValues(tamano);
			this.merito = 0;
		}
		
		/**
		 * Devuelve el atributo
		 * @return
		 */
		public Atributo getAtributo(){
			return this.atributo;
		}
		
		/**
		 * Devuelve el mérito
		 * @return
		 */
		public double getMerito(){
			return this.merito;
		}
		
		/**
		 * Modifica el mérito
		 * @param valor
		 */
		public void setMerito(double valor){
			this.merito = valor;
		}
		
		/**
		 * Devuelve la lista
		 * @return
		 */
		public ListaValues getLista(){
			return this.listaValores;
		}
		
		/**
		 * Devuelve el atributo correspondiente según el String 'atributo'
		 * @param atributo
		 * @return
		 */
		private Atributo StringAAtributo(String atributo){
			Atributo atrib = null;
			switch(atributo) {
			 case "TiempoExterior": 
				 atrib = Atributo.tiempoExterior;
			     break;
			 case "Temperatura": 
				 atrib = Atributo.temperatura;
			     break;
			 case "Humedad":  
				 atrib = Atributo.humedad;
			     break;
			 case "Viento":  
				 atrib = Atributo.viento;
			     break;
			 case "Jugar":  
				 atrib = Atributo.jugar;
			     break;		     
			}		
			return atrib;
		}
		
		/**
		 * Devuelve el tamaño de la lista de atributos
		 * @param atributo
		 * @return
		 */
		private int tamanoLista(String atributo){
			int tamano = 0;
	
			switch(atributo) {
			 case "TiempoExterior": 
				 tamano = TIEMPOEXTERIOR;
			     break;
			 case "Temperatura": 
				 tamano = TEMPERATURA;
			     break;
			 case "Humedad":  
				 tamano = HUMEDAD;
			     break;
			 case "Viento":  
				 tamano = VIENTO;
			     break;
			 case "Jugar":  
				 tamano = JUGAR;
			     break;		     
			}
			return tamano;
		}
}
