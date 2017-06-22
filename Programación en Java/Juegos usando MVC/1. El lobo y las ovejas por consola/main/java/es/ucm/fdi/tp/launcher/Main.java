//
//  Created by Jonathan Carrero.
//  Copyright (c) Jonathan Carrero. All rights reserved.
//

package main.java.es.ucm.fdi.tp.launcher;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import main.java.es.ucm.fdi.tp.base.console.ConsolePlayer;
import main.java.es.ucm.fdi.tp.base.model.GameAction;
import main.java.es.ucm.fdi.tp.base.model.GamePlayer;
import main.java.es.ucm.fdi.tp.base.model.GameState;
import main.java.es.ucm.fdi.tp.base.player.RandomPlayer;
import main.java.es.ucm.fdi.tp.base.player.SmartPlayer;
import main.java.es.ucm.fdi.tp.ttt.TttState;
import main.java.es.ucm.fdi.tp.was.WolfAndSheepState;

import java.util.concurrent.ThreadLocalRandom;

public class Main {
	private static String[] nombres = {"Fischer", "Murphy", "Kasparov", "Spassky", "Capablanca", "Lasker", "Karpov", "Alekhine"};
	
	/**
	 * Método main
	 * @param args
	 */
	public static void main(String[] args) {
		if(Main.argsNumber(args)){
			GameState<?, ?> initialState = createInitialState(args[0]);
	        if (initialState == null) { // Si el estado inicial se ha inicializado correctamente...
	            System.err.println("Juego no válido");
	            System.exit(1);
	        }
	        if (initialState.getPlayerCount() != args.length - 1){ // Si el número de jugadores es correcto...
	            System.err.println("Configuración de jugadores incorrecta");
	            System.exit(1);
	        }
	        
	    List<GamePlayer> players = new ArrayList<GamePlayer>();
	    
	        for (int i = 1; i < args.length; i++) {
	            GamePlayer p = createPlayer(args[i]);
	            if (p == null) { // Si la inicialización del jugador i-ésimo es incorrecta...
	                System.err.println("Tipo <" + args[i] + "> de jugador incorrecto");
	                System.exit(1);
	            }
	            players.add(p);  
	        }
	    playGame(initialState, players);
		}else{
            System.err.println("Número de argumentos incorrecto");
            System.exit(1);
		}
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
	 * Controla el flujo de ejecución de una partida
	 * @param initialState
	 * @param players
	 * @return
	 */
	public static <S extends GameState<S, A>, A extends GameAction<S, A>> int playGame(GameState<S, A> initialState, List<GamePlayer> players) {
		int playerCount = 0;
		for (GamePlayer p : players) {
			p.join(playerCount++); // welcome each player, and assign
									// playerNumber
		}
		@SuppressWarnings("unchecked")
		S currentState = (S) initialState;

		while (!currentState.isFinished()) {
			// request move
			A action = players.get(currentState.getTurn()).requestAction(currentState);
			// apply move
			currentState = action.applyTo(currentState);
			System.out.println("After action:\n" + currentState);

			if (currentState.isFinished()) {
				// game over
				String endText = "The game ended: ";
				int winner = currentState.getWinner();
				if (winner == -1) {
					endText += "draw!";
				} else {
					endText += "player " + (winner + 1) + " (" + players.get(winner).getName() + ") won!";
				}
				System.out.println(endText);
			}
		}
		return currentState.getWinner();
	}
	
	/**
	 * Crea un estado inicial para el juego pasado por parámetro
	 * @param gameName
	 * @return
	 */
	public static GameState<?, ?> createInitialState(String gameName){
		GameState<?, ?> game = null;
		if(gameName.equals("ttt"))
			game = new TttState(3);
		else if(gameName.equals("was"))
			game = new WolfAndSheepState();
		return game;
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
		if(playerType.equals("console"))
			jugador = new ConsolePlayer(nombres[ThreadLocalRandom.current().nextInt(0, 8)], s);
		else if(playerType.equals("smart"))
			jugador = new SmartPlayer(nombres[ThreadLocalRandom.current().nextInt(0, 8)], 5);	
		else if(playerType.equals("rand"))
			jugador = new RandomPlayer(nombres[ThreadLocalRandom.current().nextInt(0, 8)]);	
		
		return jugador;
	}
	
	/**
	 * Repeatedly plays a game-state with a vs b
	 * @param initialState
	 * @param a
	 * @param b
	 * @param times
	 */
	public static void match(GameState<?, ?> initialState, GamePlayer a, GamePlayer b, int times) {
		int va = 0, vb = 0;

		List<GamePlayer> players = new ArrayList<GamePlayer>();
		players.add(a);
		players.add(b);

		for (int i = 0; i < times; i++) {
			switch (playGame(initialState, players)) {
			case 0:
				va++;
				break;
			case 1:
				vb++;
				break;
			}
		}
		System.out.println("Result: " + va + " for " + a.getName() + " vs " + vb + " for " + b.getName());
	}
}
