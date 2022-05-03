package main.java.es.ucm.fdi.tp.view;

import main.java.es.ucm.fdi.tp.base.model.GameAction;
import main.java.es.ucm.fdi.tp.base.model.GameState;

public abstract class MessageViewer<S extends GameState<S, A>, A extends GameAction<S, A>> extends GUIView<S, A> {

	private static final long serialVersionUID = 1L;
	
	/**
	 * Modifica el contenido de MessageViewerComp
	 */
	public void setMessageViewer(MessageViewer<S, A> messageInfoViewer){
		// Es vacío por defecto. Puede sobreescribirse en las subclases. //
	}
	
	/**
	 * Limpia el MessagesView
	 */
	abstract public void clearMessagesViewer();
	
	/**
	 * Añade mensajes
	 * @param msg
	 */
	abstract public void addContent(String msg);
	
	/**
	 * Modifica el texto
	 * @param msg
	 */
	abstract public void setContent(String msg);
	
	/**
	 * Devuelve el texto del JTextArea
	 * @return
	 */
	abstract public String getContent();
}
