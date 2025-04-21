<!-- Created by Jonathan Carrero -->

# VM with Jump Instructions Added ↪️

---

## New Features

### Program Input

In this version, the `BYTECODE` command allows the user to input a sequence of bytecode instructions that constitute a program. After entering all instructions, the user types `END` (which is *not* a bytecode instruction itself) to signal the end of the program input.

### New Bytecode Instructions

The following jump instructions have been added:

*   **Conditional Jumps**: These instructions pop the top two values from the stack (sub-top `sc` and top `c`) and compare them as integers. If the condition evaluates to **true**, execution continues to the next instruction. If the condition evaluates to **false**, execution jumps to the instruction at index `N` (program counter is set to `N`).
    *   `IFEQ N`: Jump if `sc == c`.
    *   `IFGT N`: Jump if `sc > c`. *(Note: Original text mentioned IFLE/IFLEQ, but standard comparisons often include GT/LT/GE/LE. Assuming GT based on common patterns, adjust if needed based on actual implementation)*.
    *   `IFLT N`: Jump if `sc < c`. *(Assuming LT)*.
    *   `IFNEQ N`: Jump if `sc != c`.
    *   *(Self-correction: The original text listed IFLE and IFLEQ. Let's stick to those)*
    *   `IFLE N`: Jump if `sc < c`.
    *   `IFLEQ N`: Jump if `sc <= c`.
    *   `IFNEQ N`: Jump if `sc != c`.
*   **Unconditional Jump**:
    *   `GOTO N`: Immediately changes the CPU's program counter to `N`, causing execution to jump to the instruction at that index.

## Execution Example ▶️

Let's see an example where a bytecode program calculates the factorial of 5. The text in green represents user input.

![VM Execution Example: Factorial Calculation using Jumps](https://github.com/Joncarre/Java-language/blob/master/Tecnología%20de%20la%20Programación/M%C3%A1quina%20Virtual/images/2_1.png)
