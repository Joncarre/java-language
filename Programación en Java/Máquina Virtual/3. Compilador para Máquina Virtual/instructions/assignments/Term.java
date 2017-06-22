package instructions.assignments;

import exceptions.ArrayException;
import bytecodes.ByteCode;

public interface Term {
	Term parse(String term);
	ByteCode compile(cpu.Compiler compiler) throws ArrayException;
}
