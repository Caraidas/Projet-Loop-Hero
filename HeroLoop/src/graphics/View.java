package graphics;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;
import java.awt.geom.Point2D;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;

import javax.imageio.ImageIO;

import collectable.Card;
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
		Path path = Path.of("ressources/Entities-Sprite/Player.png");
		int column = gameData.map().loop().get(player.position()).column();
		int line = gameData.map().loop().get(player.position()).line();
		drawImage(graphics, (int)((line * caseSize) + (caseSize * 0.25)), (int)((column * caseSize) + (caseSize * 1.25)), path, 30, 30);
	}
	
	private  void drawImage(Graphics2D graphics, int i, int j, Path path, int sizeW, int sizeH) {
		try (InputStream in = Files.newInputStream(path)) {
			BufferedImage img = ImageIO.read(in);
			
			if (sizeW == -1 && sizeW == -1) {
				sizeW = img.getWidth();
				sizeH = img.getHeight();
			}
			
			AffineTransformOp scaling = new AffineTransformOp(AffineTransform
					.getScaleInstance(sizeW / (double) img.getWidth(), sizeH / (double) img.getHeight()),
					AffineTransformOp.TYPE_BILINEAR);
			graphics.drawImage(img, scaling, i, j);
		} catch (IOException e) {
			throw new RuntimeException("problème d'affichage : " + path.getFileName());
		}
	}
	
	public  void drawMonsters(Graphics2D graphics, int i, int j, Cell c) {		
		int newI;
		int newJ;
		ArrayList<Monster> monsters = ((RoadCell)(c)).getEntities();
		for (int index = 0; index < monsters.size(); index++) {
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
			drawImage(graphics, newI, newJ, Path.of(monsters.get(index).getSprite()), (int)(caseSize * 0.25), (int)(caseSize * 0.25));
		}
	}
	
	public void drawCell(Graphics2D graphics, int i, int j, Cell c) {
		
		drawImage(graphics, i, j, Path.of(c.sprite()), caseSize, caseSize);
		if (c instanceof RoadCell) {
			drawMonsters(graphics, i, j, c);
		}
	}
	
	public  void drawMap(Graphics2D graphics) {
		for (int i = 0; i < gameData.map().lines(); i++) {
			for (int j = 0; j < gameData.map().columns(); j++) {
				drawCell(graphics, j * caseSize, i * caseSize + caseSize, gameData.map().getCase(i, j));
			}
		}
	}
	
	public void drawHudRight(Graphics2D graphics) {
		int HudWidth = (int)(width - (21 * caseSize));
		
		graphics.setColor(Color.gray);
		graphics.fillRect((21 * caseSize), 0, HudWidth, (int) height);
		
		
		String playerHp = player.getHp() + " / " + ((int)player.getHpMax());
		graphics.setColor(Color.red);
		graphics.setFont (new Font("TimesRoman", Font.BOLD, 40));
		graphics.drawString(playerHp, (21 * caseSize) + 30, 150);
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
			Path path = Path.of(player.deck().getCard(i).sprite());
			if (i == gameData.selectedCard()) {
				drawImage(graphics, (int)(i * caseSize * 1.62), (int)(12 * caseSize), path, -1, -1);
			} else {
				drawImage(graphics, (int)(i * caseSize * 1.62), (int)(12 * caseSize + caseSize * 1.25), path, -1, -1);
			}	
		}
	}
	
	public boolean deposedCard(Point2D.Float location) {
		return location.x >= 0 * caseSize && location.x < 21 * caseSize && location.y >= caseSize 
				&& location.y < 12 * caseSize + caseSize && gameData.selectedCard() != -1;
	}
	
	public void drawScreen() {
		
		context.renderFrame(graphics -> {
			drawMap(graphics);
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
