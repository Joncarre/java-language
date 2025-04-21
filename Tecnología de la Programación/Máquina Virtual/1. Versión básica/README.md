<!-- Created by Jonathan Carrero -->

# Basic Virtual Machine (Stack-Based) 💻⚙️

---

## Introduction

A **virtual machine (VM)** is software that simulates a computer system and can execute programs as if it were a real computer. (For more general info, see Wikipedia: [https://en.wikipedia.org/wiki/Virtual_machine](https://en.wikipedia.org/wiki/Virtual_machine)). VMs have various uses, with one common application being testing operating systems without altering the host system.

The VM presented here is a **stack machine**. Its main components are an *operand stack* and a *memory* area.
*   The **operand stack** stores intermediate data generated during execution.
*   The **memory** allows storing some of this data, acting similarly to a program's variable table.

## Description

This Virtual Machine consists of two simple parts:

*   **Memory**: Capable of storing integer data. The minimum unit is an integer (unlike real machines which often use bytes). Memory capacity is conceptually *unlimited*; you can write to any address (≥ 0) up to the limits of the underlying physical machine's memory.
*   **Operand Stack**: Where operations are performed. Most bytecode instructions operate on this stack, consuming values from it and/or pushing results onto it.

### Supported Bytecode Instructions

This initial version of the VM supports the following bytecode instructions:

*   `PUSH n`: Pushes the integer `n` onto the operand stack.
*   `LOAD pos`: Reads the value stored at memory address `pos` and pushes it onto the operand stack.
*   `STORE pos`: Pops the top value from the operand stack and writes it to memory address `pos`.
*   `ADD`, `SUB`, `MUL`, `DIV`: Arithmetic operations (Sum, Subtract, Multiply, Divide).
    *   They use the top two values on the stack as operands (sub-top and top).
    *   Both operands are popped, and the result is pushed back onto the stack.
    *   The *first* operand is the sub-top value (important for non-commutative operations like SUB and DIV).
*   `OUT`: Pops the top value from the stack and prints it to the console.
*   `HALT`: Stops the VM execution.

## Execution Example ▶️

Below is an example of running a simulation on this VM. Note that commands and bytecode instructions are **case-insensitive** (you can type them in upper or lower case). However, when the application displays instructions or bytecode, it will use uppercase.

When displaying the machine's state, the application shows the contents of the **stack** and **memory** after each bytecode instruction executes.
*   If either is empty, it's indicated as `<vacia>` (empty).
*   For memory, only occupied positions (those that have been written to at some point) are displayed.

Here's an execution example. The text in green represents user input typed into the console.

![Basic VM Execution Example](https://github.com/Joncarre/Java-language/blob/master/Tecnología%20de%20la%20Programación/M%C3%A1quina%20Virtual/images/1_1.png)

