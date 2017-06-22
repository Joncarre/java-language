
public class Command {
	private ENUM_COMMAND command;
	private ByteCode instruction; // Toma valor cuando se referencia 'NEWINST'
	private int replace; // Lo mismo pero para 'REPLACE'
	
	/**
	 * Constructora
	 * @param operation
	 */
	public Command(ENUM_COMMAND operation){
		this.command = operation;
	}
	/**
	 * Constructora
	 * @param operation
	 * @param replace
	 */
	public Command(ENUM_COMMAND operation, int replace){
		this.command = operation;
		this.replace = replace;
	}
	/**
	 * Constructora
	 * @param operation
	 * @param instruction
	 */
	public Command(ENUM_COMMAND operation, ByteCode instruction){
		this.command = operation;
		this.instruction = instruction;
	}
	/**
	 * Lleva a cabo la ejecución del comando
	 * @param engine
	 * @return
	 */
	public boolean execute(Engine engine){
		if(this.command == ENUM_COMMAND.HELP){
			engine.commandHelp();
			return true;
		}else if (this.command == ENUM_COMMAND.QUIT){
			engine.commandQuit();
			return true;
		}else if (this.command == ENUM_COMMAND.NEWINST){
			engine.commandNewinst(this.instruction);
			return true;
		}else if (this.command == ENUM_COMMAND.RUN){
			engine.commandRun();
			return true;
		}else if (this.command == ENUM_COMMAND.RESET){
			engine.commandReset();
			return true;
		}else if (this.command == ENUM_COMMAND.REPLACE){
			if(engine.commandReplace(this.replace))
				return true;
			else
				return false;
		}else
			return false;
	}
}