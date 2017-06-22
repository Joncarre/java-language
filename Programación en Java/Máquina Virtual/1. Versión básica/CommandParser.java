
public class CommandParser {
	/**
	 * Parsea la cadena que recibe por parámetro para 
	 * saber si en ella hay almacenado un 'Command'
	 * @param line
	 * @return
	 */
	public static Command parse(String line){
		line = line.trim(); // Elimina caracteres en blanco
		String[] fragment = line.split(" +"); // Divide 'line' y lo mete en el array 'fragment'
		if(fragment.length == 0) // Si el parámetro viene vacío
			return null;
		else{
			if (fragment.length == 1){ // Si tiene longitud 1 es que sólo hay comando
				if(fragment[0].equalsIgnoreCase("HELP"))
					return new Command(ENUM_COMMAND.HELP);
				else if(fragment[0].equalsIgnoreCase("QUIT"))
					return new Command(ENUM_COMMAND.QUIT);
				else if(fragment[0].equalsIgnoreCase("RUN"))
					return new Command(ENUM_COMMAND.RUN);
				else if(fragment[0].equalsIgnoreCase("RESET"))
					return new Command(ENUM_COMMAND.RESET);
				else
					return null;
			} else if(fragment.length == 2){ // Si tiene longitud 2 es que viene comando + instrucción
				if(ByteCodeParser.parse(fragment[1]) != null){ // Si el ByteCode es distinto de 'null' (el parseo es correcto)
					if(fragment[0].equalsIgnoreCase("NEWINST"))
						return new Command(ENUM_COMMAND.NEWINST, ByteCodeParser.parse(fragment[1]));
					else
						return null;
				}else if(fragment[0].equalsIgnoreCase("REPLACE"))
					return new Command(ENUM_COMMAND.REPLACE, Integer.parseInt(fragment[1]));
				else
					return null;		
			}else if(fragment.length == 3){ // Si tiene longitud 3 es que viene comando + instrucción + parámetro
				if(ByteCodeParser.parse(fragment[1] + " " + Integer.parseInt(fragment[2])) != null){  // Si el ByteCode es distinto de 'null' (el parseo es correcto)
					if(fragment[0].equalsIgnoreCase("NEWINST"))
						return new Command(ENUM_COMMAND.NEWINST, ByteCodeParser.parse(fragment[1] + " " + Integer.parseInt(fragment[2])));
					else 
						return null;
				}else 
					return null;
			}else
				return null;
		}
	}
}