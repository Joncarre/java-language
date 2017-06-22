package commands;

import main.Engine;

public class Reset extends Command {
	/**
	 * Imprime "RESET"
	 */
	public String toString(){
		return "RESET";
	}
	/**
	 * Ejecuta el comando RESET
	 */
	public boolean execute(Engine engine){
		return engine.resetProgram();
	}
	/**
	 * Parsea el comando RESET
	 */
	public Command parse(String[] s){
		if(s.length == 1 && s[0].equalsIgnoreCase(("RESET")))
			return new Reset();
		else
			return null;
	}
	/**
	 * Ayuda del comando RESET
	 */
	public String textHelp() {
		return "	RESET: Vacía el programa actual " +
					System.getProperty("line.separator");
	}
}
