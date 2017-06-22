package cpu;

import bytecodes.ByteCode;
import main.ByteCodeProgram;

public class CPU {
	private OperandStack pila;
	private Memory memoria;
	private boolean halt;
	private int programCounter;
	private ByteCodeProgram bcProgram = new ByteCodeProgram();
	
	/**
	 * Constructora con el programa por parámetro
	 * @param bcProgram
	 */
	public CPU(ByteCodeProgram bcProgram){
		this.pila = new OperandStack();
		this.memoria = new Memory();
		this.halt = false;
		this.bcProgram = bcProgram;
	}
	/**
	 * Imprimir estado de la CPU
	 */
	public String toString(){
		String mensaje = "El estado de la maquina tras ejecutar programa es: " +
		System.getProperty("line.separator") +
		System.getProperty("line.separator") +
		"Estado de la CPU:" + 
		System.getProperty("line.separator") + 
		memoria.toString() +
		System.getProperty("line.separator") +
		pila.toString() + 
		System.getProperty("line.separator");
		return mensaje;
	}
	/**
	 * Ejecuta el programa 'bcProgram'
	 * @return
	 */
	public boolean run(){
		this.programCounter = 0;
		boolean seguir = true;
		while(this.programCounter < bcProgram.getnumElems() && seguir && !this.halt){
			ByteCode bc = bcProgram.getByteCode(this.programCounter);
			// Incrementamos el 'programCounter'. Si hay alguna instrucción
			// de salto que lo usa, simplemente lo modifica y no surgen problemas con la ejecución
			this.increaseProgramCounter();
			if(!bc.execute(this))
				seguir = false;
		}
		return seguir;
	}
	// -------------------------------- MÉTODOS GENÉRICOS --------------------------------
	/**
	 * Devuelve el tamaño de la pila
	 * @return
	 */
	public int getSizeStack(){
		return this.pila.getSize();
	}
	/**
	 * Lleva el contador de programa a la posición 'pos'
	 * @param pos
	 */
	public boolean setProgramCounter(int pos){
		if(pos < this.bcProgram.getnumElems() && pos > 0){ // Controlamos el rango del salto
			this.programCounter = pos;
			return true;
		}else
			return false;
	}
	/**
	 * Incrementa el contador de programa
	 */
	public void increaseProgramCounter(){
		this.programCounter++;
	}
	/**
	 * Limpiar la CPU (crea otra memoria y pila)
	 */
	public void erase(){
		this.pila = new OperandStack();
		this.memoria = new Memory();
	}
	/**
	 * Nos dice si la MV está parada
	 * @return
	 */
	public boolean isHalt(){
		return this.halt;
	}
	// -------------------------------- OPERACIONES GENÉRICAS --------------------------------
	/**
	 * Llama al método push de la pila
	 * @param value
	 * @return
	 */
	public boolean push(int value){
		return this.pila.push(value);
	}
	/**
	 * Llama al método pop de la pila
	 * @return
	 */
	public int pop(){
		return this.pila.pop();
	}
	/**
	 * Lee un elemento en la posición 'pos' de memoria y hace push
	 * @param pos
	 * @return
	 */
	public boolean read(int pos){
		return this.pila.push(this.memoria.read(pos));
	}
	/**
	 * Escribe el elemento 'value' en la memoria
	 * @param pos
	 * @return
	 */
	public boolean store(int pos){
		if(!this.pila.isEmpty()){
			if(this.memoria.store(pos, this.pila.pop()))
				return true;
			else
				return false;
		}else
			return false;
	}
	// -------------------------------- OPERACIONES POPURRÍ --------------------------------
	/**
	 * Detiene la CPU
	 */
	public void halt(){
		this.halt = true;
	}
	/**
	 * Imprime la cima de la pila
	 */
	public void out(){
		System.out.println("La cima es: " + this.pila.getCima());
	}
	// --------------------------------- OPERACIONES ARITMÉTICAS ---------------------------------
	/**
	 * Operación ADD
	 * @param c
	 * @param sc
	 * @return
	 */
	public boolean add(int c, int sc){
		return this.pila.push(sc + c);
	}
	/**
	 * Operación SUB
	 * @param c
	 * @param sc
	 * @return
	 */
	public boolean sub(int c, int sc){
		return this.pila.push(sc - c);
	}
	/**
	 * Operación MUL
	 * @param c
	 * @param sc
	 * @return
	 */
	public boolean mul(int c, int sc){
		return this.pila.push(sc * c);
	}
	/**
	 * Operación DIV
	 * @param c
	 * @param sc
	 * @return
	 */
	public boolean div(int c, int sc){
		return this.pila.push(sc / c);
	}
}