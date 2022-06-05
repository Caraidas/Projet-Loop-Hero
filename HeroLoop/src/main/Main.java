package main;

import java.awt.Color;
import java.awt.geom.Point2D;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.HashMap;
import Battle.BattleData;
import collectable.Card;
import collectable.EnteringBoost;
import collectable.Item;
import collectable.OverGrownField;
import collectable.SpawnCard;
import collectable.Village;
import collectable.ZoneCard;
import data.GameData;
import data.GridPosition;
import data.Range;
import entities.Player;
import fr.umlv.zen5.Application;
import fr.umlv.zen5.ApplicationContext;
import fr.umlv.zen5.Event;
import graphics.View;
import map.Map;
import map.RoadCell;
import save.Save;
import time.TimeData;

public class Main {
	private final static Map m = new Map();
	private static Player player = new Player(0, new HashMap<>(), new Range(20, 22), 0, 0, 0, 0);
	private static TimeData timeData = new TimeData();
	private static GameData gameData = new GameData(m, timeData);
	private static View view = new View(player, timeData, gameData);
	private BattleData battleData = new BattleData(gameData, timeData, view);
	private final static Save save = new Save();
	
	public static void doKeyActionAndDraw(ApplicationContext context, Event event) throws FileNotFoundException, IOException {
		doKeyAction(context, event);
		view.drawScreen();
	}
	
	public void doTimeAction(ApplicationContext context) {
		if (timeData.dayPassed()) { 
			gameData.spawn();
			gameData.cardAction(player);
		}
		
		if (timeData.elapsedRegen() >= TimeData.REGEN_DELAY) {
			player.globalheal(player.getStat("regen"));
			timeData.resetElapsedRegen();
		}
	}
	
	public static void doKeyAction(ApplicationContext context, Event event) throws FileNotFoundException, IOException {
		switch (event.getKey()) {
		case SPACE -> {
			System.out.println("Fin du jeu");
			context.exit(0);
			throw new AssertionError("ne devrait pas arriver");
		}
		
		case Q -> {
			save.write(gameData, timeData, player);
		}
	
		case S -> {  // do freeze or play the game
			if (!gameData.inBattle()) {
				timeData.timeControl();
				view.blackScreen();
				gameData.selectCard(-1);
			}	
		}
		
		case A -> timeData.accelerateTime(1); // set the acceleration * 1
		case Z -> timeData.accelerateTime(2); // set the acceleration * 2 
		case E -> timeData.accelerateTime(4); // set the acceleration * 4
		
		case D -> {
			player.addItemInInventory(Item.debugItem(gameData.getLoopCount()));
			view.drawScreen();
		}
		
		case C -> {
			player.clearInventory();
			gameData.selectItem(-1);
			view.drawScreen();
		}
		
		default -> System.out.println("Touche inactive : " + event.getKey());
		}
	}
	
	public void doMouseAction(ApplicationContext context, Event event) throws FileNotFoundException, IOException, ClassNotFoundException {
		
		Point2D.Float location = event.getLocation();
		
		if (!gameData.inGame()) {
			if (view.clickedOnPlay(location)) {
				gameData.startGame();
				
			} else if (view.clickedOnContinue(location)) {
				gameData = (GameData)save.read("saves/gameData.txt");
				timeData = (TimeData)save.read("saves/timeData.txt");
				player = (Player)save.read("saves/player.txt");
				
				gameData.setTimeData(timeData); 
				gameData.startGame();
				
				view = new View(player, timeData, gameData);
				battleData = new BattleData(gameData, timeData, view);
				view.initContext(context);
			}
			
		} else {
			if (view.clickedOnRessources(location)) {
				gameData.updateRessourceState();
				
			} else {
				gameData.updateRessourceState(false);
			}
			
			if (view.clickedOnCards(location)) {
				gameData.selectCard((view.toCardPos(location)));
				timeData.stop(); // planification mode
				
			} else if (view.deposedCard(location)) {
				Card deposedCard = player.selectCard(gameData.selectedCard());
				GridPosition pos = view.toCellPos(location);
				
				if (deposedCard instanceof SpawnCard) {
					((SpawnCard)deposedCard).setBirthday(timeData.dayCount());
				} else if (deposedCard instanceof ZoneCard) {
					((ZoneCard)deposedCard).setPosition(pos, gameData);
				} else if (deposedCard instanceof Village) {
					((Village)deposedCard).createFields(gameData, pos);
				}
				
				gameData.depositCard(deposedCard, pos);
				
				if (deposedCard instanceof EnteringBoost == false) {
					deposedCard.cardAction(player, gameData, timeData, pos);
				} 
				
				deposedCard.giveRessource(player);
				
				player.deck().remove(gameData.selectedCard());
				gameData.selectCard(-1);
				
			} else if (view.clickedOnItems(location)) {
				gameData.selectItem(view.toItemPos(location));
				timeData.stop();
				
			} else if (view.deposedItem(location)) {
				player.equipItem(gameData.selectedItem());
				gameData.selectItem(-1);
			} else {
				gameData.selectCard(-1);
				gameData.selectItem(-1);
			}
			
			view.blackScreen();
			view.drawScreen();
		}
	}
	
	private void doPlayerAction(ApplicationContext context) {
		if (timeData.elapsedPlayer() >= TimeData.PLAYER_DELAY) { // rajouter dans time data playerMoved avec dedans
			// le updatePosition et le player delay deveindrai un multiplicateur de la stat
			player.updatePosition();
			System.out.println(((RoadCell)gameData.map().playerCell(player)).getEntities().size());
			timeData.resetElapsedBob();
			
			if (gameData.map().playerCell(player).card() instanceof EnteringBoost) {
				((Village)gameData.map().playerCell(player).card()).cardAction(player, gameData, 
						timeData, gameData.map().getPlayerPos(player));
			}
			
			if (player.position() == 0) {
				gameData.updateLoopCount();
				player.heal(0.2);
			}
		}
		
		if (gameData.map().playerCell(player).card() instanceof OverGrownField) {
			GridPosition g = gameData.map().getPlayerPos(player);
			((OverGrownField)gameData.map().playerCell(player).card()).fill(g.line(), g.column(), gameData);
		}

		battleData.startBattle(gameData.map().playerCell(player), player);
	}
	
	public void doEventAction(ApplicationContext context) throws FileNotFoundException, IOException, ClassNotFoundException { 
		Event event = context.pollOrWaitEvent(50);
		
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
	
	public void intro(ApplicationContext context) throws FileNotFoundException, IOException, ClassNotFoundException {
		while (!gameData.inGame()) {
			doEventAction(context);
		}
	}
	
	public void gameLoop(ApplicationContext context) throws FileNotFoundException, IOException, ClassNotFoundException {
		
		view.initContext(context);
		gameData.endGame();
		gameData.generateGameBoard();
		view.drawScreen();
		
		intro(context);	
		view.blackScreen();
		view.drawScreen();
		
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
		Application.run(Color.black, t -> {
			try {
				controller.gameLoop(t);
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			} catch (ClassNotFoundException e) {
				e.printStackTrace();
			}
		});
		System.out.println("Perdu :(");
	}
}
