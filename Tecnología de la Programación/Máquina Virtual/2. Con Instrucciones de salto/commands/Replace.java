package commands;

import main.Engine;

public class Replace extends Command {
	private int parametro;
	/**
	 * Constructora con argumento
	 * @param param
	 */
	public Replace(int param){
		this.parametro = param;
	}
	/**
	 * Imprime "REPLACE"
	 */
	public String toString(){
		return "REPLACE";
	}
	/**
	 * Ejecuta el comando REPLACE
	 */
	public boolean execute(Engine engine){
		return engine.replaceProgram(this.parametro);
	}
	/**
	 * Parsea el comando REPLACE
	 */
	public Command parse(String[] s){
		if(s.length == 2 && s[0].equalsIgnoreCase(("REPLACE")))
			return new Replace(Integer.parseInt(s[1]));
		else
			return null;
	}
	/**
	 * Ayuda del comando REPLACE
	 */
	public String textHelp() {
		return "	REPLACE N: Reemplaza la instrucción N por la solicitada al usuario " +
					System.getProperty("line.separator");
	}
}
