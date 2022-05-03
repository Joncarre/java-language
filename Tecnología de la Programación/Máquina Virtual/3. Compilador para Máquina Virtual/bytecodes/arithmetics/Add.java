package bytecodes.arithmetics;

import bytecodes.ByteCode;
import cpu.CPU;
import exceptions.StackException;

public class Add extends Arithmetics {
	
	@Override
	protected void executeAux(int c, int sc, CPU cpu) throws StackException{
		try {
			cpu.push(sc + c);
		} catch (StackException e) {
			throw new StackException("EX: en ADD, tamaño insuficiente de pila");
		}
	}
	@Override
	protected ByteCode parseAux(String s){
		if(s.equalsIgnoreCase(("ADD")))
			return new Add();
		else
			return null;
	}
	/**
	 * Imprime "ADD"
	 */
	public String toStringAux(){
		return "ADD";
	}
}
