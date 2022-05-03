package commands;

import exceptions.ArrayException;
import exceptions.ExecutionErrorException;
import main.Engine;

public class Run implements Command {

	/**
	 * Ejecuta el comando RUN
	 * @throws ArrayException 
	 * @throws ExecutionErrorException 
	 */
	public void execute(Engine engine) throws ExecutionErrorException, ArrayException{
		try {
			engine.executeRun();
		} catch (ExecutionErrorException a) {
			throw a;
		} catch (ArrayException b) {
			throw b;
		}
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
	/**
	 * Imprime "RUN"
	 */
	public String toString(){
		return "RUN";
	}
}
