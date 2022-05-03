package cpu;

import exceptions.ArrayException;
import exceptions.ExecutionErrorException;
import exceptions.StackException;
import bytecodes.ByteCode;
import main.ByteCodeProgram;

public class CPU {
	private OperandStack pila;
	private Memory memoria;
	private boolean halt;
	private int counter;
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
	 * @throws ArrayException
	 * @throws ExecutionErrorException
	 */
	public void run() throws ArrayException, ExecutionErrorException{
		this.counter = 0;
		try {
			while(this.counter < bcProgram.getSize() && !this.halt){
				ByteCode bc = bcProgram.getInst(this.counter);
				this.increaseProgramCounter();
				bc.execute(this);
			}
		} catch (ArrayException a) {
			throw a;
		} catch (StackException b) {
			throw b;
		}
	}
	/**
	 * Añade una instrucción al programa bytecode
	 * @param bc
	 * @param pos
	 * @throws ArrayException
	 */
	public void addByteCode(ByteCode bc, int pos) throws ArrayException{
		try {
			this.bcProgram.addByteCode(bc, pos);
		} catch (ArrayException e) {
			throw e;
		}
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
	 * Devuelve si la pila está vacía
	 * @return
	 */
	public boolean emptyStack(){
		return this.pila.isEmpty();
	}
	/**
	 * Lleva el contador de programa a la posición 'pos'
	 * @param pos
	 */
	public void setProgramCounter(int pos) throws StackException{
		if(pos < this.bcProgram.getSize() && pos > 0){ // Controlamos el rango del salto
			this.counter = pos;
		}else
			throw new StackException("EX: el contador pretende saltar a una posición fuera de rango");
	}
	/**
	 * Incrementa el contador de programa
	 */
	public void increaseProgramCounter(){
		this.counter++;
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
	public void push(int value) throws StackException{
		try {
			this.pila.push(value);
		} catch (StackException e) {
			throw e;
		}
	}
	/**
	 * Llama al método pop de la pila
	 * @return
	 */
	public int pop() throws StackException{
		try {
			return this.pila.pop();
		} catch (StackException e) {
			throw e;
		}
	}
	/**
	 * Lee un elemento en la posición 'pos' de memoria y hace push
	 * @param pos
	 * @return
	 * @throws StackException 
	 */
	public void read(int pos) throws StackException{
		try {
			int lectura = this.memoria.read(pos);
			this.pila.push(lectura);
		} catch (StackException e) {
			throw e;
		}
	}
	/**
	 * Escribe el elemento sacado de la pila en la memoria
	 * @param pos
	 * @return
	 * @throws StackException 
	 */
	public boolean store(int pos) throws StackException{
		int value;
		try {
			value = this.pila.pop();
			if(this.memoria.store(pos, value))
				return true;
			else
				return false;
		}catch (StackException e) {
			throw e;
		}
	}
	// -------------------------------- OPERACIONES ESPECIALES --------------------------------
	/**
	 * Detiene la CPU
	 */
	public void halt(){
		this.halt = true;
	}
	/**
	 * Devuelve la cima de la pila
	 */
	public void out() throws StackException{
		if(!this.pila.isEmpty()){
			int cima = this.pila.pop();
			System.out.println("consola: " + cima);
		}else
			throw new StackException("EX: pila vacía al hacer out");
	}
}