//
//  Created by Jonathan Carrero.
//  Copyright (c) Jonathan Carrero. All rights reserved.
//

package main;

import exceptions.ArrayException;
import exceptions.LexicalAnalysisException;

public class Main {
	public static void main(String[] args) throws LexicalAnalysisException, java.io.FileNotFoundException, ArrayException {
		try {
			Engine engine = new Engine();
			engine.start();
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}
}

/** ------------------------------------- JERARQUÍA DE CLASES -------------------------------------
* 
*----ByteCode <---- ByteCodeParser
*		Halt
*		Out
*		Arithmetics
*			Add
*			Sub
*			Mul
*			Div
*		OneParameter
*			Goto
*			Load
*			Push
*			Store
*			ConditionalJumps
*				Ifeq
*				Ifneq
*				Ifle
*				Ifleq
*
*----Command <---- CommandParser
*		Compile
*		Run
*		Help
*		Load
*		Quit
*		ReplaceByteCode
*		Reset
*
*----Exception
*		LexicalAnalysisException
*		ArrayException
*		BadFormatByteCodeException
*		ExecutionErrorException
*			DivisionByZeroException
*			StackException
*
*----Instruction <---- ParserInstruction
*		While
*		IfThen
*		SimpleAssignment
*		CompoundAssignment
*
*----Term <---- TermParser
*		Number
*		Variable
*
*----Condition <---- ConditionParser
*		Equal
*		NotEqual
*		Less
*		LessEq
*
*----Compiler
*----CPU
*----LexicalParser
*----Memory
*----OperandStack
*
*----ByteCodeProgram
*----Engine
*----Main
*----ParsedProgram
*----SourceProgram
*
*/