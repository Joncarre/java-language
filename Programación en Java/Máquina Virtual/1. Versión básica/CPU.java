
public class CPU {
	private OperandStack pila;
	private Memory memoria;
	private boolean halt;
	
	/**
	 * Constructora
	 */
	public CPU(){
		this.pila = new OperandStack();
		this.memoria = new Memory();
		this.halt = false;
	}
	/**
	 * Imprimir estado de la CPU
	 */
	public String toString(){
		String mensaje = "Estado de la CPU:" + 
		System.getProperty("line.separator") + 
		memoria.toString() +
		System.getProperty("line.separator") +
		pila.toString() + 
		System.getProperty("line.separator");
		return mensaje;
	}
	/**
	 * Lleva a cabo la ejecución de las intrucciones Bytecode
	 * @param bc
	 * @return
	 */
	public boolean execute(ByteCode bc){
		if(bc.getInstruction() == ENUM_BYTECODE.ADD){
			if(sumaPila())
				return true;
			else
				return false;
		}else if(bc.getInstruction() == ENUM_BYTECODE.SUB){
			if(restaPila())
				return true;
			else
				return false;
		}else if(bc.getInstruction() == ENUM_BYTECODE.MUL){
			if(multiplicaPila())
				return true;
			else
				return false;
		}else if(bc.getInstruction() == ENUM_BYTECODE.DIV){
			if(dividePila())
				return true;
			else
				return false;
		}else if(bc.getInstruction() == ENUM_BYTECODE.HALT){
			this.halt = true;
			return true;
		}else if(bc.getInstruction() == ENUM_BYTECODE.OUT){
			if(!pila.isEmpty()){
				System.out.println("La cima de la pila es: " + pila.getCima() + System.getProperty("line.separator"));
				return true;
			}else
				return false;
		}else if(bc.getInstruction() == ENUM_BYTECODE.PUSH){
			if(pila.push(bc.getParam()))
				return true;
			else
				return false;
		}else if(bc.getInstruction() == ENUM_BYTECODE.LOAD){
			if(pila.push(memoria.read(bc.getParam()))) // Lee de memoria el valor en 'pos' y haz un push
				return true;
			else
				return false;
		}else if(bc.getInstruction() == ENUM_BYTECODE.STORE){
			if(!pila.isEmpty()){
				if(memoria.write(bc.getParam(), pila.pop())) // Escribe en 'pos' lo que haya en la cima de la pila (y lo elimina)
					return true;
				else
					return false;
			}else
				return false;
		}else
			return false;
	}
	/**
	 * Limpiar la CPU (crea otra memoria y pila)
	 */
	public void erase(){
		this.pila = new OperandStack();
		this.memoria = new Memory();
	}
	/**
	 * Permite poner la CPU en marcha (se activa después de runProgram)
	 * pues entiendo que, al finalizar el programa, la CPU deja de estar parada
	 * y vuelve a ponerse en marcha (aunque hasta que no se haga un RESET del programa
	 * seguirá pasando lo mismo una y otra vez)
	 */
	public void runCPU(){
		this.halt = false;
	}
	/**
	 * Nos dice si la MV está parada
	 * @return
	 */
	public boolean isHalt(){
		return this.halt;
	}
	/**
	 * Operación ADD
	 * @return
	 */
	public boolean sumaPila(){
		int a = pila.pop();
		int b = pila.pop();
		if(b != -1){ // Si sólo hay 1 elemento	
			int resultado = b + a;
			pila.push(resultado);
			return true;
		}else{
			pila.push(a); // ¡Es necesario volver a meter 'a' en la pila!
			return false;
		}
	}
	/**
	 * Operación SUB
	 * @return
	 */
	public boolean restaPila(){
		int a = pila.pop();
		int b = pila.pop();
		if (b != -1){ // Si sólo hay 1 elemento
			int resultado = b - a;
			pila.push(resultado);
			return true;
		}else{
			pila.push(a); // ¡Es necesario volver a meter 'a' en la pila!
			return false;			
		}
	}
	/**
	 * Operación MUL
	 * @return
	 */
	public boolean multiplicaPila(){
		int a = pila.pop();
		int b = pila.pop();
		if(b != -1){ // Si sólo hay 1 elemento
			int resultado = b * a;
			pila.push(resultado);
			return true;
		}else{
			pila.push(a); // ¡Es necesario volver a meter 'a' en la pila!
			return false;			
		}
	}
	/**
	 * Operación DIV
	 * @return
	 */
	public boolean dividePila(){
		int a = pila.pop();
		int b = pila.pop();
		if (b != -1){ // Si sólo hay 1 elemento
			if(a != 0){ // Si el divisor es != 0
				int resultado = b / a;
				pila.push(resultado);
				return true;
			}else
				return false;
		}else{
			pila.push(a); // ¡Es necesario volver a meter 'a' en la pila!
			return false;			
		}
	}
}