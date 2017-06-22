

public class ByteCodeParser {
	/**
	 * Parsea la cadena que recibe por parámetro para 
	 * saber si en ella hay almacenado un 'ByteCode'
	 * @param chain
	 * @return
	 */
	public static ByteCode parse(String s){
		s = s.trim(); // Elimina caracteres en blanco
		String[] fragment = s.split(" +"); // Divide 's' y lo mete en el array 'fragment'
		if(fragment.length == 0) // Si el parámetro viene vacío
			return null;
		else{
			if(fragment.length == 1){ // Si tiene longitud 1 es que sólo está el nombre
				if(fragment[0].equalsIgnoreCase("ADD"))
					return new ByteCode(ENUM_BYTECODE.ADD);
				else if(fragment[0].equalsIgnoreCase("SUB"))
					return new ByteCode(ENUM_BYTECODE.SUB);
				else if(fragment[0].equalsIgnoreCase("MUL"))
					return new ByteCode(ENUM_BYTECODE.MUL);
				else if(fragment[0].equalsIgnoreCase("DIV"))
					return new ByteCode(ENUM_BYTECODE.DIV);
				else if(fragment[0].equalsIgnoreCase("OUT"))
					return new ByteCode(ENUM_BYTECODE.OUT);
				else if(fragment[0].equalsIgnoreCase("HALT"))
					return new ByteCode(ENUM_BYTECODE.HALT);
				else 
					return null;
			}else if(fragment.length == 2){ // Si tiene longitud 2 es que hay nombre + parámetro
				if(fragment[0].equalsIgnoreCase("PUSH"))
					return new ByteCode(ENUM_BYTECODE.PUSH, Integer.parseInt(fragment[1]));
				else if(fragment[0].equalsIgnoreCase("LOAD"))
					return new ByteCode(ENUM_BYTECODE.LOAD, Integer.parseInt(fragment[1]));
				else if(fragment[0].equalsIgnoreCase("STORE"))
					return new ByteCode(ENUM_BYTECODE.STORE, Integer.parseInt(fragment[1]));
				else
					return null;
			}else
				return null;
		}
	}
}