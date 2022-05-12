package main;

import java.awt.Color;
import java.awt.geom.Point2D;
import java.util.HashMap;
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
import map.Map;
import time.TimeData;

public class Main {
	private final static Map m = new Map();
	private final static Player player = new Player(0, new HashMap<>(), new Range(4, 6));
	private final static TimeData timeData = new TimeData();
	private final static GameData gameData = new GameData(m, timeData);
	private final static View view = new View(player, timeData, gameData);
	private final BattleData battleData = new BattleData(gameData, timeData, view);
	
	public static void doKeyActionAndDraw(ApplicationContext context, Event event) {
		doKeyAction(context, event);
		view.drawScreen();
	}
	
	public void doTimeAction(ApplicationContext context) {
		if (timeData.dayPassed()) { 
			gameData.spawn();
			gameData.applyDailyBoosts(player);
		}
	}
	
	public static void doKeyAction(ApplicationContext context, Event event) {
		switch (event.getKey()) {
		case SPACE -> {
			System.out.println("Fin du jeu");
			context.exit(0);
			throw new AssertionError("ne devrait pas arriver");
		}
		case S -> {
			if (!gameData.inBattle()) {
				timeData.timeControl();
				view.blackScreen();
				gameData.selectCard(-1);
			}		
		}
		case A -> timeData.accelerateTime(1);
		case Z -> timeData.accelerateTime(2);
		case E -> timeData.accelerateTime(4);
		
		default -> System.out.println("Touche inactive : " + event.getKey());
		}
	}
	
	public void doMouseAction(ApplicationContext context, Event event) {
		Point2D.Float location = event.getLocation();
		
		if (view.clickedOnCards(location)) {
			gameData.selectCard((view.toCardPos(location)));
			timeData.stop(); // planification mode
			
		} else if (view.deposedCard(location)) {
			Card deposedCard = player.selectCard(gameData.selectedCard());		
			
			player.boostStat(deposedCard);
			player.giveRessource(deposedCard);
			
			GridPosition pos = view.toCellPos(location);
			gameData.depositCard(deposedCard, pos);
			player.deck().remove(gameData.selectedCard());
			gameData.selectCard(-1); 
			
		} else if (view.clickedOnItems(location)) {
			System.out.println("clicked on items");
			gameData.selectItem(view.toItemPos(location));
			player.equipItem(gameData.selectedItem());
			gameData.selectItem(-1);
		} else {
			gameData.selectCard(-1);
		}
		
		view.blackScreen();
		view.drawScreen();
	}
	
	private void doPlayerAction(ApplicationContext context) {
		if (timeData.elapsedPlayer() >= TimeData.PLAYER_DELAY) {
			player.updatePosition();
			timeData.resetElapsedBob();
			
			if (player.position() == 0) {
				gameData.updateLoopCount();
				player.heal(0.2);
			}
			player.giveRessourcesWhenCrossed(m.playerCell(player).card());
		}
		
		battleData.startBattle(m.playerCell(player), player);
		m.playerCell(player).clear();
	}
	
	public void doEventAction(ApplicationContext context) { 
		
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
			
			case POINTER_UP ->{
				
			}
			
			default -> {
				System.out.println("pas une action");
			}
		}
	}
	
	public void gameLoop(ApplicationContext context) {
		
		view.initContext(context);
		gameData.generateGameBoard();
		view.drawScreen();
		Card rock = Card.createCard("Rock");
		Card grove = Card.createCard("Grove");
		Card meadow = Card.createCard("Meadow");
		
		player.addCard(grove, 1);
		player.addCard(rock, 1);
		player.addCard(meadow, 1);
		
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
	
	public static void main(String[] args) {
		
		Main controller = new Main();
		Application.run(Color.black, controller::gameLoop);
		System.out.println("Perdu :(");
	}
}
