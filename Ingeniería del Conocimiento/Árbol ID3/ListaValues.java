
public class ListaValues {
	
	private Value lista[];
	private int contador;
	private int n; // Es el valor N de los apuntes
	
	/**
	 * Constructora
	 * @param tamano
	 */
	public ListaValues(int tamano){
		this.lista = new Value[tamano];
		this.contador = 0;
		this.n = 0;
	}
	
	/**
	 * Retorna N
	 * @return
	 */
	public int getN(){
		return this.n;
	}
	
	/**
	 * Actualiza N
	 * @param n
	 */
	public void setN(int n){
		this.n += n;
	}
	
	/**
	 * Inserta un valor
	 * @param valor
	 */
	public void insertar(String valor){
		Valor val = StringAValor(valor);
		Value value = new Value(val);
		this.lista[contador] = value;
		this.contador++;
	}
	
	/**
	 * Transforma de String a Valor
	 * @param valor
	 * @return
	 */
	public Valor StringAValor(String valor){
		Valor val = null;
		
		switch(valor) {
		 case "soleado": 
			 val = Valor.soleado;
		     break;
		 case "nublado": 
			 val = Valor.nublado;
		     break;
		 case "lluvioso":  
			 val = Valor.lluvioso;
		     break;
		 case "caluroso":  
			 val = Valor.caluroso;
		     break;
		 case "templado":  
			 val = Valor.templado;
		     break;		
		 case "frio":  
			 val = Valor.frio;
		     break;
		 case "alta":  
			 val = Valor.alta;
		     break;
		 case "normal":  
			 val = Valor.normal;
		     break;    
		 case "verdad":  
			 val = Valor.verdad;
		     break;    
		 case "falso":  
			 val = Valor.falso;
		     break;
		 case "si":  
			 val = Valor.si;
		     break;
		 case "no":  
			 val = Valor.no;
		     break;
		}
		
		return val;
	}
	
	/**
	 * Retorna el valro
	 * @param valor
	 * @return
	 */
	public Value getValue(Valor valor){
		Value value = null;
		
		for (int i = 0; i < contador; i++){
			if (valor == lista[i].getValor())
				value = lista[i];		
		}
		
		return value;
	}
	
	/**
	 * Retorna el valor de la posición 'pos'
	 * @param pos
	 * @return
	 */
	public Value getValue(int pos){		
		return lista[pos];
	}
	
	/**
	 * Añade un valor
	 * @param value
	 * @param val
	 */
	public void setValue(Value value,Valor val){
		boolean encontrado = false;
		int contador = 0;
		
		while (!encontrado){
			if (val == lista[contador].getValor()){
				lista[contador] = value;
				encontrado = true;
			}
			else
				contador++;
		}
		actualizaTotal();
	}
	
	/**
	 * Actualiza el total
	 */
	private void actualizaTotal(){
		for (int i = 0; i < this.contador; i++){
			lista[i].setTotal(this.n);
		}
	}
	
	/**
	 * Retorn el contador
	 * @return
	 */
	public int getContador(){
		return this.contador;
	}
}
