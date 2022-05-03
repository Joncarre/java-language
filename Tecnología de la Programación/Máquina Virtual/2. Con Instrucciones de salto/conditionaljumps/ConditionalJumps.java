package conditionaljumps;

import cpu.CPU;
import oneparameter.OneParameter;

public abstract class ConditionalJumps extends OneParameter {
	
	/**
	 * Constructora
	 */
	public ConditionalJumps(){};
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
	public boolean execute(CPU cpu){
		if(cpu.getSizeStack() >= 2){
			int c = cpu.pop();
			int sc = cpu.pop();
			if(!compare(c, sc)){
				return cpu.setProgramCounter(this.param);
			}
		}
		return true;
	}
	/**
	 * Método de comparación auxiliar para las subclases de ConditionalJumps
	 * @param n1
	 * @param n2
	 * @return
	 */
	abstract protected boolean compare(int n1, int n2);
}
