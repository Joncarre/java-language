package bytecodes;

import cpu.CPU;
import exceptions.DivisionByZeroException;
import exceptions.StackException;

public interface ByteCode {	
	void execute(CPU cpu) throws DivisionByZeroException, StackException;
	ByteCode parse(String[] s);
}