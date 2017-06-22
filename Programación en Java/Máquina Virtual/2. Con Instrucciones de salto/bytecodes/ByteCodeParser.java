package bytecodes;

import oneparameter.Goto;
import oneparameter.Load;
import oneparameter.Push;
import oneparameter.Store;
import arithmetics.Add;
import arithmetics.Div;
import arithmetics.Mul;
import arithmetics.Sub;
import conditionaljumps.Ifeq;
import conditionaljumps.Ifle;
import conditionaljumps.Ifleq;
import conditionaljumps.Ifneq;

public class ByteCodeParser {
	private final static ByteCode[] bytecodes = {
		new Add(), new Sub(), new Mul(), new Div(),
		new Halt(), new Out(),
		new Ifeq(1), new Ifneq(1), new Ifle(1), new Ifleq(1),
		new Goto(), new Load(), new Store(), new Push() 
	};
	/**
	 * Busca un posible parseo entre las subclases de ByteCode
	 * @param line
	 * @return
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