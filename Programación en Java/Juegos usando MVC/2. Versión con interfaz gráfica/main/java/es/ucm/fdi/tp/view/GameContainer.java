package main.java.es.ucm.fdi.tp.view;

import java.awt.BorderLayout;
import java.awt.Component;

import javax.swing.BoxLayout;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

import main.java.es.ucm.fdi.tp.base.model.GameAction;
import main.java.es.ucm.fdi.tp.base.model.GameState;
import main.java.es.ucm.fdi.tp.mvc.GameEvent;
import main.java.es.ucm.fdi.tp.mvc.GameObservable;
import main.java.es.ucm.fdi.tp.mvc.GameObserver;
import main.java.es.ucm.fdi.tp.mvc.GameEvent.EventType;

public class GameContainer<S extends GameState<S, A>, A extends GameAction<S, A>> extends GUIView<S, A> implements GameObserver<S, A>{

	private static final long serialVersionUID = 1L;
	
	private GUIView<S, A> gameView;
	private MessageViewer<S, A> messageViewer;
	private PlayersInfoViewer<S, A> playersInfoViewer;
	private ControlPanel<S, A> controlPanel;
	private GameController<S, A> gameCtrl;
	private S state;
	private int currTurn;
	private boolean activeGame;
	
	/**
	 * Constructora con parámetros
	 * @param gameView
	 * @param gameCtrl
	 * @param game
	 */
	public GameContainer(GUIView<S, A> gameView, GameController<S, A> gameCtrl, GameObservable<S, A> game){
		this.gameView = gameView;
		this.gameCtrl = gameCtrl;
		initGUI();
		game.addObserver(this);
	}
	
	/**
	 * Inicializa todo lo necesario para el contenedor principal
	 */
	public void initGUI(){	
		controlPanel = new ControlPanel<S, A>(this.gameCtrl);
		playersInfoViewer = new PlayersInfoViewerComp<S, A>(2);
		messageViewer = new MessageViewerComp<S, A>();

		this.setLayout(new BorderLayout(5, 5));
		//this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS)); // Y_AXIS para que se distribuya el contenido de forma vertical
		
		this.add(playersInfoViewer, BorderLayout.EAST);
		this.add(messageViewer, BorderLayout.EAST);
		this.add(controlPanel, BorderLayout.PAGE_START);
		this.add(gameView, BorderLayout.CENTER);
		this.setOpaque(true);
	}
	
	@Override
	public void notifyEvent(final GameEvent<S, A> e) {
		SwingUtilities.invokeLater(new Runnable(){
			@Override
			public void run(){
				handleEvent(e);
			}
		});
	}
	
	/**
	 * Realiza la acción indicada por el evento. Al finalizar, llama al handleEvent del controlador
	 * @param e
	 */
	public void handleEvent(final GameEvent<S, A> e) {
		state = e.getState();
		currTurn = state.getTurn();
		activeGame = !state.isFinished() || e.getType() == EventType.Stop;
	
		if(gameCtrl.getPlayerId() == state.getTurn())
			messageViewer.addContent("Tu turno.");
		else
			messageViewer.addContent("Turno del jugador " + state.getTurn() + ".");
		
		/* --------------------- handleEvent de GameContainer --------------------- */
		switch (e.getType()) {
			case Start:
				this.setTitle(state.getGameDescription() + " (view for player " + gameCtrl.getPlayerId() + ")");
				messageViewer.setContent(e.toString());
				messageViewer.addContent("Comienza el jugador " + state.getTurn() + ".");
				gameView.update(state);
			break;
			case Info:

			break;
			case Change:
				if(state.isFinished()){ // Si el juego ha terminado...
					String endText = "Fin del juego: ";
					int winner = state.getWinner();
					if (winner == -1)
						endText += "¡Empate!";
					else
						endText += "¡El jugador " + winner + " ganó!";
					messageViewer.addContent(endText);
				}
				gameView.update(state);
			break;
			case Stop:

			break;
			case Error:

			break;
			
			default:
		
			break;
		}
		gameCtrl.handleEvent(e); // Hacemos que entre en acción el handleEvent del controlador	
	}

	@Override
	public void enable() {
		// No hace nada. //
	}

	@Override
	public void disable() {
		// No hace nada. //
	}

	@Override
	public void update(S state) {
		// No hace nada. //
	}

	@Override
	public void setPlayersInfoViewer(PlayersInfoViewer<S, A> playersInfoViewer) {
		this.playersInfoViewer = playersInfoViewer;	
	}

	@Override
	public void setMessageViewer(MessageViewer<S, A> messageViewer) {
		this.messageViewer = messageViewer;
	}

	@Override
	public void setGameController(GameController<S, A> gameCtrl) {
		this.gameCtrl = gameCtrl;
	}
}