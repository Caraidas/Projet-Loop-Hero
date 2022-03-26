package main;

import java.awt.Color;

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
	private final static TimeData timeData = new TimeData();
	private final static Map m = new Map();
	private final static Player player = new Player(0, new Inventory(), 250, 250.0, new Range(4, 6), 0.0, 0.0, 0, 0, 0);
	
	public static void doKeyActionAndDraw(ApplicationContext context, Event event) {
		doKeyAction(context, event);
		View.drawScreen(context, m, player, timeData);
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
		}
		
		m.getCase(m.getPlayerPos(player)).clear();
	}
	
	public void gameLoop(ApplicationContext context) {
		m.generateLoop();
		m.generateMap();
		View.drawScreen(context, m, player, timeData);
		while (true) {
			doPlayerAction(context);
			doEventAction(context);
			doTimeAction(context);
			View.drawScreen(context, m, player, timeData);
		}
	}
	
	public void doTimeAction(ApplicationContext context) {
		if (timeData.timeFraction() > 0.95) { 
			m.spawn();
		}
	}
	
	public void doEventAction(ApplicationContext context) {
		
		Event event = context.pollOrWaitEvent(200);
		
		if (event == null) { // no event
			return;
		}
		
		switch (event.getAction()) {
		case KEY_PRESSED:
			doKeyActionAndDraw(context, event);
			break;
			
		default:
			System.out.println("bravo nidal");
		}
	}
	
	public static void main(String[] args) {
		
		Main controller = new Main();
		Application.run(Color.black, controller::gameLoop);
	}
}
