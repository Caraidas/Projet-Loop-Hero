package main;

import java.awt.Color;

import entities.Player;
import fr.umlv.zen5.Application;
import fr.umlv.zen5.ApplicationContext;
import fr.umlv.zen5.Event;
import graphics.View;
import map.Map;
import time.TimeData;

public class Main {
	private final static TimeData timeData = new TimeData();
	
	public static void doKeyActionAndDraw(ApplicationContext context, Event event, Map m, Player player) {
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
	
	private void doPlayerAction(ApplicationContext context, Player player) {
		if (timeData.elapsedPlayer() >= TimeData.PLAYER_DELAY) {
			player.updatePosition();
			timeData.resetElapsedBob();
		}
	}
	
	public void gameLoop(ApplicationContext context) {
		Map m = new Map();
		m.generateLoop();
		m.generateMap();
		Player player = new Player(0);
		View.drawScreen(context, m, player, timeData);
		while (true) {
			doPlayerAction(context, player);
			code(context, m, player);
			View.drawScreen(context, m, player, timeData);
		}
	}
	
	public void code(ApplicationContext context, Map m, Player player) {
		
		Event event = context.pollOrWaitEvent(200);
		
		if (event == null) { // no event
			return;
		}
		
		switch (event.getAction()) {
		case KEY_PRESSED:
			doKeyActionAndDraw(context, event, m, player);
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
