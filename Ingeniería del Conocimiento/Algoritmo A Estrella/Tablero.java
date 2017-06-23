import java.util.Vector;


public class Tablero {
	
	private Nodo[][] tablero;
	private int ancho;
	private int alto;
	Nodo sucesores[] = new Nodo[1000]; // Lista abierta
	Nodo cerrada[] = new Nodo[1000]; // Lista cerrada
	private int contadorAbierta;
	private int contadorCerrada;
	private int contadorResultado;
	
	/**
	 * Constructora con parámetros
	 * @param nr
	 * @param nc
	 */
	public Tablero (int nr, int nc){
		this.ancho = nr;
		this.alto = nc; 
		this.contadorAbierta = 0;
		this.contadorCerrada = 0;
		this.tablero = new Nodo[nr][nc];
		for (int i = 0; i < this.ancho; i++){
			for (int j = 0; j < this.alto; j++){
				Nodo nodo = new Nodo(i, j);
				this.tablero[i][j] = nodo;
			}
		}
	}
	
	/**
	 * Retorna el ancho del tablero
	 * @return
	 */
	public int getAncho(){
		return this.ancho;
	}
	
	/**
	 * Retorna el alto del tablero
	 * @return
	 */
	public int getAlto(){
		return this.alto;
	}
	
	/**
	 * Devuelve el contador
	 * @return
	 */
	public int getContadorResultado(){
		return this.contadorResultado;
	}
	
	/**
	 * Devuelve el nodo
	 * @param fila
	 * @param columna
	 * @return
	 */
	public Nodo getNodo(int fila, int columna){
		return this.tablero[fila][columna];
	}
	
	/**
	 * Modifica el nodo
	 * @param fila
	 * @param columna
	 * @param nodo
	 */
	public void setNodo(int fila,int columna,Nodo nodo){
		this.tablero[fila][columna] = nodo;
	}
	
	/** 
	 * Devuelve la representación en texto para cada tipo de nodo
	 * @param tipo
	 * @return
	 */
	private String fichaATipo(Ficha tipo){
		String tipoFicha = "";
		switch(tipo) {
		 case DESPEGUE: 
			 tipoFicha = " E ";
		     break;
		 case FLECHA: 
			 tipoFicha = " • ";
		     break;
		 case ASTEROIDE:  
			 tipoFicha = " ø ";
		     break;
		 case ASISTENCIA:
			 tipoFicha = " Û ";
		     break;
		 case AGUJERO:  
			 tipoFicha = " Ô ";
		     break;
		 case ACOPLAMIENTO:  
			 tipoFicha = "ISS";
		     break;
		 case VACIA:  
			 tipoFicha = " · ";
		     break;
		}
		return tipoFicha;
	}

	/**
	 * Método toString de la clase Tablero (imprime el tablero)
	 */
	public String toString(){ 
		String tablero="\n";
		for(int i = this.ancho-1; i >= 0; i--){
			tablero += i+1 + " |";
			for (int j = 0; j < this.alto; j++){
				tablero += fichaATipo(getNodo(i, j).getFichaNodo());
			}
			tablero += " |";
            tablero += "\n";
		}
		for(int k = 0; k <= this.alto; k++){
			if(k == 0)
				tablero += "   ";
			else
				tablero += " " + k + " ";
		}
		return tablero;
	}
	
	/**
	 * 
	 * @param fila
	 * @param columna
	 * @param ficha
	 */
	public void cambiarFichaNodo(int fila, int columna, Ficha ficha){
		this.tablero[fila][columna].setFichaNodo(ficha);
	}
	
	/**
	 * Fija el nodo inicial (despegue)
	 * @param fila
	 * @param columna
	 */
	public void fijarNodoInicial(int fila, int columna){
		this.tablero[fila][columna].setTipoLista(TipoLista.CERRRADA);
		cambiarFichaNodo(fila,columna,Ficha.DESPEGUE);
		this.tablero[fila][columna].setGn(fila, columna, this.tablero[fila][columna].getFilaMeta(), this.tablero[fila][columna].getColumnaMeta());
		this.tablero[fila][columna].setFn();
		meterEnCerrada(this.tablero[fila][columna]);
	}
	
	/**
	 * Busca y devuelve el nodo inicial
	 * @return
	 */
	public Nodo getNodoInicial(){
		Nodo inicial = null;
		boolean seguir = true;
		int i = 0, j = 0;
		while(i < this.ancho && seguir){
			while(j < this.alto && seguir){
				if (this.tablero[i][j].getFichaNodo() == Ficha.DESPEGUE){
					inicial = getNodo(i, j);
					seguir = false;
				}					
			}
		}
		return inicial;
	}
	
	/**
	 * Fija el nodo final (acoplamiento)
	 * @param fila
	 * @param columna
	 * @param ficha
	 */
	public void fijarNodoMeta(int row, int columna){
		this.tablero[row][columna].setFila(row);
		this.tablero[row][columna].setColumna(columna);
		this.tablero[row][columna].setFichaNodo(Ficha.ACOPLAMIENTO);
		for (int i = 0; i < this.ancho; i++){
			for (int j = 0; j < this.alto; j++){
				this.tablero[i][j].setFilaMeta(row);
				this.tablero[i][j].setColumnaMeta(columna);
				this.tablero[i][j].setFichaNodoMeta(Ficha.ACOPLAMIENTO);
			}
		}
	}
	
	/**
	 * Mete un nodo en la lista abierta
	 * @param filaPadre
	 * @param columnaPadre
	 * @param fila
	 * @param columna
	 * @param tipo
	 * @param contador
	 */
	public void meterEnAbierta(int filaPadre, int columnaPadre, int fila, int columna, TipoLista tipo, int contador){
		Nodo nodoPadre = getNodo(filaPadre,columnaPadre);
		double hnPadre;
		int filaMeta = this.tablero[fila][columna].getFilaMeta();
		int columnaMeta = this.tablero[fila][columna].getColumnaMeta();
		
		this.tablero[fila][columna].setTipoLista(tipo);	
		this.tablero[fila][columna].setFilaPadre(filaPadre);
		this.tablero[fila][columna].setColumnaPadre(columnaPadre);
		this.tablero[fila][columna].setFichaNodoPadre(this.tablero[filaPadre][columnaPadre].getFichaNodo());
		hnPadre = nodoPadre.getHn();
		this.tablero[fila][columna].setHn(fila, columna, filaPadre, columnaPadre, hnPadre);
		this.tablero[fila][columna].setGn(fila, columna, filaMeta, columnaMeta);
		this.tablero[fila][columna].setFn();
		this.sucesores[contador] = getNodo(fila,columna);
	}
	
	/**
	 * Mete un nodo en la lista cerrada
	 * @param nodo
	 */
	public void meterEnCerrada(Nodo nodo){
		nodo.setTipoLista(TipoLista.CERRRADA);
		cerrada[this.contadorCerrada] = nodo;
		this.contadorCerrada++;
	}
	
	/**
	 * Lleva a cabo la expansión de un determinado nodo
	 * @param i
	 * @param j
	 * @param tablero
	 * @return
	 */
	public boolean expansion(int i, int j, Tablero tablero){
		boolean expansion = false, enAbierta = false;
		int fila,columna, contador = this.contadorAbierta;
		Vector v;
		
		// Busco los posibles nodos a expandir
		for (int dir = 0; dir <=7; dir++){
			v = direccion(dir);
			fila = i + (int)v.elementAt(0);
			columna = j + (int)v.elementAt(1);
			// Miro que está en el tablero y que no sea un obstaculo o un nodo ya en cerrada
			if ((inLimit(fila,columna, v, tablero)) 
					&& ((this.tablero[fila][columna].getTipoLista()==TipoLista.ABIERTA)
						||(this.tablero[fila][columna].getTipoLista()==TipoLista.DESCONOCIDO)
							||this.tablero[fila][columna].getFichaNodo() == Ficha.ACOPLAMIENTO)){
				// Compruebo si ya está en abierta
				int indice = 0;
				while ((indice <= contador) && (!enAbierta)){
					if (this.sucesores[indice] == this.tablero[fila][columna]){//si est� en abierta
						reevaluarEnlaces(this.tablero[i][j], this.tablero[fila][columna]);
						enAbierta = true;
					}
					else enAbierta = false;
					indice++;
				}
				// Si no está lo meto en abierta
				if (!enAbierta){
					meterEnAbierta(i,j,fila,columna,TipoLista.ABIERTA,contador);
					this.contadorAbierta++;
				}
				enAbierta = false;
				contador = this.contadorAbierta;
			}
			if (contador != 0)
				expansion = true;
		}
		return expansion;
	}
	
	/**
	 * Asigna un vector a una dirección dada
	 * @param k
	 * @return
	 */
	private Vector direccion (int k){
		Vector vector = new Vector(2); // fila, columna
			
		switch (k) {
		case 0:{ // arriba
			vector.addElement(1);
			vector.addElement(0);
			}
		break;
		case 1:{ // arriba y derecha
			vector.addElement(1);
			vector.addElement(1);
			}
		break;
		case 2:{ // derecha
			vector.addElement(0);
			vector.addElement(1);
			}
		break;
		case 3:{ // abajo y derecha
			vector.addElement(-1);
			vector.addElement(1);
			}
		break;
		case 4:{ // abajo
			vector.addElement(-1);
			vector.addElement(0);
			}
		break;
		case 5:{ // abajo e izquierda
			vector.addElement(-1);
			vector.addElement(-1);
			}
		break;
		case 6:{ //izquierda
			vector.addElement(0);
			vector.addElement(-1);
			}
		break;
		case 7:{ //arriba e izquierda
			vector.addElement(1);
			vector.addElement(-1);
			}
		break;
		
		}
		return vector;
	}	
	
	/**
	 * Comprueba si la ficha está en el tablero
	 * @param fila
	 * @param columna
	 * @param v
	 * @param tablero
	 * @return
	 */
	private boolean inLimit(int fila, int columna, Vector v, Tablero tablero) {
			
		boolean enFila = ((int)v.elementAt(0) == 1)? fila < tablero.getAlto(): fila >= 0;
		boolean enColumna = ((int)v.elementAt(1) == 1)? columna < tablero.getAncho(): columna >= 0;
			
		return enFila && enColumna;
	}
	
	/**
	 * Lleva a cabo la revaluación de enlaces
	 * @param nuevoPadre
	 * @param nodoYaEnAbierta
	 */
	public void reevaluarEnlaces(Nodo nuevoPadre, Nodo nodoYaEnAbierta){
		double hnAntigua = nodoYaEnAbierta.getHn();
		double hnNueva;
		// Recalculo h(n)
		nodoYaEnAbierta.setHn(nodoYaEnAbierta.getFila(), nodoYaEnAbierta.getColumna(), nuevoPadre.getFila(), nuevoPadre.getColumna(), hnAntigua);
		hnNueva = nodoYaEnAbierta.getHn(); // Hay que sumar la h(n) que ya tiene su padre
		if (hnAntigua > hnNueva){// Reoriento enlace al nuevo padre	
			nodoYaEnAbierta.setFilaPadre(nuevoPadre.getFila());
			nodoYaEnAbierta.setColumnaPadre(nuevoPadre.getColumna());
			}	
		else// Restauro la h(n) que ya tenía y su padre seguirá siendo el antiguo
			nodoYaEnAbierta.setHn(hnAntigua);
	}
	
	/**
	 * Saca un noso de la lista abierta
	 */
	public void sacarDeAbierta(){
		Nodo auxiliar = null;
		double fn = this.sucesores[0].getFn(), aux;
		int indice=0;
		
		for (int i = 0; i < this.contadorAbierta; i++){
			aux = this.sucesores[i].getFn();
			if (aux <= fn){
				fn = aux;
				indice = i;
			}
		}
		for (int ind = indice; ind < this.contadorAbierta; ind++){
			auxiliar = this.sucesores[ind];
			this.sucesores[ind] = this.sucesores[ind+1];
			this.sucesores[ind+1] = auxiliar;
		}
		
		meterEnCerrada(this.sucesores[contadorAbierta]);
		this.contadorAbierta--;		
	}
	
	/**
	 * Mete un nodo en la lista cerrada
	 * @return
	 */
	public boolean metaEnCerrada(){
		boolean metaAlcanzada = false;
		int i = 0;
		
		while ((i < contadorCerrada) && !metaAlcanzada){
			if (cerrada[i].getFichaNodo() == Ficha.ACOPLAMIENTO)
				metaAlcanzada = true;
			i++;
		}
		
		return metaAlcanzada;
	}
	
	/**
	 * Recupera el siguiente nodo a expandir según la lista cerrada
	 * @return
	 */
	public Nodo siguienteNodoAExpandir(){
		return cerrada[contadorCerrada-1];
	}
	
	/**
	 * Devuelve el nodo resultado
	 * @return
	 */
	public Nodo[] resultado(){
		int fila, columna, contador = 0;
		Nodo listaResultado[] = new Nodo[50];
		int tam;
		Nodo nodo;

		boolean terminado = false;
		tam = contadorCerrada;
		fila = cerrada[tam-1].getFila();
		columna = cerrada[tam-1].getColumna();
		nodo = getNodo(fila,columna);
		while (!terminado)
			if (nodo.getFichaNodo() != Ficha.DESPEGUE){
				listaResultado[contador] = nodo;
				fila = nodo.getFilaPadre();
				columna = nodo.getColumnaPadre();
				nodo = getNodo(fila,columna);
				contador++;
			}
			else terminado = true;
		contadorResultado = contador;
		return listaResultado;
	}
}
