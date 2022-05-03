//
//  Created by Jonathan Carrero.
//  Copyright (c) Jonathan Carrero. All rights reserved.
//

package main.java.es.ucm.fdi.tp.launcher;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

import main.java.es.ucm.fdi.tp.base.console.ConsolePlayer;
import main.java.es.ucm.fdi.tp.base.model.GameAction;
import main.java.es.ucm.fdi.tp.base.model.GamePlayer;
import main.java.es.ucm.fdi.tp.base.model.GameState;
import main.java.es.ucm.fdi.tp.base.player.RandomPlayer;
import main.java.es.ucm.fdi.tp.base.player.SmartPlayer;
import main.java.es.ucm.fdi.tp.extra.jboard.BoardExample;
import main.java.es.ucm.fdi.tp.mvc.GameTable;
import main.java.es.ucm.fdi.tp.ttt.TttAction;
import main.java.es.ucm.fdi.tp.ttt.TttState;
import main.java.es.ucm.fdi.tp.view.ConsoleController;
import main.java.es.ucm.fdi.tp.view.ConsoleView;
import main.java.es.ucm.fdi.tp.view.GUIController;
import main.java.es.ucm.fdi.tp.view.GUIView;
import main.java.es.ucm.fdi.tp.view.GameContainer;
import main.java.es.ucm.fdi.tp.view.GameController;
import main.java.es.ucm.fdi.tp.view.TttView;
import main.java.es.ucm.fdi.tp.view.WasView;
import main.java.es.ucm.fdi.tp.was.WolfAndSheepAction;
import main.java.es.ucm.fdi.tp.was.WolfAndSheepState;

import java.util.concurrent.ThreadLocalRandom;

import javax.swing.SwingUtilities;

/**
 * 
 * @author Jonathan Carrero y Carlos Villasur
 *
 */
public class Main {
	private static String[] nombres = {"Fischer", "Morphy", "Kasparov", "Spassky", "Capablanca", "Lasker", "Karpov", "Alekhine"};
	
	/**
	 * Método main
	 * @param args
	 */
	public static void main (String[] args) {
		if (args.length < 2){
			usage();
			System.exit(1);
		}
		
		GameTable<?, ?> game = createGame(args[0]);
		
		if (game == null){
			System.err.println("Invalid game");
			usage();
			System.exit(1);			
		}
		
		String[] otherArgs = Arrays.copyOfRange(args, 2, args.length);
		
		switch (args[1]) {
			case "console":
				startConsoleMode(args[0], game, otherArgs);
				break;
			case "gui":
				startGUIMode(args[0], game, otherArgs);
				break;
			default:
				System.err.println("Invalid view mode: " + args[1]);
				usage();
				System.exit(1);	
		}
	}
	
	/**
	 * Inicia el juego por consola
	 * @param gType
	 * @param game
	 * @param playerModes
	 */
	private static <S extends GameState<S, A>, A extends GameAction<S, A>> void startConsoleMode(
			String gType, GameTable<S, A> game, String playerModes[]){
		
		List<GamePlayer> players = new ArrayList<GamePlayer>();
		
	        for (int i = 1; i < playerModes.length; i++) {
	            GamePlayer p = createPlayer(playerModes[i]);
	            if (p == null) { // Si la inicialización del jugador i-ésimo es incorrecta...
	                System.err.println("Tipo <" + playerModes[i] + "> de jugador incorrecto");
	                System.exit(1);
	            }
	            players.add(p);  
	        }
	        
		new ConsoleView<S, A>(game);
		new ConsoleController<S, A>(players, game).run();
	}
	
	/**
	 * Inicia el juego por GUI
	 * @param gType
	 * @param game
	 * @param playerModes
	 */
	private static <S extends GameState<S, A>, A extends GameAction<S, A>> void startGUIMode(
			final String gType, final GameTable<S, A> game, String playerModes[]){
		
		/* ---------- Creamos una ventana para cada jugador ---------- */
		for (int i = 0; i < game.getState().getPlayerCount(); i++){
			final GamePlayer player1;
			final GamePlayer player2;
			player1 = new RandomPlayer("Jugador " + i);
			player2 = new SmartPlayer("Jugador " + i, 4);
			player1.join(i);
			player2.join(i);
			
			final int j = i;
			
			try{
				SwingUtilities.invokeAndWait(new Runnable() {
					@SuppressWarnings("unchecked")
					@Override
					public void run() { 
						GameController<S, A> gameCtrl = new GUIController<S, A>(j, player1, player2, game);
						GUIView<S, A> guiView = (GUIView<S, A>)createGameView(gType);
						guiView.setGameController(gameCtrl);
						GUIView<S, A> container = new GameContainer<S, A>(guiView, gameCtrl, game);
						container.enableWindowMode();
					}
				});
			}catch(InvocationTargetException | InterruptedException e){
				System.err.println("Ha ocurrido un error al crear las ventanas.");
				System.exit(1);
			}
		}
		/* ---------- Inicializamos el juego ---------- */
		SwingUtilities.invokeLater(new Runnable() {
			@Override
			public void run() {
				game.start();
			}
		});
	}
	
	/**
	 * Crea un nuevo GameTable (modelo)
	 * @param gType
	 * @return
	 */
	private static GameTable<?, ?> createGame(String gType){
		GameTable<?, ?> game = null;
		if(gType.equals("ttt"))
			game = new GameTable<TttState, TttAction>(new TttState(3));
		else if(gType.equals("was"))
			game = new GameTable<WolfAndSheepState, WolfAndSheepAction>(new WolfAndSheepState());
		return game;
	}
	
	/**
	 * Crea la vista del juego en función de qué juego sea
	 * @param gType
	 * @return
	 */
	private static GUIView<?, ?> createGameView(String gType){
		GUIView<?, ?> gameView = null;
		if(gType.equals("ttt"))
			gameView = new TttView();
		else if(gType.equals("was"))
			gameView = new WasView();
		return gameView;
	}
	
	/**
	 * Crea un jugador (del tipo indicado) para un determinado juego
	 * @param gameName
	 * @param playerType
	 * @return
	 */
	public static GamePlayer createPlayer(String playerType){
		Scanner s = new Scanner(System.in);
		GamePlayer jugador = null;
		if(playerType.equals("manual"))
			jugador = new ConsolePlayer(nombres[ThreadLocalRandom.current().nextInt(0, 8)], s);
		else if(playerType.equals("smart"))
			jugador = new SmartPlayer(nombres[ThreadLocalRandom.current().nextInt(0, 8)], 5);	
		else if(playerType.equals("random"))
			jugador = new RandomPlayer(nombres[ThreadLocalRandom.current().nextInt(0, 8)]);	
		
		return jugador;
	}
	
	/**
	 * Nos indica si el número de argumentos es correcto
	 * @param args
	 * @return
	 */
	public static boolean argsNumber(String[] args){
		if(args.length == 3)
			return true;
		else
			return false;
	}
	
	/**
	 * 
	 */
	private static void usage(){
		// ...
	}
}
 
