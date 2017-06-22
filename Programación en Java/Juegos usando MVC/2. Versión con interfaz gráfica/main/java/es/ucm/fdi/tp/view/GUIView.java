package main.java.es.ucm.fdi.tp.view;

import javax.swing.BorderFactory;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JPanel;

import main.java.es.ucm.fdi.tp.base.model.GameAction;
import main.java.es.ucm.fdi.tp.base.model.GameState;

public abstract class GUIView<S extends GameState<S, A>, A extends GameAction<S, A>> extends JPanel{
	
	private static final long serialVersionUID = 1L;
	
	
	protected JFrame window;
	
	/**
	 * Crea el JFrame sobre el que se construirá todo lo demás
	 */
	public void enableWindowMode(){
		window = new JFrame("...");
		window.setContentPane(this);
		window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		window.pack();
		window.setSize(900, 700);
		window.setVisible(true);
	}
	
	/**
	 * Destruye el contenido del JFrame y lo pone a 'null'
	 */
	public void disableWindowMode(){
		//...
		window.dispose();
		window = null;
	}
	
	/**
	 * Devuelve la ventana
	 * @return
	 */
	public JFrame getWindow(){
		return window;
	}
	
	/**
	 * Si la ventana existe modifica el título
	 * @param title
	 */
	public void setTitle(String title){
		if(window != null)
			window.setTitle(title);
		else
			this.setBorder(BorderFactory.createTitledBorder(title));
	}

	/** Permite al usuario jugar 
	 * @return */
	public abstract void enable();
	
	/** Impide al usuario jugar */
	public abstract void disable();
	
	/** Actualiza la vista */
	public abstract void update(S state);
	
	/** Modifica el contenido de MessageViewerComp */
	public abstract void setMessageViewer(MessageViewer<S, A> messageInfoViewer);
	
	/** Modifica el contenido de PlayersInfoViewerComp */
	public abstract void setPlayersInfoViewer(PlayersInfoViewer<S, A> playersInfoViewer);
	
	/** Sirve para comunicar GUIController con GUIView */
	public abstract void setGameController(GameController<S, A> gameCtrl);
}