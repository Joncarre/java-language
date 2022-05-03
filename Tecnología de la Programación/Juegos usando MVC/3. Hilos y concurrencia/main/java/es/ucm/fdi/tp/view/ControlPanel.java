package main.java.es.ucm.fdi.tp.view;

import java.awt.Button;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JSpinner;
import javax.swing.JToolBar;
import javax.swing.SpinnerDateModel;
import javax.swing.SpinnerModel;
import javax.swing.SpinnerNumberModel;
import javax.swing.SwingUtilities;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import main.java.es.ucm.fdi.tp.base.Utils;
import main.java.es.ucm.fdi.tp.base.model.GameAction;
import main.java.es.ucm.fdi.tp.base.model.GameState;
import main.java.es.ucm.fdi.tp.ttt.TttAction;
import main.java.es.ucm.fdi.tp.view.GUIController.PlayerMode;

public class ControlPanel<S extends GameState<S, A>, A extends GameAction<S, A>> extends GUIView<S, A> implements SmartMoveObserver{

	private static final long serialVersionUID = 1L;
	
	private GameController<S, A> gameCtrl;
	private MessageViewer<S, A> messageViewer;
	private JLabel thinkingIcon;
	private JButton cancel;
	private JButton randomMove;
	private JButton smartMove;
	
	/**
	 * Constructora
	 * @param gameCtrl
	 */
	public ControlPanel(GameController<S, A> gameCtrl) {
		this.gameCtrl = gameCtrl;
		this.thinkingIcon = new JLabel();
		this.cancel = new JButton();
		this.randomMove = new JButton();
		this.smartMove = new JButton();
		initGUI();
	}
	
	/*   RUTA JONATHAN ->  /home/bobby/Documentos/UCM/4º Año/TP/Práctica 6/P6/src/main/resources   */
	/*   RUTA CARLOS  ->   /Users/carlosvillasurbarahona/Downloads/TP PR5/Práctica 5/P5   */
	
	/**
	 * Inicializa todo lo necesario para el 'ControlPanel'
	 */
	private void initGUI(){
		this.setLayout(new FlowLayout(FlowLayout.LEFT));
		this.setBorder(BorderFactory.createTitledBorder("Control Panel"));
		
		/* ---------------------------- Buttons ---------------------------- */
		
		// --- Botón randomMove ---
		randomMove.setBackground(new Color(240, 240, 240));
		randomMove.setToolTipText("Random Move");
		randomMove.setIcon(new ImageIcon("/home/bobby/Documentos/UCM/4º Año/TP/Práctica 6/P6/src/main/resources/dice.png"));
		this.add(randomMove);
		randomMove.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
			
				gameCtrl.makeRandomMove();
			}
		});
		
		// --- Botón smartMove ---
		smartMove.setBackground(new Color(240, 240, 240));
		smartMove.setToolTipText("Smart move");
		smartMove.setIcon(new ImageIcon("/home/bobby/Documentos/UCM/4º Año/TP/Práctica 6/P6/src/main/resources/nerd.png"));
		this.add(smartMove);
		smartMove.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				gameCtrl.makeSmartMove();
			}
		});
		// --- Botón restart ---
		JButton restart = new JButton();
		restart.setBackground(new Color(240, 240, 240));
		restart.setToolTipText("Restart game");
		restart.setIcon(new ImageIcon("/home/bobby/Documentos/UCM/4º Año/TP/Práctica 6/P6/src/main/resources/restart.png"));
		this.add(restart);
		restart.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				gameCtrl.restartGame();

			}
		});
		// --- Botón exit ---
		JButton exit = new JButton();
		exit.setBackground(new Color(240, 240, 240));
		exit.setToolTipText("Exit game");
		exit.setIcon(new ImageIcon("/home/bobby/Documentos/UCM/4º Año/TP/Práctica 6/P6/src/main/resources/exit.png"));
		this.add(exit);
		exit.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				int n = JOptionPane.showOptionDialog(new JFrame(),
						"¿Seguro que quieres salir?", "Exit Game",
						JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE,
						null, null, null);
				if (n == 0)
					System.exit(0);
			}
		});
		
		/* ---------------------------- JComboBox ---------------------------- */
		
		final JComboBox comboBox = new JComboBox();
		comboBox.addItem("Manual");
		comboBox.addItem("Random");
		comboBox.addItem("Smart");
		comboBox.setMaximumSize(new Dimension(110, 25));
		comboBox.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if(comboBox.getSelectedItem() == "Manual")
					gameCtrl.changePlayerMode(PlayerMode.MANUAL);
				else if(comboBox.getSelectedItem() == "Random")
					gameCtrl.changePlayerMode(PlayerMode.RANDOM);
				else
					gameCtrl.changePlayerMode(PlayerMode.AI);
			}
		});
		JLabel label = new JLabel("  Player Mode: ");
	    this.add(label);
		this.add(comboBox);
		
		/* ---------------------------- Smart Moves ---------------------------- */
		
		thinkingIcon.setIcon(new ImageIcon("/home/bobby/Documentos/UCM/4º Año/TP/Práctica 6/P6/src/main/resources/brain.png"));
		this.add(thinkingIcon);
		thinkingIcon.setOpaque(true);
		final JSpinner threadSpinner = new JSpinner(new SpinnerNumberModel(1, 1, Runtime.getRuntime().availableProcessors(), 1));
		this.add(threadSpinner);
		threadSpinner.addChangeListener(new ChangeListener() {
			@Override
			public void stateChanged(ChangeEvent e) {
				int value = (int) threadSpinner.getValue();
				gameCtrl.smartPlayerConcurrencyLevel(value);
			}
		});

	    this.add(new JLabel(" threads "));
		this.add(new JLabel(new ImageIcon("/home/bobby/Documentos/UCM/4º Año/TP/Práctica 6/P6/src/main/resources/timer.png")));
		
		final JSpinner timeSpinner = new JSpinner(new SpinnerNumberModel(1000, 100, 10000, 100));
		this.add(timeSpinner);
		timeSpinner.addChangeListener(new ChangeListener() {
			@Override
			public void stateChanged(ChangeEvent e) {
				int value = (int) timeSpinner.getValue();
				gameCtrl.smartPlayerTimeLimit(value);
			}
		});

	    this.add(new JLabel(" ms. "));
		
		// --- Botón cancel ---
	    cancel.setBackground(new Color(240, 240, 240));
		cancel.setToolTipText("Smart move");
		cancel.setIcon(new ImageIcon("/home/bobby/Documentos/UCM/4º Año/TP/Práctica 6/P6/src/main/resources/stop.png"));
		this.add(cancel);
		cancel.setEnabled(false);
		cancel.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				messageViewer.addContent("Movimiento cancelado");
				gameCtrl.stopSmartPlayer();
			}
		});
		gameCtrl.addSmartPlayerObserver(this); // Le añadimos como observador
	}
	
	/* ----------------------------------- Modificar información de los mensajes y jugadores ----------------------------------- */
	
	@Override
	public void setMessageViewer(MessageViewer<S, A> messageViewer){
		this.messageViewer = messageViewer;
	}
	
	@Override
	public void setPlayersInfoViewer(PlayersInfoViewer<S, A> playersInfoViewer){
		// No hace nada. //
	}
	
	@Override
	public void setGameController(GameController<S,A> gameCtrl){
		this.gameCtrl = gameCtrl;
	}
	
	/* ----------------------------------- enable, disable & update ----------------------------------- */
	
	@Override
	public void enable(){
		randomMove.setEnabled(true);
		smartMove.setEnabled(true);
	}
	
	@Override
	public void disable(){
		randomMove.setEnabled(false);
		smartMove.setEnabled(false);
	}
	
	@Override
	public void update(S state){
		// No hace nada. //
	}

	@Override
	public void colorChanged(int p, Color color) {
		// No hace nada. //
	}

	@Override
	public void onStart() {
		SwingUtilities.invokeLater(new Runnable() {
			@Override
			public void run() {
				thinkingIcon.setBackground(Color.YELLOW);
				cancel.setEnabled(true);
			}
		});
	}

	@Override
	public void onEnd(boolean success, final long time, final int nodes, final double value) {
		SwingUtilities.invokeLater(new Runnable() {
			@Override
			public void run() {
				thinkingIcon.setBackground(getBackground());
				cancel.setEnabled(false);
				messageViewer.addContent(nodes + " nodes in " + time + " ms. value = " + String.format( "%.5f", value));
			}
		});
	}
}
