package commands;

public class CommandParser {
	private final static Command[] commands = {
		new Help(), new Quit(), new Reset(),
		new Replace(1), new Run(), new AddByteCodeProgram() };

	/**
	 * Busca un posible parseo entre las subclases de Command
	 * @param line
	 * @return
	 */
	public static Command parse(String line){
		line = line.trim(); // Elimina caracteres en blanco
		String[] fragment = line.split(" +"); // Divide 'line' y lo mete en el array 'fragment'
		if(fragment.length == 0)
			return null;
		else{
			int i = 0; 
			boolean seguir = true;
			Command c = null;
			while(i < commands.length && seguir){
				c = commands[i].parse(fragment);
				if(c != null)
					seguir = false;
				else
					i++;
			}
			return c;			
		}
	}
	/**
	 * Muestra la ayuda del conjunto de comandos
	 */
	public static void showHelp(){
		for(int i = 0; i < commands.length; i++)
			System.out.print(CommandParser.commands[i].textHelp());
	}
}