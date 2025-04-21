<!-- Created by Jonathan Carrero -->

# Compiler for Virtual Machine 📜➡️⚙️

---

## Description

This version introduces a **compiler**, allowing users to write programs in a **simple imperative language**. These source programs are then translated (compiled) into bytecode that the Virtual Machine can execute.

### Source Language Syntax Requirements

Source programs written in a file must adhere to the following syntax rules:

*   Each assignment (`variable = expression`) or `write` instruction must be on its own line.
*   There should be **no blank lines** anywhere in the file, including at the beginning or end.
*   The program must end with an `end` statement.
*   Supported constructs include variable assignment, simple arithmetic expressions, `while` loops, and `write` for output.

Here is an example of a valid program that calculates the factorial of 5 (stored in variable `r`) and prints the result:

```
x = 5
r = 1
while 0 < x
    r = r * x
    x = x - 1
endwhile
write r
end
```

### Available Commands

The set of commands available in this version is:

*   `HELP`: Displays help information for the available commands.
*   `QUIT`: Exits the application.
*   `LOAD FICH`: Loads the file named `FICH` as the source program. *Note: This command might not perform syntax checking.*
*   `REPLACEBC N`: Prompts the user for a new bytecode instruction and replaces the instruction at line `N` of the *current bytecode program* (if one exists).
*   `COMPILE`: Performs lexical analysis and parsing of the loaded source program, generating a corresponding bytecode program.
*   `RUN`: Executes the current bytecode program (similar to the previous version).

## Execution Example ▶️

While the previous version allowed direct input of bytecode instructions, this version introduces a two-phase process: loading/compiling a source file first.

1.  **Load Source Program**: First, we load a program written in the simple imperative language using the `LOAD` command. This step reads the source code from the specified file.

    ![Compiler Execution: Loading the source file](https://github.com/Joncarre/Java-language/blob/master/Tecnología%20de%20la%20Programación/M%C3%A1quina%20Virtual/images/3_1.png)

2.  **Compile Program**: Next, we use the `COMPILE` command. This phase analyzes the source code for correctness (parsing) and translates it into the equivalent VM bytecode instructions. Error checking (e.g., for invalid syntax or jumps) occurs here.

    ![Compiler Execution: Compiling the source code to bytecode](https://github.com/Joncarre/Java-language/blob/master/Tecnología%20de%20la%20Programación/M%C3%A1quina%20Virtual/images/3_2.png)

3.  **Run Bytecode**: Finally, once the program is successfully compiled into bytecode, we can execute it using the `RUN` command. In this example, the compiled bytecode calculates the factorial of 5. User input is shown in green.

    ![Compiler Execution: Running the compiled bytecode (Factorial 5)](https://github.com/Joncarre/Java-language/blob/master/Tecnología%20de%20la%20Programación/M%C3%A1quina%20Virtual/images/3_3.png)

