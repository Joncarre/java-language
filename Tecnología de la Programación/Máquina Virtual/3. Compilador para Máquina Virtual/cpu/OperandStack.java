package cpu;

import exceptions.StackException;

public class OperandStack {
	public static final int MAX_STACK = 3; 
	private int[] stack;
	private int numElems;
	
	/**
	 * Constructora
	 */
	public OperandStack(){
		this.numElems = 0;
		this.stack = new int[OperandStack.MAX_STACK];
	}
	/**
	 * Imprimir Pila
	 */
	public String toString(){
		String mensaje = "  Pila:";
		for (int i = 0; i < numElems; i++){
			mensaje += " " + this.stack[i];
		}
		if (this.isEmpty())
			mensaje += " <vacia>";
		return mensaje;
	}
	/**
	 * Añade un elemento (modificadora)
	 * @param value
	 * @throws StackException 
	 */
	public boolean push(int value) throws StackException{
		if(this.numElems < OperandStack.MAX_STACK && value != -1){ // Si es -1 es que la posición de memoria leída no ha sido escrita
			this.stack[this.numElems] = value;
			this.numElems++;
			return true;
		}else{
			throw new StackException("EX: error al hacer push en la pila");
		}
	}
	/**
	 * Quita un elemento y lo devuelve (modificadora)
	 * @return
	 */
	public int pop() throws StackException{
		if(!this.isEmpty()){
			this.numElems--;
			return this.stack[this.numElems];
		}else
			throw new StackException("EX: pila vacía al hacer pop");
	}
	/**
	 * Operación OUT (observadora)
	 */
	public int getCima() throws StackException{
		if(!this.isEmpty())
			return this.stack[this.numElems - 1]; // '-1' porque la pila comienza en la posición 0
		else
			throw new StackException("EX: pila vacía al intentar coger la cima");
	}
	/**
	 * Devuelve si la pila está vacía (observadora)
	 * @return
	 */
	public boolean isEmpty(){
		return this.numElems == 0;
	}
	/**
	 * Devuelve el tamaño de la pila
	 * @return
	 */
	public int getSize(){
		return this.numElems;
	}
}