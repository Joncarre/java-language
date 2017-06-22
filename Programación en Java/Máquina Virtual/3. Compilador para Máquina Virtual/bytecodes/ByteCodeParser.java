package bytecodes;

import exceptions.DivisionByZeroException;
import exceptions.StackException;
import bytecodes.arithmetics.Add;
import bytecodes.arithmetics.Div;
import bytecodes.arithmetics.Mul;
import bytecodes.arithmetics.Sub;
import bytecodes.oneparameter.Goto;
import bytecodes.oneparameter.Load;
import bytecodes.oneparameter.Push;
import bytecodes.oneparameter.Store;
import bytecodes.oneparameter.conditionaljumps.Ifeq;
import bytecodes.oneparameter.conditionaljumps.Ifle;
import bytecodes.oneparameter.conditionaljumps.Ifleq;
import bytecodes.oneparameter.conditionaljumps.Ifneq;

public class ByteCodeParser {
	private final static ByteCode[] bytecodes = {
		new Add(), new Sub(), new Mul(), new Div(),
		new Halt(), new Out(),
		new Ifeq(0), new Ifneq(0), new Ifle(0), new Ifleq(0),
		new Goto(0), new Load(0), new Store(0), new Push(0) 
	};
	/**
	 * Busca un posible parseo entre las subclases de ByteCode
	 * @param line
	 * @return
	 * @throws StackException 
	 * @throws DivisionByZeroException 
	 */
	public static ByteCode parse(String line){
		line = line.trim(); // Elimina caracteres en blanco
		String[] fragment = line.split(" +"); // Divide 'line' y lo mete en el array 'fragment'
		if(fragment.length == 0)
			return null;
		else{
			int i = 0; 
			boolean seguir = true;
			ByteCode bc = null;
				while(i < bytecodes.length && seguir){
					bc = bytecodes[i].parse(fragment);
					if(bc != null)
						seguir = false;
					else
						i++;
				}
			return bc;	
		}
	}
}