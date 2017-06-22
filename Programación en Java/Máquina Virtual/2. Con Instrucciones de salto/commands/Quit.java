package commands;

import main.Engine;

public class Quit extends Command {
	/**
	 * Imprime "QUIT"
	 */
	public String toString(){
		return "QUIT";
	}
	/**
	 * Ejecuta el comando QUIT
	 */
	public boolean execute(Engine engine){
		return engine.quitProgram();
	}
	/**
	 * Parsea el comando QUIT
	 */
	public Command parse(String[] s){
		if(s.length == 1 && s[0].equalsIgnoreCase(("QUIT")))
			return new Quit();
		else
			return null;
	}
	/**
	 * Ayuda del comando QUIT
	 */
	public String textHelp() {
		return "	QUIT: Cierra la aplicación " +
					System.getProperty("line.separator");
	}
}
