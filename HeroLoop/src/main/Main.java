package main;

import map.Map;

public class Main {
	public static void main(String[] args) {
		Map m = new Map();
		
		m.generateMap();
		
		System.out.println(m);
	}
}
