
public class ByteCodeProgram {
	private ByteCode[] program;
	private int size; // Indica el tamaño del array 'this.program'
	private int numElems; // Indica el número de elementos del array 'this.program'

	/**
	 * Constructora
	 */
	public ByteCodeProgram(){
		this.size = 1; // Si pusieramos the.size = 0, al multiplicar x 2 en el resize() daría problemas
		this.program = new ByteCode[this.size];
		this.numElems = 0;
	}
	/**
	 * Imprimir el programa almacenado
	 */
	public String toString(){
		String mensaje;
		if(this.size > 0){
			mensaje = "Programa almacenado:" + System.getProperty("line.separator");
			for(int i = 0; i < this.numElems; i++)
				mensaje += i + ": " + this.program[i].toString() + System.getProperty("line.separator");
		}else
			mensaje = "";
		return mensaje;
	}
	/**
	 * Ejecuta las instrucciones almacenadas en 'this.program'
	 */
	public String runProgram(CPU cpu){
		String mensaje = "";
		for(int i = 0; i < this.numElems; i++){ // Ejecuta todas las intrucciones que tenga el programa
			if(!cpu.isHalt() && cpu.execute(this.program[i])) // Si no está parada y no hay error en la ejecución
				mensaje +=
				"El estado de la máquina tras ejecutar el bytecode " + this.program[i].toString() + " es: " + 
				System.getProperty("line.separator") +
				System.getProperty("line.separator") +
				cpu.toString() +
				System.getProperty("line.separator");
			else if(!cpu.isHalt()){ // Si hay error en la ejecución
				mensaje += "Error: Ejecución incorrecta del comando" + 
				System.getProperty("line.separator") +
				System.getProperty("line.separator");
			}
		}
		cpu.erase(); // Al terminar el programa limpiamos la CPU
		cpu.runCPU(); // Y la volvemos a poner en marcha (por si anteriormente estaba parada)
		return mensaje;
	}
	/**
	 * Inserta una nueva instrucción en el programa
	 * @param bc
	 */
	public void setInstruction(ByteCode bc){
		if(this.numElems == this.size) // Si hemos llegado al tamaño que tenía el array
			this.resize(); // Redimensionamos
		this.program[this.numElems] = bc; // Insertamos la intrucción 'bc'
		this.numElems++;
	}
	/**
	 * Inserta una nueva instrucción en la posición 'pos'
	 * @param bc
	 * @param pos
	 */
	public boolean setInstructionPosition(ByteCode bc, int pos){
		if(pos >= 0 && pos < this.numElems){ // Si 'pos' está en el rango adecuado
			this.program[pos] = bc;
			return true;
		}else
			return false;
	}
	/**
	 * Devuelve la instrucción i-ésima del programa
	 * @param i
	 * @return
	 */
	public ByteCode getIntruction(int i){
		if(i >= 0 && i < this.numElems)
			return this.program[i];
		else
			return null;
	}
	/**
	 * Redimensiona el array 'this.program'
	 */
	public void resize(){
		ByteCode[] programAux = new ByteCode[this.size * 2]; // Aumentamos el tamaño al doble
		for(int i = 0; i < this.size * 2; i++)
			programAux[i] = null; // Inicializamos las posiciones a 'null'
		for(int i = 0; i < this.size; i++)
			programAux[i] = this.program[i]; // Copiamos el valor en el nuevo array
		this.program = programAux;
		this.size *= 2;
	}
	/**
	 * "Borra" el programa almacenado
	 */
	public void reset(){
		this.size = 1;
		this.numElems = 0;
	}
	/**
	 * Devuelve el número de elementos que tiene 'this.program'
	 * @return
	 */
	public int getnumElems(){
		return this.numElems;
	}
}