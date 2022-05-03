package commands;

import main.Engine;

public class Help extends Command {
	/**
	 * Imprime "HELP"
	 */
	public String toString(){
		return "HELP";
	}
	/**
	 * Ejecuta el comando HELP
	 */
	public boolean execute(Engine engine){
		return engine.helpProgram();
	}
	/**
	 * Parsea el comando HELP
	 */
	public Command parse(String[] s){
		if(s.length == 1 && s[0].equalsIgnoreCase(("HELP")))
			return new Help();
		else
			return null;
	}
	/**
	 * Ayuda del comando HELP
	 */
	public String textHelp() {
		return "	HELP: Muestra esta ayuda " +
					System.getProperty("line.separator");
	}
}
