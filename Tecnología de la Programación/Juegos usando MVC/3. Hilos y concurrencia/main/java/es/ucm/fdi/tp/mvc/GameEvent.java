package main.java.es.ucm.fdi.tp.mvc;

import main.java.es.ucm.fdi.tp.base.model.GameAction;
import main.java.es.ucm.fdi.tp.base.model.GameError;
import main.java.es.ucm.fdi.tp.base.model.GameState;

/**
 * Used to notify game events to observers.
 */
public class GameEvent<S extends GameState<S,A>, A extends GameAction<S,A>> {

    public enum EventType {
        /** Sent at the start of a game */
        Start,
        /** The state of the game has changed; use getState() to get updated state */
        Change,
        /** An error has occured; see getError() to retrieve it */
        Error,
        /** The game has ended */
        Stop,
        /** None of the above; see toString() */
        Info
    }

    private EventType type;
    private A action;
    private S state;
    private GameError error;
    private String description;

    /**
     * Creates a new GameEvent. Not all fields are needed.
     * @param type of action. Always present.
     * @param action that caused this event. Can be null.
     * @param state of the game when event was sent. Can be null if not yet started.
     * @param error reported in an Error event. Must be null if not an error event.
     * @param description intended for use in toString; must NOT be null
     */
    public GameEvent(EventType type, A action, S state, GameError error, String description) {
        this.type = type;
        this.action = action;
        this.state = state;
        this.error = error;
        this.description = description;
    }
    
    /**
     * Devuelve el tipo de evento
     * @return
     */
    public EventType getType() {
        return type;
    }
    
    /**
     * Devuelve la acción
     * @return
     */
    public A getAction() {
        return action;
    }
    
    /**
     * Devuelve el estado
     * @return
     */
    public S getState() {
        return state;
    }
    
    /**
     * Devuelve el error
     * @return
     */
    public GameError getError() {
        return error;
    }
    
    /**
     * Devuelve una breve explicación 
     */
    public String toString() {
        return description;
    }
}