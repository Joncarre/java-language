package commands;

import exceptions.ArrayException;
import exceptions.BadFormatByteCodeException;
import exceptions.DivisionByZeroException;
import exceptions.ExecutionErrorException;
import exceptions.LexicalAnalysisException;
import exceptions.StackException;
import main.Engine;

public interface Command {
	void execute(Engine engine) throws java.io.FileNotFoundException,
										LexicalAnalysisException,
										ArrayException,
										BadFormatByteCodeException,
										StackException,
										DivisionByZeroException,
										ExecutionErrorException;
	Command parse(String[] s);
	String textHelp();
}