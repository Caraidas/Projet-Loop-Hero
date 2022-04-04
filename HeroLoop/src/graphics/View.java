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
		ImageConstructor playerImage = new ImageConstructor(Path.of("ressources/Entities-Sprite/Player.png"), (int)(caseSize * 0.5), (int)(caseSize * 0.5));
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
			ImageConstructor monsterImage = new ImageConstructor(Path.of(monsters.get(index).getSprite()), (int)(caseSize * 0.25), (int)(caseSize * 0.25)) ;
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
		graphics.drawString(playerHp, (21 * caseSize) + 30, 150);
		
		graphics.setColor(Color.black);
		graphics.setFont (new Font("TimesRoman", Font.BOLD, 20));
		
		int i = 1;
		for (String s : player.ressources().keySet()) {
			graphics.drawString(s + " : " + player.ressources().get(s), (21 * caseSize) + 30, (int)(150 + (caseSize * 0.5) * i));
			i++;
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
	}
	
	public void drawHud(Graphics2D graphics) {
		drawHudRight(graphics);
		drawHudTop(graphics);
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
	
	public void drawScreen() {
		
		context.renderFrame(graphics -> {
			drawMap(graphics);
			drawCliquableCells(graphics);
			drawHud(graphics);
			drawPlayer(graphics);
			drawDeck(graphics);
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
	
	public int toCardPos(Point2D.Float location) {
		return (int)(location.x / (caseSize * 1.62));
		
	}
	
	public GridPosition toCellPos(Point2D.Float location) {
		return new GridPosition((int)(location.x / caseSize), (int)(location.y / caseSize) - 1);
	}
}
