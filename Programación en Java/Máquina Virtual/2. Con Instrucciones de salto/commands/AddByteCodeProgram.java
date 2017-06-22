package commands;

import main.Engine;

public class AddByteCodeProgram extends Command {
	/**
	 * Imprime "BYTECODE"
	 */
	public String toString(){
		return "BYTECODE";
	}
	/**
	 * Ejecuta el comando BYTECODE
	 */
	public boolean execute(Engine engine){
		return engine.readByteCodeProgram();
	}
	/**
	 * Parsea el comando BYTECODE
	 */
	public Command parse(String[] s){
		if(s.length == 1 && s[0].equalsIgnoreCase(("BYTECODE")))
			return new AddByteCodeProgram();
		else
			return null;
	}
	/**
	 * Ayuda del comando BYTECODE
	 */
	public String textHelp() {
		return "	BYTECODE: Permite añadir instrucciones al programa " +
					System.getProperty("line.separator");
	}
}
