package main.java.es.ucm.fdi.tp.view;

import java.awt.BorderLayout;
import java.awt.Color;
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
	private ControlPanel<S, A> settingArea;
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
		this.activeGame = false;
		initGUI();
		game.addObserver(this);
	}
	
	/**
	 * Inicializa todo lo necesario para el contenedor principal
	 */
	public void initGUI(){	
		this.setLayout(new BorderLayout(5, 5));

		// settings
		settingArea = new ControlPanel<S, A>(this.gameCtrl);
		this.add(settingArea, BorderLayout.PAGE_START);

		// the game area
		this.add(gameView, BorderLayout.CENTER);

		// side panel
		JPanel sidePanel = new JPanel();
		sidePanel.setLayout(new BoxLayout(sidePanel, BoxLayout.Y_AXIS));
		this.add(sidePanel, BorderLayout.LINE_END);
		messageViewer = new MessageViewerComp<>();
		sidePanel.add(messageViewer);
		playersInfoViewer = new PlayersInfoViewerComp<>();
		sidePanel.add(playersInfoViewer);

		settingArea.setMessageViewer(messageViewer);
		settingArea.setPlayersInfoViewer(playersInfoViewer);

		gameView.setMessageViewer(messageViewer);
		gameView.setPlayersInfoViewer(playersInfoViewer);
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
		if(currTurn == gameCtrl.getPlayerId()) settingArea.enable(); else settingArea.disable();
			
		/* --------------------- handleEvent de GameContainer --------------------- */
		switch (e.getType()) {
			case Start:
				this.setTitle(state.getGameDescription() + " (view for player " + gameCtrl.getPlayerId() + ")");
				messageViewer.addContent("¡Que gane el mejor!");
				playersInfoViewer.setNumberOfPlayer(state.getPlayerCount());
				updateView();
			break;
			case Info:
				updateView();
			break;
			case Change:
				updateView();
			break;
			case Stop:
				updateView();
			break;
			case Error:
				updateView();
				System.err.println(e.getError().getMessage());
			break;
			
			default:
		
			break;
		}
		gameCtrl.handleEvent(e); // Hacemos que entre en acción el handleEvent del controlador	
	}
	
	/**
	 * Actualiza todo lo que tenga que ver con la vista
	 */
	private void updateView() {
		if (!activeGame) { // Si el juego ha finalizado...
			gameView.disable();

			String endText = "The game ended: ";
			int winner = state.getWinner();

			if (winner == -1) {
				endText += "draw!";
			} else {
				endText += "player " + winner + " won!";
			}
			messageViewer.addContent(endText);
		} else if (this.gameCtrl.getPlayerId() == currTurn) { // Si es el turno del jugador actual...
			messageViewer.addContent("Your turn");
			gameView.enable();
		} else { // Si NO es el turno del jugador actual...
			gameView.disable();
			messageViewer.addContent("Turn for player " + currTurn);
		}
		gameView.update(state);
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
		// No hace nada. //
	}

	@Override
	public void setMessageViewer(MessageViewer<S, A> messageViewer) {
		// No hace nada. //
	}

	@Override
	public void setGameController(GameController<S, A> gameCtrl) {
		// No hace nada. //
	}

	@Override
	public void colorChanged(int p, Color color) {
		// No hace nada. //
	}
}