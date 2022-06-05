package save;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import data.GameData;
import entities.Player;
import time.TimeData;

public class Save {
	
	
	public void write(GameData g, TimeData t, Player p) throws IOException {
		File fichier =  new File("saves/gameData.txt");
		ObjectOutputStream oos =  new ObjectOutputStream(new FileOutputStream(fichier));
		oos.writeObject(g);
		
		oos.close();
		 
		fichier =  new File("saves/timeData.txt");
		oos =  new ObjectOutputStream(new FileOutputStream(fichier));
		oos.writeObject(t);
		
		oos.close();
		
		fichier =  new File("saves/player.txt");
		oos =  new ObjectOutputStream(new FileOutputStream(fichier));
		oos.writeObject(p);
		
		oos.close();
	}
	
	public Object read(String s) throws FileNotFoundException, IOException, ClassNotFoundException {
		File fichier =  new File(s);
		ObjectInputStream ois =  new ObjectInputStream(new FileInputStream(fichier));
		Object o = ois.readObject();
		
		ois.close();
		return o;
	}
	
	
	
}
