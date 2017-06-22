package cpu;

import exceptions.ArrayException;
import instructions.Instruction;
import bytecodes.ByteCode;
import main.ByteCodeProgram;
import main.ParsedProgram;

public class Compiler {
	public static final int MAX_VARS = 100;
	private ByteCodeProgram bcProgram;
	private String[] varTable;
	private int numVars;
	
	/**
	 * Constructora
	 */
	public Compiler(){
		this.varTable = new String[Compiler.MAX_VARS];
		this.numVars = 0;
	}
	/**
	 * Lleva a cabo la compilación completa del programa 'pProgram'
	 * @param pProgram
	 * @throws ArrayException
	 */
	public void compile(ParsedProgram pProgram) throws ArrayException{
		int i = 0;
		try {
			while(i < pProgram.getSize()){
				Instruction inst = pProgram.getInst(i);
				inst.compile(this);
				i++;
			}	
		} catch (ArrayException e) {
			throw e;
		}
	}
	/**
	 * Añade una instrucción bytecode al 'bcProgram'
	 * @param bc
	 * @throws ArrayException
	 */
	public void addByteCode(ByteCode bc) throws ArrayException{
		try {
			this.bcProgram.addNextByteCode(bc);
		} catch (ArrayException e) {
			throw e;
		}
	}
	/**
	 * Devuelve el índice en el que se encuentra 'nameVar' en la tabla de variables
	 * O, si no la encuentra, la añade
	 * @param nameVar
	 * @return
	 */
	public int getIndex(String nameVar){
		int i = 0;
		boolean seguir = true;
		while(i < this.numVars && seguir){
			if(this.varTable[i].equalsIgnoreCase(nameVar)){
				seguir = false;
			}else{
				i++;
			}
		}
		if(seguir){
			this.varTable[this.numVars] = nameVar;
			this.numVars++;
			return this.numVars - 1;
		}else{
			return i;
		}
	}
	/**
	 * Inicializa el programa bytecode
	 * @param sProgram
	 */
	public void initialize(ByteCodeProgram bcProgram){
		this.bcProgram = bcProgram;
	}
	/**
	 * Devuelve el tamaño del programa bytecode
	 * @return
	 */
	public int getSizeBcProgram(){
		return this.bcProgram.getSize();
	}
	
	public String toString(){
		return "";
	}
}
