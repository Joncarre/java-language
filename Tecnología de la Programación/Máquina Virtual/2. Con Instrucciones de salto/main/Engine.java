package main;

import java.util.Scanner;

import commands.Command;
import commands.CommandParser;
import cpu.CPU;
import bytecodes.ByteCode;
import bytecodes.ByteCodeParser;

public class Engine {
	private ByteCodeProgram program;
	private boolean end;
	Scanner sc = new Scanner(System.in);
	
	/**
	 * Constructora
	 */
	public Engine(){
		this.program = new ByteCodeProgram();
		this.end = false;
	}
	/**
	 * Controla el flujo de la TPMV
	 */
	public void start(){
		while(!this.end){
			System.out.print("> ");
			String entrada = sc.nextLine();
			Command comando = CommandParser.parse(entrada);
			if(comando != null){ // Si la sintaxis del comando es correcta
				System.out.println("Comienza la ejecución de " + comando.toString().toUpperCase());
				if(!comando.execute(this)) // Si la ejecución es incorrecta
					System.out.println("Error: Ejecución incorrecta del comando");
				if(!this.end)
					System.out.println(this.program.toString()); // Imprimir el programa actual
			}else // Si la sintaxis es incorrecta
				System.out.println("Error: Sintaxis incorrecta" + System.getProperty("line.separator"));	
		}
	}
	/**
	 * Realiza la lectura de instrucciones y construye el programa
	 * @return
	 */
	public boolean readByteCodeProgram(){
		System.out.println("Introduce el bytecode. Una instrucción por línea:" +
							System.getProperty("line.separator"));
		String entrada = "";
		boolean seguir = true;
		ByteCode c = null;
		entrada = sc.nextLine(); // Leemos la entrada
		while(!entrada.equalsIgnoreCase("END") && seguir){
			c = ByteCodeParser.parse(entrada);
			if(c != null){ // Si el parseo es correcto
				this.program.setInstruction(c); // Añadimos la instrucción al programa
				entrada = sc.nextLine(); // Leemos la entrada otra vez
			}else if (c == null)
				seguir = false;		
		}
		return seguir;
	}
	// -------------------------------- COMANDOS --------------------------------
	/**
	 * Lleva a cabo el comando RUN
	 * @return
	 */
	public boolean runProgram(){
		CPU cpu = new CPU(this.program); // Creamos una nueva CPU con el programa que hemos leído
		if(cpu.run()){
			System.out.println(cpu.toString());
			return true;
		}else
			return false;
	}
	/**
	 * Lleva a cabo el comando HELP
	 */
	public boolean helpProgram(){
		CommandParser.showHelp();
		return true;
	}
	/**
	 * Lleva a cabo el comando QUIT
	 */
	public boolean quitProgram(){
		this.end = true;
		System.out.println(
			"Fin de la ejecución..." + 
			System.getProperty("line.separator")
		);
		return true;
	}
	/**
	 * Lleva a cabo el comando RESET
	 */
	public boolean resetProgram(){
		return this.program.reset();
	}
	/**
	 * Lleva a cabo el comando REPLACE
	 */
	public boolean replaceProgram(int replace){
		if(replace < this.program.getnumElems() && replace >= 0){ // El reemplazamiento debe estar entre 0 y la longitud del programa
			System.out.println("Nueva instrucción: "); // Pedimos la nueva instrucción
			ByteCode newInstruction = ByteCodeParser.parse(sc.nextLine()); // Recogemos la nueva instrucción
			if(newInstruction != null){ // Si la nueva instrucción es correcta
				this.program.setInstructionPosition(newInstruction, replace);
				return true;
			}else
				return false;
		}else
			return false;
	}
}