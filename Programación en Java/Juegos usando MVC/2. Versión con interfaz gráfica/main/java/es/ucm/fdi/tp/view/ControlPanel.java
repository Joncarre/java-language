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

import main.java.es.ucm.fdi.tp.base.Utils;
import main.java.es.ucm.fdi.tp.base.model.GameAction;
import main.java.es.ucm.fdi.tp.base.model.GameState;
import main.java.es.ucm.fdi.tp.ttt.TttAction;
import main.java.es.ucm.fdi.tp.view.GUIController.PlayerMode;

public class ControlPanel<S extends GameState<S, A>, A extends GameAction<S, A>> extends GUIView<S, A> {

	private static final long serialVersionUID = 1L;
	
	private GameController<S, A> gameCtrl;
	
	/**
	 * Constructora
	 * @param gameCtrl
	 */
	public ControlPanel(GameController<S, A> gameCtrl) {
		this.gameCtrl = gameCtrl;
		initGUI();
	}
	
	/**
	 * Inicializa todo lo necesario para el 'ControlPanel'
	 */
	private void initGUI(){
		this.setLayout(new FlowLayout(FlowLayout.LEFT));
		/* ---------------------------- Buttons ---------------------------- */
		this.setBorder(BorderFactory.createTitledBorder("Control Panel"));
		// --- Botón randomMove ---
		JButton randomMove = new JButton();
		randomMove.setToolTipText("Random Move");
		randomMove.setIcon(new ImageIcon("/home/bobby/Documentos/UCM/4º Año/TP/Práctica 5/P5/src/main/resources/dice.png"));
		this.add(randomMove);
		randomMove.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
			
				gameCtrl.makeRandomMove();
			}
		});
		// --- Botón smartMove ---
		JButton smartMove = new JButton();
		smartMove.setToolTipText("Smart move");
		smartMove.setIcon(new ImageIcon("/home/bobby/Documentos/UCM/4º Año/TP/Práctica 5/P5/src/main/resources/nerd.png"));
		this.add(smartMove);
		smartMove.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				gameCtrl.makeSmartMove();
			}
		});
		// --- Botón restart ---
		JButton restart = new JButton();
		restart.setToolTipText("Restart game");
		restart.setIcon(new ImageIcon("/home/bobby/Documentos/UCM/4º Año/TP/Práctica 5/P5/src/main/resources/restart.png"));
		this.add(restart);
		restart.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				gameCtrl.restartGame();
			}
		});
		// --- Botón exit ---
		JButton exit = new JButton();
		exit.setToolTipText("Exit game");
		exit.setIcon(new ImageIcon("/home/bobby/Documentos/UCM/4º Año/TP/Práctica 5/P5/src/main/resources/exit.png"));
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
		comboBox.setMaximumSize(new Dimension(110, 25));
		comboBox.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if(comboBox.getSelectedItem() == "Manual")
					gameCtrl.changePlayerMode(PlayerMode.MANUAL);
				else
					gameCtrl.changePlayerMode(PlayerMode.RANDOM);	
			}
		});
		JLabel label1 = new JLabel("  Player Mode: ");
	    this.add(label1);
		this.add(comboBox);
		
		/* ---------------------------- Smart Moves ---------------------------- */
		
		JLabel label2 = new JLabel();
		label2.setIcon(new ImageIcon("/home/bobby/Documentos/UCM/4º Año/TP/Práctica 5/P5/src/main/resources/brain.png"));
		this.add(label2);
		
		SpinnerModel spinnerThreads = new SpinnerNumberModel(0, 0, 10, 1);
		JSpinner spinnerContentThreads = new JSpinner(spinnerThreads);
		this.add(spinnerContentThreads);
		
		JLabel label3 = new JLabel(" threads ");
	    this.add(label3);
	    
		JLabel label4 = new JLabel();
		label4.setIcon(new ImageIcon("/home/bobby/Documentos/UCM/4º Año/TP/Práctica 5/P5/src/main/resources/timer.png"));
		this.add(label4);
		
		SpinnerModel spinnerTime = new SpinnerNumberModel(0, 0, 10000, 100);
		JSpinner spinnerContentTime = new JSpinner(spinnerTime);
		this.add(spinnerContentTime);
	    
		JLabel label5 = new JLabel(" ms. ");
	    this.add(label5);
		
		// --- Botón cancel ---
		JButton cancel = new JButton();
		cancel.setToolTipText("Smart move");
		cancel.setIcon(new ImageIcon("/home/bobby/Documentos/UCM/4º Año/TP/Práctica 5/P5/src/main/resources/stop.png"));
		this.add(cancel);
		cancel.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {

			}
		});
	}

	/* ----------------------------------- Modificar información de los mensajes y jugadores ----------------------------------- */
	
	@Override
	public void setMessageViewer(MessageViewer<S, A> messageViewer){
		// No hace nada. //
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
		// No hace nada. //
	}
	
	@Override
	public void disable(){
		// No hace nada. //
	}
	
	@Override
	public void update(S state){
		// No hace nada. //
	}
}
