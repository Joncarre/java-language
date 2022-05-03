package main.java.es.ucm.fdi.tp.mvc;

import java.util.ArrayList;
import java.util.List;

import main.java.es.ucm.fdi.tp.base.model.GameAction;
import main.java.es.ucm.fdi.tp.base.model.GameError;
import main.java.es.ucm.fdi.tp.base.model.GameState;
import main.java.es.ucm.fdi.tp.mvc.GameEvent.EventType;

/**
 * An event-driven game engine.
 * Keeps a list of players and a state, and notifies observers
 * of any changes to the game.
 */
public class GameTable<S extends GameState<S, A>, A extends GameAction<S, A>> implements GameObservable<S, A> {

	private S state;
	private S initState;
	private boolean active;
	protected List<GameObserver<S, A>> obs;
	
	/**
	 * Constructora con parámetros
	 * @param initState
	 */
    public GameTable(S initState) {
    	if(initState == null)
    		throw new GameError("El estado inicial del juego no puede ser null.");
        this.initState = initState;
        this.state = initState;
        this.active = false;
        obs = new ArrayList<GameObserver<S, A>>();
    }
    
    /**
     * Lanza el juego
     */
    public void start() {
        this.state = this.initState;
        this.active = true;
        notifyObservers(new GameEvent<>(EventType.Start, null, this.state, null, "El juego ha empezado."));
    }
    
    /**
     * Reincia el juego
     */
	public void restart() {
		if(this.active){
			this.state = this.initState;
	        notifyObservers(new GameEvent<>(EventType.Start, null, this.state, null, "El juego se reinició."));
		}else{
        	GameError err = new GameError("El juego estaba parado.");
        	notifyObservers(new GameEvent<>(EventType.Error, null, state, err, "El juego estaba parado."));
        	throw err;
		}
	}
    
    /**
     * Detiene el juego
     */
    public void stop() {
    	if(this.active){
            this.active = false;
            notifyObservers(new GameEvent<>(EventType.Start, null, state, null, "El juego se ha detenido.")); 
        }else{
        	GameError err = new GameError("El juego estaba parado.");
        	notifyObservers(new GameEvent<>(EventType.Error, null, state, err, "El juego estaba parado."));
        	throw err;
        }
    }
    
    /**
     * Ejecuta la acción dada sobre el estado actual
     * @param action
     */
    public void execute(A action) {
    	try{
    		if(this.active){
                this.state = action.applyTo(state);
                notifyObservers(new GameEvent<>(EventType.Change, action, this.state, null, "Acción ejecutada con éxito.")); 	
    		}else{
            	GameError err = new GameError("El juego estaba parado.");
            	notifyObservers(new GameEvent<>(EventType.Error, null, state, err, "El juego estaba parado."));
            	throw err;
    		}
    	}catch(IllegalArgumentException e){
    		GameError err = new GameError("Error al ejecutar la acción.");
    		notifyObservers(new GameEvent<>(EventType.Error, null, state, err, "Error al ejecutar la acción.")); 
    		throw err;
    	}
    }
    
    /**
     * Actualiza el estado actual
     * @param state
     */
    public void setState(S state){
    	this.state = state;
    }
    
    /**
     * Devuelve el estado actual
     * @return
     */
    public S getState() {
    	return this.state;
    }
    
    /**
     * Devuelve si el juego está activo o no
     * @return
     */
    public boolean isActive(){
    	return this.active;
    }
    
    /**
     * Notifica el evento a todos los obervadores
     * @param e
     */
    protected void notifyObservers(GameEvent<S, A> e){
    	for(GameObserver<S, A> o : obs)
    		o.notifyEvent(e); // Llama al método de la interfaz 'GameObserver'
    }
    
    @Override
    public void addObserver(GameObserver<S, A> o) {
		obs.add(o);
		if(this.active){
			// Si el juego ya había comenzado, enviamos un evento con el estado actual
			o.notifyEvent(new GameEvent<>(EventType.Info, null, state, null, "Estado actual del juego."));
		}
    }
    
    @Override
    public void removeObserver(GameObserver<S, A> o) {
		obs.remove(o);	
    }
}
