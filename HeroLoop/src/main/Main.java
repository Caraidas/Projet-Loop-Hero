package main;

import java.awt.Color;
import java.awt.geom.Point2D;

import Battle.BattleData;
import collectable.Card;
import data.GameData;
import data.GridPosition;
import data.Range;
import entities.Player;
import fr.umlv.zen5.Application;
import fr.umlv.zen5.ApplicationContext;
import fr.umlv.zen5.Event;
import graphics.View;
import inventory.Inventory;
import map.Map;
import time.TimeData;

public class Main {
	private final static Map m = new Map();
	private final static Player player = new Player(0, 250, new Range(4, 6), 0.0, 0.0, 0, 0, 0);
	private final BattleData battleData = new BattleData();
	private final static GameData gameData = new GameData(m);
	private final static TimeData timeData = new TimeData();
	private final static View view = new View(player, timeData, gameData);
	
	public static void doKeyActionAndDraw(ApplicationContext context, Event event) {
		doKeyAction(context, event);
		view.drawScreen();
	}
	
	public static void doKeyAction(ApplicationContext context, Event event) {
		switch (event.getKey()) {
		case SPACE -> {
			System.out.println("Fin du jeu");
			context.exit(0);
			throw new AssertionError("ne devrait pas arriver");
		}
		case S -> timeData.timeControl();
		case A -> timeData.accelerateTime(1);
		case Z -> timeData.accelerateTime(2);
		case E -> timeData.accelerateTime(4);
		
		default -> System.out.println("Touche inactive : " + event.getKey());
		}
	}
	
	private void doPlayerAction(ApplicationContext context) {
		if (timeData.elapsedPlayer() >= TimeData.PLAYER_DELAY) {
			player.updatePosition();
			timeData.resetElapsedBob();
			
			if (player.position() == 0) {
				gameData.updateLoopCount();
				player.heal(0.2);
			}
		}
		
		battleData.startBattle(m.getCase(m.getPlayerPos(player)), player);
		m.getCase(m.getPlayerPos(player)).clear();
	}
	
	public void gameLoop(ApplicationContext context) {
		
		view.initContext(context);
		gameData.generateGameBoard();
		view.drawScreen();
		player.addCard(new Card("ressources/Card-Sprite/Landscape-Cards/Mountain.png"), 14);
		
		while (true) {
			doTimeAction(context);
			doPlayerAction(context);
			doEventAction(context);
			view.drawScreen();
			if (player.isDead()) {
				context.exit(0);
				break;
			}
		}
	}
	
	public void doTimeAction(ApplicationContext context) {
		if (timeData.timeFraction() > 0.95 && !timeData.stopped()) { 
			gameData.spawn();
		}
	}
	
	public void doMouseAction(ApplicationContext context, Event event) {
		Point2D.Float location = event.getLocation();
		if (view.clickedOnCards(location)) {
			gameData.selectCard((view.toCardPos(location)));
			view.blackScreen();
			timeData.stop();
		} else if (view.deposedCard(location)) {
			GridPosition pos = view.toCellPos(location);
			System.out.println(pos);
			gameData.depositCard(player.selectCard(gameData.selectedCard()), pos);
			player.deck().remove(gameData.selectedCard());
			gameData.selectCard(-1);
		} else {
			gameData.selectCard(-1);
		}
		
		view.blackScreen();
		view.drawScreen();
	}
	
	public void doEventAction(ApplicationContext context) { // R�alise les actions li�s � l'utilisateur
		
		Event event = context.pollOrWaitEvent(200);
		
		if (event == null) { // no event
			return;
		}
		
		switch (event.getAction()) {
			case KEY_PRESSED -> {
				doKeyActionAndDraw(context, event);
			}
			
			case POINTER_DOWN -> {
				doMouseAction(context, event);
			}
			default -> {
				System.out.println("bravo nidal");
			}
		}
	}
	
	public static void main(String[] args) {
		
		Main controller = new Main();
		Application.run(Color.black, controller::gameLoop);
		System.out.println("Perdu :(");
	}
}
