

public class ByteCode {	
	private ENUM_BYTECODE name;
	private int param; // 'param' es necesario en PUSH, STORE y LOAD
	
	/**
	 * Constructora
	 * @param operation
	 */
	public ByteCode (ENUM_BYTECODE operation){
		this.name = operation;
	}
	/**
	 * Constructora
	 * @param operation
	 * @param param
	 */
	public ByteCode(ENUM_BYTECODE operation, int param){
		this.name = operation;
		this.param = param;
	}
	/**
	 * Imprime por pantalla la instrucción correspondiente
	 */
	public String toString(){
		if(name.getValueArg() > 0)
			return this.name + " " + this.param;
		else
			return this.name + "";
	}
	/**
	 * Devuelve el nombre de la instrucción
	 * @return
	 */
	public ENUM_BYTECODE getInstruction(){
		return this.name;
	}
	/**
	 * Devuelve el valor del parámetro de la instrucción
	 * @return
	 */
	public int getParam(){
		return this.param;
	}
}