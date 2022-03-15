package main;

import java.awt.Color;

import fr.umlv.zen5.Application;
import fr.umlv.zen5.ApplicationContext;
import fr.umlv.zen5.Event;
import map.Map;

public class Main {
	
	public static void doKeyActionAndDraw(ApplicationContext context, Event event, Map m) {
		doKeyAction(context, event);
		m.draw(context);
	}
	
	public static void doKeyAction(ApplicationContext context, Event event) {
		switch (event.getKey()) {
		case SPACE -> {
			System.out.println("Fin du jeu");
			context.exit(0);
			throw new AssertionError("ne devrait pas arriver");
		}
		default -> System.out.println("Touche inactive : " + event.getKey());
		}
	}
	
	public void gameLoop(ApplicationContext context) {
		Map m = new Map();
		m.generateMap();
		m.draw(context);
		while (true) {
			System.out.println("start");
			code(context, m);
			System.out.println("end");
		}
	}
	
	public void code(ApplicationContext context, Map m) {
		
		Event event = context.pollOrWaitEvent(200);
		
		if (event == null) { // no event
			return;
		}
		
		switch (event.getAction()) {
		case KEY_PRESSED:
			doKeyActionAndDraw(context, event, m);
			break;
			
		default:
			System.out.println("bravo nidal");
		}
	}
	
	public static void main(String[] args) {
		
		Main controller = new Main();
		Application.run(Color.blue, controller::gameLoop);
	}
}
