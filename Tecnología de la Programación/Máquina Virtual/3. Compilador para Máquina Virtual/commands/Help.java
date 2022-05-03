package commands;

import main.Engine;

public class Help implements Command {

	/**
	 * Ejecuta el comando HELP
	 */
	public void execute(Engine engine){
		CommandParser.showHelp();
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
	/**
	 * Imprime "HELP"
	 */
	public String toString(){
		return "HELP";
	}
}
