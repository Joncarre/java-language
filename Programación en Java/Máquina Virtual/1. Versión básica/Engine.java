import java.util.Scanner;

public class Engine {
	private ByteCodeProgram program;
	private boolean end;
	private CPU cpu;
	Scanner sc = new Scanner(System.in);
	
	/**
	 * Constructora
	 */
	public Engine(){
		this.program = new ByteCodeProgram();
		this.cpu = new CPU();
		this.end = false;
	}
	/**
	 * Controla el flujo de la TPMV
	 */
	public void start(){
		while(!this.end){
			String entrada = sc.nextLine(); // Leemos la entrada
			Command comando = CommandParser.parse(entrada); // Miramos si es un comando a traveś del parseo
			System.out.println("Comienza la ejecución de " + entrada.toUpperCase());
			if(comando != null){ // Si la sintaxis del comando es correcta
				if(!comando.execute(this)) // Si la ejecución del comando es incorrecta
					System.out.println("Error: Ejecución incorrecta del comando" + System.getProperty("line.separator") + this.program.toString());						
			}else // Si la sintaxis es incorrecta
				System.out.println("Error: Comando incorrecto" + System.getProperty("line.separator"));	
		}
	}
	/**
	 * Muestra la ayuda
	 */
	public void commandHelp(){
		System.out.println(
			"   HELP: Muestra esta ayuda" + System.getProperty("line.separator") +
			"   QUIT: Cierra la aplicación" + System.getProperty("line.separator") +
			"   RUN: Ejecuta el programa" + System.getProperty("line.separator") +
			"   NEWINST BYTECODE: Introduce una nueva instrucción al programa" + System.getProperty("line.separator") +
			"   RESET: Vacía el programa actual" + System.getProperty("line.separator") +
			"   REPLACE N: Reemplaza la instrucción N por la solicitada al usuario" + System.getProperty("line.separator")
		);
	}
	/**
	 * Lleva a cabo al operación QUIT
	 */
	public void commandQuit(){
		this.end = true;
		System.out.println(this.program.toString() + 
			System.getProperty("line.separator") +
			"Fin de la ejecución..." + 
			System.getProperty("line.separator")
		);
	}
	/**
	 * Lleva a cabo la operación NEWINST
	 * @param comando
	 */
	public void commandNewinst(ByteCode bc){
		this.program.setInstruction(bc);
		System.out.println(this.program.toString());
	}
	/**
	 * Lleva a cabo la operación RUN
	 */
	public void commandRun(){
		System.out.println(this.program.runProgram(this.cpu) + this.program.toString());
	}
	/**
	 * Lleva a cabo la operación RESET
	 */
	public void commandReset(){
		this.program.reset();
	}
	/**
	 * Lleva a cabo la operación REPLACE
	 */
	public boolean commandReplace(int replace){
		if(replace < this.program.getnumElems()){ // El reemplazamiento debe estar dentro de la longitud de programa
			System.out.println("Nueva instrucción: "); // Pedimos la nueva instrucción
			ByteCode newInstruction = ByteCodeParser.parse(sc.nextLine()); // Recogemos la nueva instrucción
			if(newInstruction != null){ // Si la nueva instrucción es correcta
				this.program.setInstructionPosition(newInstruction, replace);
				System.out.println(this.program.toString());
				return true;
			}else
				return false;
		}else
			return false;
	}
}