package commands;

import main.Engine;

public class Run extends Command {
	/**
	 * Imprime "RUN"
	 */
	public String toString(){
		return "RUN";
	}
	/**
	 * Ejecuta el comando RUN
	 */
	public boolean execute(Engine engine){
		return engine.runProgram();
	}
	/**
	 * Parsea el comando RUN
	 */
	public Command parse(String[] s){
		if(s.length == 1 && s[0].equalsIgnoreCase(("RUN")))
			return new Run();
		else
			return null;
	}
	/**
	 * Ayuda del comando RUN
	 */
	public String textHelp() {
		return "	RUN: Ejecuta el programa " +
					System.getProperty("line.separator");
	}
}
