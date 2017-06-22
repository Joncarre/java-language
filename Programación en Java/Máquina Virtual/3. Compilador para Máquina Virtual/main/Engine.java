package main;

import java.util.Scanner;

import commands.Command;
import commands.CommandParser;
import cpu.CPU;
import cpu.LexicalParser;
import exceptions.ArrayException;
import exceptions.BadFormatByteCodeException;
import exceptions.ExecutionErrorException;
import exceptions.LexicalAnalysisException;
import bytecodes.ByteCode;
import bytecodes.ByteCodeParser;

import java.io.File;
import java.io.FileNotFoundException;

public class Engine {
	private SourceProgram sProgram;
	private ParsedProgram pProgram;
	private ByteCodeProgram bcProgram;
	private LexicalParser lexParser;
	private cpu.Compiler compiler;
	private boolean end;
	Scanner sc = new Scanner(System.in);

	/**
	 * Constructora
	 */
	public Engine(){
		this.bcProgram = new ByteCodeProgram();
		this.pProgram = new ParsedProgram();
		this.sProgram = new SourceProgram();
		this.lexParser = new LexicalParser();
		this.compiler = new cpu.Compiler();
		this.end = false;
	}
	/**
	 * Controla el flujo de la TPMV
	 */
	public void start(){
		while(!this.end){
			try {
				System.out.print("> ");
				String entrada = sc.nextLine();
				Command comando = CommandParser.parse(entrada);
				if(comando != null){ // Si la sintaxis del comando es correcta
					System.out.println("Comienza la ejecución de " + comando.toString());
						comando.execute(this);
						System.out.println(this.sProgram.toString()); // Se imprime el programa 'sProgram'
						System.out.println(this.bcProgram.toString()); // Se imprime el programa 'bcProgram'
				}else{ // Si es incorrecta
					System.out.println("Error: sintaxis de comando incorrecta" + System.getProperty("line.separator"));
				}
			}catch (FileNotFoundException | LexicalAnalysisException
						| ArrayException | BadFormatByteCodeException
						| ExecutionErrorException e) {
				System.out.println(e.getMessage());
			}
		}
	}
	/**
	 * Lleva a cabo el análisis léxico, el parseo y la generación del programa bytecode
	 * @throws LexicalAnalysisException
	 * @throws ArrayException
	 */
	public void executeCompile() throws LexicalAnalysisException, ArrayException{
		if(!this.sProgram.isEmpty()){
			try{
				this.lexicalAnalysis();
				this.generateByteCode();
			}catch(LexicalAnalysisException a){
				this.pProgram.reset();
				System.out.println(a.getMessage());
			}catch(ArrayException b){
				this.bcProgram.reset();
				System.out.println(b.getMessage());
			}
		}else{
			throw new LexicalAnalysisException("EX: es necesario cargar un programa fuente");
		}
	}
	/**
	 * Lleva a cabo el análisis léxico del programa fuente
	 * @throws LexicalAnalysisException
	 */
	public void lexicalAnalysis() throws LexicalAnalysisException{
		try {
			this.lexParser.initialize(this.sProgram);
			this.lexParser.lexicalParser(this.pProgram, "END");
		} catch (LexicalAnalysisException e) {
			this.pProgram.reset();
			System.out.println(e.getMessage());
		}
	}
	/**
	 * Lleva a cabo la generación del programa bytecode (a partir del programa parseado)
	 * @throws ArrayException
	 */
	public void generateByteCode() throws ArrayException{
		try {
			this.compiler.initialize(this.bcProgram);
			this.compiler.compile(this.pProgram);
		} catch (ArrayException e) {
			throw e;
		}
	}
	/**
	 * Carga el fichero e instancia un objeto de la clase SourceProgram
	 * @param fichName
	 * @throws java.io.FileNotFoundException
	 * @throws ArrayException
	 */
	public void load(String fichName) throws java.io.FileNotFoundException, ArrayException{	
		// Antes de hacer un load hay que hacer un reset de los programas almacenados
		this.resetPrograms();
		try {
			Scanner sc = new Scanner(new File(fichName));
			while(sc.hasNextLine()){
				this.sProgram.addInst(sc.nextLine());
			}
			sc.close();
		} catch (ArrayException a) {
			throw a;
		} catch (java.io.FileNotFoundException a) {
			throw new FileNotFoundException("EX: fichero no encontrado");
		}
	}
	/**
	 * Lleva a cabo el comando QUIT
	 */
	public void executeQuit(){
		this.end = true;
		System.out.println(
			"Fin de la ejecución..." + 
			System.getProperty("line.separator")
		);
	}
	/**
	 * Lleva a cabo el comando REPLACE
	 */
	public void executeReplace(int replace) throws BadFormatByteCodeException, ArrayException{
		try {
			System.out.println("Nueva instrucción: "); // Pedimos el nuevo bytecode
			ByteCode newByteCode = ByteCodeParser.parse(sc.nextLine()); // Recogemos el nuevo bytecode
			if(newByteCode != null){ // Si el nuevo bytecode es correcto
				this.bcProgram.addByteCode(newByteCode, replace);
			}else
				 throw new BadFormatByteCodeException("EX: sintaxis del nuevo bytecode incorrecta");
		} catch (ArrayException e) {
			throw e;
		}
	}
	/**
	 * Lleva a cabo el comando RUN
	 * @return
	 */
	public void executeRun() throws ExecutionErrorException, ArrayException{
		if(!this.pProgram.isEmpty()){
			CPU cpu = new CPU(this.bcProgram);
			try {
				cpu.run();
				System.out.println(cpu.toString());
			} catch (ExecutionErrorException a) {
				throw a;
			} catch (ArrayException b) {
				throw b;
			}
		}else{
			System.out.println("EX: es necesario compilar antes de ejecutar");
		}
	}
	/**
	 * Hace un reset de los programas almacenados
	 */
	public void resetPrograms(){
		this.sProgram.reset();
		this.pProgram.reset();
		this.bcProgram.reset();
		this.lexParser.resetCounter();
	}
}