package commands;

import exceptions.ArrayException;
import exceptions.BadFormatByteCodeException;
import main.Engine;

public class ReplaceByteCode implements Command {
	private int parametro;
	
	/**
	 * Constructora con argumento
	 * @param param
	 */
	public ReplaceByteCode(int param){
		this.parametro = param;
	}
	/**
	 * Ejecuta el comando REPLACEBC
	 */
	public void execute(Engine engine) throws BadFormatByteCodeException, ArrayException{
		try {
			engine.executeReplace(this.parametro);
		} catch (BadFormatByteCodeException a) {
			throw a;
		} catch (ArrayException b) {
			throw b;
		}
	}
	/**
	 * Parsea el comando REPLACEBC
	 */
	public Command parse(String[] s){
		if(s.length == 2 && s[0].equalsIgnoreCase(("REPLACEBC")))
			return new ReplaceByteCode(Integer.parseInt(s[1]));
		else
			return null;
	}
	/**
	 * Ayuda del comando REPLACEBC
	 */
	public String textHelp() {
		return "	REPLACEBC N: Reemplaza la instrucción N por la solicitada al usuario " +
					System.getProperty("line.separator");
	}
	/**
	 * Imprime "REPLACEBC"
	 */
	public String toString(){
		return "REPLACEBC";
	}
}
