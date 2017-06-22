package cpu;

import instructions.Instruction;
import instructions.ParserInstruction;
import exceptions.LexicalAnalysisException;
import main.ParsedProgram;
import main.SourceProgram;

public class LexicalParser {
	private SourceProgram sProgram;
	private int programCounter;
	
	/**
	 * Lleva a cabo el parseo de un programa fuente
	 * @param pProgram
	 * @param stopKey
	 * @throws LexicalAnalysisException
	 */
	public void lexicalParser(ParsedProgram pProgram, String stopKey)throws LexicalAnalysisException{
		boolean stop = false;
		try {
			while(this.programCounter < sProgram.getSize() && !stop){
				String inst_i = sProgram.getInst(this.programCounter); // Tomamos la instrucción i-ésima del 'sProgram'
				inst_i = inst_i.trim(); // Quitamos espacios en blanco
				if(inst_i.equalsIgnoreCase(stopKey)){
					stop = true;
				}else{
					Instruction instruction = ParserInstruction.parse(inst_i, this); // Y buscamos un posible parseo
					if(instruction != null){ // Si el parseo fue bien
						pProgram.addInst(instruction); // La añadimos como una instrucción más del 'pProgram'
						this.increaseProgramCounter();
					}else{
						throw new LexicalAnalysisException("EX: la intrucción " +  this.programCounter + " es incorrecta");
					}
				}
			}
			if(!stop){ // Si no hay instrucción de final de programa
				throw new LexicalAnalysisException("EX: falta la intrucción END del final");
			}
		} catch (LexicalAnalysisException e) {
			throw e;
		}
	}
	/**
	 * Incrementa el contador de programa
	 */
	public void increaseProgramCounter(){
		this.programCounter++;
	}
	/**
	 * Inicializa el programa fuente que se parseará gracias al que se leyó con LOAD
	 * @param sProgram
	 */
	public void initialize(SourceProgram sProgram){
		this.sProgram = sProgram;
	}
	/**
	 * Resetea el contador de programa (para cuando se hacen los reset de los programas almacenados)
	 */
	public void resetCounter(){
		this.programCounter = 0;
	}
}
