package bytecodes.oneparameter.conditionaljumps;

import bytecodes.oneparameter.OneParameter;
import cpu.CPU;
import exceptions.StackException;

public abstract class ConditionalJumps extends OneParameter {
	
	/**
	 * Constructora
	 * @param j
	 */
	public ConditionalJumps(int j){
		super(j);
	}
	/**
	 * Ejecuta el 'execute' del ByteCode correspondiente
	 */
	public void execute(CPU cpu) throws StackException{
		try {
			int c = cpu.pop();
			int sc = cpu.pop();
			if(cpu.getSizeStack() >= 0){
				if(!compare(c, sc)){
					cpu.setProgramCounter(this.param);			
				}
			}else {
				throw new StackException("EX: elementos insuficientes en la pila");
			}
		} catch (StackException e) {
			throw e;
		}
	}
	/**
	 * Método de comparación auxiliar para las subclases de ConditionalJumps
	 * @param n1
	 * @param n2
	 * @return
	 */
	abstract protected boolean compare(int n1, int n2);
	/**
	 * Modifica el parámetro que le pasa la clase 'Condition'
	 * @param jump
	 */
	public void setJump(int jump){
		this.param = jump;
	}
}
