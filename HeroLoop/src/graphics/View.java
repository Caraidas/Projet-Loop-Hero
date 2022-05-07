package graphics;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.geom.Point2D;
import java.nio.file.Path;
import java.util.ArrayList;
import collectable.CardState;
import data.GameData;
import data.GridPosition;
import entities.Entity;
import entities.Monster;
import entities.Player;
import fr.umlv.zen5.ApplicationContext;
import fr.umlv.zen5.ScreenInfo;
import map.Cell;
import map.RoadCell;
import time.TimeData;

public class View {
	private final Player player;
	private final TimeData timeData;
	private final GameData gameData;
	private ApplicationContext context;
	
	double width = 0.0;
	double height = 0.0;
	int caseSize = 0;
	
	public View(Player player, TimeData timeData, GameData gameData) {
		this.player = player;
		this.timeData = timeData;
		this.gameData = gameData;
	}
	
	public void initContext(ApplicationContext context) {
		this.context = context;
		this.width = getScreenWidth();
		this.height = getScreenHeight();
		this.caseSize = (int) ((60 * width) / 1536);
	}
	
	private  float getScreenWidth() {
		ScreenInfo screenInfo = context.getScreenInfo();
		return screenInfo.getWidth();
	}
	
	private  float getScreenHeight() {
		ScreenInfo screenInfo = context.getScreenInfo();
		return screenInfo.getHeight();
	}
	
	public  void drawPlayer(Graphics2D graphics) {
		ImageConstructor playerImage = new ImageConstructor(Path.of("ressources/Entities-Sprite/outOfBattle/Player.png"), (int)(caseSize * 0.5), (int)(caseSize * 0.5));
		int column = gameData.map().loop().get(player.position()).column();
		int line = gameData.map().loop().get(player.position()).line();
		drawImage(graphics, (int)((line * caseSize) + (caseSize * 0.25)), (int)((column * caseSize) + (caseSize * 1.25)), playerImage);
	}
	
	private  void drawImage(Graphics2D graphics, int i, int j, ImageConstructor imgConstructor) {
		graphics.drawImage(imgConstructor.img(), imgConstructor.scaling(), i, j);
	}
	
	public  void drawMonsters(Graphics2D graphics, int i, int j, Cell c) {		
		int newI;
		int newJ;
		ArrayList<Monster> monsters = ((RoadCell)(c)).getEntities();
		for (int index = 0; index < monsters.size(); index++) {
			ImageConstructor monsterImage = new ImageConstructor(Path.of("ressources/Entities-Sprite/outOfBattle/monsters/" + monsters.get(index).getSprite()), (int)(caseSize * 0.25), (int)(caseSize * 0.25)) ;
			if (index % 2 != 0) {
				newI = (int)(i + (caseSize * 0.75)) - 5;
			} else {
				newI = i + 5;
			}
			
			if (index <= 1) { 
				newJ = j + 5;
			} else {
				newJ = (int)(j + (caseSize * 0.75)) - 5;
			}
			drawImage(graphics, newI, newJ, monsterImage);
		}
	}
	
	public void drawCell(Graphics2D graphics, int i, int j, Cell c) {
		
		if (c.card() == null) {
			graphics.setColor(Color.BLACK);
			graphics.fillRect(i, j, caseSize, caseSize);
		} else {
			ImageConstructor cellImage = new ImageConstructor(Path.of("ressources/Map-Sprite/Card-Sprites/" + c.sprite()), caseSize, caseSize);
			drawImage(graphics, i, j, cellImage);
		}
		
		if (c instanceof RoadCell) {
			if (c.card() == null) {
				ImageConstructor cellImage = new ImageConstructor(Path.of("ressources/Map-Sprite/Card-Sprites/horizontal-road.png"), caseSize, caseSize);
				drawImage(graphics, i, j, cellImage);
			} else {
				ImageConstructor cellImage = new ImageConstructor(Path.of("ressources/Map-Sprite/Card-Sprites/" + c.sprite()), caseSize, caseSize);
				drawImage(graphics, i, j, cellImage);
			}
			
			drawMonsters(graphics, i, j, c);
			
		}
	}
	
	public  void drawMap(Graphics2D graphics) {
		for (int i = 0; i < gameData.map().lines(); i++) {
			for (int j = 0; j < gameData.map().columns(); j++) {
				drawCell(graphics, j * caseSize, i * caseSize + caseSize, gameData.map().getCell(i, j));
			}
		}
	}
	
	public void drawHudRight(Graphics2D graphics) {
		int HudWidth = (int)(width - (21 * caseSize));
		
		graphics.setColor(Color.gray);
		graphics.fillRect((21 * caseSize), 0, HudWidth, (int) height);
		
		
		String playerHp = (int)(player.getHp()) + " / " + (int)(player.getHpMax());
		graphics.setColor(Color.red);
		graphics.setFont (new Font("TimesRoman", Font.BOLD, 40));
		graphics.drawString(playerHp, (21 * caseSize) + 60, 100);
		
		graphics.setColor(Color.black);
		graphics.setFont (new Font("TimesRoman", Font.BOLD, 20));
		
		String playerDamage = "damage : " + player.damageString();
		graphics.drawString(playerDamage, (21 * caseSize) + 60, 125);
		
		int i = 0;
		for (String stat : player.basicStats().keySet()) {
			if (!(stat.equals("hp") || stat.equals("hpMax"))) {
				i++;
				graphics.drawString(stat + " : " + (player.basicStats().get(stat)), (21 * caseSize) + 60, 125 + 25 * i);
			}
		}
		
		i = 1;
		for (String s : player.ressources().keySet()) {
			graphics.drawString(s + " : " + player.ressources().get(s), (21 * caseSize) + 30, (int)(550 + (caseSize * 0.5) * i));
			i++;
		}
		
		drawItems(graphics);
		drawInventory(graphics);
		
	}
	
	public void drawItems(Graphics2D graphics) {
		for (int i = 0; i < 3; i++) {
			for (int j = 0; j < 4; j++) {
				graphics.setColor(Color.DARK_GRAY);
				graphics.fillRect((21 * caseSize + (j * caseSize)) + 20, (int)(200 + (caseSize * i)), caseSize -1, caseSize -1);
				if (!(player.items().get((i * 4) + j) == null)) {
					ImageConstructor itemImage = new ImageConstructor(Path.of(player.items().get((i * 4) + j).sprite()), (double)(caseSize -1), (double)(caseSize -1));
					drawImage(graphics, (21 * caseSize + (j * caseSize)) + 20, (int)(200 + (caseSize * i)), itemImage);
				}
			}
		}
	}
	
	public void drawInventory(Graphics2D graphics) {
		for (int i = 0; i < 3; i++) {
			graphics.setColor(Color.DARK_GRAY);
			graphics.fillRect((21 * caseSize + (i * caseSize)) + 50, 400 + caseSize, caseSize -1, caseSize -1);
		}
		
		if (player.inventory().hasWeapon()) {
			ImageConstructor itemImage = new ImageConstructor(Path.of(player.inventory().weapon().sprite()), (double)(caseSize), (double)(caseSize));
			drawImage(graphics, (21 * caseSize + (0 * caseSize)) + 50, 400 + caseSize, itemImage);
		}
		
		if (player.inventory().hasArmor()) {
			ImageConstructor itemImage = new ImageConstructor(Path.of(player.inventory().armor().sprite()), (double)(caseSize), (double)(caseSize));
			drawImage(graphics, (21 * caseSize + (1 * caseSize)) + 50, 400 + caseSize, itemImage);
		}
		
		if (player.inventory().hasShield()) {
			ImageConstructor itemImage = new ImageConstructor(Path.of(player.inventory().shield().sprite()), (double)(caseSize), (double)(caseSize));
			drawImage(graphics, (21 * caseSize + (2 * caseSize)) + 50, 400 + caseSize, itemImage);
		}
	}
	
	public void drawHudTop(Graphics2D graphics) {
		graphics.setColor(Color.gray);
		graphics.fillRect(0, 0, (int) width, caseSize);
	}
	
	public void drawTimeBar(Graphics2D graphics) {
		graphics.setColor(Color.LIGHT_GRAY);
		graphics.fillRect(10, 10, 400, 20);
		graphics.setColor(Color.GREEN);
		graphics.fillRect(15, 15, (int)(390 * timeData.timeFraction()), 10);
		graphics.setFont(new Font("TimesRoman", Font.BOLD, 20));
		graphics.setColor(Color.BLACK);
		graphics.drawString(gameData.getLoopCount() + " loops", 420, 30);
		graphics.drawString("day " + timeData.dayCount(), 500, 30);
	}
	
	public void drawHud(Graphics2D graphics) {
		drawHudTop(graphics);
		drawHudRight(graphics);
		drawTimeBar(graphics);
	}
	
	public void drawDeck(Graphics2D graphics) {
		for (int i = 0; i < player.deckSize(); i++) {
			Path path = Path.of("ressources/Card-Sprite/" + player.deck().getCard(i).sprite());
			ImageConstructor cardImage = new ImageConstructor(path, -1, -1);
			if (i == gameData.selectedCard()) {
				drawImage(graphics, (int)(i * caseSize * 1.62), (int)(12 * caseSize), cardImage);
			} else {
				drawImage(graphics, (int)(i * caseSize * 1.62), (int)(12 * caseSize + caseSize * 1.25), cardImage);
			}	
		}
	}
	
	public boolean deposedCard(Point2D.Float location) {
		
		if (!(location.x >= 0 * caseSize && location.x < 21 * caseSize && location.y >= caseSize 
				&& location.y < 12 * caseSize + caseSize && gameData.selectedCard() != -1)) 
		{
			return false;
		}
		
		for (CardState c : player.selectedCard(gameData.selectedCard()).cardState()) {
			if (c != gameData.map().getCell(toCellPos(location)).acceptableCardState()) {
				return false;
			}
		}
		
		if (gameData.map().getCell(toCellPos(location)).card() != null || gameData.map().getCell(toCellPos(location))
				== gameData.map().getCell(gameData.map().loop().get(0))) {
			return false;
		}
		
		return true;
	}
	
	public void drawCliquableCells(Graphics2D graphics) {
		ImageConstructor selectable = new ImageConstructor(Path.of("ressources/Map-Sprite/selectable.png"), caseSize, caseSize);
		if (gameData.selectedCard() != -1) {
			for (int i = 0; i < gameData.map().lines(); i++) {
				for (int j = 0; j < gameData.map().columns(); j++) {
					if (player.deck().getCard(gameData.selectedCard()).contains(gameData.map().getCell(i, j).acceptableCardState()) && gameData.map().getCell(i, j).card() == null) {
						drawImage(graphics, j * caseSize, i * caseSize + caseSize, selectable);
					}
				}
			}
		}
	}
	
	public void drawBattle(Graphics2D graphics) { 
		graphics.setColor(Color.black);
		graphics.fillRect((int)(width / 5), (int)(height / 6), (int)(width / 2), (int)(height * 0.7));
		graphics.setColor(Color.gray);
		graphics.drawRect((int)(width / 5), (int)(height / 6), (int)(width / 2), (int)(height * 0.7));
		drawMonstersInBattle(graphics, (RoadCell)(gameData.map().getCell(gameData.map().getPlayerPos(player))));
	}
	
	public void drawMonstersInBattle(Graphics2D graphics, RoadCell c) {
		int i = 0;
		for (Monster m : c.getEntities()) {
			ImageConstructor mnstrImage = new ImageConstructor(Path.of("ressources/Entities-Sprite/InBattle/monsters/" + m.getSprite()), -0.15, -0.15);
			drawImageInBattle(graphics, i, mnstrImage); 
			drawMonsterStat(graphics, i, m);
			i++;
		}
		
		ImageConstructor pImage = new ImageConstructor(Path.of("ressources/Entities-Sprite/InBattle/" + player.getSprite()), -0.2, -0.2);
		drawImageInBattle(graphics, -1, pImage);
	}
	
	public void drawMonsterStat(Graphics2D graphics, int i, Monster m) {
		int index = 1;
		for (String stat : m.basicStats().keySet()) {
			if (stat != "hpMax") {
				if (stat == "hp")
				{
					String s = stat + " : " + m.basicStats().get(stat) + "/" + m.getHpMax();
					graphics.setColor(Color.red);
					graphics.setFont(new Font("TimesRoman", Font.BOLD, 20));
					graphics.drawString(s, (int)(3 * (width / 6)) + 2 * caseSize, (int)(i * 2 * caseSize + (height / 6) + caseSize) + caseSize);
				} else {
					String s = stat + " : " + m.basicStats().get(stat);
					graphics.setColor(Color.white);
					graphics.setFont(new Font("TimesRoman", Font.BOLD, 20));
					graphics.drawString(s, (int)(3 * (width / 6)) + 2 * caseSize, (int)(i * 2 * caseSize + (height / 6) + caseSize) + caseSize + index * 20);
					index++;
				}
				
			}
		}
		String s = "Strength : " + m.strength();
		graphics.setColor(Color.white);
		graphics.setFont(new Font("TimesRoman", Font.BOLD, 20));
		graphics.drawString(s, (int)(3 * (width / 6)) + 2 * caseSize, (int)(i * 2 * caseSize + (height / 6) + caseSize) + caseSize + index * 20);
		
	}
	
	public void drawImageInBattle(Graphics2D graphics, int i, ImageConstructor imgConstructor) {
		if (i != -1) {
			graphics.drawImage(imgConstructor.img(), imgConstructor.scaling(), (int)(3 * (width / 6)), (int)(i * 2 * caseSize + (height / 6) + caseSize));
		} else {
			graphics.drawImage(imgConstructor.img(), imgConstructor.scaling(), (int)(1.5* (width / 6)), (int)(height / 2));
		}
	}
	
	public void drawScreen() {
		
		context.renderFrame(graphics -> {
			if (gameData.inBattle()) {
				drawBattle(graphics);
				drawHud(graphics);
			} else {
				drawMap(graphics);
				drawCliquableCells(graphics);
				drawHud(graphics);
				drawPlayer(graphics);
				drawDeck(graphics);
			}
			
		});
	}
	
	public void blackScreen() {
		context.renderFrame(graphics -> {
			graphics.setColor(Color.black);
			graphics.fillRect(0, (int)(12 * caseSize + caseSize), (int)(13 * caseSize * 1.62), (int)height);
		});
		
	}
	
	public boolean clickedOnCards(Point2D.Float location) {
		return location.x >= 0 && location.x < (int)(player.deckSize() * caseSize * 1.62) && location.y >= 
				(int)(12 * caseSize + caseSize * 1.25) && location.y < height;
	}
	
	public boolean clickedOnItems(Point2D.Float location) {
		return (location.x >= (21 * caseSize + (0 * caseSize)) + 20 && location.x < width && location.y >= 
				(int)(200 + (caseSize * 0)) && location.y < (int)(200 + (caseSize * 3))) && timeData.stopped();
	}
	
	public int toCardPos(Point2D.Float location) {
		return (int)(location.x / (caseSize * 1.62));	
	}
	
	public int toItemPos(Point2D.Float location) {
		int x = (int)(location.x - ((21 * caseSize) + 20)) / (caseSize);
		int y =(int)(location.y - (int)(200)) / (caseSize);
		return (int)(x + (y * 4));
	}
	
	public GridPosition toCellPos(Point2D.Float location) {
		return new GridPosition((int)(location.x / caseSize), (int)(location.y / caseSize) - 1);
	}
}
