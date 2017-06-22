package commands;

import java.io.FileNotFoundException;

import main.Engine;
import exceptions.ArrayException;


public class Load implements Command {
	private String fichName;
	
	/**
	 * Constructora
	 * @param fichName
	 */
	public Load(String fichName){
		this.fichName = fichName;
	}
	@Override
	public void execute(Engine engine) throws FileNotFoundException, ArrayException {
		engine.load(this.fichName);
	}
	@Override
	public Command parse(String[] s) {
		if(s.length == 2 && s[0].equalsIgnoreCase(("LOAD")))
			return new Load(s[1]);
		else
			return null;
	}
	@Override
	public String textHelp() {
		return "	LOAD FICH: Carga el fichero 'FICH' como programa fuente " +
					System.getProperty("line.separator");
	}
	/**
	 * Imprime "LOAD"
	 */
	public String toString(){
		return "LOAD" + " " + this.fichName;
	}
}
